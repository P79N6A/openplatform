<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var dsType = ${dataSource.dsType}
	$("#dsType option[value=0" + dsType + "]").attr("selected", "selected");
</script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="get"
				class="form-horizontal read-only" role="form">
				<input type="hidden" id="dsId" name="dsId"
					value="${dataSource.dsId}" />
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据别名</label>
					<div class="col-sm-10">
						<input type="text" id="dsAlias" name="dsAlias"
							class="form-control" value="${dataSource.dsAlias}"
							readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="system" class="col-sm-2 control-label">所属系统</label>
					<div class="col-sm-10">
						<select class="form-control" id="system" name="sysId"
							disabled="disabled">
							<c:forEach items="${sysInfos}" var="sys">
								<option value="${sys.sysId}" <c:if test="${sys.sysId==dataSource.sysId}">selected</c:if>>${sys.sysName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">数据类型</label>
					<div class="col-sm-10">
						<input type="text" id="dsAlias" name="dsAlias"
							class="form-control" value="${dsType}"
							readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="system" class="col-sm-2 control-label">驱动类型</label>
					<div class="col-sm-10">
						<input type="text" id="dsAlias" name="dsAlias"
							class="form-control" value="${dataSource.drvClass}"
							readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据地址</label>
					<div class="col-sm-10">
						<input type="text" id="dsIp" name="dsIp" class="form-control"
							value="${dataSource.dsIp}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据端口</label>
					<div class="col-sm-10">
						<input type="text" id="dsPort" name="dsPort" class="form-control"
							value="${dataSource.dsPort}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">实例名</label>
					<div class="col-sm-10">
						<input type="text" id="dsSid" name="dsSid" class="form-control"
							value="${dataSource.dsSid}" readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
						<input type="text" id="dsUser" name=""
							dsUser"" class="form-control" value="${dataSource.dsUser}"
							readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="remark" rows="3"
							readonly="readonly">${dataSource.remark}</textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>