<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/basic/editTopic.js" %> type="text/javascript"></script>
<script type="text/javascript">
	var position = ${topic.position}
	$("#position option[value=0" + position + "]").attr("selected","selected");
	var tagSwitch = ${topic.tagSwitch};
	$("#tagSwitch option[value=" + tagSwitch + "]").attr("selected","selected");
</script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="id" name="id" value="${topic.id}" />
		    	<input type="hidden" id="state" name="state" value="${topic.state}" />
		    	<input type="hidden" id="createTime" name="createTime" value="${topic.createTime}" />
		        <div class="form-group">
		        	<label for="name" class="col-sm-2 control-label">主题名称</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="name" name="name" readonly="readonly" class="form-control" value="${topic.name}" />
		        	</div>
		        </div>
		        <div class="form-group">
					<label for="position" class="col-sm-2 control-label">所属区域</label>
					<div class="col-sm-10">
						<select class="form-control" id="position" name="position">
							<option value="01">内网</option>
							<option value="03">外网</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="system" class="col-sm-2 control-label">所属系统</label>
					<div class="col-sm-10">
						<select class="form-control" id="system" name="sysId">
							<c:forEach items="${sysInfos}" var="sys">
                           		<option value="${sys.sysId}" <c:if test="${sys.sysId==topic.sysId}">selected</c:if>>${sys.sysName}</option>
                           	</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="tagSwitch" class="col-sm-2 control-label">需要标签</label>
					<div class="col-sm-10">
						<select class="form-control" id="tagSwitch" name="tagSwitch">
							<option value="0">不需要</option>
							<option value="1">需要</option>
						</select>
					</div>
				</div>
				<div class="form-group">
		        	<label for="name" class="col-sm-2 control-label">消费标识</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="consumerId" name="consumerId" value="${topic.consumerId}" class="form-control" placeholder="请输入消费者标识" />
		        	</div>
		        </div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="remark" rows="3">${topic.remark}</textarea>
					</div>
				</div>
		    </form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>