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
				 url:"check.do?updateApprovalUser",
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