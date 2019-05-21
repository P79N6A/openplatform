$(document).ready(function(){
	$('#addForm').bootstrapValidator({
        fields:{
            roleCode: {
                message: '角色编码验证失败',
                validators: {
                    notEmpty: {
                        message: '角色编码不能为空'
                    },
                    threshold:1 , //有1字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                        url: 'roleController.do?checkRoleCode',//验证地址
                        message: '角色编码已存在',//提示消息
                        delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式
                    },
					stringLength: {
						min: 1,
						max: 10,
                        message: '角色编码为1-10个字符'
                    }
                }
            },
            roleName: {
                message: '角色名称验证失败',
                validators: {
                    notEmpty: {
                        message: '角色名称不能为空'
                    },
					stringLength: {
						min: 1,
						max: 50,
                        message: '角色名称为1-50个字符'
                    }
                }
            }
        }
    });
})
function updateRole(dialog){
	var options={
		 url:"roleController.do?saveOrUpdateRole",
		 type:"post",
		 dataType:"json",
		 beforeSubmit:function(){
		 },
		 success:function(data){
			 if(data.success){
        		 dialog.close();
        		 refreshTable();
        		 quickNotify("角色更新成功！","success");
        	 }else{
        		 slowNotify(data.msg,"danger");
        	 }
		 },
	}
	$("#addForm").ajaxSubmit(options);
}