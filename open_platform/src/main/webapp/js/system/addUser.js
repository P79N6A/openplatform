var aaa = {};
$(document).ready(function(){
	// $('#beginDate').datetimepicker({
	// 	format:	'H:i',
	// 	datepicker:false,
	// });
	//
	// $('#endDate').datetimepicker({
	// 	format:	'H:i',
	// 	datepicker:false,
	// });
	
})
$(document).ready(function(){
	$('#addForm').bootstrapValidator({
		fields:{
			userName: {
				message: '用户账号验证失败',
				validators: {
					notEmpty: {
						message: '用户账号不能为空'
					},
					threshold:1 , //有1字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
					remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
						url: 'userController.do?checkExist',//验证地址
						message: '用户账号已存在',//提示消息
						delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
						type: 'POST'//请求方式
					},
					stringLength: {
                        min: 1,
                        max: 10,
                        message: '用户账号为1-10个字符'
                    },
					regexp: {
                        regexp: /^[A-Za-z][\w]{1,20}$/,
                        message: '用户账号由字母数字下划线组成,必须以字母开头'
                    }
				}
			},
			realName: {
				message: '用户名称验证失败',
				validators: {
					notEmpty: {
						message: '用户名称不能为空'
					},
					stringLength: {
						min: 1,
						max: 20,
                        message: '用户名称为1-20个字符'
                    }
				}
			},
			email: {
				validators: {
					// notEmpty: {
					// 	message: '邮箱不能为空'
					// },
					emailAddress: {
						message: '邮箱格式有误'
					}
				}
			},
			mobilePhone: {
                validators: {
                    // notEmpty: {
					// 	message: '手机号码不能为空'
					// },
					threshold:11 , //有1字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
					remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
						url: 'userController.do?checkExist',//验证地址
						message: '手机号码已被使用',//提示消息
						delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
						type: 'POST'//请求方式
					},
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },
                    regexp: {
                        regexp: /^1[3|4|5|7|8]{1}[0-9]{9}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            },
            // officePhone:{
            // 	validators:{
            // 		regexp: {
            //             regexp:/0\d{2,3}-\d{7,8}/,
            //             message: '请输入正确的办公号码'
            //         }
            // 	}
            // },
            /*userIp: {
				message: '用户Ip验证失败',
				validators: {
					notEmpty: {
						message: '用户访问ip不能为空'
					},
                    callback:{
                    	message:'IP地址格式输入有误',
                    	callback:function(value,validator,$filed){
                    		if(value == null || value == ""){
                    			return true;
                    		}
                    		var ip=value;
                    		var ips=ip.split(",");
                    		var reg ="";
                    		for(var i=0;i<ips.length;i++){
                    			var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                       		    reg = ips[i].match(exp);
                    		}
                   		    if(reg==null){
                   		    	return false;
                   		    }
                   		    return true;
                    	}
                    }
				}
			}*/
		}
	});
})
function createUser(dialog){
	var validator = $("#addForm").data("bootstrapValidator")
	var username=$("#userName").val();
	var departName=$("#departName").val();
	var roleName=$("#roleName").val();
	var roleid=$("#roleid").val();
	// var beginDate=$("#beginDate").val();
	// var endDate=$("#endDate").val();
	var v = aaa["aaa"];
	var newpass=aaa["bbb"];
	var numasc = 0;
    var charasc = 0;
    var otherasc = 0;
	validator.validate();
	if(validator.isValid()){
		if(v==null || v==""){
			quickNotify("密码不能为空！","danger");
			return false;
		}
		if(v.toString().length<8||v.toString().length>20){
			quickNotify("新密码长度为8—20位","danger");
			return false;
		}
		if(newpass==null || newpass==""){
			quickNotify("重复密码不能为空！","danger");
			return false;
		}
		if(newpass!=v){
			quickNotify("两次输入的新密码不一致，请重新输入！","danger");
			return false;
		}
		for (var i = 0; i < v.length; i++) {
	            var asciiNumber = v.substr(i, 1).charCodeAt();
	            if (asciiNumber >= 48 && asciiNumber <= 57) {
	                numasc += 1;
	            }
	            if ((asciiNumber >= 65 && asciiNumber <= 90)||(asciiNumber >= 97 && asciiNumber <= 122)) {
	                charasc += 1;
	            }
	            if ((asciiNumber >= 33 && asciiNumber <= 47)||(asciiNumber >= 58 && asciiNumber <= 64)||(asciiNumber >= 91 && asciiNumber <= 96)||(asciiNumber >= 123 && asciiNumber <= 126)) {
	                otherasc += 1;
	            }
	        }
		
		if(0==otherasc){
			quickNotify("密码必须包含特殊字符！","danger");
			return false;
		}
		if(v == username){
			quickNotify("密码不能与用户名相同！","danger");
			return false;
		}
		if(v.indexOf(username)!=-1){
			quickNotify("密码不能包含用户名！","danger");
			return false;
		}
		//密码强度通过后，将新密码加密
		var newPassword = doCrypt(v,keyStr1);
		$("#newPassword").val(newPassword);
		var password=$("#newPassword").val();
		if(departName == null || departName==""){
			quickNotify("组织机构不能为空！","danger");
			return false;
		}if(roleName==null || roleName==""){
			quickNotify("角色不能为空！","danger");
			return false;
		}
		// if(beginDate==null && endDate==null){
		// 	quickNotify("用户访问时间不能为空！","danger");
		// 	return false;
		// }if(endDate<=beginDate){
		// 	quickNotify("结束时间必须大于开始时间！","danger");
		// 	return false;
		// }else{
			DwrMessagePush.getRandom("",function(data){
				var random = data.randomStr;
				var options={
						url:"userController.do?saveUser&newpassword="+password+"&randomStr="+random,
						type:"post",
						dataType:"json",
						beforeSubmit:function(){
						},
						success:function(data){
							if(data.success){
								dialog.close();
								refreshTable();
								quickNotify("添加用户成功！","success");
							}else{
								slowNotify("添加用户失败,"+data.msg,"danger");
							}
						},
				}
				$("#addForm").ajaxSubmit(options);
			})
		// }
	}
}
function openDepartmentSelect(){
	var orgIds = $("#orgIds").val();
	var dialog = new BootstrapDialog({
		title: '组织机构列表',
		type:BootstrapDialog.TYPE_DEFAULT,
		message: function(dialog) {
			var $message = $('<div></div>');
			var pageToLoad = dialog.getData('pageToLoad');
			$message.load(pageToLoad);
			return $message;
		},
		closable: false,
		data: {
			'pageToLoad': 'departController.do?departSelect&orgIds'+orgIds
		},
		buttons: [{
			label: '确定',
			cssClass: 'btn-primary',
			action: function(dialogItself){
				callbackDepartmentSelect();
				dialogItself.close();
			}
		}, {
			label: '取消',
			cssClass: 'btn-default',
			action: function(dialogItself){
				dialogItself.close();
			}
		}]
	});
	dialog.open();
}

