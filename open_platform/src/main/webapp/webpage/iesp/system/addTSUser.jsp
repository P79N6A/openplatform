<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page
	import="org.jeecgframework.web.system.manager.ClientManager,org.jeecgframework.web.system.pojo.base.Client"%>
<%
	Client client = ClientManager.getInstance().getClient();
	String keyStr1 = "";
	String randomStr="";
	if (client != null) {
		keyStr1 = client.getPassKey();
		randomStr=client.getRandomStr();
	}
%>
<html>
<%@ include file="/webpage/common/sm.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%=request.getContextPath() + "/js/system/addUser.js"%>
	type="text/javascript"></script>
<script type="text/javascript"
	src=<%=request.getContextPath() + "/plug-in/aes/aes.js"%>></script>
<script type="text/javascript"
	src=<%=request.getContextPath() + "/plug-in/aes/mode-ecb.js"%>></script>
<script type="text/javascript"
	src=<%=request.getContextPath() + "/plug-in/md5/md5.js"%>></script>
<script type="text/javascript"
	src=<%=request.getContextPath() + "/plug-in/aes/aesfunction.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
<style>
.btn-sm {
	padding: 2px 8px;
}
</style>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<input id="newPassword" type="hidden" name="newpass"/> 
				<div class="form-group">
					<label for="userName" class="col-sm-2 control-label">用户账号</label>
					<div class="col-sm-10">
						<input type="text" id="userName"name="userName" class="form-control"
							placeholder="请输入用户账号" />
					</div>
				</div>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">真实姓名</label>
					<div class="col-sm-10">
						<input type="text" id="realName" name="realName"
							class="form-control" placeholder="请输入真实姓名" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
						<input type="password" id="password"
							class="form-control" placeholder="请输入密码"  onfocus="focusInput('password')" onclick="focusInput('password')" onkeyup="changePwd(this,'aaa')"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">重复密码</label>
					<div class="col-sm-10">
						<input type="password" id="password1"
							class="form-control" placeholder="请重复输入密码"  onfocus="focusInput('password1')" onclick="focusInput('password1')" onkeyup="changePwd(this,'bbb')"/>
					</div>
				</div>
				<div class="form-group">
					<label for="departName" class="col-sm-2 control-label">组织机构</label>
					<div class="col-sm-10">
						<input id="departName" name="departName" type="text"
							class="form-control" readonly="readonly" onclick="openDepartmentSelect()"/>
                        <input id="orgIds" name="orgIds" type="hidden" />
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label for="apiGroup" class="col-sm-2 control-label">能力中心</label>--%>
					<%--<div class="col-sm-10">--%>
						<%--<input id="apiGroup" name="apiGroup" type="text"--%>
							   <%--class="form-control" readonly="readonly" onclick="openGroupSelect()"/>--%>
						<%--<input id="groupIds" name="groupIds" type="hidden" />--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="form-group">
					<label for="roleName" class="col-sm-2 control-label">角色</label>
					<div class="col-sm-10">
                        <input id="roleName" name="userKey" class="form-control"
                                readonly="readonly" onclick="openRoleSelect()"/>
						<input id="roleid" name="roleid" type="hidden" />
					</div>
				</div>
				<div class="form-group">
					<label for="mobilePhone" class="col-sm-2 control-label">手机号码</label>
					<div class="col-sm-10">
						<input type="number" id="mobilePhone" name="mobilePhone"
							class="form-control" placeholder="请输入手机号码" />
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label for="officePhone" class="col-sm-2 control-label">办公电话</label>--%>
					<%--<div class="col-sm-10">--%>
						<%--<input type="text" id="officePhone" name="officePhone"--%>
							<%--class="form-control" placeholder="请输入办公电话" />--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">常用邮箱</label>
					<div class="col-sm-10">
						<input type="text" id="email" name="email" class="form-control"
							placeholder="请输入常用邮箱" />
					</div>
				</div>
				<%--<div class="form-group">
					<label for="name" class="col-sm-2 control-label">访问IP</label>
					<div class="col-sm-10">
						<input type="text" id="userIp" name="userIp" class="form-control"
							placeholder="请输入访问IP,多个IP地址请用英文逗号,分割" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">开始时间</label>
					<div class="col-sm-10">
						<input type="text" id="beginDate" name="beginDate"
							class="form-control" placeholder="请输入访问开始时间" autocomplete="off" />
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">结束时间</label>
					<div class="col-sm-10">
						<input type="text" id="endDate" name="endDate"
							class="form-control" placeholder="请输入访问结束时间" autocomplete="off" />
					</div>
				</div>
				<div class="form-group">
					<label for="type" class="col-sm-2 control-label">用户类型</label>
					<div class="col-sm-10">
						<input type="radio" name="type" checked="checked" value="1">长期</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="type" value="0">临时</input>
					</div>
				</div>--%>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
<script type="text/javascript">
	function setOrgIds() {
	}
	$(function() {
		$("#departname").prev().hide();
	});
</script>
<script type="text/javascript">
var keyStr1 = "<%=keyStr1%>";
var randomStr="<%=randomStr%>"
</script>
</html>