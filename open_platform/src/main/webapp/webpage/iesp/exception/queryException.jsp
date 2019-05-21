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
					<label for="name" class="col-sm-2 control-label">异常标题</label>
					<div class="col-sm-10">
						<input type="text" id="title" name="title" class="form-control"
							value="${exception.title}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">异常类型</label>
					<div class="col-sm-10">
					<select class="form-control" id="exceptionLevel" name="exceptionLevel"
							disabled="disabled">
							<c:if test="${exception.exceptionLevel==1 }">
								<option value="1">登陆</option>
							</c:if>
							<c:if test="${exception.exceptionLevel==2 }">
								<option value="2">退出</option>
							</c:if>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">异常内容</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="exceptionContent" rows="3"
							readonly="readonly">${exception.exceptionContent}</textarea>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">创建者</label>
					<div class="col-sm-10">
						<input type="text" id="createUser" name="note" class="form-control"
							value="${exception.createUser}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">操作时间</label>
					<div class="col-sm-10">
						<input type="text" id="createTime" name="createTime" class="form-control"
							value="${exception.createTime}" readonly="readonly" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>