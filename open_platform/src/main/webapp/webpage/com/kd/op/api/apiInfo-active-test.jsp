<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>服务测试</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
    <script src="${webRoot}/plug-in/tools/curdtools.js"></script>
    <script src=<%= request.getContextPath() + "/plug-in/api-test-treeview.js"%>></script>
    <style>
        .childTr{
            margin-left:20px;
        }
        .btn-div a{
            border-color:#ccc;
            border-radius:3px;
        }
        #step-1,#step-2,#step-3,#step-4,#step-5{
            padding-top:10px;
        }
        .table-div{
            overflow:auto;
            min-height: 200px;
            max-height: 300px;
        }
        .nodisplay{
           display:none;
        }
        .tree-icon{
            color:#00CAAB;
        }
    </style>
</head>
<body>
<div style="index:9999">
    <div class="container" style="width:100%;overflow-x:hidden;">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="table-responsive" style="max-height:300px;overflow: auto">
                    <div style="margin-bottom:0;font-size: 18px">请求头参数</div>
                    <table id="requestHeaderInitTestList"></table>
                    <div style="margin-top:10px;font-size: 18px">请求参数</div>
                    <table id="requestInitTestList"></table>

                </div><br>



                <%--<div class="bt-item col-md-12 col-sm-12">--%>
                    <%--<div class="row">--%>
                        <%--<div class="col-md-2 col-sm-2 col-xs-2 bt-label">--%>
                            <%--请求地址：--%>
                        <%--</div>--%>
                        <%--<div class="col-md-10 col-sm-10 col-xs-10 bt-content">--%>
                            <%--<input id="address" name="address" value = "${apiInfoPage.reqAddrHsf}" type="text" class="form-control" ignore="ignore"  readonly="readonly" />--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <div class="bt-item col-md-12 col-sm-12">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            测试结果：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <textarea rows="10"  id= "output" name="output" class="form-control input-sm" readonly="readonly"></textarea>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var dataTypes = ${dataTypes};
    var selects = $("#apiInfoList").bootstrapTable("getSelections");
    var id = selects[0].id;
    $(function() {
        initTest();
    })

    $('#btn_sub').click(function(){
        debugger;
        var params = {};
        var allRequestData = loadRequestData();
        var allRequestHeaderData = loadRequestHeaderData();
        var rootNodes = [];
        $.each(allRequestData,function(i,row){
            if(row.parentId == null){
                rootNodes.push(row);
            }
        });
        $.each(allRequestHeaderData,function(i,row){
            if(row.parentId == null){
                rootNodes.push(row);
            }
        });
        getPara(rootNodes,params);
        // getRequstHeaderPara(rootNodes,params);
        // params["appId"] = "WebTest";
        params["apiId"] = id;
        var paramString = JSON.stringify(params);
        // var url=document.getElementById("address").value;
        $.post(
            "apiInfoController.do?activeDoTest&arg="+paramString,
            function (data) {
                $("#output").text(JSON.stringify(data));
            },
            'json'
        );
//         $.ajax({
//             type: "post",
// //            url:"http://10.112.206.4:8088/platform/openapi/haf_api_IDataServer_getData?orgNo=1111&orgType=4&token=22",
//             url:"apiInfoController.do?activeDoTest&data="+paramString,
//             dataType: "jsonp",
//             jsonp:'callback',
//             // data:{"data":paramString},
//             jsonpCallback:'jsonpCallback',
//             contentType:"application/json",
//             success: function (data) {
//                 $("#output").text(JSON.stringify(data));
//             }
//         });
    })

    function getPara(nodes,params) {
        $.each(nodes,function(i,row){
            var children = getFirstLevelChildren(loadRequestData(),row.id);
            if(children.length > 0){
                params[row.paramName] = {};
                getPara(children,params[row.paramName]);
            }else{
                params[row.paramName] = row.defaultValue;
            }
        });
    }


    function loadRequestData(){
        return loadNowData($("#requestInitTestList").find("tbody"));
    }
    function loadRequestHeaderData(){
        return loadNowData($("#requestHeaderInitTestList").find("tbody"));
    }

    function getFirstLevelChildren(allRequestData,id){
        var children = new Array();
        $.each(allRequestData,function(i,row){
            if(row.parentId == id){
                children.push(row);
            }
        });
        return children;
    }
    //加载参数列表
    function initTest() {
        // var selects = $("#apiInfoList").bootstrapTable("getSelections");
        // var id = selects[0].id;
        var loadedRequestHeaderData = [];
        var loadedRequestData = [];
        $.ajax({
            type:"post",
            dataType:"json",
            data:{
                apiId:id,
                paramType:0
            },
            async:false,
            url:"apiInfoController.do?loadParamsByApiAndType",
            success:function(data){
                loadedRequestData = data;
            }
        });
        $.each(loadedRequestData,function(i,row){
            if(getFirstLevelChildren(loadedRequestData,row.id).length > 0){
                row.hidden = false;
                row.isLeaf = false;
            }else{
                row.isLeaf = true;
            }
        });
        //获取头参数信息
        $('#requestInitTestList').bootstrapTable({
            data : loadedRequestData,
            method : 'post', //请求方式（*）
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
            columns : [
                {
                    field: 'paramName',
                    title: '参数名称',
                    width:"10%",
                },{
                    field: 'parentId',
                    visible:false,
                },
                {
                    field: 'paramDesc',
                    title: '参数描述',
                    width:"10%",
                },
                {
                    field: 'paramVisible',
                    title: '是否可见',
                    width:"5%",
                    formatter:function(value,row,index){
                        if(value == 1 || value == undefined){
                            return "是";
                        }else{
                            return "否";
                        }
                    }
                },{
                    field: 'dataType',
                    title: '数据类型',
                    width:"5%",
                    formatter:function(value,row,index){
                        //拼接参数类型下拉框
                        var dataType = value;
                        $.each(dataTypes,function(i,type){
                            if(value != null && value != undefined && value == type.id){
                                dataType = type.name;
                            }
                        })
                        return dataType;
                    }
                },{
                    field: 'defaultValue',
                    title: '参数默认值',
                    width:"10%",
                    formatter:function(value,row,index){
                        if(value == undefined){
                            value = "";
                        }
                        var parentId = row.parentId;
                        if(parentId == undefined){
                            parentId = "";
                        }
                        var paramName = row.paramName;
                        if(paramName == undefined){
                            paramName = "";
                        }
                        return "<input name='defaultValue' class='defaultValue' value='" + value + "'/>"+
                            "<input name='parentId' type='hidden' value='" + parentId + "'/>"+
                            "<input name='paramName' type='hidden' value='" + paramName + "'/>"+
                            "<input name='isLeaf' type='hidden' value='" + row.isLeaf + "'/>"+
                            "<input name='id' type='hidden' value='" + row.id + "'/>";
                    }
                }],
        });
        //获取请求头参数信息
        $.ajax({
            type:"post",
            dataType:"json",
            data:{
                apiId:id,
                paramType:2
            },
            async:false,
            url:"apiInfoController.do?loadParamsByApiAndType",
            success:function(data){
                loadedRequestHeaderData = data;
            }
        });

        $('#requestHeaderInitTestList').bootstrapTable({
            data : loadedRequestHeaderData,
            method : 'post', //请求方式（*）
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
            columns : [
                {
                    field: 'paramName',
                    title: '参数名称',
                    width:"10%",
                },{
                    field: 'parentId',
                    visible:false,
                },
                {
                    field: 'paramDesc',
                    title: '参数描述',
                    width:"10%",
                },
                {
                    field: 'paramVisible',
                    title: '是否可见',
                    width:"5%",
                    formatter:function(value,row,index){
                        if(value == 1 || value == undefined){
                            return "是";
                        }else{
                            return "否";
                        }
                    }
                },{
                    field: 'dataType',
                    title: '数据类型',
                    width:"5%",
                    formatter:function(value,row,index){
                        //拼接参数类型下拉框
                        var dataType = value;
                        $.each(dataTypes,function(i,type){
                            if(value != null && value != undefined && value == type.id){
                                dataType = type.name;
                            }
                        })
                        return dataType;
                    }
                },{
                    field: 'defaultValue',
                    title: '参数默认值',
                    width:"10%",
                    formatter:function(value,row,index){
                        if(value == undefined){
                            value = "";
                        }
                        var parentId = row.parentId;
                        if(parentId == undefined){
                            parentId = "";
                        }
                        var paramName = row.paramName;
                        if(paramName == undefined){
                            paramName = "";
                        }
                        return "<input name='defaultValue' class='defaultValue' value='" + value + "'/>"+
                            "<input name='parentId' type='hidden' value='" + parentId + "'/>"+
                            "<input name='paramName' type='hidden' value='" + paramName + "'/>"+
                            "<input name='isLeaf' type='hidden' value='" + row.isLeaf + "'/>"+
                            "<input name='id' type='hidden' value='" + row.id + "'/>";
                    }
                }],
        });
    }
</script>
</body>
</html>
