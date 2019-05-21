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
	.tree-icon{
		color:#00CAAB;
	}
</style>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstraptable-treeview.js"%>></script>
<script src=<%= request.getContextPath() + "/js/system/departList.js" %> type="text/javascript"></script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="col-xs-1 pull-right no-padding">
				<%--<button type="button" class="btn btn-success formnew" id="add-btn">--%>
					<%--<span class="glyphicon glyphicon-plus"></span>新建--%>
				<%--</button>--%>
			</div>
		</form>
	</div>
    <table id="departs"></table>
</body>
</html>