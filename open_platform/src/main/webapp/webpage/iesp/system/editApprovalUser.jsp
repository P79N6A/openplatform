<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/system/editApprovalUser.js" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="baseUserId" name="baseUserId" value="${baseUser.id}" />
		        <div class="form-group">
		        	<label for="userName" class="col-sm-2 control-label">用户名</label>
		        	<div class="col-sm-8" style="padding-right:0px">
		        		<input type="text" id="userName" name="userName" readonly="readonly" class="form-control" value="${baseUser.userName}" />
		        	</div>
		        </div>
		       <div class="form-group">
					<label for="checkStatus" class="col-sm-2 control-label">审核状态</label>
					<div class="col-sm-8" style="padding-right:0px">
						<select class="form-control" id="checkStatus" name="checkStatus">
								<option selected value="2">审核通过</option>
								<option  value="3">审核未通过</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="approvalOpinion" class="col-sm-2 control-label ">审核意见</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="approvalOpinion" name="approvalOpinion" rows="3">${baseUser.approvalOpinion}</textarea>
					</div>
				</div>
		    </form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>