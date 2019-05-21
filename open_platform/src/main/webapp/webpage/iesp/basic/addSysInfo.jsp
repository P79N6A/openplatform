<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/basic/addSysInfo.js" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		 <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		     <div class="form-group">
		        <label for="sysname" class="col-sm-2 control-label">系统名称</label>
		        <div class="col-sm-10">
		        	<input type="text" id="sysname" name="sysname" class="form-control" placeholder="请输入系统名称" />
		        </div>
		     </div>
		     <div class="form-group">
		        <label for="syscode" class="col-sm-2 control-label">系统编号</label>
		        <div class="col-sm-10">
		        	<input type="text" id="syscode" name="syscode" class="form-control" placeholder="请输入系统编号" />
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
				<label for="remark" class="col-sm-2 control-label">备注</label>
				<div class="col-sm-10">
					<textarea class="form-control" name="remark" rows="3"></textarea>
				</div>
			</div>	 
		 </form>
		</div>
	</div>
	<div class='notifications'></div>	
</body>
</html>