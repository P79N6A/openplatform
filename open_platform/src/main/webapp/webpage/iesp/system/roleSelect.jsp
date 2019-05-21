<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.table > tbody > tr > td{
	overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	var roleCodes = $("#roleCodes").val();
	if(roleCodes != null && roleCodes != undefined){
		var codes = roleCodes.split(",");
		for(var i = 0;i < codes.length;i++){
			if(codes[i] != "" && codes[i] != ''){
				var td = $("td[name='" + codes[i] + "']");
				$(td).prev().find("input[type='checkbox']").prop("checked",true);
				$(td).parent().addClass("click-tr-bg");
			}
		}
	}
})
function clickTr(tr){
	if($(tr).hasClass("click-tr-bg")){
		$(tr).removeClass("click-tr-bg");
		$(tr).find("input[type='checkbox']").prop("checked",false);
	}else{
		// $(tr).parent().find("tr").removeClass("click-tr-bg");
		// $(tr).parent().find("input[type='checkbox']").prop("checked",false);
		$(tr).addClass("click-tr-bg");
		$(tr).find("input[type='checkbox']").prop("checked",true);
	}
}
</script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="get"
				class="form-horizontal" role="form">
				<div class="form-group">
					<table class="table table-bordered table-striped table-hover" style="table-layout:fixed;">
						<thead>
							<tr>
								<th style="width:30px"></th>
								<th>角色编码</th>
								<th>角色名称</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${roles}" var="role">
								<tr onclick="clickTr(this)">
									<td style="width:30px">
										<input type="hidden" value="${role.id}"/>
										<input type="checkbox" name="roleName"/>
									</td>
									<td name="${role.roleCode}">
										${role.roleCode}
									</td>
									<td>
										<span title="${role.roleName}">${role.roleName}</span>
									</td>
								</tr>
							</c:forEach>
					</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>