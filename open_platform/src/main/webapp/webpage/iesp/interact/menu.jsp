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
	<ul id="myTab" class="nav nav-tabs">
	    <li class="active">
	        <iframe src="task.do?taskList" width="100%" height="100%"></iframe>
	    </li>
	    <li><a href="#ios" data-toggle="tab">iOS</a></li>
	</ul>
</body>
</html>