<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>附件审核</title>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="id" name="id" value="${apiAnnex.id}" />
		        <div class="form-group">
		        	<label  class="col-sm-2 control-label">附件名称</label>
		        	<div class="col-sm-8" style="padding-right:0px">
		        		<input type="text"  name="annexName" readonly="readonly" class="form-control" value="${apiAnnex.annexName}" />
		        	</div>
		        </div>
		       <div class="form-group">
					<label for="auditStatus" class="col-sm-2 control-label">审核状态</label>
					<div class="col-sm-8" style="padding-right:0px">
						<select class="form-control" id="auditStatus" name="auditStatus">
								<option selected value="2">审核通过</option>
								<option  value="3">审核失败</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="auditMsg" class="col-sm-2 control-label ">审核意见</label>
					<div class="col-sm-9">
						<textarea class="form-control" id="auditMsg" name="auditMsg" rows="3"></textarea>
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
                    url:"apiAnnexController.do?doAudit",
                    type:"post",
                    dataType:"json",
                    beforeSubmit:function(){
                        var auditStatus = $("#auditStatus").find("option:selected").val();
                        if(auditStatus == 3){
                            var auditMsg = $("#auditMsg").val();
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