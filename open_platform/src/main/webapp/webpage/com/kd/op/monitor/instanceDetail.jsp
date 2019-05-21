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
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap-select.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/form.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/modify.css" %> type="text/css">--%>

    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-select.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/defaults-zh_CN.min.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/confirm.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/jquery.selection.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>--%>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/com/kd/op/monitor/instanceDetail.js"%>"></script>
</head>

<body class="gray-bg" style="background:#fff;font-size: 20px;width:100%">
    <div class="col-md-12" style="margin-top:20px">
        <div id="count" class="col-md-6" style="height:350px;margin:0 auto;width:50%;"></div>
        <div id="responseTimeLength" class="col-md-6" style="height:350px;margin:0 auto;"></div>
    </div>
    <div class="col-md-12" style="margin-top:20px">
        <div id="successRate" class="col-md-6" style="height:350px;margin:0 auto;"></div>
        <div id="flowSize" class="col-md-6" style="height:350px;margin:0 auto;"></div>
    </div>

</body>
<script src="plug-in-ui/hplus/js/content.js"></script>
</html>
