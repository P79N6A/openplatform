<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var flag = ${tableInfo.incrFlag}
	if(flag=="1"){
		$("#rncr").attr("style", "display:none");
	}
</script>

</head>
<body>
	<div>
		<div class="modal-body" id="add">
			<form id="addForm" name="addForm" method="get"
				class="form-horizontal read-only" role="form">
				<input type="text" id="tabId" name="tabId" hidden="hidden"
					value="${tableInfo.tabId }" />
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">表名称</label>
					<div class="col-sm-10">
						<input type="text" id="tabName" name="tabName"
							class="form-control" placeholder="请输入表名称"
							value="${tableInfo.tabName }" readonly="readonly" />
					</div>

				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">所属数据</label>
					<div class="col-sm-10">
						<select class="form-control" id="dsAlias" name="dsAlias"
							disabled="disabled">
							<option value=${tableInfo.dsId}>${tableInfo.dsAlias}</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">是否增量</label>
					<div class="col-sm-5" style="padding-top:6px">
						<c:if test="${tableInfo.incrFlag=='1' }">
							<input type="radio" name="incrFlag" id="incrFlag"
								checked="checked"   value="1"
								readonly="readonly">全量</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								
								</c:if>
						<c:if test="${tableInfo.incrFlag=='2' }">
							<input type="radio" name="incrFlag" id="incrFlag"
								 value="2" readonly="readonly"
								checked="checked">增量</input>
						</c:if>
					</div>
				</div>
				<div class="form-group" id="rncr">
					<label for="remark" class="col-sm-2 control-label">增量标识</label>
					<div class="col-sm-5">
						<input type="text" name="incrValue" id="incrValue"
							class="form-control" value="${tableInfo.incrValue }" readonly="readonly"/>
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
										placeholder="请输入列名称" value="${list.colName }"
										readonly="readonly" /></td>
									<td><select class="form-control" name="colType"
										disabled="disabled">
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
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="remark" name="remark" rows="3"
							readonly="readonly">${tableInfo.remark }</textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>