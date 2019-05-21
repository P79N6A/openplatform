$(document).ready(function(){
	$('#addForm').bootstrapValidator({
        fields:{
        	//审核意见check
            sceneAuditMsg: {
				validators: {
					stringLength: {
                        min: 0,
                        max: 1000,
                        message: '审核意见必须在128个字符范围以内'
                    }
				}
			}
        }
    });
})

function auditScene(dialog) {
		var validator = $("#addForm").data("bootstrapValidator")
		//手动触发验证
        validator.validate();
		if(validator.isValid()){
			var options={
				 url:"apiSceneController.do?auditScene",
				 type:"post",
				 // data:{
				 // 	"auditStatus": checkStatus,
					//  "auditMsg":approvalOpinion,
					//  "id":$("#id").val()
				 // },
				 dataType:"json",
				 beforeSubmit:function(){
					 var checkStatus = $("#sceneStatus option:selected").val();
					 var approvalOpinion =$("#sceneAuditMsg").val();
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
                         reloadTable();
	            		 $.notify({
	         	            message: "审核场景成功！"
	         	         },{
	         	            type: "success",
	         	            delay:1000,
	         	            placement: {
	   	     	  			from: "bottom",
	   	     	  			align: "right"
	   	     	  			 	},
	         	         });
	            	 }else{
	            		 $.notify({
	         	            message: "审核场景失败！"
	         	         },{
	         	            type: "warning",
	         	            z_index:9999,
	         	            delay:2000,
	         	            placement: {
		   	     	  			from: "bottom",
		   	     	  			align: "right"
		   	     	  			 	},
	         	         });
	            	 }
				 },
			}
			$("#addForm").ajaxSubmit(options);
		}
}