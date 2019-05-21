<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
<script src="${pageContext.request.contextPath}/js/log/logConfigList.js" type="text/javascript"></script>
</head>
<body>
    <table id="logConfigs"></table>
        <div class="modal" id="logConfigModal">
	    <div class="modal-dialog" style="">
	        <div class="modal-content">
	          
	        </div>
	    </div>
	</div>
</body>
</html>