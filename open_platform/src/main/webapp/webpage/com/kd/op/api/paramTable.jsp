<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/webpage/common/resource.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>能力参数表格页面</title>
    <script src=<%= request.getContextPath() + "/plug-in/bootstraptable-treeview.js"%>></script>
    <style>
        .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
            margin-bottom:2px;
        }
        .tree-icon{
            color:#00CAAB;
        }
        .paramInput,.paramDesc,.defaultValue{
            width:100%;
            border:0px;
            background-color: transparent;
            border-left:1px solid #ccc;
        }
        #departs table>td{
            padding-top: 0px;
            padding-bottom: 0px;
            height: 100%;
            vertical-align: middle;
        }
        #departs input[disabled]{
            border-left:0px;
        }
        .param-select{
            width: 100%;
            border: 0;
            background-color: transparent;
        }
        .action-td{
            text-align:center;
        }
        #departs tbody>tr{
            background:#ffffff;
        }
    </style>
</head>
<body>
<input id="paramType" type="hidden" value="${paramType}"/>
<input id="apiId" type="hidden" value="${apiId}"/>
<input id="loadType" type="hidden" value="${loadType}"/>
<input id="ifId" type="hidden" value="${ifId}"/>
<div class="search-div">
    <c:if test="${loadType != 3 && loadType != 4}">
        <form class="" class="form-horizontal">
            <div class="col-xs-1 pull-left no-padding">
                <button type="button" class="btn btn-success formnew" id="add-btn">
                    <span class="glyphicon glyphicon-plus"></span>新建
                </button>
            </div>
        </form>
    </c:if>

