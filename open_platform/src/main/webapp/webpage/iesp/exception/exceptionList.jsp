<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap-table.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrapValidator.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/form.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap-dialog.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
<script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/form/jquery-form.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-table.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-table-zh-CN.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-notify.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-dialog.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.fixed-table-toolbar .bs-bars,.fixed-table-toolbar .search,.fixed-table-toolbar .columns
	{
	margin-bottom: 2px;
}
.fixed-table-body{
	height:auto
}
</style>
<script src=<%= request.getContextPath() + "/js/exception/ExceptionList.js" %>
	type="text/javascript">
</script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right"
							for="search_loglevel">异常类型</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="search_exceptionlevel" name="loglevel">
								<option value="">全部</option>
								<option value="1">普通异常</option>
								<option value="2">严重异常</option>
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
			</div>
		</form>
	</div>
		<table id="Exception"></table>
</body>
</html>