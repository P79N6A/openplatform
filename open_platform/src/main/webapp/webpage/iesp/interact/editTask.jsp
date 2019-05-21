<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/interact/editTask.js" %>
	type="text/javascript">
</script>
<script type="text/javascript">
	var type = ${task.type}
	if(type=="2"){
	$("#jieshu").attr("style", "display:block");
	$("#kaishi").attr("style", "display:block");
	$("#pinlv").attr("style", "display:block");
	$("#pinlvType").attr("style", "display:block");
	}
</script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label"
						style="display:none">Id</label>
					<div class="col-sm-10">
						<input type="text" id=id name="id" hidden="hidden"
							value="${task.id }" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">任务别名</label>
					<div class="col-sm-10">
						<input type="text" id="alias" name="alias" class="form-control"
							placeholder="请输入任务别名" value="${task.alias }" />
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">交互数据</label>
					<div class="col-sm-10">
						<select class="form-control" id="dbConfigObjName"
							name="dbConfigObjId">
							<option value="">请选择交互配置数据</option>
							<c:forEach items="${confSyncs}" var="con">
								<option value="${con.csId}"<c:if test="${con.csId==task.dbConfigObjId}">selected</c:if>>${con.csName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">任务类型</label>
					<div class="col-sm-10" style="padding-top:6px">
						<c:if test="${task.type=='1' }">
							<input type="radio" name="type" id="type"
								checked="checked" onclick="change()" value="1">手动</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="type" id="type"
								onclick="onChange()" value="2">自动</input>
						</c:if>
						<c:if test="${task.type=='2' }">
							<input type="radio" name="type" id="type"
								onclick="change()" value="1">手动</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="type" id="type"
								onclick="onChange()" value="2" checked="checked">自动</input>
						</c:if>
					</div>
				</div>
				<div class="form-group" style="display:none" id=kaishi>
					<label label for="beginDate" class="col-sm-2 control-label">开始时间</label>
					<div class="col-sm-10">
						<div class="jeitem">
							<div class="jeinpbox">
								<input type="text" class="jeinput" id="beginDate"
									name="beginDate" placeholder="请选择开始时间"
									value="${task.beginDate}" autocomplete="off"/>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group " style="display:none" id="jieshu">
					<label label for="endDate" class="col-sm-2 control-label">结束时间</label>
					<div class="col-sm-10">
						<div class="jeitem">
							<div class="jeinpbox">
								<input type="text" class="jeinput" id="endDate" name="endDate"
									placeholder="请选择结束时间" value="${task.endDate }" autocomplete="off"/>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" style="display:none" id="pinlv">
					<label for="name" class="col-sm-2 control-label">频率</label>
					<div class="col-sm-10">
						<input type="number" id="frequence" name="frequence"
							class="form-control" placeholder="请输入频率"
							value="${task.frequence }" />
					</div>
				</div>
				<div class="form-group" style="display:none" id="pinlvType">
					<label for="name" class="col-sm-2 control-label">频率类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="freqType" name="freqType">
							<c:if test="${task.freqType=='' || task.freqType==null}">
								<option value="1">秒</option>
								<option value="2">分</option>
								<option value="3">时</option>
								<option value="4">天</option>
								<option value="5">周</option>
								<option value="6">月</option>
								<option value="7">年</option>
							</c:if>
							<c:if test="${task.freqType=='1' }">
								<option value="1">秒</option>
								<option value="2">分</option>
								<option value="3">时</option>
								<option value="4">天</option>
								<option value="5">周</option>
								<option value="6">月</option>
								<option value="7">年</option>
							</c:if>
							<c:if test="${task.freqType=='2' }">
								<option value="2">分</option>
								<option value="1">秒</option>
								<option value="3">时</option>
								<option value="4">天</option>
								<option value="5">周</option>
								<option value="6">月</option>
								<option value="7">年</option>
							</c:if>
							<c:if test="${task.freqType=='3' }">
								<option value="3">时</option>
								<option value="1">秒</option>
								<option value="2">分</option>
								<option value="4">天</option>
								<option value="5">周</option>
								<option value="6">月</option>
								<option value="7">年</option>
							</c:if>
							<c:if test="${task.freqType=='4' }">
								<option value="4">天</option>
								<option value="1">秒</option>
								<option value="2">分</option>
								<option value="3">时</option>
								<option value="5">周</option>
								<option value="6">月</option>
								<option value="7">年</option>
							</c:if>
							<c:if test="${task.freqType=='5' }">
								<option value="5">周</option>
								<option value="1">秒</option>
								<option value="2">分</option>
								<option value="3">时</option>
								<option value="4">天</option>
								<option value="6">月</option>
								<option value="7">年</option>
							</c:if>
							<c:if test="${task.freqType=='6' }">
								<option value="6">月</option>
								<option value="1">秒</option>
								<option value="2">分</option>
								<option value="3">时</option>
								<option value="4">天</option>
								<option value="5">周</option>
								<option value="7">年</option>
							</c:if>
							<c:if test="${task.freqType=='7' }">
								<option value="7">年</option>
								<option value="1">秒</option>
								<option value="2">分</option>
								<option value="3">时</option>
								<option value="4">天</option>
								<option value="5">周</option>
								<option value="6">月</option>
							</c:if>
						</select>
					</div>
				</div>
				<div class="form-group" id="flag">
					<label for="remark" class="col-sm-2 control-label">是否增量</label>
					<div class="col-sm-5" style="padding-top:6px">
						<c:if test="${task.incrFlag=='1' }">
							<input type="radio" name="incrFlag" id="incrFlag"
								checked="checked"  value="1">全量</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="incrFlag" id="incrFlag"
							 value="2" >增量</input>
					</c:if>
						<c:if test="${task.incrFlag=='2' }">
						<input type="radio" name="incrFlag" id="incrFlag"
								 value="1">全量</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="incrFlag" id="incrFlag"
							 value="2" checked="checked">增量</input>
					</c:if>
					</div>
				</div>
				<div class="form-group" id="rncr" style="display:none">
					<label for="remark" class="col-sm-2 control-label"
						style="display:none">增量标识</label>
					<div class="col-sm-5">
						<input type="text" name="incrValue" id="incrValue" value="${task.incrValue }" hidden="hidden" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>