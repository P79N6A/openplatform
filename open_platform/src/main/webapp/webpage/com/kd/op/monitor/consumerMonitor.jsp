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
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
<script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/com/kd/op/monitor/consumerMonitor.js"%>"></script>
</head>

<body class="" style="background:#fff;font-size: 14px;">
    <div class="search-div">
        <form id="searchForm" class="searchForm" class="form-horizontal">
            <div class="form-horizontal col-xs-10 pull-left" role="form">
                <div class="col-xs-12 pull-left no-padding">
                    <div class="col-xs-4 form-group no-padding">
                        <label class="col-sm-5 control-label near-padding-right" style="font-size:17px;font-weight:inherit" for="search_group">应用</label>
                        <div class="col-sm-7 no-padding">
                            <select class="selectpicker form-control" name="appId" id="search_group">
                                <c:forEach items="${apps}" var="app" varStatus="status">
                                	 <c:if test="${status.index==0}">
                                	 	<option selected="selected" value="${app.id}">${app.appName}</option>
                                	 </c:if>
                                	 <c:if test="${status.index>0}">
                                	 	<option value="${app.id}">${app.appName}</option>
                                	 </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4  form-group " style="padding-left:20px;">
                        <label for="startDate" style="font-size:17px;font-weight:inherit" class="col-md-5 control-label near-padding-right">开始日期</label>
                        <div class="col-md-7 no-padding">
                            <input type="text" id="startDate" name="invokeTime_begin" class="form-control" />
                        </div>
                    </div>
                    <div class="col-md-4  form-group " style="padding-left:20px;">
                        <label for="endDate" style="font-size:17px;font-weight:inherit" class="col-md-5 control-label near-padding-right">结束日期</label>
                        <div class="col-md-7 no-padding">
                            <input type="text" id="endDate" name="invokeTime_end" class="form-control" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xs-2 pull-left no-padding">
                <button type="button" class="btn btn-success" id="search-btn" onclick="reloadTable()">
                    <span class="glyphicon glyphicon-search"></span>查询
                </button>
            </div>
        </form>
    </div>
    <div class="">
        <table id="consumerTable"></table>
    </div>
</body>
</html>
