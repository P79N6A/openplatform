<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统日志表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="shortcut icon" href="images/favicon.ico">
    <style>
        .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns {
            margin-bottom: 2px;
        }

        .table {
            table-layout: fixed;
        }
    </style>
    <link rel="stylesheet"
          href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
    <script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() + "/js/com/kd/op/monitor/consumerMonitor.js"%>"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
<div class="panel-body" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="apiAppForm" onkeydown="if(event.keyCode==13){searchAppList();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="col-sm-5 control-label near-padding-right"
                                       style="font-size:15px;font-weight:inherit;margin-top: 5px">业务模块</label>
                                <div class="col-sm-7 no-padding">
                                    <t:dictSelect field="loglevel" type="select" hasLabel="false" title="日志类型"
                                                  extendJson="{class:'form-control input-sm'}"
                                                  typeGroupCode="loglevel"></t:dictSelect>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="col-sm-5 control-label near-padding-right"
                                       style="font-size:15px;font-weight:inherit;margin-top: 5px">标记</label>
                                <div class="col-sm-7 no-padding">
                                    <t:dictSelect field="successFlag" type="select" hasLabel="false" title="标记"
                                                  extendJson="{class:'form-control input-sm'}"
                                                  typeGroupCode="invoRes"></t:dictSelect>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4" >
                                <div class="input-group col-md-12" style="margin-left: 45px">
                                    <a class='btn btn-success' onclick="reloadAppTable();"><span
                                            class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>&nbsp
                                    <a class='btn btn-warning' onclick="searchAppRest();"><span
                                            class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置</a>
                                </div>
                            </div>

                            <%--<div class="form-horizontal col-xs-10 pull-left" role="form">--%>
                            <%--<div class="col-xs-12 pull-left no-padding">--%>
                            <%--<div class="col-xs-4 form-group no-padding" style="width:40%">--%>

                            <%--<label class="col-sm-5 control-label near-padding-right"--%>
                            <%--style="font-size:15px;font-weight:inherit;padding-left:0px;">业务模块</label>--%>
                            <%--<div class="col-sm-7 no-padding">--%>
                            <%--<t:dictSelect field="loglevel" type="select" hasLabel="false" title="日志类型"--%>
                            <%--extendJson="{class:'form-control input-sm'}"--%>
                            <%--typeGroupCode="loglevel"></t:dictSelect>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="col-xs-4 form-group no-padding" style="width: 40%">--%>
                            <%--<label class="col-sm-5 control-label near-padding-right"--%>
                            <%--style="font-size:15px;font-weight:inherit">标记</label>--%>
                            <%--<div class="col-sm-7 no-padding">--%>
                            <%--<t:dictSelect field="successFlag" type="select" hasLabel="false" title="标记"--%>
                            <%--extendJson="{class:'form-control input-sm'}"--%>
                            <%--typeGroupCode="invoRes"></t:dictSelect>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="col-xs-4 pull-left no-padding ">--%>
                            <%--<a class='btn btn-success' onclick="reloadAppTable();"><span--%>
                            <%--class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>&nbsp--%>
                            <%--<a class='btn btn-warning' onclick="searchAppRest();"><span--%>
                            <%--class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置</a>--%>
                            <%--</div>--%>
                            <div class="col-xs-12 col-sm-6 col-md-4" style="margin-top: 15px">
                                <label for="startDate" style="font-size:15px;font-weight:inherit;margin-top: 5px"
                                       class="col-md-5 control-label near-padding-right">开始日期</label>
                                <div class="col-md-7 no-padding">
                                    <input type="text" id="startDate" name="operatetime_begin"
                                           class="form-control" autocomplete="off"/>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4" style="margin-top: 15px">
                                <label for="endDate" style="font-size:15px;font-weight:inherit;margin-top: 5px"
                                       class="col-md-5 control-label near-padding-right">结束日期</label>
                                <div class="col-md-7 no-padding">
                                    <input type="text" id="endDate" name="operatetime_end"
                                           class="form-control" autocomplete="off"/>
                                </div>
                            </div>
                            <%--</div>--%>
                            <%--</div>--%>


                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
    <div id="toolbar">
        <button id="btn_look" class="btn btn-info btn-sm">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
        </button>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="apiAppList"></table>
    </div>
