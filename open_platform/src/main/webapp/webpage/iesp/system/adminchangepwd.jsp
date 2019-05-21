<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ include file="/webpage/common/sm.jsp"%>
<%@page
	import="org.jeecgframework.web.system.manager.ClientManager,org.jeecgframework.web.system.pojo.base.Client"%>
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
<html>
<head>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
<title>用户信息</title>
<script src=<%= request.getContextPath() + "/js/system/editUser.js" %>
	type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<input type="text" id="id" name="id" hidden="hidden" value="${id }">
				<input id="newPassword" type="hidden" name="newpass"/> 
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
						<input type="password" id="password"
							class="form-control" placeholder="请输入新密码"  onfocus="focusInput('password')" onclick="focusInput('password')" onkeyup="changePwd(this,'aaa')"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">重复密码</label>
					<div class="col-sm-10">
						<input type="password" id="password1" 
							class="form-control" placeholder="请重复输入密码"  onfocus="focusInput('password1')" onclick="focusInput('password1')" onkeyup="changePwd(this,'bbb')"/>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
var keyStr1 = "<%=keyStr1%>";
var randomStr = "<%=randomStr%>";
</script>
</html>