<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.min.css" %> type="text/css">

    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-select.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/defaults-zh_CN.min.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/confirm.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/jquery.selection.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>--%>
    <script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-notify.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.js" %> type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/com/kd/op/monitor/countAnalyse.js"%>"></script>
</head>

<body class="gray-bg" style="background:#fff;font-size: 14px;">
    <div class="search-div">
        <form id="searchForm" class="searchForm" class="form-horizontal">
            <div class="form-horizontal col-xs-10 pull-left" role="form">
                <div class="col-xs-12 pull-left no-padding">
                    <div class="col-xs-3 form-group no-padding">
                        <label class="col-sm-5 control-label near-padding-right" style="font-size:17px;font-weight:inherit" for="search_user">用户</label>
                        <div class="col-sm-7 no-padding">
                            <select class="selectpicker form-control" id="search_user">
                                <c:forEach items="${users}" var="user" varStatus="varStatus">
                                    <c:choose>
                                        <c:when test="${varStatus.first}">
                                            <option value="${user.id}" selected>${user.userName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${user.id}">${user.userName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-2 pull-left no-padding">
                <button type="button" class="btn btn-success" id="search-btn">
                    <span class="glyphicon glyphicon-search"></span>查询
                </button>
                <%--<button type="button" class="btn btn-warning" id="reset-btn">
                    <span class="glyphicon glyphicon-refresh"></span>重置
                </button>--%>
            </div>
        </form>
    </div>
    <%--<div class="col-md-12" id="emptyDiv" style="margin-top:150px;text-align:center">
        <p style="color:#ccc;font-size:30px;">请根据查询条件进行数据查询</p>
    </div>--%>
    <hr>
    <div id="graph" class="col-md-12" style="height:550px;margin:0 auto;"></div>

</body>
<%--<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>--%>
<%--<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>--%>

<script src="plug-in-ui/hplus/js/content.js"></script>
</html>
