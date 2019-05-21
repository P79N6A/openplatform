<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var checkStatus=${user.checkStatus};
	if(checkStatus==1){
		$("#checkStatus").val("待审核");
	}else if(checkStatus==2){
		$("#checkStatus").val("审核通过");
	}else{
		$("#checkStatus").val("审核失败");
	}
	var type=${user.type};
	if(type==1){
		$("#type").val("长期");
	}else{
		$("#type").val("临时")
	}
</script>
<style>
.btn-sm{
	padding:2px 8px;
}
</style>
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
							class="form-control"  value="${user.userName }" readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">真实姓名</label>
					<div class="col-sm-10">
						<input type="text" id="realName" name="realName"
							class="form-control" value="${user.realName }"readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">组织机构</label>
					<div class="col-sm-10">
						<input id="departName"  type="text" class="form-control"readonly="readonly" value="${depart.departname}"readonly="readonly"> 
						<input id="orgIds" name="orgIds" type="hidden" value="${user.departid}">	
					</div>
				</div>
				<div class="form-group">
					<label for="role" class="col-sm-2 control-label">角色</label>
					<div class="col-sm-10">
						<input id="roleCodes" type="hidden"  value="${roleCodes}"/>
		                <input id="roleid"  name="roleid" type="hidden" value="${roleIds}"/>
       					<input id="roleName"  name="userKey" class="form-control" readonly="readonly" value="${roleNames}"readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">手机号码</label>
					<div class="col-sm-10">
						<input type="number" id="mobilePhone" name="mobilePhone"
							class="form-control" value="${user.mobilePhone }"readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">办公电话</label>
					<div class="col-sm-10">
						<input type="text" id="officePhone" name="officePhone"
							class="form-control" value="${user.officePhone }"readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">常用邮箱</label>
					<div class="col-sm-10">
						<input type="text" id="email" name="email" class="form-control"
							value="${user.email }"readonly="readonly"/>
					</div>
				</div>
				<%--<div class="form-group">
					<label for="name" class="col-sm-2 control-label">用户Ip</label>
					<div class="col-sm-10">
						<input type="text" id="userIp" name="userIp" class="form-control"
							placeholder="请输入用户Ip"  value="${user.userIp }"readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">开始时间</label>
					<div class="col-sm-10">
						<input type="text" id="beginDate" name="beginDate" class="form-control"
							placeholder="请输入用户访问开始时间"  value="${user.beginDate}:00"readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">结束时间</label>
					<div class="col-sm-10">
						<input type="text" id="endDate" name="endDate" class="form-control"
							placeholder="请输入用户访问结束时间"  value="${user.endDate}:00"readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户类型</label>
					<div class="col-sm-10">
						<input type="text" id="type" name="type" class="form-control"
							  value=""readonly="readonly"/>
					</div>
				</div>--%>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">审核状态</label>
					<div class="col-sm-10">
						<input type="text" id="checkStatus" name="checkStatus" class="form-control"
							  value=""readonly="readonly"/>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">审核意见</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="approvalOpinion" rows="3"
							readonly="readonly">${user.approvalOpinion}</textarea>
					</div>
				</div>
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

	</script>
</html>