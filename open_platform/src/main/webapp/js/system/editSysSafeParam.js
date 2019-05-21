$(document).ready(function(){
	$('#addForm').bootstrapValidator({
        fields:{
        	sysSafeValue: {
				validators: {
					notEmpty: {
						message: '安全参数值不能为空'
					},
                    regexp: {
						regexp: /^[0-9]+$/,
                        message: '安全参数值只能是数字类型'
                    }
				}
			},
			remark: {
				validators: {
					stringLength: {
                        min: 0,
                        max: 255,
                        message: '备注必须在0-255个字符范围以内'
                    }
				}
			}
        }
    });
})

function updateSysSafeParam(dialog) {
		var validator = $("#addForm").data("bootstrapValidator")
		//手动触发验证
        validator.validate();
		if(validator.isValid()){
			var options={
				 url:"sysSafeParam.do?updateSysSafeParam",
				 type:"post",
				 dataType:"json",
				 beforeSubmit:function(){
				 	var sysSafeCode =  $("#value").val();
					var sysSafeValue = $("#sysSafeValue").val();
					 if(sysSafeCode =="mailCodeTime") {
						 if(sysSafeValue < 1 || sysSafeValue > 30) {
							quickNotify("邮件认证码有效时间必须在1-30分钟范围以内！","danger");
							return false;
						 }
					 } else if(sysSafeCode =="sessionNum") {
						 if(sysSafeValue < 1 || sysSafeValue > 500) {
								quickNotify("系统会话并发访问数量必须在1-500范围以内！","danger");
								return false;
							 }
					 } else if(sysSafeCode =="sessionExpiredTime") {
						 if(sysSafeValue < 1 || sysSafeValue > 30) {
								quickNotify("会话超时时间必须在1-30分钟范围以内 ！","danger");
								return false;
							 }
					 } else if (sysSafeCode =="faildTimes") {
						 if(sysSafeValue < 1 || sysSafeValue > 10) {
								quickNotify("登陆失败次数必须在1-10范围以内！","danger");
								return false;
							 }
					 } else if(sysSafeCode =="lockedTime") {
						 if(sysSafeValue < 20) {
								quickNotify("账户锁定时间至少20分钟！","danger");
								return false;
							 }
					 } else if(sysSafeCode =="passExpiredTime") {
						 if(sysSafeValue < 1 || sysSafeValue > 90) {
								quickNotify("密码过期时间必须在1-90天范围以内！","danger");
								return false;
							 }
					 } else if(sysSafeCode =="logLength") {
						 if(sysSafeValue < 1024 || sysSafeValue > 2147483647) {
								quickNotify("日志容量限值必须在1024-2147483647M范围以内！","danger");
								return false;
							 }
					 }else {
							quickNotify("未识别的安全参数类型！","danger");
							return false;
					 }
				 },
				 success:function(data){
					 if(data.success){
						 dialog.close();
	            		 refreshTable();
	            		 $.notify({
	         	            message: "安全参数更新成功！"
	         	         },{
	         	            type: "success",
	         	            delay:1000,
	         	            placement: {
	   	     	  			from: "bottom",
	   	     	  			align: "right"
     	  			 	}
	         	         });
	            	 }else{
	            		 $.notify({
	         	            message: "安全参数更新失败！"
	         	         },{
	         	            type: "warning",
	         	            z_index:9999,
	         	            delay:2000,
	         	            placement: {
		   	     	  			from: "bottom",
		   	     	  			align: "right"
   	     	  			 	}
	         	         });
	            	 }
				 },
			}
			$("#addForm").ajaxSubmit(options);
		}
}