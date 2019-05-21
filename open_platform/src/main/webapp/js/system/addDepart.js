$(document).ready(function(){
	$('#addForm').bootstrapValidator({
		fields:{
			orgCode: {
				message: '组织编码验证失败',
				validators: {
					notEmpty: {
						message: '组织编码不能为空'
					},
				}
			},
			departname: {
				message: '组织名称证失败',
				validators: {
					notEmpty: {
						message: '组织名称不能为空'
					}
				}
			},
		}
	});
})
function createDepart(dialog){
	var validator = $("#addForm").data("bootstrapValidator")
	validator.validate();
	if(validator.isValid()){
		var options={
			url:"departController.do?save",
			type:"post",
			dataType:"json",
			beforeSubmit:function(){
			},
			success:function(data){
				if(data.success){
					dialog.close();
					refreshTable();
					quickNotify("添加组织机构成功！","success");
				}else{
					slowNotify("添加组织机构失败","danger")
				}
			},
		}
		$("#addForm").ajaxSubmit(options);
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
function callbackDepartmentSelect() {
	var treeObj = $.fn.zTree.getZTreeObj("departSelect");
	var nodes = treeObj.getCheckedNodes(true);
	var departId='';
	var ids='',names='';
	if(nodes.length>0){
		for(i=0;i<nodes.length;i++){
			var node = nodes[i];
			ids += node.id+'';
			names += node.name+'';
			departId=node.id;
		}
	}else{
		quickNotify("未选择父组织，此次创建的组织机构将为根节点！");
	}
	$('#departName').val(names);
	$('#orgIds').val(ids);
	return departId;
}
function callbackDepartClean(){
	$('#departName').val('');
	$('#orgIds').val('');	
	quickNotify("未选择父组织，此次创建的组织机构将为根节点！")
}