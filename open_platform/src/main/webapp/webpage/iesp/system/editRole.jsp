<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/system/editRole.js" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="id" name="id" value="${role.id}" />
		    	<input type="hidden" id="createBy" name="createBy" value="${role.createBy}" />
		    	<input type="hidden" id="createDate" name="createDate" value="${role.createDate}" />
		    	<input type="hidden" id="createName" name="createName" value="${role.createName}" />
		        <div class="form-group">
		        	<label for="roleCode" class="col-sm-2 control-label">角色编码</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="roleCode" name="roleCode" value="${role.roleCode}" class="form-control" placeholder="请输入角色编码" />
		        	</div>
		        </div>
				<div class="form-group">
		        	<label for="roleName" class="col-sm-2 control-label">角色名称</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="roleName" name="roleName" value="${role.roleName}" class="form-control" placeholder="请输入角色名称" />
		        	</div>
		        </div>
		    </form>
		</div>
	</div>
</body>
</html>