<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%--<script src=<%= request.getContextPath() + "/js/system/editApprovalUser.js" %> type="text/javascript"></script>--%>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="baseUserId" name="baseUserId" value="${baseUser.id}" />
		        <div class="form-group">
		        	<label for="userName" class="col-sm-2 control-label">用户名</label>
		        	<div class="col-sm-8" style="padding-right:0px">
		        		<input type="text" id="userName" name="userName" readonly="readonly" class="form-control" value="${baseUser.userName}" />
		        	</div>
		        </div>
		       <div class="form-group">
					<label for="checkStatus" class="col-sm-2 control-label">审核状态</label>
					<div class="col-sm-8" style="padding-right:0px">
						<select class="form-control" id="checkStatus" name="checkStatus">
								<option selected value="2">审核通过</option>
								<option  value="3">审核未通过</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="approvalOpinion" class="col-sm-2 control-label ">审核意见</label>
					<div class="col-sm-10">
						<textarea class="form-control" id="approvalOpinion" name="approvalOpinion" rows="3">${baseUser.approvalOpinion}</textarea>
					</div>
				</div>
		    </form>
		</div>
	</div>
	<div class='notifications'></div>
<script type="text/javascript">
    $(document).ready(function(){
        $('#addForm').bootstrapValidator({
            fields:{
                //审核意见check
                approvalOpinion: {
                    validators: {
                        stringLength: {
                            min: 0,
                            max: 128,
                            message: '审核意见必须在128个字符范围以内'
                        }
                    }
                }
            }
        });
    })

    function updateApprovalUser(dialog) {
        var validator = $("#addForm").data("bootstrapValidator")
        //手动触发验证
        validator.validate();
        if(validator.isValid()){
            $("#saveBtn").prop('disabled', true);
            var options={
                url:"userController.do?updateApprovalIspUser",
                type:"post",
                dataType:"json",
                beforeSubmit:function(){
                    var checkStatus = $("#checkStatus option:selected").val();
                    var approvalOpinion =$("#approvalOpinion").val();
                    if (checkStatus == "3") {
                        if (approvalOpinion ==null || approvalOpinion == "")
                        {
                            quickNotify("审核失败，请输入审核意见！","danger");
                            return false;
                        }
                    }
                },
                success:function(data){
                    if(data.success){
                        dialog.close();
                        refreshTable();
                        $.notify({
                            message: "审核状态更新成功！"
                        },{
                            type: "success",
                            delay:1000,
                            placement: {
                                from: "bottom",
                                align: "right"
                            },
                            onClosed:function(){
                                $("#saveBtn").prop('disabled', false);
                            }
                        });
                    }else{
                        $.notify({
                            message: "审核状态更新失败！"
                        },{
                            type: "warning",
                            z_index:9999,
                            delay:2000,
                            placement: {
                                from: "bottom",
                                align: "right"
                            },
                            onClosed:function(){
                                $("#saveBtn").prop('disabled', false);
                            }
                        });
                    }
                },
            }
            $("#addForm").ajaxSubmit(options);
        }
    }
</script>
</body>
</html>