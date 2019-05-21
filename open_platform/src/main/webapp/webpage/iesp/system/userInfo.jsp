<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrapValidator.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/form.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap-dialog.css" %> type="text/css">

<script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/form/jquery-form.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-notify.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-dialog.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
	    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
	        <div class="form-group">
	        	<label  class="col-sm-2 control-label">用户账号</label>
	        	<div class="col-sm-10">
	        		<input type="text" name="userName" class="form-control" readonly="readonly" value="${user.userName}"/>
	        	</div>
	        </div>
			<div class="form-group">
	        	<label for="realName" class="col-sm-2 control-label">真实姓名</label>
	        	<div class="col-sm-10">
	        		<input type="text" name="realName" class="form-control" readonly="readonly" value="${user.realName}" />
	        	</div>
	        </div>
	        <div class="form-group">
	        	<label for="departName" class="col-sm-2 control-label">组织机构</label>
	        	<div class="col-sm-10">
	        		<input type="text" name="departName" class="form-control" readonly="readonly" value="${user.currentDepart.departname}"/>
	        	</div>
	        </div>
	        <%--<div class="form-group">
	        	<label for="newpassowrd" class="col-sm-2 control-label">角色</label>
	        	<div class="col-sm-10">
	        		<input type="text" name="newpassword" class="form-control" readonly="readonly" value="${roles}" />
	        	</div>
	        </div>--%>
	        <div class="form-group">
	        	<label for="mobilePhone" class="col-sm-2 control-label">手机号码</label>
	        	<div class="col-sm-10">
	        		<input type="text" name="mobilePhone" class="form-control" readonly="readonly" value="${user.mobilePhone}" />
	        	</div>
	        </div>
	        <div class="form-group">
	        	<label for="email" class="col-sm-2 control-label">邮箱</label>
	        	<div class="col-sm-10">
	        		<input type="text" name="email" class="form-control" readonly="readonly" value="${user.email}" />
	        	</div>
	        </div>
	        <%-- <div class="form-group">
	        	<label for="newpassowrd" class="col-sm-2 control-label">角色</label>
	        	<div class="col-sm-10">
	        		<input type="text" name="newpassword" class="form-control" value="${user.userName}" />
	        	</div>
	        </div> --%>
	    </form>
	</div>
</body>
</html>