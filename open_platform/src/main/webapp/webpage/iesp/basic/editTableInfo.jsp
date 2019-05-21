<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%=request.getContextPath() + "/js/basic/editTableInfo.js"%>
	type="text/javascript"></script>
<script type="text/javascript">
	var dsId = ${tableInfo.dsId}
	$("#dsAlias option[value=" + dsId + "]").attr("selected", "selected");
</script>
<script type="text/javascript">
function change(){
	$("#rncr").show();
}
function onChange(){
	$("#rncr").hide();
}

</script>
<script type="text/javascript">
	var flag = ${tableInfo.incrFlag}
	if(flag=="1")
	$("#rncr").attr("style", "display:none");
</script>
</head>
<body>
	<div>
		<div class="modal-body" id="add">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<input type="text" id="tabId" name="tabId" hidden="hidden" value="${tableInfo.tabId }" />
				<input type="text" id="state" name="state" hidden="hidden" value="${tableInfo.state }" />
				<input type="text" id="createTime" name="createTime" hidden="hidden" value="${tableInfo.createTime }" />
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">表名称</label>
					<div class="col-sm-10">
						<input type="text" id="tabName" name="tabName"
							class="form-control" readonly="readonly"
							value="${tableInfo.tabName }" />
					</div>

				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">所属数据</label>
					<div class="col-sm-10">
						<input name="dsId" value="${tableInfo.dsId}" type="hidden"/>
						<input type="text" id="dsALias" name="dsALias"
							class="form-control" readonly="readonly"
							value="${tableInfo.dsAlias }" />
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">是否增量</label>
					<div class="col-sm-5" style="padding-top:6px">
						<c:if test="${tableInfo.incrFlag=='1' }">
							<input type="radio" name="incrFlag" id="incrFlag"
								checked="checked" onclick="onChange()" value="1">全量</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="incrFlag" id="incrFlag"
							onclick="change()" value="2" >增量</input>
					</c:if>
						<c:if test="${tableInfo.incrFlag=='2' }">
						<input type="radio" name="incrFlag" id="incrFlag"
								onclick="onChange()" value="1">全量</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="incrFlag" id="incrFlag"
							onclick="change()" value="2" checked="checked">增量</input>
					</c:if>
					</div>
				</div>
				<div class="form-group" id="rncr" >
				<label for="remark" class="col-sm-2 control-label">增量标识</label>
					<div class="col-sm-5">
						<input type="text" name="incrValue" id="incrValue" class="form-control"  value="${tableInfo.incrValue }"/>
					</div>
				</div>

				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">列名称</label> <input
						type="hidden" id="columnInfos" name="columnInfos" />
					<div class="col-sm-10" style="max-height:200px;overflow:auto;">
						<table class="table table-bordered" id="addTable">
							<c:forEach items="${ list}" var="list">
								<tr>
									<td><input type="text" name="colName" class="form-control"
										placeholder="请输入列名称" value="${list.colName }" /></td>
									<td><select class="form-control" name="colType">
											<c:if test="${list.colType=='1'}">
												<option value="1">Integer</option>
												<option value="2">Varchar</option>
												<option value="3">Double</option>
												<option value="4">Datetime</option>
												<option value="5">TIMESTAMP</option>
												<option value="6">Text</option>
											</c:if>
											<c:if test="${list.colType=='2'}">
												<option value="2">Varchar</option>
												<option value="1">Integer</option>
												<option value="3">Double</option>
												<option value="4">Datetime</option>
												<option value="5">TIMESTAMP</option>
												<option value="6">Text</option>
											</c:if>
											<c:if test="${list.colType=='3'}">
												<option value="3">Double</option>
												<option value="1">Integer</option>
												<option value="2">Varchar</option>
												<option value="4">Datetime</option>
												<option value="5">TIMESTAMP</option>
												<option value="6">Text</option>
											</c:if>
											<c:if test="${list.colType=='4'}">
												<option value="4">Datetime</option>
												<option value="1">Integer</option>
												<option value="2">Varchar</option>
												<option value="3">Double</option>
												<option value="5">TIMESTAMP</option>
												<option value="6">Text</option>
											</c:if>
											<c:if test="${list.colType=='5'}">
												<option value="5">TIMESTAMP</option>
												<option value="1">Integer</option>
												<option value="2">Varchar</option>
												<option value="3">Double</option>
												<option value="4">Datetime</option>
												<option value="6">Text</option>
											</c:if>
											<c:if test="${list.colType=='6'}">
												<option value="6">Text</option>
												<option value="1">Integer</option>
												<option value="2">Varchar</option>
												<option value="3">Double</option>
												<option value="4">Datetime</option>
												<option value="5">TIMESTAMP</option>
											</c:if>
									</select></td>
									<td class="text-center">
										<button class='btn btn-xs btn-success form-control'
											name="add-btn" title='添加' style='display:none'
											onclick="addColumn(this)">
											<span class='glyphicon glyphicon-plus'></span>
										</button>
										<button class='btn btn-xs btn-danger form-control'
											name="delete-btn" title='删除' onclick="deleteColumn(this)">
											<span class='glyphicon glyphicon-remove'></span>
										</button>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="remark" name="remark" rows="3">${tableInfo.remark }</textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>