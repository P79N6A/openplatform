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

function updateApprovalRole(dialog) {
		var validator = $("#addForm").data("bootstrapValidator")
		//手动触发验证
        validator.validate();
		if(validator.isValid()){
			$("#saveBtn").prop('disabled', true); 
			var options={
				 url:"check.do?updateApprovalRole",
				 type:"post",
				 dataType:"json",
				 beforeSubmit:function(){
					 var checkStatus = $("#checkStatus option:selected").val();
					 var approvalOpinion =$("#approvalOpinion").val();
					 if (checkStatus == "3") {
						 if (approvalOpinion ==null || approvalOpinion == "")
							 {
								quickNotify("审核未通过，请输入审核意见！","danger");
								return false;
							 }
					 }
				 },
				 success:function(data){
					 if(data.success){
						 if(data.checkStatus == 2) {
							 updateApprovalFunctions();
						 } else if (data.checkStatus == 3) { 
							 quickNotify("审核未通过，审核状态更新成功！","danger");
						 }
						 dialog.close();
	            		 refreshTable();
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

//审核通过后，更新权限
function updateApprovalFunctions(){
var roleId = $("#roleId").val();
$.ajax({
	type:"post",
	dataType:"json",
	url:"check.do?UpdateApprovalAuthority&roleId=" + roleId,
	success:function(data){
		if(data && data.success){
			quickNotify("审核通过，权限更新成功,权限已生效！","success");
			dialog.close();
		}else{
			slowNotify("权限更新失败！","danger");
		}
	}
})
}
