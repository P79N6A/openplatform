<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--<%@ include file="/webpage/common/resource.jsp"%>--%>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="shortcut icon" href="images/favicon.ico">
    <style>
        .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
            margin-bottom:2px;
        }
        .table{
            table-layout: fixed;
        }
    </style>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap-select.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/form.css" %> type="text/css">--%>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/modify.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.min.css" %> type="text/css">--%>

    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-select.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/defaults-zh_CN.min.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/confirm.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/jquery.selection.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>--%>
    <script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-notify.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.js" %> type="text/javascript"></script>--%>
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/com/kd/op/monitor/countNodeData.js"%>"></script>
</head>

<body class="gray-bg" style="background:#fff;font-size: 14px;">
    <div class="col-sm-12" style="margin-top:20px">
        <form id="subSearchForm" class="form form-horizontal">
            <input id="nodeType" name="nodeType" value="${type}" type="hidden"/>
            <input id="nodeId" name="nodeId" value="${id}" type="hidden"/>
            <input id="appId" name="appId" value="${appId}" type="hidden"/>
            <div class="form-horizontal col-xs-10 pull-left" role="form">
                <div class="col-xs-12 pull-left no-padding">
                    <div class="col-md-6  form-group " style="padding-left:20px;">
                        <label for="startDate" style="font-size:17px;font-weight:inherit" class="col-md-5 control-label near-padding-right">开始日期</label>
                        <div class="col-md-7 no-padding">
                            <input type="text" id="startDate" name="startDate" class="form-control" />
                        </div>
                    </div>
                    <div class="col-md-6  form-group " style="padding-left:20px;">
                        <label for="endDate" style="font-size:17px;font-weight:inherit" class="col-md-5 control-label near-padding-right">结束日期</label>
                        <div class="col-md-7 no-padding">
                            <input type="text" id="endDate" name="endDate" class="form-control" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-2 pull-left no-padding">
                <button type="button" class="btn btn-success" id="search">
                    <span class="glyphicon glyphicon-search"></span>查询
                </button>
                <%--<button type="button" class="btn btn-warning" id="reset-btn">
                    <span class="glyphicon glyphicon-refresh"></span>重置
                </button>--%>
            </div>
        </form>
    </div>
    <div class="col-sm-12">
        <div id="line" class="" style="height:300px;margin:10px 0 0 0;width:900px"></div>
    </div>

    <div class="com-sm-12 table-div" style="">
        <table id="logList"></table>
    </div>
</body>
<%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>--%>
<%--<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>--%>

<%--<script src="plug-in-ui/hplus/js/content.js"></script>--%>
</html>
