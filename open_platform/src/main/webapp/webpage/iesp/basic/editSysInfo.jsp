<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/basic/editSysInfo.js" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="sysId" name="sysId" value="${sysInfo.sysId}" />
		    	<input type="hidden" id="state" name="state" value="${sysInfo.state}" />
		    	<input type="hidden" id="createTime" name="createTime" value="${sysInfo.createTime}" />
		        <div class="form-group">
		        	<label for="sysName" class="col-sm-2 control-label">系统名称</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="sysName" name="sysName" readonly="readonly" class="form-control" value="${sysInfo.sysName}" />
		        	</div>
		        </div>
		        <div class="form-group"> 
		        	<label for="sysCode" class="col-sm-2 control-label">系统编号</label>
		        	<div class="col-sm-10">
		        		<input type="text" id="sysCode" name="sysCode" class="form-control" value="${sysInfo.sysCode}" />
		        	</div>
		        </div>
		        <div class="form-group">
					<label for="position" class="col-sm-2 control-label">所属区域</label>
					<div class="col-sm-10">
						<select class="form-control" id="position" name="position">
							<c:if test="${sysInfo.position == '01'}">
								<option selected value="01">内网</option>
								<option value="03">外网</option>
							</c:if>
							<c:if test="${sysInfo.position == '03'}">
								<option value="01">内网</option>
								<option selected value="03">外网</option>
							</c:if>	
						</select>
					</div>
				</div>

				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="remark" rows="3">${sysInfo.remark}</textarea>
					</div>
				</div>
		    </form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>