</div>
<script type="text/javascript">
    $("#btn_look").bind("click", function () {
        var selects = $("#apiAppList").bootstrapTable("getSelections");
        if (selects == null || selects.length == 0) {
            quickNotify("请先选中一行", "warning");
        } else if (selects.length > 1) {
            quickNotify("只能选择一行", "warning");
        } else {
            var id = selects[0].id;
            bootOpenLook('系统日志查看', 'monitor.do?goSystemLogLook&id=' + id, 'normal');
        }
    })
    var systemLogTypeData = {};
    $(function () {
        initDictByCode(systemLogTypeData, "loglevel", function () {
        });

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#apiAppList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function () {
                $('#btn_look').prop('disabled', $('#apiAppList').bootstrapTable('getSelections').length != 1);
            });

    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#apiAppList').bootstrapTable({
                url: 'monitor.do?systemLogDatagrid', //请求后台的URL（*）
                method: 'post', //请求方式（*）
                contentType: "application/x-www-form-urlencoded",
                toolbar: '#toolbar', //工具按钮用哪个容器
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true, //是否显示分页（*）
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 10, //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                strictSearch: true,
                showRefresh: true,
                clickToSelect: true, //是否启用点击选中行
                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                sortName: 'operatetime',
                sortOrder: 'desc',
                columns: [
                    // 单选框
                    {
                        radio: true,
                        width: 35,
                        align: 'center'
                    },
                    {
                        field: 'broswer',
                        title: '用户浏览器类型',
                        valign: 'middle',
                        width: 100,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'loglevel',
                        title: '业务模块',
                        valign: 'middle',
                        width: 100,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return listDictFormat(value, systemLogTypeData.loglevel);
                        }
                    },
                    {
                        field: 'note',
                        title: '操作IP',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    }, {
                        field: 'operatetime',
                        title: '操作时间',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter: function (value) {
//                            return formatDate(value)
                            return value.replace(".0", "");
//                            return value.substring(0,value.length-2);
                        }
                    },
                    {
                        field: 'operatetype',
                        title: '日志类型',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return "正常";
                            } else if (value == 2) {
                                return "警告";
                            } else if (value == 3) {
                                return "错误";
                            }
                        }
                    },
                    {
                        field: 'successFlag',
                        title: '标记',
                        valign: 'middle',
                        width: 100,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "失败";
                            } else if (value == 1) {
                                return "成功";
                            }
                        }
                    },
                    {
                        field: 'aaa',
                        title: '日志分类',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "业务事件";
                            } else if (value == 1) {
                                return "系统事件";
                            }
                        }
                    }
                ],
                onLoadSuccess: function () { //加载成功时执行
                },
                onLoadError: function () { //加载失败时执行
                }
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sort: params.sort, // 排序规则
                order: params.order,
                rows: params.limit, //页面大小
                page: (params.offset / params.limit) + 1, //页码
                pageIndex: params.pageNumber,//请求第几页
                field: 'id,broswer,loglevel,note,operatetype,operatetime,successFlag,aaa'
            };

            var params = $("#apiAppForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };

    function searchAppList() {
        reloadAppTable();
    }


    function reloadAppTable() {
        var endDate=new Date($("#endDate").val())
        var startDate=new Date($("#startDate").val())
        if (endDate-startDate<0) {
            quickNotify("结束日期不能小于开始日期", "warning");
            return;
        }
        $('#apiAppList').bootstrapTable('refresh');
    }

    function searchAppRest() {
        $('#apiAppForm').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else {
                $(this).val("");
            }
        });
        $('#apiAppForm').find("input[type='checkbox']").each(function () {
            $(this).attr('checked', false);
        });
        $('#apiAppForm').find("input[type='radio']").each(function () {
            $(this).attr('checked', false);
        });
        reloadAppTable();
    }
</script>
</body>
</html>