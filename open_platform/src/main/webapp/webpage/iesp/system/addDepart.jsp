<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/system/addDepart.js" %> type="text/javascript"></script>
<style>
.btn-sm{
	padding:2px 8px;
}
</style>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<div class="form-group">
					<label for="departname" class="col-sm-2 control-label">组织名称</label>
					<div class="col-sm-10">
						<input type="text" name="departname"
							class="form-control" placeholder="请输入组织名称" />
					</div>
				</div>
				<div class="form-group">
					<label for="orgCode" class="col-sm-2 control-label">组织编码</label>
					<div class="col-sm-10">
						<input type="text" name="orgCode"
							class="form-control" placeholder="请输入组织编码" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">上级组织</label>
					<div class="col-sm-10">
						<input id="departName"  name="departName"type="text" value="${msg}" class="form-control"readonly="readonly" onclick="openDepartmentSelect()"/>
						<input id="orgIds" name="TSPDepart.id" type="hidden" value="${parent.id}"/>
					</div>
				</div>
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label">地址</label>
					<div class="col-sm-10">
						<input type="text" id="address" name="address"
							class="form-control" placeholder="请输入地址" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>