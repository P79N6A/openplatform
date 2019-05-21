<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src=<%= request.getContextPath() + "/js/basic/editDataSource.js" %>
	type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="get"
				class="form-horizontal" role="form">
				<input type="hidden" id="dsId" name="dsId"
					value="${data.dsId}" />
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">数据状态</label>
					<div class="col-sm-10">
						<select class="form-control" id="dsState" name="dsState">
							<c:if test="${data.dsState == '1'}">
								<option value="1">正常</option>
								<option value="2">测试</option>
								<option value="3">停用</option>
								<option value="4">锁定</option>
							</c:if>
							<c:if test="${data.dsState == '2'}">
								<option value="2">测试</option>
								<option value="1">正常</option>
								<option value="3">停用</option>
								<option value="4">锁定</option>
							</c:if>
							<c:if test="${data.dsState == '3'}">
								<option value="3">停用</option>
								<option value="1">正常</option>
								<option value="2">测试</option>
								<option value="4">锁定</option>
							</c:if>
							<c:if test="${data.dsState == '4'}">
								<option value="4">锁定</option>
								<option value="1">正常</option>
								<option value="2">测试</option>
								<option value="3">停用</option>
							</c:if>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-10">
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>