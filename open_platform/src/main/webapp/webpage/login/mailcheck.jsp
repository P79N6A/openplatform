<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="checkForm" name="checkForm" method="post" class="form-horizontal" role="form">
		    <input id="userId" type="hidden" name="userId" value="${userId}">
		        <div class="form-group">
		        	<label for="mailCode" class="col-sm-2 control-label">认证码：</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="mailCode" name="mailCode" class="form-control" placeholder="请输入认证码" />
		        	</div>
		        </div>
		    </form>
		</div>
	</div>
</body>
</html>