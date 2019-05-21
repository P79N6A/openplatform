<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>场景审核表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
</head>
<body>
<div class="panel-body col-sm-12" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="apiSceneForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label for="sceneAuditStatus">审核状态：</label>
                                <select class="selectpicker form-control" name="sceneAuditStatus" id="sceneAuditStatus">
                                    <option value="1">待审核</option>
                                    <option value="2">审核通过</option>
                                    <option value="3">审核失败</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="input-group col-md-12" style="margin-top: 20px">
                                    <a type="button" style="margin-right:15px;" onclick="searchList();" class="btn btn-primary">
                                        <span class="glyphicon glyphicon-search"></span>查询
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="padding-top: 0px; padding-bottom: 0px;">
        <div id="toolbar">
            <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                    class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
        </div>
        <div class="table-responsive">
            <table id="apiSceneList" class="table table-bordered"></table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(".laydate-datetime").each(function () {
        var _this = this;
        laydate.render({
            elem: this,
            format: 'yyyy-MM-dd HH:mm:ss',
            type: 'datetime'
        });
    });
    $(".laydate-date").each(function () {
        var _this = this;
        laydate.render({
            elem: this
        });
    });
    var apiAppListdictsData = {};
    $(function () {
        initDictByCode(apiAppListdictsData, "auditSts", function () {
        });

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#apiSceneList').bootstrapTable({
                url: 'apiSceneController.do?datagrid', //请求后台的URL（*）
                contentType : "application/x-www-form-urlencoded",
                method: 'POST', //请求方式（*）
                toolbar: '#toolbar', //工具按钮用哪个容器
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true, //是否显示分页（*）
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 10, //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                strictSearch: false,
                showColumns: false, //是否显示所有的列
                showRefresh: true, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                showToggle: false, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                showExport: false, //显示到处按钮
                sortName: 'createTime',
                sortOrder: 'desc',
                columns: [
                    {
                        title: '序号',
                        width: 15,
                        align: 'center',
                        switchable: false,
                        formatter: function (value, row, index) {
                            //return index+1; //序号正序排序从1开始
                            var pageSize = 10
                            var pageNumber = 1;
                            return pageSize * (pageNumber - 1) + index + 1;
                        }
                    },
                    {
                        field: 'id',
                        title: 'id',
                        valign: 'middle',
                        width: 120,
                        visible: false, //不可见
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'sceneName',
                        title: '应用场景名称',
                        valign: 'middle',
                        width: 80,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'sceneDes',
                        title: '应用场景描述',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'sceneAuditStatus',
                        title: '审核状态',
                        valign: 'middle',
                        width: 50,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return listDictFormat(value, apiAppListdictsData.auditSts);
                        }
                    },
                    {
                        field: 'createName',
                        title: '创建人',
                        valign: 'middle',
                        width: 70,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        width: 60,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            var href = '';
                            href += "<button class='btn btn-xs btn-success' onclick='goLook(\"" + row.id + "\")'><span>查看</span></button>&nbsp";
                            if(row.sceneAuditStatus == 1){
                                href += "<button class='btn btn-xs btn-primary' onclick='audit(\"" + row.id + "\")'><span>审核</span></button>";
                            }
                            return href;
                        }
                    }],
                onLoadSuccess: function () { //加载成功时执行
                },
                onLoadError: function () { //加载失败时执行
                }
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sort: params.sort, // 排序规则
                order: params.order,
                rows: params.limit, //页面大小
                page: (params.offset / params.limit) + 1, //页码
                pageIndex: params.pageNumber,//请求第几页
                field: 'id,sceneName,sceneDes,sceneStatus,sceneAuditStatus,sceneAuditMsg,createName' //sceneStatus代表审核状态
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
        reloadTable();
    }

    function reloadTable() {
        $('#apiSceneList').bootstrapTable('refresh');
    }
    function goLook(id) {//查看订购按钮绑定函数
        bootOpenLook('场景查看', 'apiSceneController.do?goLook&id=' + id, 'wide');
    }

    //审核按钮绑定函数
    function audit(id) {
        var dialog = new BootstrapDialog({
            title: '审核场景',
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "size-default",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: true,
            data: {
                'pageToLoad': "apiSceneController.do?goAuditScene&id=" + id //待修正
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                action: function (dialogRef) {
                    auditScene(dialog);
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dialog.open();
    }
</script>
</body>
</html>