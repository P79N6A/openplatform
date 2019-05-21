<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>附件审核列表</title>
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
                        <form id="annexForm" onkeydown="if(event.keyCode==13){reloadApiSceneTable();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label for="annexName">附件名称：</label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" class="form-control input-sm" id="annexName" name="annexName"/>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-3 col-md-2">
                                <label>审核状态：</label>
                                <select class="selectpicker form-control" name="auditStatus" id="auditStatus">
                                    <option value="1">待审核</option>
                                    <option value="2">审核通过</option>
                                    <option value="3">审核失败</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="input-group col-md-12" style="margin-top: 20px">
                                    <a class='btn btn-success' onclick="searchList();"><span
                                            class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>&nbsp
                                    <%--<a class='btn btn-warning' onclick="searchRest();"><span--%>
                                            <%--class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置</a>--%>
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
        <button id="btn_look" class="btn btn-info btn-sm">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
        </button>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="apiAnnexList"></table>
    </div>
</div>
<script type="text/javascript">

    $("#btn_look").bind("click", function () {
        var selects = $("#apiAnnexList").bootstrapTable("getSelections");
        if (selects == null || selects.length == 0) {
            quickNotify("请先选中一行", "warning");
        } else if (selects.length > 1) {
            quickNotify("只能选择一行", "warning");
        } else {
            var id = selects[0].id;
            var dialog = bootOpenLook('附件详情', 'apiAnnexController.do?goLook&id=' + id, 'wide');
        }
    })

    var auditListdictsData = {};

    $(function () {
        initDictByCode(auditListdictsData, "auditSts", function () {
        });
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#apiAnnexList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function () {
                $('#btn_look').prop('disabled', $('#apiAnnexList').bootstrapTable('getSelections').length != 1);
            });

    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#apiAnnexList').bootstrapTable({
                url: 'apiAnnexController.do?datagrid', //请求后台的URL（*）
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
                clickToSelect: true, //是否启用点击选中行
                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                sortName: 'auditStatus',
                sortOrder: 'asc',
                columns: [
                    {
                        radio: true,
                        align: 'center'
                    },
                    {
                        field: 'id',
                        title: 'id',
                        valign: 'middle',
                        width: 120,
                        visible: false,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'annexName',
                        title: '附件名称',
                        valign: 'middle',
                        width: 100,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'annexDesc',
                        title: '附件描述',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'annexStatus',
                        title: '附件状态',
                        valign: 'middle',
                        width: 80,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "不可用";
                            } else if (value == 1) {
                                return "可用";
                            }
                        }
                    }, {
                        field: 'annexSuffix',
                        title: '附件格式',
                        valign: 'middle',
                        width: 80,
                        align: 'center',
                        switchable: true,
                    }
                    , {
                        field: 'annexSize',
                        title: '附件大小(KB)',
                        valign: 'middle',
                        width: 80,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'auditStatus',
                        title: '审核状态',
                        valign: 'middle',
                        width: 80,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, row, index) {

                            return listDictFormat(value, auditListdictsData.auditSts);
                        }
                    },
                    {
                        field: 'createName',
                        title: '创建人',
                        valign: 'middle',
                        width: 100,
                        align: 'center',
                        switchable: true,
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
                            if(row.auditStatus == 1){
                                href += "<button class='btn btn-xs btn-primary' onclick='goAudit(\"" + row.id + "\")'><span>审核</span></button>";
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
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sort: params.sort, // 排序规则
                order: params.order,
                rows: params.limit, //页面大小
                page: (params.offset / params.limit) + 1, //页码
                pageIndex: params.pageNumber,//请求第几页
                field: 'id,annexName,annexDesc,annexStatus,annexSuffix,annexSize,auditStatus,createName'
            };

            var params = $("#annexForm").serializeArray();
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

    function searchRest() {
        $('#annexForm').find(':input').each(function () {
            if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("text" == $(this).attr("type")){
                $(this).val("");
            }
        });
        reloadTable();
    }


    //跳转审核
    function goAudit(id) {
        bootOpenNormal('附件详情', 'apiAnnexController.do?goAudit&id=' + id, 'wide');
    }

    function reloadTable() {
        $('#apiAnnexList').bootstrapTable('refresh');
    }
</script>

</body>
</html>
