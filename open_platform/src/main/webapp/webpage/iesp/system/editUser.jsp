<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page
	import="org.jeecgframework.web.system.manager.ClientManager,org.jeecgframework.web.system.pojo.base.Client"%>
<%
Client client = ClientManager.getInstance().getClient();
String randomStr="";
if (client != null) {
	randomStr=client.getRandomStr();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.btn-sm{
	padding:2px 8px;
}
</style>
<script src=<%= request.getContextPath() + "/js/system/editUser.js" %> type="text/javascript"></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post"
				class="form-horizontal" role="form">
				<input type="hidden" id="id" name="id" value="${user.id }"></input>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">用户账号</label>
					<div class="col-sm-10">
						<input type="text" name="userName"
							class="form-control" placeholder="请输入用户账号"  value="${user.userName }" readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">真实姓名</label>
					<div class="col-sm-10">
						<input type="text" id="realName" name="realName"
							class="form-control" placeholder="请输入真实姓名"  value="${user.realName }"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">组织机构</label>
					<div class="col-sm-10">
						<input id="departName"  type="text" class="form-control"readonly="readonly" value="${depart.departname}" onclick="openDepartmentSelect()">
						<input id="orgIds" name="orgIds" type="hidden" value="${user.departid}">
					</div>
				</div>
				<div class="form-group">
					<label for="role" class="col-sm-2 control-label">角色</label>
					<div class="col-sm-10">
						<input id="roleCodes" type="hidden"  value="${roleCodes}"/>
		                <input id="roleid"  name="roleid" type="hidden" value="${roleIds}"/>
       					<c:choose>
       						<c:when test="${roleCodes=='admin' || roleCodes=='audit'}">
								<input id="roleName"  name="userKey" class="form-control" readonly="readonly" value="${roleNames}"/>
       						</c:when>
       						<c:otherwise>
								<input id="roleName"  name="userKey" class="form-control" readonly="readonly" value="${roleNames}" onclick="openRoleSelect()"/>
       						</c:otherwise>
       					</c:choose>
					</div>
				</div>
				<div class="form-group">
					<label for="mobilePhone" class="col-sm-2 control-label">手机号码</label>
					<div class="col-sm-10">
						<input type="number" id="mobilePhone" name="mobilePhone" autofocus="autofocus"
							class="form-control" placeholder="请输入手机号码"  value="${user.mobilePhone }"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">办公电话</label>
					<div class="col-sm-10">
						<input type="text" id="officePhone" name="officePhone"
							class="form-control" placeholder="请输入办公电话"  value="${user.officePhone }"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">常用邮箱</label>
					<div class="col-sm-10">
						<input type="text" id="email" name="email" class="form-control"
							placeholder="请输入常用邮箱"  value="${user.email }"/>
					</div>
				</div>
				<%--<div class="form-group">
					<label for="name" class="col-sm-2 control-label">用户Ip</label>
					<div class="col-sm-10">
						<input type="text" id="userIp" name="userIp" class="form-control"
							placeholder="请输入用户Ip"  value="${user.userIp }"/>
					</div>
				</div>--%>
				<%--<div class="form-group">
					<label for="name" class="col-sm-2 control-label">开始时间</label>
					<div class="col-sm-10">
						<input type="text" id="beginDate" name="beginDate" class="form-control"
							placeholder="请输入用户访问开始时间"  value="${user.beginDate}:00"autocomplete="off"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">结束时间</label>
					<div class="col-sm-10">
						<input type="text" id="endDate" name="endDate" class="form-control"
							placeholder="请输入用户访问结束时间"  value="${user.endDate}:00"autocomplete="off"/>
					</div>
				</div>--%>
				<%--<div class="form-group">
					<label for="type" class="col-sm-2 control-label">用户类型</label>
					<div class="col-sm-5" style="padding-top:6px">
						<c:if test="${user.type==1 }">
							<input type="radio" name="type" id="incrFlag" checked="checked" value="1">长期</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="type" id="incrFlag" value="0" >临时</input>
						</c:if>
						<c:if test="${user.type==0 }">
							<input type="radio" name="type" id="incrFlag" value="1">长期</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="type" id="incrFlag" value="0" checked="checked">临时</input>
						</c:if>
					</div>
				</div>--%>
			</form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
<script type="text/javascript">
	
 /* function callbackDepartmentSelect() {
	  var treeObj = $.fn.zTree.getZTreeObj("departSelect");
	  var nodes = treeObj.getCheckedNodes(true);
	  if(nodes.length>0){
	  var ids='',names='';
	  for(i=0;i<nodes.length;i++){
	     var node = nodes[i];
	     ids += node.id+',';
	    names += node.name+',';
	 }
	 $('#departname').val(names);
	 $('#departname').blur();		
	 $('#orgIds').val(ids);		
	}
}  */

function callbackClean(){
	$('#departname').val('');
	 $('#orgIds').val('');	
}

function setOrgIds() {}
$(function(){
	$("#departname").prev().hide();
});
	</script>
	<script type="text/javascript">
var randomStr="<%=randomStr%>"
</script>
</html>