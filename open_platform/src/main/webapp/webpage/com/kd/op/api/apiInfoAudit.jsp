<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/com/kd/op/app/auditApp.js" %> type="text/javascript"></script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="id" name="id" value="${apiInfoPage.id}" />
		        <div class="form-group">
		        	<label  class="col-sm-2 control-label">能力名称</label>
		        	<div class="col-sm-8" style="padding-right:0px">
		        		<input type="text" id="apiName" name="apiName" readonly="readonly" class="form-control" value="${apiInfoPage.apiName}" />
		        	</div>
		        </div>
		       <div class="form-group">
					<label for="auditStatus" class="col-sm-2 control-label">审核状态</label>
					<div class="col-sm-8" style="padding-right:0px">
						<select class="form-control" id="auditStatus" name="apiAuditStatus">
								<option selected value="2">审核通过</option>
								<option  value="3">审核不通过</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="apiAuditMsg" class="col-sm-2 control-label ">审核意见</label>
					<div class="col-sm-9">
						<textarea class="form-control" id="apiAuditMsg" name="apiAuditMsg" rows="3"></textarea>
					</div>
				</div>
		    </form>
		</div>
	</div>
</body>
</html>
<script>
	$(document).ready(function(){
        $("#btn_sub").bind("click",function(){
                var options={
                    url:"apiInfoController.do?doApiInfoAudit",
                    type:"post",
                    dataType:"json",
                    beforeSubmit:function(){
                        var auditStatusStr = $("#auditStatus option:selected").val();
                        var auditMsg = $("#apiAuditMsg").val();
                        if(auditStatusStr == 3){
                            if(auditMsg.trim() == ""){
                                slowNotify("审核不通过，请填写审核意见！","danger");
                                return false;
                            }
                        }
                    },
                    success:function(data){
                        if(data.success){
                            $(".bootstrap-dialog").modal("hide")
                            reloadTable();
                            quickNotify(data.msg,"success");
                        }else{
                            slowNotify(data.msg,"danger");
                        }
                    },
                }
                $("#addForm").ajaxSubmit(options);
            // }
        })
	})
</script>