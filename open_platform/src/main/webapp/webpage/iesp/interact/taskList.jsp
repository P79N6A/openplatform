<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%@ include file="/webpage/common/resource.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
		margin-bottom:2px;
	}
</style>
<script src=<%= request.getContextPath() + "/js/interact/taskList.js" %> type="text/javascript"></script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-5 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_type">任务类型</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" data-size=10 id="search_type">
                            	<option value="">全部</option>
                            	<option value="1">手动</option>
                            	<option value="2">自动</option>
                            </select>
						</div>
					</div>
					<div class="col-xs-5 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_name">任务状态</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="search_curState">
                            	<option value="">全部</option>
                            	<option value="1">正常</option>
                            	<option value="3">运行</option>
                            	<option value="4">暂停</option>
                            </select>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3 pull-left no-padding">
				<button type="button" class="btn btn-primary" id="search-btn">
					<span class="glyphicon glyphicon-search"></span>查询
				</button>
				<button type="button" class="btn btn-warning" id="reset-btn">
					<span class="glyphicon glyphicon-refresh"></span>重置
				</button>
				<button type="button" class="btn btn-success formnew" id="add-btn">
					<span class="glyphicon glyphicon-plus"></span>新建
				</button>
			</div>
		</form>
	</div>
    <table id="Task"></table>
</body>
</html>