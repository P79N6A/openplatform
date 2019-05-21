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
</head>
<body>
<ul class="nav nav-pills">
	<li class="active"><a href="${pageContext.request.contextPath}/task.do?taskList">同步配置</a></li>
	<li><a href="#">同步任务</a></li>
	<li><a href="#">iOS</a></li>
	<li><a href="#">VB.Net</a></li>
	<li><a href="#">Java</a></li>
	<li><a href="#">PHP</a></li>
</ul>
</body>
</html>