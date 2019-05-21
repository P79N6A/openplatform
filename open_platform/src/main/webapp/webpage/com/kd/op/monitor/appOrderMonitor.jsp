<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%--<%@ include file="/webpage/common/resource.jsp"%>--%>
<link rel="shortcut icon" href="images/favicon.ico">
<style>
    .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
        margin-bottom:2px;
    }
    .table{
        table-layout: fixed;
    }
</style>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap-select.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/form.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/modify.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.min.css" %> type="text/css">

    <script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-select.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/defaults-zh_CN.min.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/js/confirm.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/js/jquery.selection.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-notify.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.js" %> type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/com/kd/op/monitor/appOrderMonitor.js"%>"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/select2/css/select2.min.css">
    <script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.min.js"></script>
</head>

<body class="gray-bg" style="background:#fff;font-size: 14px;">
    <div class="search-div">
        <form id="searchForm" class="searchForm" class="form-horizontal">
                    <div class="col-xs-12 col-sm-6 col-md-3">
                        <label for="appId" style="font-size:14px;">应用：</label>
                        <select class="js-data-example-ajax form-control" name="appId" style="width: 100%"
                                id="appId">
                            <option value="">全部</option>
                        </select>
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-3">
                        <label for="startDate" style="font-size:14px;">开始日期：</label>
                        <%--<label for="startDate" style="font-size:14px;margin-top: 25px;margin-right: -30px;" class="col-md-4 control-label near-padding-right">开始日期</label>--%>
                        <input type="text" id="startDate" name="date" class="form-control" autocomplete="off"/>
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-3">
                        <label for="endDate" style="font-size:14px;">结束日期：</label>
                        <%--<label for="startDate" style="font-size:14px;margin-top: 25px;margin-right: -30px;" class="col-md-4 control-label near-padding-right">结束日期</label>--%>
                        <input type="text" id="endDate" name="date" class="form-control" autocomplete="off"/>
                    </div>
                 <div class="col-xs-12 col-sm-6 col-md-3">
                    <div class="input-group col-md-12" style="margin-top: 25px;margin-left: 45px">
                        <button type="button" class="btn btn-primary" id="search-btn">
                            <span class="glyphicon glyphicon-search"></span>查询
                        </button>
                        <button type="button" class="btn btn-warning" id="reset-btn">
                            <span class="glyphicon glyphicon-refresh"></span>重置
                        </button>
                    </div>
                </div>
        </form>
    </div>
    <%--<div class="col-md-12" id="emptyDiv" style="margin-top:150px;text-align:center">--%>
        <%--<p style="color:#ccc;font-size:30px;">请根据查询条件进行数据查询</p>--%>
    <%--</div>--%>
    <div class="col-md-12" style="margin-top:20px">
        <div id="count" class="col-md-6" style="height:550px;"></div>
        <div id="sort" class="col-md-6" style="height:550px;"></div>
    </div>
    <%--<div class="col-md-12" style="margin-top:20px">--%>
        <%--<div id="successRate" class="col-md-6" style="height:550px;margin:0 auto;"></div>--%>
        <%--<div id="flowSize" class="col-md-6" style="height:550px;margin:0 auto;"></div>--%>
    <%--</div>--%>

</body>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<script src="plug-in-ui/hplus/js/content.js"></script>
</html>