</div>
<table id="departs"></table>
<script>
    var dataTypes = ${dataTypes};
    var loadType = null;
    var paramType = null;
    var apiId = null;
    $(document).ready(function(){
        paramType = $("#paramType").val();
        apiId = $("#apiId").val();
        loadType = $("#loadType").val();
        initTable();
        //初始化新建按钮
        $("#add-btn").bind("click",function(){
            var allData = loadNowData($("#departs").find("tbody"));
            allData.push({id:timeId(),parentId:null,isLeaf:true,hidden:false});
            refreshData(allData);
        });
        initEvent();
    });
    function initTable(){
        var visible = true;
        var loadedData = [];
        if(apiId != null && apiId != undefined && loadType != null && loadType != undefined && (loadType == 2 || loadType == 3 || loadType ==4)
            && paramType != null && paramType != undefined){
            $.ajax({
                type:"post",
                dataType:"json",
                data:{
                    apiId:apiId,
                    paramType:paramType
                },
                async:false,
                url:"apiInfoController.do?loadParamsByApiAndType",
                success:function(data){
                    if(loadType == 3){
                        //从订购页面进来的，如果某个参数不可见，就看不到该参数
                        //控制是否可見，1可見
                        for(var item in data){
                            if(data[item].paramVisible == 1){
                                //把可见的参数放进去
                                loadedData.push(data[item]);
                            }
                        }
                    }else{
                        loadedData = data;
                    }
                }
            });
        }
        if(loadType == 3 || loadType ==4){
            visible = false;
        }
        $('#departs').bootstrapTable({
            data:loadedData,
            class: 'table table-hover table-bordered',
            contentType: 'application/x-www-form-urlencoded; charset=utf-8',
            sidePagination: 'server',
            pagination: false,
            treeView: true,
            treeId: "id",
            treeField: "paramName",
            striped:false,
            rowAttributes: function (row, index) {
                return {
                    xx:index
                };
            },
            columns: [{
                field: 'action',
                title: '操作',
                width:"5%",
                visible:visible,
                class:"action-td",
                formatter:function(value,row,index){
                    var dataType = row["dataType"];
                    var dataTypeName = null;
                    var display = "none";
                    $.each(dataTypes,function(i,type){
                        if(type.id == dataType){
                            dataTypeName = type.name;

                        }
                    });
                    if(dataTypeName != null){
                        dataTypeName = dataTypeName.toLowerCase();
                        if(dataTypeName.indexOf("object") != -1){
                            display = "inline-block";
                        }
                    }
                    return "<button class='btn btn-xs btn-success add-child' style='margin-right:5px;display:" + display + "' title='添加子节点' onclick='addChildren(\"" + row.id + "\",this)'><span class='glyphicon glyphicon-plus'></span></button>"+
                        "<button class='btn btn-xs btn-danger delete-self' style='margin-right:5px' title='删除当前节点和子节点' onclick='deleteParam(\"" + row.id + "\")'><span class='glyphicon glyphicon-trash'></span></button>"+
                        "<button class='btn btn-xs btn-primary add-model' style='margin-right:5px;display:" + display + "' title='选择模型' onclick='addModel(\"" + row.id + "\")'>模型</button>";
                }

            },{
                field: 'paramName',
                title: '参数名称',
                width:"10%",
                formatter:function(value,row,index){
                    if(value == undefined){
                        value = "";
                    }
                    var parentId = row.parentId;
                    if(parentId == undefined){
                        parentId = "";
                    }
                    return "<input name='paramName' class='paramInput' value='" + value + "'/>" +
                        "<input name='parentId' type='hidden' value='" + parentId + "'/>"+
                        "<input name='id' type='hidden' value='" + row.id + "'/>";
                }
            },{
                field: 'parentId',
                visible:false,
            },
                {
                    field: 'paramDesc',
                    title: '参数描述',
                    width:"10%",
                    formatter:function(value,row,index){
                        if(value == undefined){
                            value = "";
                        }
                        return "<input name='paramDesc' class='paramDesc' value='" + value + "'/>";

                    }
                },
                {
                    field: 'paramVisible',
                    title: '是否可见',
                    width:"5%",
                    formatter:function(value,row,index){
                        if(value == 1 || value == undefined){
                            return "<select name='paramVisible' class='param-select'><option value=1 selected>是</option><option value=2>否</option></select>";
                        }else{
                            return "<select name='paramVisible' class='param-select'><option value=1>是</option><option value=2 selected>否</option></select>";
                        }

                    }
                },
               /* {
                    field: 'paramEncrypt',
                    title: '是否加密',
                    width:"5%",
                    formatter:function(value,row,index){
                        if(value == 0 || value == undefined){
                            return "<select name='paramEncrypt' class='param-select'><option value=0 selected>否</option><option value=1>是</option></select>";
                        }else{
                            return "<select name='paramEncrypt' class='param-select'><option value=0>否</option><option value=1 selected>是</option></select>";
                        }

                    }
                },*/
                {
                    field: 'isSource',
                    title: '是否资源控制',
                    width:"5%",
                    formatter:function(value,row,index){
                        if(value == 0 || value == undefined){
                            return "<select name='isSource' class='param-select'><option value=0 selected>否</option><option value=1>是</option></select>";
                        }else{
                            return "<select name='isSource' class='param-select'><option value=0>否</option><option value=1 selected>是</option></select>";
                        }
                    }
                },

                {
                    field: 'dataType',
                    title: '数据类型',
                    width:"5%",
                    formatter:function(value,row,index){
                        //拼接参数类型下拉框
                        var html = "<select name='dataType' class='param-select'>";
                        $.each(dataTypes,function(i,type){
                            if(value != null && value != undefined && value == type.id){
                                html += "<option selected value=" + type.id + ">" + type.name + "</option>";
                            }else{
                                html += "<option value=" + type.id + ">" + type.name + "</option>";
                            }
                        })
                        html += "</select>";
                        return html;

                    }
                },{
                    field: 'defaultValue',
                    title: '参数默认值',
                    width:"10%",
                    formatter:function(value,row,index){
                        if(value == undefined){
                            value = "";
                        }
                        return "<input name='defaultValue' class='defaultValue' value='" + value + "'/>";
                    }
                }
            ],
            onClickRow:function(row,$element,field){
                /*$element.parent().find("tr").removeClass("click-tr-bg");
                $element.parent().find("input[type='checkbox']").prop("checked",false);
                $element.addClass("click-tr-bg");
                $element.find("input[type='checkbox']").prop("checked",true);
                $("#departs").bootstrapTable("uncheckAll");
                $("#departs").bootstrapTable("checkBy", {field:"id", values:[row["id"]]})*/
            },
        });
        if(loadType == 3 || loadType ==4){
            $('#departs').find("input").attr("disabled",true);
            $('#departs').find("select").attr("disabled",true);
        }
    }
    function addChildren(id,btn){
        // var tableId = $(btn).parent().parent().parent().parent().attr("id");
        var allData = loadNowData($("#departs").find("tbody"));
        allData.push({id:timeId(),parentId:id,isLeaf:true,hidden:false});
        refreshData(allData);
    }

    //初始化表格的事件
    function initEvent(){
        //初始化数据类型的选择事件
        $("select[name='dataType']").change(function(){
            var dataTypeName = $(this).find("option:selected").text().trim().toLowerCase();
            if(dataTypeName.indexOf("object") != -1){
                $(this).parent().parent().find(".add-child,.add-model").css("display","inline-block");
            }else{
                //如果已经包含了子节点，就进行提示
                //获取当前行的id
                var id = $(this).parent().parent().find("input[name='id']").val();
                if(getFirstLevelChildren(id).length > 0){
                    parent.alertMessage("当前参数包含了子节点，如果要修改参数类型，请先删除子节点！");
                    $(this).val("3");
                }
                $(this).parent().parent().find(".add-child,.add-model").css("display","none");
            }
        })
    }
    //获取当前页面的所有参数，用于iframe外调用
    function loadAllData(){
        return loadNowData($('#departs').find("tbody"));
    }

    function refreshData(allData){
        $('#departs').bootstrapTable("load",allData);
        initEvent();
    }

    /**
     * 删除当前参数，包括改参数的子节点
     * @param id 参数对应的id
     */
    function deleteParam(id){
        var allData = loadAllData();
        $.each(allData,function(i,row){
            if(row.id == id){
                allData=$.grep(allData,function(n,j){
                    return i!=j;
                });
                allData = removeChildren(allData,id);
            }
        });
        refreshData(allData);
    }

    /**
     * 删除parentId对应的所有子节点
     * @param allData 所有行数据
     * @param parentId 父节点id
     * @returns {*}
     */
    function removeChildren(allData,parentId){
        var parentLevel = null;
        var newData = new Array();
        $.each(allData,function(i,row){
            if(parentLevel == null || row.level <= parentLevel){
                if(row.id == parentId){
                    parentLevel = row.level;
                }
                newData.push(row);
            }
        });
        return newData;
    }

    /**
     * 打开选择模型的model
     * @param id
     */
    function addModel(id){
        //获取当前iframe的id
        var ifId = $("#ifId").val();
        parent.selectParamMode(id,ifId);
    }

    /**
     * 根据id获取当前行数据对象
     * @param id
     */
    function getRowById(id){
        var row = null;
        var allData = loadAllData();
        for(var i = 0;i < allData.length;i++){
            if(allData[i]["id"] == id){
                row = allData[i];
                break;
            }
        }
        return row;
    }

    /**
     * 获取参数的第一级子节点
     * @param id 当前参数id
     */
    function getFirstLevelChildren(id){
        var children = new Array();
        var allData = loadAllData();
        $.each(allData,function(i,row){
            if(row.parentId == id){
                children.push(row);
            }
        });
        return children;
    }

    /**
     * 为参数填充模型数据
     * @param id 参数行id
     * @param modelId 模型id
     */
    function fillModelParam(id,modelId){
        $.ajax({
            type:"post",
            dataType:"json",
            data:{
                id:modelId
            },
            url:"apiParamModelController.do?getModelById",
            success:function(data){
                if(data != null){
                    //删除当前参数的子节点
                    var allData = loadAllData();
                    allData = removeChildren(allData,id);
                    //将当前参数的信息变更为模型的数据
                    allData = allData.map(function(obj,index) {
                        var rObj = {};
                        if(obj.id == id){
                            rObj.paramName == data.modelName;
                            rObj.paramDesc = data.modelDesc;
                        }
                        for(i in obj){
                            rObj[i] = obj[i];
                        }
                        if(obj.id == id){
                            rObj.paramName = data.modelName;
                            rObj.paramDesc = data.modelDesc;
                        }
                        return rObj;
                    });
                    $.each(data.paramModelDetails,function(i,param){
                        var newChild = {};
                        newChild["paramName"] = param.paramName;
                        newChild["id"] = param.id;
                        newChild["paramDesc"] = param.paramDesc;
                        newChild["defaultValue"] = param.defaultValue;
                        newChild["dataType"] = param.dataType;
                        newChild["parentId"] = id;
                        allData.push(newChild);
                    });
                    refreshData(allData);
                }else{
                    slowNotify("获取模型数据失败","danger");
                }
            }
        })
    }
</script>
</body>
</html>