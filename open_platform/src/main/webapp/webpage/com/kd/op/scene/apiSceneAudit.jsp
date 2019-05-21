<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrapValidator.js" %> type="text/javascript"></script>
	<script src=<%= request.getContextPath() + "/js/com/kd/op/app/auditScene.js" %> type="text/javascript"></script>
</head>
<body>
<div>
	<div class="modal-body">
		<form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
			<input type="hidden" id="id" name="id" value="${scene.id}" />
			<div class="form-group">
				<label for="sceneName" class="col-sm-2 control-label">场景名称</label>
				<div class="col-sm-8" style="padding-right:0px">
					<input type="text" id="sceneName" name="sceneName" readonly="readonly" class="form-control" value="${scene.sceneName}" />
				</div>
			</div>
			<div class="form-group">
				<label for="sceneStatus" class="col-sm-2 control-label">审核状态</label>
				<div class="col-sm-8" style="padding-right:0px">
					<select class="form-control" id="sceneStatus" name="sceneAuditStatus">
						<option selected value="2">审核通过</option>
						<option  value="3">审核不通过</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="sceneAuditMsg" class="col-sm-2 control-label ">审核意见</label>
				<div class="col-sm-9">
					<textarea class="form-control" id="sceneAuditMsg" name="sceneAuditMsg" rows="3"></textarea>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>
<%--<script>--%>
    <%--$(document).ready(function(){--%>
        <%--// $("#auditStatus").change(function(){--%>
        <%--//     var auditStatus = $(this).val();--%>

        <%--$("#btn_sub").bind("click",function(){--%>
            <%--var options={--%>
            <%--// $.ajax({--%>
                <%--url:"apiSceneController.do?auditScene",--%>
                <%--type:"post",--%>
                <%--// data: {--%>
                <%--//         auditStatus: auditStatus,--%>
                <%--//         id:$("#Id").val()--%>
                <%--// },--%>
                <%--dataType:"json",--%>
                <%--beforeSubmit:function(){--%>
                <%--},--%>
                <%--success:function(data){--%>
                    <%--if(data.success){--%>
                        <%--$(".bootstrap-dialog").modal("hide")--%>
                        <%--reloadTable();--%>
                        <%--quickNotify(data.msg,"success");--%>
                    <%--}else{--%>
                        <%--slowNotify(data.msg,"danger");--%>
                    <%--}--%>
                <%--}--%>
            <%--}--%>
            <%--$("#addForm").ajaxSubmit(options);--%>
            <%--}); // }--%>
        <%--})--%>
    <%--// })--%>
<%--</script>--%>