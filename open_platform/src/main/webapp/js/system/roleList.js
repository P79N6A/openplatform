$(document).ready(function(){
	 //1.初始化Table
	TableInit();
    //2.初始化Button的点击事件
	//查询按钮
	$("#search-btn").bind("click",function(){
		refreshTable();
	})
	//重置按钮
	$("#reset-btn").bind("click",function(){
		$('#search_roleName').val("");
		refreshTable();
	})
	//新建按钮点击
	$("#add-btn").bind("click",function(){
		 var dialog = new BootstrapDialog({
             title: '新建角色',
             
             type:BootstrapDialog.TYPE_DEFAULT,
             message: function(dialog) {
                 var $message = $('<div></div>');
                 var pageToLoad = dialog.getData('pageToLoad');
                 $message.load(pageToLoad);
                 return $message;
             },
             closable: false,
             data: {
                 'pageToLoad': 'roleController.do?addorupdate'
             },
             buttons: [{
                 label: '保存',
                 cssClass: 'btn-primary',
                 action: function(dialogRef){
                     createRole(dialog);
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
	})
})
function TableInit() {
	$("#roles").bootstrapTable({
        url: 'roleController.do?getRoleList',         //请求后台的URL（*）
        method: 'post',                      //请求方式（*）
        contentType: "application/x-www-form-urlencoded",
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        queryParams: function (params){//传递参数（*）
        	return {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        		limit: params.pageSize,   //页面大小
                offset: params.pageNumber,  //页码
                roleName:$("#search_roleName").val(),
            };
        },
        queryParamsType:"page",
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列
        clickToSelect: true,                //是否启用点击选中行
//        height: "calc(100% - 45px)",        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        searchOnEnterKey:true,
        columns: [{  
            field: 'Number',
            title: '序号',
            formatter: function (value, row, index) {  
            	return index+1;  
            },
            width:"2%",
        },{
            field: 'roleCode',
            title: '角色编码',
            width:'5%'
        }, {
            field: 'roleName',
            title: '角色名称',
            width:'5%'
        }, {
            field: 'checkStatus',
            title: '审核状态',
            width:'10%'
        }, {
            field: 'approvalOpinion',
            title: '审核意见',
            width:'10%'
        },{
            field: 'createDate',
            title: '创建时间',
            width:'10%'
        },{
            field: 'createName',
            title: '创建人',
            width:'5%'
        }, {
        	field:"action",
        	title:"操作",
        	width:'10%',
        	formatter:function(value,row,index){
        		return "<button class='btn btn-xs btn-success' style='margin-right:5px' title='编辑' onclick='editRole(\"" + row.id + "\")'><span class='glyphicon glyphicon-pencil'></span></button>" +
        				"<button class='btn btn-xs btn-danger' style='margin-right:5px' title='删除' onclick='deleteRole(\"" + row.id + "\")'><span class='glyphicon glyphicon-trash'></span></button>" +
        				"<button class='btn btn-xs btn-primary' style='margin-right:5px' onclick='showUsers(\"" + row.id + "\")'><span>查看用户</span></button>"+
        				"<button class='btn btn-xs btn-success' onclick='configPermissions(\"" + row.id + "\")'><span>配置权限</span></button>";
        	}
        }],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
			$element.addClass("click-tr-bg");
        }
    });
};
function refreshTable(){
	$('#roles').bootstrapTable("refresh");
}
function editRole(id){
    $.ajax({
        type: "post",
        url: "roleController.do?checkUnableUpdate&id=" + id,
        dataType:"json",
        data: {
        	id:id
        },
        success: function (data) {
            if (data.success) {
            	refreshTable();
           		quickNotify("该角色不允许被编辑！","danger");
            }else{
            	var dialog = new BootstrapDialog({
                    title: '编辑角色',
                    type:BootstrapDialog.TYPE_DEFAULT,
                    message: function(dialog) {
                        var $message = $('<div></div>');
                        var pageToLoad = dialog.getData('pageToLoad');
                        $message.load(pageToLoad);
                        return $message;
                    },
                    closable: false,
                    data: {
                        'pageToLoad': 'roleController.do?addorupdate&id=' + id
                    },
                    buttons: [{
                        label: '更新',
                        cssClass: 'btn-primary',
                        action: function(dialogRef){
                            updateRole(dialog);
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
        }
    });

}
function deleteRole(id){
	Confirm.confirm({ message: "确认要删除该角色吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "roleController.do?delRole",
            dataType:"json",
            data: {
            	id:id
            },
            success: function (data) {
                if (data.success) {
                	refreshTable();
	           		quickNotify("角色删除成功！","success");
                }else{
                	slowNotify(data.msg,"danger");
                }
            }
        });
    });
}
function showUsers(id){
	$.ajax({
		type:"post",
		dataType:"json",
		url:"roleController.do?getUserListByRole",
		data:{
			id:id
		},
		success:function(data){
			if(data && data.length > 0){
				var html = "<table class='table table-striped table-bordered table-hover'><thead><tr><th>用户账号</th><th>用户名称</th><th>状态</th></tr></thead><tbody>";
				for(var i = 0;i < data.length;i++){
					var user = data[i];
					var state = user.status;
//					if(state == 4){
//						state = "已删除";
//					}else {
//						state = "激活";
//					}
					if(state == 1){
						state = "激活";
					}else if(state == 2){
						state = "注销";
					}else if(state == 3){
						state = "新建";
					}else if(state == 4){
						state = "编辑";
					}else if(state == 0){
						state = "锁定";
					}else if(state == 5){
						state = "休眠";
					}
					
					html += "<tr><td>" + user.userName + "</td><td>" + user.realName + "</td><td>" + state + "</td></tr>";
				}
				html += "</tbody><table>";
				var dialog = new BootstrapDialog({
			        title: '查看用户',
			        type:BootstrapDialog.TYPE_DEFAULT,
			        message: function(dialog) {
			            var $message = $("<div style='max-height:300px;overflow:auto'></div>");
			            $message.html(html);
			            return $message;
			        },
			        buttons: [{
			            label: '关闭',
			            cssClass: 'btn-default',
			            action: function(dialogItself){
			                dialogItself.close();
			            }
			        }]
			    });
			    dialog.open();
			}else{
				quickNotify("该角色没有关联用户！")
			}
		}
	})
}
function configPermissions(id){
    $.ajax({
        type: "post",
        url: "roleController.do?checkUnableUpdate&id=" + id,
        dataType:"json",
        data: {
        	id:id
        },
        success: function (data) {
            if (data.success) {
            	refreshTable();
           		quickNotify("该角色不允许修改权限！","danger");
            } else {
                $.ajax({
                    async : false,   //设置为false。请求为同步请求
                    cache:false,   	//不设置缓存
                    type: 'post',
                    dataType : "json",
                    url: "roleController.do?getFunctionsByRole&roleId=" + id,//后台处理程序
                    error: function () {//请求失败处理函数
                        return false;
                    },
                    success:function(data){
                        if (data.data!=null && data.data!=""){
                            var dialog = new BootstrapDialog({
                                title: '配置权限',
                                type: BootstrapDialog.TYPE_DEFAULT,
                                size: "size-small",
                                message: function (dialog) {
                                    var $message = $('<div></div>');
                                    var pageToLoad = dialog.getData('pageToLoad');
                                    $message.load(pageToLoad);
                                    return $message;
                                },
                                closable: false,
                                data: {
                                    'pageToLoad': 'roleController.do?configFun&roleId=' + id
                                },
                                buttons: [{
                                    label: '保存',
                                    cssClass: 'btn-primary',
                                    action: function (dialogRef) {
                                        updateFunctions(dialog);
                                    }
                                }, {
                                    label: '取消',
                                    cssClass: 'btn-default',
                                    action: function (dialogItself) {
                                        dialogItself.close();
                                    }
                                }]
                            });
                            dialog.open();
                        }else {
                            refreshTable();
                            quickNotify("该角色尚未配置任何功能，不允许配置权限！","danger");
                        }
                    }
                });

            }
        }
    });
	
}
