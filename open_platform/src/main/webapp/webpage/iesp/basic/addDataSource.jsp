<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%@ include file="/webpage/common/sm.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/basic/addDataSource.js" %>
	type="text/javascript"></script>
	
<script type="text/javascript">
var keyStr1 = "<%=keyStr1%>";
</script>

<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<input type="password" id="dsPwd1" name="dsPwd1"  hidden="hidden"/>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label"
						style="display:none">Id</label>
					<div class="col-sm-10">
						<input type="text" id="dsId" name="dsId" hidden="hidden" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据别名</label>
					<div class="col-sm-10">
						<input type="text" id="dsAlias" name="dsAlias"
							class="form-control" placeholder="请输入数据别名" />
					</div>
				</div>
				 <div class="form-group">
					<label for="position" class="col-sm-2 control-label">所属系统</label>
					<div class="col-sm-10">
						<select class="form-control" id="system" name="sysId">
							<c:forEach items="${sysInfos}" var="sys">
                           		<option value=${sys.sysId}>${sys.sysName}</option>
                           	</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">数据类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="dsType" name="dsType">
							<c:forEach items="${params}" var="params">
                           		<option value=${params.value} data-paramid="${params.paramsId}">${params.name}</option>
                           	</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">驱动类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="drvClass" name="drvClass">
							<%-- <c:forEach items="${list}" var="list">
                           		<option value="${list.paramsId}" style="display:none">${list.name}</option>
                           	</c:forEach> --%>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据地址</label>
					<div class="col-sm-10">
						<input type="text" id="dsIp" name="dsIp" class="form-control"
							placeholder="请输入数据地址" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">数据端口</label>
					<div class="col-sm-10">
						<input type="text" id="dsPort" name="dsPort" class="form-control"
							placeholder="请输入数据端口" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">实例名</label>
					<div class="col-sm-10">
						<input type="text" id="dsSid" name="dsSid" class="form-control"
							placeholder="请输入实例名" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
						<input type="text" id="dsUser" name="dsUser" class="form-control"
							placeholder="请输入用户名"  value=""/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">口令</label>
					<div class="col-sm-10">
						<input type="password" id="dsPwd" class="form-control"
							placeholder="请输入口令"  onfocus="focusInput('dsPwd')" onclick="focusInput('dsPwd')" onkeyup="changePwd(this,'aaa')"/>
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
<script type="text/javascript">
	var randomStr="<%=randomStr%>";
</script>
</html>