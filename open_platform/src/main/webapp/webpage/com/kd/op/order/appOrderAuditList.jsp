<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>能力订单审核列表表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
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
                        <form id="apiOrderForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label for="appName">应用名称：</label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" class="form-control input-sm" id="appName" name="appName"/>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label for="auditStatus">审核状态：</label>
                                <select class="selectpicker form-control" name="auditStatus" id="auditStatus">
                                    <option value="1">待审核</option>
                                    <option value="2">审核通过</option>
                                    <option value="3">审核未通过</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="input-group col-md-12" style="margin-top: 20px">
                                    <a type="button" style="margin-right:15px;" onclick="searchList();" class="btn btn-primary">
                                        <span class="glyphicon glyphicon-search"></span>查询
                                    </a>
                                    <%--<a type="button" onclick="searchRest();" class="btn btn-danger">
                                        <span class="glyphicon glyphicon-trash"></span>重置
                                    </a>--%>
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
        <button id="btn_detail" onclick="goOrderDetailPage()"
                class="btn btn-info btn-sm">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
        </button>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="apiOrderList"></table>
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
    $(function () {
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#apiOrderList').bootstrapTable({
                url: 'order.do?orderDatagrid&listType=audit&OrderType=app', //请求后台的URL（*）
                method: 'post', //请求方式（*）
                contentType : "application/x-www-form-urlencoded",
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
                showColumns: false, //是否显示所有的列
                showRefresh: true, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
//                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                showToggle: false, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                showExport: false, //显示到处按钮
                sortName: 'createDate',
                sortOrder: 'desc',
                columns: [
                    // 单选框
                    {
                        radio: true,
                        align: 'center',
                        width:20,
                    },
                    {
                        field: 'id',
                        title: '订单编号',
                        valign: 'middle',
                        width: 180,
                        visible: true,
                        align: 'center',
                        switchable: true,
                        formatter : function(value, rec, index) {
                            return "<span title=" + value + ">" + value + "</span>"
                        }
                    },
                    // {
                    //     field: 'appId',
                    //     title: '应用id',
                    //     valign: 'middle',
                    //     width: 120,
                    //     visible: false,
                    //     align: 'center',
                    //     switchable: true,
                    // },
                    {
                        field: 'appName',
                        title: '应用名称',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter : function(value, rec, index) {
                            return "<span title=" + value + ">" + value + "</span>"
                        }
                    },
                    {
                        field: 'money',
                        title: '订单金额',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'payStatus',
                        title: '支付状态',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                        formatter : function(value, rec, index) {
                            if(value == 0){
                                return "未支付";
                            }else if(value == 1){
                                return "已支付";
                            }
                            return value;
                        }
                    },
//                    {
//                        field: 'payTime',
//                        title: '支付时间',
//                        valign: 'middle',
//                        width: 120,
//                        align: 'center',
//                        switchable: true,
//                    },
                    {
                        field: 'createDate',
                        title: '下单时间',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter : function(value, rec, index) {
                            if(value == null || value==''){
                                return value;
                            }else{
                                value = value.substring(0,value.lastIndexOf("."));
                            }
                            return value;
                        }
                    },
                    {
                        field: 'auditStatus',
                        title: '审核状态',
                        valign: 'middle',
                        width: 80,
                        align: 'center',
                        switchable: true,
                        formatter : function(value, rec, index) {
                            if(value == 0){
                                return "暂存";
                            }else if(value == 1){
                                return "待审核";
                            }else if(value == 2){
                                return "通过";
                            }else if(value == 3){
                                return "不通过";
                            }
                            return value;
                        }
                    },
                    // {
                    //     field: 'orderType',
                    //     title: '订单类型',
                    //     valign: 'middle',
                    //     width: 60,
                    //     align: 'center',
                    //     switchable: true,
                    //     formatter : function(value, rec, index) {
                    //         if(value == 'app'){
                    //             return "应用订单";
                    //         }
                    //         return value;
                    //     }
                    // },
                    {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        width: 50,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            var href = '';
                            if(row.auditStatus == '1'){
                                href += "<button class='btn btn-xs btn-primary' onclick='goOrderAudit(\"" + row.id + "\")'>" +
                                    "<span>审核</span></button>";
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
                field: 'id,appId,appName,money,payStatus,payTime,auditStatus,orderType,createDate'
            };

            var params = $("#apiOrderForm").serializeArray();
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
        $('#apiOrderList').bootstrapTable('refresh');
    }

    function searchRest() {
        $('#apiOrderForm').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            }
            $(this).val("")
        });
        reloadTable();
    }
    /**
     * 订单关联的详情
     * @param id
     */
    function lookDetail(id) {
        var dialog = bootOpenLook('订单详情', 'order.do?lookDetail&id=' + id, 'wide');
    }


    //查看订单的详情
    function goOrderDetailPage() {
        var selects = $('#apiOrderList').bootstrapTable('getSelections');
        if (selects == null || selects.length == 0) {
            quickNotify("请先选中一行", "warning");
        }else{
            var id = selects[0].id;
            var dialog = bootOpenLook('订单详情', 'order.do?goOrderDetailPage&id=' + id, 'wide');
        }

    }

    //订单审核
    function goOrderAudit(id) {
        var dialog = bootOpenNormal('订单审核', 'order.do?goOrderAudit&id=' + id, 'wide');
    }
</script>


</body>
</html>