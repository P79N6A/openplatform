<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>接口场景表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
</head>
<body>
<div class="panel-body" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="apiSceneForm" onkeydown="if(event.keyCode==13){reloadApiSceneTable();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label for="sceneName">场景名称：</label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" class="form-control input-sm" id="sceneName" name="sceneName"/>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-3 col-md-2">
                                <label >审核状态：</label>
                                <t:dictSelect field="sceneAuditStatus"   type="select" hasLabel="false" title="" extendJson="{class:'form-control input-sm'}"  typeGroupCode="auditSts" ></t:dictSelect>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="input-group col-md-12" style="margin-top: 20px">
                                    <a class='btn btn-success' onclick="searchList();"><span
                                            class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>&nbsp
                                    <a class='btn btn-warning' onclick="searchRest();"><span
                                            class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
    <div id="toolbar">
        <button id="btn_add" class="btn btn-primary btn-sm">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 添加
        </button>
        <button id="btn_edit" class="btn btn-success btn-sm">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑
        </button>
        <button id="btn_look" class="btn btn-info btn-sm">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
        </button>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="apiSceneList"></table>
    </div>
</div>
<script type="text/javascript">
    $(".laydate-datetime").each(function(){
        var _this = this;
        laydate.render({
            elem: this,
            format: 'yyyy-MM-dd HH:mm:ss',
            type: 'datetime'
        });
    });
    $(".laydate-date").each(function(){
        var _this = this;
        laydate.render({
            elem: this
        });
    });
    $("#btn_add").bind("click",function(){
        var dialog = bootOpenNormal('场景添加','apiSceneController.do?goAdd','wide');

    })
    $("#btn_edit").bind("click",function(){
        var selects = $("#apiSceneList").bootstrapTable("getSelections");
        if(selects == null || selects.length == 0){
            quickNotify("请先选中一行","warning");
        }else if(selects.length > 1){
            quickNotify("只能选择一行","warning");
        }else{
            if(selects[0].sceneAuditStatus == 1 || selects[0].sceneAuditStatus == 2){
                quickNotify("只有暂存或者审核失败的场景才允许编辑","warning");
                return false;
            }
            var id = selects[0].id;
            var dialog = bootOpenNormal('场景编辑','apiSceneController.do?goUpdate&id=' + id,'wide');
        }
    })
    $("#btn_look").bind("click",function(){
        var selects = $("#apiSceneList").bootstrapTable("getSelections");
        if(selects == null || selects.length == 0){
            quickNotify("请先选中一行","warning");
        }else if(selects.length > 1){
            quickNotify("只能选择一行","warning");
        }else{
            var id = selects[0].id;
            var dialog = bootOpenLook('场景查看','apiSceneController.do?goLook&id=' + id,'wide');
        }
    })
    var apiSceneListdictsData = {};
    $(function() {
        initDictByCode(apiSceneListdictsData,"auditSts",function(){});

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#apiSceneList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function() {
                $('#btn_delete').prop('disabled',!$('#apiSceneList').bootstrapTable('getSelections').length);
                $('#btn_edit').prop('disabled',$('#apiSceneList').bootstrapTable('getSelections').length != 1);
                $('#btn_look').prop('disabled',$('#apiSceneList').bootstrapTable('getSelections').length != 1);
            });

    });

    var TableInit = function() {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function() {
            $('#apiSceneList').bootstrapTable({
                url : 'apiSceneController.do?datagrid', //请求后台的URL（*）
                method : 'post', //请求方式（*）
                contentType : "application/x-www-form-urlencoded",
                toolbar : '#toolbar', //工具按钮用哪个容器
                striped : true, //是否显示行间隔色
                cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination : true, //是否显示分页（*）
                queryParams : oTableInit.queryParams,//传递参数（*）
                sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber : 1, //初始化加载第一页，默认第一页
                pageSize : 10, //每页的记录行数（*）
                pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
                strictSearch : true,
                clickToSelect : true, //是否启用点击选中行
                height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", //每一行的唯一标识，一般为主键列
                sortName:'createTime',
                sortOrder:'desc',
                columns : [
                    {
                        radio : true,
                        align : 'center'
                    },
                    {
                        field : 'id',
                        title : 'id',
                        valign : 'middle',
                        width : 120,
                        visible:false,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        field : 'sceneName',
                        title : '场景名称',
                        valign : 'middle',
                        width : 100,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        field : 'sceneDes',
                        title : '场景描述',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        field : 'sceneStatus',
                        title : '场景状态',
                        valign : 'middle',
                        width : 80,
                        align : 'center',
                        switchable : true,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            if (row.sceneStatus == 0) {
                                return "不可用";
                            }else if (row.sceneStatus == 1){
                                return "可用";
                            }
                        }
                    },{
                        field : 'sceneAuditStatus',
                        title : '审核状态',
                        valign : 'middle',
                        width : 80,
                        align : 'center',
                        switchable : true,
                        formatter: function (value, row, index) {
                            //console.log(value)
                            return listDictFormat(value, apiSceneListdictsData.auditSts);
                        }
                    },
                    {
                        field : 'createName',
                        title : '创建人',
                        valign : 'middle',
                        width : 100,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        width: 100,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            var href = '';
                            if(row.sceneAuditStatus == 0){
                                href += "<button class='btn btn-xs btn-danger' style='margin-left:5px' title='删除' onclick='deleteApiScene(\"" + row.id + "\"," + row.sceneAuditStatus + ")'>" +
                                    "<span class='glyphicon glyphicon-trash'></span>删除</button>&nbsp";
                                href += "<button class='btn btn-xs btn-primary' style='margin-left:5px' title='提交审核' onclick='submitSceneAudit(\"" + row.id + "\")'>" +
                                    "<span class='glyphicon glyphicon-upload'></span>提交审核</button>";
                            }
                            //渲染可用状态按钮
                            if (row.sceneAuditStatus == 2) {
                                if(row.sceneStatus == 1){
                                    href += "<input type='checkbox' data-id='" + row.id + "' checked name='use'/>"
                                }else {
                                    href += "<input type='checkbox' data-id='" + row.id + "' name='use'/>"
                                }
                            }
                            return href;
                        }
                    } ],
                onLoadSuccess : function() { //加载成功时执行
                    initSwitch();
                },
                onLoadError : function() { //加载失败时执行
                }
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function(params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize : params.limit, // 每页要显示的数据条数
                offset : params.offset, // 每页显示数据的开始行号
                sort : params.sort, // 排序规则
                order : params.order,
                rows : params.limit, //页面大小
                page : (params.offset / params.limit) + 1, //页码
                pageIndex : params.pageNumber,//请求第几页
                field : 'id,sceneName,sceneDes,sceneStatus,sceneAuditStatus,sceneAuditMsg,createName'
            };

            var params = $("#apiSceneForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };

    function searchList() {
        reloadApiSceneTable();
    }

    function reloadApiSceneTable() {
        $('#apiSceneList').bootstrapTable('refresh');
    }

    function searchRest() {
        $('#apiSceneForm').find(':input').each(function() {
            if("checkbox"== $(this).attr("type")){
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked',false);
            }else if("radio"== $(this).attr("type")){
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked',false);
            }else{
                $(this).val("");
            }
        });
        $('#apiSceneForm').find("input[type='checkbox']").each(function() {
            $(this).attr('checked', false);
        });
        $('#apiSceneForm').find("input[type='radio']").each(function() {
            $(this).attr('checked', false);
        });
        reloadApiSceneTable();
    }
    //高级查询模态框
    function bootstrapQueryBuilder() {
        $('#superQueryModal').modal({
            backdrop : false
        });
    }

    //删除场景
    function deleteApiScene(id,sceneAuditStatus){
        if(sceneAuditStatus == 2){
            slowNotify("审核通过的场景不可删除","warning");
            return false;
        }
        BootstrapDialog.confirm({
            title: '删除提示',
            message: '是否确定删除?',
            type: BootstrapDialog.TYPE_DANGER,
            closable: true,
            size:"size-small",
            draggable: true,
            btnCancelLabel: '取消',
            btnOKLabel: '删除', // <-- Default value is 'OK',
            btnOKClass: 'btn-danger',
            callback: function (result) {
                if(result) {
                    $.ajax({
                        type:"post",
                        dataType:"json",
                        url:"apiSceneController.do?doDel",
                        data:{id:id},
                        success:function(data){
                            if(data.success){
                                quickNotify(data.msg,"success");
                                reloadApiSceneTable();
                            }else{
                                slowNotify(data.msg,"danger");
                            }
                        }
                    })
                }
            }
        });
    }

    function submitSceneAudit(id){
        BootstrapDialog.confirm({
            title: '操作提示',
            message: '确定要提交审核吗?',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            size:"size-small",
            draggable: true,
            btnCancelLabel: '取消',
            btnOKLabel: '确定', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning',
            callback: function (result) {
                if(result) {
                    $.ajax({
                        type:"post",
                        dataType:"json",
                        url:"apiSceneController.do?submitSceneAudit",
                        data:{id:id},
                        success:function(data){
                            if(data.success){
                                quickNotify(data.msg,"success");
                                reloadApiSceneTable();
                            }else{
                                slowNotify(data.msg,"danger");
                            }
                        }
                    })
                }
            }
        });
    }

    function initSwitch() {
        $("input[name='use']").bootstrapSwitch({
            size: "mini",
            onText: "可用",
            offText: "不可用",
            onColor: "success",
            offColor: "danger",
            labelWidth: "8",//设置label宽度
            disabled: false,
            onSwitchChange: function (event, state) {
                $(this).bootstrapSwitch('state', !state, true);
                var message = "确定要设置为可用吗";
                var sceneStatus = 1;
                if (!state) {
                    message = "确定要设置为不可用吗";
                    sceneStatus = 0;
                }
                var id = $(this).attr("data-id");
                BootstrapDialog.confirm({
                    title: '操作提示',
                    message: message,
                    type: BootstrapDialog.TYPE_WARNING,
                    closable: true,
                    size: "size-small",
                    draggable: true,
                    btnCancelLabel: '取消',
                    btnOKLabel: '确定', // <-- Default value is 'OK',
                    btnOKClass: 'btn-warning',
                    callback: function (result) {
                        if (result) {
                            $.ajax({
                                type: "post",
                                dataType: "json",
                                url: "apiSceneController.do?changeStatus",
                                data: {
                                    id: id,
                                    sceneStatus: sceneStatus
                                },
                                success: function (data) {
                                    if (data.success) {
                                        quickNotify(data.msg, "success");
                                        reloadTable();
                                    } else {
                                        slowNotify(data.msg, "danger");
                                    }
                                }
                            })
                        }
                    }
                });
            }
        })
    }

    function reloadTable() {
        $('#apiSceneList').bootstrapTable('refresh');
    }
</script>

</body>
</html>