function openRoleSelect(){
	var dialog = new BootstrapDialog({
		title: '角色列表',
		type:BootstrapDialog.TYPE_DEFAULT,
		message: function(dialog) {
			var $message = $('<div></div>');
			var pageToLoad = dialog.getData('pageToLoad');
			$message.load(pageToLoad);
			return $message;
		},
		closable: false,
		data: {
			'pageToLoad': 'userController.do?getRole'
		},
		buttons: [{
			label: '确定',
			cssClass: 'btn-primary',
			action: function(dialogItself){
				callbackRoleSelect();
				dialogItself.close();
			}
		}, {
			label: '取消',
			cssClass: 'btn-default',
			action: function(dialogItself){
				dialogItself.close();
			}
		}]
	});
	dialog.open();
}
function callbackDepartmentSelect() {
	var treeObj = $.fn.zTree.getZTreeObj("departSelect");
	var nodes = treeObj.getCheckedNodes(true);
	var departId='';
	if(nodes.length>0){
		var ids='',names='';
		for(i=0;i<nodes.length;i++){
			var node = nodes[i];
			ids += node.id+'';
			names += node.name+'';
			departId=node.id;
		}
		$('#departName').val(names);
		$('#departname').blur();		
		$('#orgIds').val(ids);		
	}
	return departId;
}
function callbackRoleSelect() {
	var roleNames = "";
	var roleIds = "";
	var input = $("input[name='roleName']:checked");
	for(i=0;i<input.length;i++){
		var td = $(input[i]).parent().next().next();
		roleNames += td.find("span").html().trim() + ",";
		roleIds += $(input[i]).prev().val() + ",";
	}
	roleNames=(roleNames.substring(roleNames.length-1)==',')?roleNames.substring(0,roleNames.length-1):roleNames;
	$("#roleid").val(roleIds);
	$("#roleName").val(roleNames);
}
function callbackDepartClean(){
	$('#departName').val('');
	$('#orgIds').val('');	
}
function callbackRoleClean(){
	$('#roleName').val('');
	$('#roleid').val('');	
}