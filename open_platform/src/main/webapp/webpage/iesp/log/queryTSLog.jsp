<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="get"
				class="form-horizontal read-only" role="form">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">日志类型</label>
					<div class="col-sm-10">
						<input type="text" id="logType" name="logType" class="form-control"
							value="${logType}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">日志内容</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="logcontent" rows="3"
							readonly="readonly">${tsLog.logcontent}</textarea>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">操作IP</label>
					<div class="col-sm-10">
						<input type="text" id="note" name="note" class="form-control"
							value="${tsLog.note}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">操作人</label>
					<div class="col-sm-10">
						<input type="text" id="updateBy" name="updateBy" class="form-control"
							value="${tsUser.userName}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">操作时间</label>
					<div class="col-sm-10">
						<input type="text" id="operatetime" name="operatetime"
							 class="form-control" value="${tsLog.operatetime}"
							readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">浏览器</label>
					<div class="col-sm-10">
						<input type="text" id="broswer" name="broswer" class="form-control"
							value="${tsLog.broswer}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">操作结果</label>
					<div class="col-sm-10">
						<input type="text" id="broswer" name="broswer" class="form-control"
							value="${result}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="isSystem" class="col-sm-2 control-label">日志类别</label>
					<div class="col-sm-10">
						<input type="text" id="isSystem" name="isSystem" class="form-control"
							value="${isSystem}" readonly="readonly" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>