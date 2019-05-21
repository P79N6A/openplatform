<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="org.jeecgframework.web.system.manager.ClientManager,org.jeecgframework.web.system.pojo.base.Client"%>
<%
  Client client = ClientManager.getInstance().getClient();
  String keyStr1 = "";
  String randomStr="";
  if(client != null){
	  keyStr1 = client.getPassKey();
	  randomStr=client.getRandomStr();
  }
%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script type="text/javascript">
var keyStr1 = "<%=keyStr1%>";
</script>
<%@ include file="/webpage/common/sm.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src=<%= request.getContextPath() + "/js/basic/editDataSource.js" %>
	type="text/javascript"></script>
	<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
<script type="text/javascript">
	var dsType = ${dataSource.dsType}
	$("#dsType option[value=0" + dsType + "]").attr("selected","selected");
	var dsPwd = "${dataSource.dsPwd}";
	$("#dsPwd").val(dsPwd);
</script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="get"
				class="form-horizontal" role="form">
				<input type="text" id="dsPwd1" name="dsPwd1" hidden="hidden"/>
				<input type="hidden" id="dsId" name="dsId" value="${dataSource.dsId}" />
				<input type="hidden" id="dsState" name="dsState" value="${dataSource.dsState}" />
				<input type="hidden" id="createTime" name="createTime" value="${dataSource.createTime}" />
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据别名</label>
					<div class="col-sm-10">
						<input type="text" id="dsAlias" name="dsAlias"
							class="form-control" value="${dataSource.dsAlias}"  readonly="readonly" />
					</div>
				</div>
				<div class="form-group">
					<label for="system" class="col-sm-2 control-label">所属系统</label>
					<div class="col-sm-10">
						<select class="form-control" id="system" name="sysId">
							<c:forEach items="${sysInfos}" var="sys">
								<option value="${sys.sysId}" <c:if test="${sys.sysId==dataSource.sysId}">selected</c:if>>${sys.sysName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">数据类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="dsType" name="dsType">
							<c:forEach items="${dsTypes}" var="dsType">
                           		<option value="${dsType.value}" data-paramid="${dsType.paramsId}" <c:if test="${dsType.value==dataSource.dsType}">selected</c:if>>${dsType.name}</option>
                           	</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="system" class="col-sm-2 control-label">驱动类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="drvClass" name="drvClass">
							<c:forEach items="${driverClass}" var="dc">
                           		<option value="${dc.value}" <c:if test="${dc.value==dataSource.drvClass}">selected</c:if>>${dc.name}</option>
                           	</c:forEach>


						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据地址</label>
					<div class="col-sm-10">
						<input type="text" id="dsIp" name="dsIp" class="form-control"
							value="${dataSource.dsIp}" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据端口</label>
					<div class="col-sm-10">
						<input type="text" id="dsPort" name="dsPort" class="form-control"
							value="${dataSource.dsPort}" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">实例名</label>
					<div class="col-sm-10">
						<input type="text" id="dsSid" name="dsSid" class="form-control"
							value="${dataSource.dsSid}" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
						<input type="text" id="dsUser" name="dsUser" class="form-control"
							value="${dataSource.dsUser}" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">口令</label>
					<div class="col-sm-10">
						<input type="password" id="dsPwd"
							class="form-control" onfocus="focusInput('dsPwd')" onclick="focusInput('dsPwd')" onkeyup="changePwd(this,'aaa')" onblur="changePwd(this,'aaa')"/>
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="remark" rows="3">${dataSource.remark}</textarea>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/aes/aes.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/aes/mode-ecb.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/md5/md5.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/aes/aesfunction.js"%>></script>
</body>
<script type="text/javascript">
 var randomStr="<%=randomStr%>";
</script>
</html>