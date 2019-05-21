<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css"> 
.autoScroll 
{ 
width:300px; 
height:100px; 
overflow:auto; 
}
</style>
<script src=<%= request.getContextPath() + "/js/basic/addTableInfo.js" %>
	type="text/javascript"></script>
	
</head>
<body>
	<div>
		<div class="modal-body" id="add">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label"
						style="display:none">Id</label>
					<div class="col-sm-10">
						<input type="text" id="tabId" name="tabId" hidden="hidden" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">表名称</label>
					<div class="col-sm-10">
						<input type="text" id="tabName" name="tabName"
							class="form-control" placeholder="请输入表名称" />
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">所属数据</label>
					<div class="col-sm-10">
						<select class="form-control" id="dsAlias" name="dsAlias">
							<c:forEach items="${dataSource}" var="data">
                           		<option value=${data.dsId}>${data.dsAlias}</option>
                           	</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">是否增量</label>
					<div class="col-sm-5" style="padding-top:6px">
						<input type="radio" name="incrFlag" checked="checked" onclick="onChange()" value="1">全量</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="incrFlag" onclick="change()" value="2">增量</input>
					</div>
				</div>
				<div class="form-group" id="rncr" style="display:none">
				<label for="remark" class="col-sm-2 control-label">增量标识</label>
					<div class="col-sm-5">
						<input type="text" name="incrValue" id="incrValue" class="form-control" placeholder="请输入增量标识" />
					</div>
				</div>
				 <div class="form-group" >
					<label for="position" class="col-sm-2 control-label">列名称</label>
					<input type="hidden" id="columnInfos"  name="columnInfos"/>
					<div class="col-sm-10" style="max-height:200px;overflow:auto;">
						<table class="table table-bordered" id="addTable">
							<tr>
								<td>
									<input type="text"   name="colName"  class="form-control" placeholder="请输入列名称" />
								</td>
								<td>
									<select class="form-control"  name="colType">
										<option value="1">Integer</option>
										<option value="2">Varchar</option>
										<option value="3">Double</option>
										<option value="4">Datetime</option>
										<option value="5">TIMESTAMP</option>
										<option value="6">Text</option>
									</select>
								</td>
								<td class="text-center">
									<button class='btn btn-xs btn-success form-control'  title='添加' onclick="addColumn(this)"><span class='glyphicon glyphicon-plus'></span></button>
									<button class='btn btn-xs btn-danger form-control'  title='删除' style='display:none'onclick="deleteColumn(this)"><span class='glyphicon glyphicon-remove'></span></button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="remark" name="remark" rows="3"></textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>