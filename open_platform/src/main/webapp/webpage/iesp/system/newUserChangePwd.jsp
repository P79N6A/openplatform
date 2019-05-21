<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.jeecgframework.web.system.manager.ClientManager,org.jeecgframework.web.system.pojo.base.Client"%><%
  Client client = ClientManager.getInstance().getClient();
  String keyStr1 = "";
  if(client != null){
	  keyStr1 = client.getPassKey();
  }
%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/webpage/common/sm.jsp"%>
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrapValidator.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/form.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap-dialog.css" %> type="text/css">
<script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrapValidator.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/form/jquery-form.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-notify.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-dialog.js" %> type="text/javascript"></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/aes/aes.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/aes/mode-ecb.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/md5/md5.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/aes/aesfunction.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
	    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
	    <input id="oldPassword1" type="hidden" name="oldpass"/>
		<input id="newPassword1" type="hidden" name="newpass"/>
	        <div class="form-group">
	        	<label for="password" class="col-sm-2 control-label">旧密码</label>
	        	<div class="col-sm-10">
	        		<input type="password" id="oldpassword" class="form-control" placeholder="请输入旧密码" 
	        		onfocus="focusInput('oldpassword')" onclick="focusInput('oldpassword')" onkeyup="changePwd(this,'aaa')"/>
	        	</div>
	        </div>
			<div class="form-group">
	        	<label for="newPassword1" class="col-sm-2 control-label">新密码</label>
	        	<div class="col-sm-10">
	        		<input type="password" id="newpassword1" class="form-control" onblur="check()" placeholder="请输入新密码" 
	        		onfocus="focusInput('newpassword1')" onclick="focusInput('newpassword1')" onkeyup="changePwd(this,'bbb')"/>
	        	</div>
	        </div>
	        <div class="form-group">
	        	<label for="newpassowrd" class="col-sm-2 control-label">确认密码</label>
	        	<div class="col-sm-10">
	        		<input type="password" id="newpassword" class="form-control" placeholder="请重新输入新密码" 
	        		onfocus="focusInput('newpassword')" onclick="focusInput('newpassword')" onkeyup="changePwd(this,'ccc')"/>
	        	</div>
	        </div>
	    </form>
	</div>
</body>
<script type="text/javascript">
var keyStr1 = "<%=keyStr1%>";

</script>
</html>