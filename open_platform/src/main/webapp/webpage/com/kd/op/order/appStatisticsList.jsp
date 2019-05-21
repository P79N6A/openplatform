<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>应用购买统计</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
    <script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/select2/css/select2.min.css">
    <script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.min.js"></script>
</head>
<body>
<div class="panel-body" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="appOrderForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
                            <div class="col-xs-6 col-sm-3 col-md-3">
                                <label for="appId">应用:</label>
                                <div class="input-group" style="width: 100%">
                                    <%--<select class="js-data-example-ajax form-control" name="appId" style="width: 100%"
                                            id="appId">
                                    </select>--%>
                                    <select class="selectpicker form-control" name="appId" style="width: 100%"
                                            id="appId">
                                    </select>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-3 col-md-3">
                                <label for="startDate">开始日期:</label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" id="startDate" name="startDate" class="form-control" autocomplete="off"/>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-3 col-md-3">
                                <label for="endDate">结束日期:</label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" id="endDate" name="endDate" class="form-control" autocomplete="off"/>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-3 col-md-3">
                                <div class="input-group col-md-12" style="margin-top: 20px;margin-left: 45px">
                                    <button type="button" class="btn btn-success" id="search-btn" onclick="reloadTable()">
                                        <span class="glyphicon glyphicon-search"></span>查询
                                    </button>
                                    <button type="button" class="btn btn-warning" id="reset-btn" onclick="searchRest()">
                                        <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置
                                    </button>
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
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="orderLogList"></table>
    </div>
</div>
<script type="text/javascript">
    var systemOrderTypeData = {

    };
    $(document).ready(function() {
      /*  $("#search_group").on("change", function () {
            reloadTable();
        });*/
        //初始化数据字典
        initDictByCode(systemOrderTypeData, "order", function () {
        });

        queryAppList();
        $('#appId').select2();
        timeInit();
        var oTable = new TableInit();
        oTable.Init();

    });



    //时间插件初始化
    function timeInit(){
        var date = new Date();
        var day = intToTwoStr(date.getDate());   //28
        var month = intToTwoStr(date.getMonth()+1);   //4  + 1
        var today = date.getFullYear()+"-"+month+"-"+day;
        $('#startDate,#endDate').datetimepicker({
            //lang:"ch",
            format:	'Y-m-d',
            timepicker:false,
            // value:today
        });
        $.datetimepicker.setLocale('ch');
    }

    function queryAppList() {
        //加载应用选择下拉列表
        $.post(
            "monitor.do?invokeAppDatagrid",
            function (data) {
                var htmlStr = "<option value=\"\">全部</option>";
                for (var i = 0; i < data.length; i++) {
                    htmlStr += "<option value=\"" + data[i].id + "\">" + data[i].appName + "</option>";
                }
                $('#appId').empty();
                $('#appId').html(htmlStr)
            },
            'json'
        );
    }


    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#orderLogList').bootstrapTable({
                url: 'order.do?queryAppDatagird', //请求后台的URL（*）
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
                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                showToggle: false, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                showExport: false, //显示到处按钮
                sortName: 'createDate',
                sortOrder: 'desc',
                columns: [
                    // 复选框
                    /*{
                        radio: true,
                        width:20,
                        align: 'center'
                    },*/
                    {
                        field: 'orderId',
                        title: '订单编号',
                        valign: 'middle',
                        width: 120,
                        visible: true,
                        align: 'center',
                        switchable: true,
                    },
                    /*{
                        field: 'order_type',
                        title: '订单类型',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return listDictFormat(value, systemOrderTypeData.order);
                        }
                    },*/
                    {
                        field: 'app_name',
                        title: '应用名称',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'pay_status',
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
                    {
                        field: 'create_name',
                        title: '创建人名称',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'create_date',
                        title: '创建日期',
                        valign: 'middle',
                        width:60,
                        align: 'center',
                        switchable: true,
                        formatter : function(value) {

                            return formatDate(value);
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
                field:'orderId,order_type,api_name,scene_name,app_name,create_name,create_date,pay_status'
            };

            var params = $("#appOrderForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };



    function reloadTable() {
        $('#orderLogList').bootstrapTable('refresh');
    }

    function searchRest(){
        $('#appOrderForm').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            }else {
                $(this).val("");
            }
        });
        $('#appOrderForm').find("input[type='checkbox']").each(function () {
            $(this).attr('checked', false);
        });
        $('#appOrderForm').find("input[type='radio']").each(function () {
            $(this).attr('checked', false);
        });
       /*$('#appOrderForm').find("select").each(function () {
            $(this).selectpicker('val', '');
        });*/
        $("#appId").val("");
        $("#appId").trigger("change");
        reloadTable();
    }
</script>
</body>
</html>

