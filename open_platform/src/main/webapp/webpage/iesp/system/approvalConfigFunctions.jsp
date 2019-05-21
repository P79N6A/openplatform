<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src=<%= request.getContextPath() +"/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() +"/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"%>></script>
<link rel="stylesheet" href=<%= request.getContextPath() +"/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css"%> type="text/css">
<%-- <link rel="stylesheet" href=<%= request.getContextPath() +"/plug-in/ztree/css/zTreeStyle.css"%> type="text/css"> --%>
<script src=<%= request.getContextPath() + "/js/system/approvalConfigFunctions.js" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <div id="addForm" class="form-horizontal" role="form">
		    	<input type="hidden" id="roleId" value="${roleId}" />
		        <div class="form-group">
		        	<div class="col-sm-12" style="max-height:300px;overflow:auto">
		        		<ul id="functions" class="ztree"></ul>
		        	</div>
		        </div>
		    </div>
		</div>
	</div>
</body>
</html>