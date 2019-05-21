<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/interact/addMqInteract.js" %> type="text/javascript"></script>
<style>
	.table-hover > tbody > tr > td.success:hover, .table-hover > tbody > tr > th.success:hover, .table-hover > tbody > tr.success:hover > td, .table-hover > tbody > tr:hover > .success, .table-hover > tbody > tr.success:hover > th {
		background-color:#91f16b
	}
	.table > thead > tr > td.success, .table > tbody > tr > td.success, .table > tfoot > tr > td.success, .table > thead > tr > th.success, .table > tbody > tr > th.success, .table > tfoot > tr > th.success, .table > thead > tr.success > td, .table > tbody > tr.success > td, .table > tfoot > tr.success > td, .table > thead > tr.success > th, .table > tbody > tr.success > th, .table > tfoot > tr.success > th{
		background-color:#91f16b
	}
</style>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<div class="form-group ">
		    		<div class="col-sm-6">
		    			<div class="text-center">
			    			<label>源端主题</label>
			    		</div>
			    		<select class="bootstrap-select form-control" id="srcPosition" style="border:1px solid #ccc;" data-tableid="srcTopics">
			    			<option value="01">内网</option>
			    			<option value="03">外网</option>
			    		</select>
			    		<div style="border:1px #ccc solid;height:300px;overflow:auto">
			    			<table id="srcTopics" class="table table-striped table-bordered table-hover table-condensed">
			    				<thead>
			    					<tr>
			    						<th>主题名称</th>
			    						<th class="text-center">标签</th>
			    						<th class="text-center">编辑</th>
			    					</tr>
			    				</thead>
			    			</table>
			    		</div>
		    		</div>
		    		<div class="col-sm-6">
			    		<div class="text-center">
			    			<label>目的端主题</label>
			    		</div>
			    		<select class="bootstrap-select form-control" style="border:1px solid #ccc;" id="targetPosition" data-tableid="targetTopics">
			    			<option value="01">内网</option>
			    			<option value="03">外网</option>
			    		</select>
			    		<div style="border:1px #ccc solid;height:300px;overflow:auto">
			    			<table id="targetTopics" class="table table-striped table-bordered table-hover table-condensed">
			    				<thead>
			    					<tr>
			    						<th>主题名称</th>
			    						<th class="text-center">标签</th>
			    						<th class="text-center">编辑</th>
			    					</tr>
			    				</thead>
			    			</table>
			    		</div>
			    	</div>
		    	</div>
				<input type="hidden" id="srcTags" name="srcTags" />
				<input type="hidden" id="targetTags" name="targetTags" />
		    </form>
		</div>
	</div>
</body>
</html>