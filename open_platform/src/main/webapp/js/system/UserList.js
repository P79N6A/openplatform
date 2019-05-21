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
		$('#searchForm').get(0).reset();
		$("#search_orgIds").val(''); 
		$("#search_state").selectpicker("val","");
		refreshTable();
	})
	//新建按钮点击
	$("#add-btn").bind("click",function(){
		 var dialog = new BootstrapDialog({
             title: '添加用户',
             type:BootstrapDialog.TYPE_DEFAULT,
             message: function(dialog) {
                 var $message = $('<div></div>');
                 var pageToLoad = dialog.getData('pageToLoad');
                 $message.load(pageToLoad);
                 return $message;
             },
             closable: false,
             data: {
                 'pageToLoad': 'userController.do?addorupdate'
             },
             buttons: [{
                 label: '保存',
                 cssClass: 'btn-primary',
                 action: function(dialogRef){
                	 createUser(dialog);
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
});
function TableInit() {
	$('#TsUser').bootstrapTable({
		url: 'userController.do?getManagerUsers',         //请求后台的URL（*）
		method: 'post',                      //请求方式（*）
		contentType: "application/x-www-form-urlencoded",
		toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true,                   //是否显示分页（*）
		sortable: false,                     //是否启用排序
		sortOrder: "asc",                   //排序方式
		queryParams: function (params){//传递参数（*）
			return {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit: params.pageSize,   //页面大小
                offset: params.pageNumber, //页码
                departid:$("#search_orgIds").val(),
                status:$("#search_state").val()
			};
		},
		queryParamsType:"page",
		sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
		pageNumber:1,                       //初始化加载第一页，默认第一页
		pageSize: 10,                       //每页的记录行数（*）
		pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
		strictSearch: true,
		clickToSelect: true, 
		//是否启用点击选中行
		showColumns:false,
		//height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
		showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
		cardView: false,                    //是否显示详细视图
		detailView: false,                   //是否显示父子表
		searchOnEnterKey:true,
		columns: [{  
            field: 'Number',  
            formatter: function (value, row, index) {  
            	return index+1;  
            },
            width:"2%",
        },{
			field: 'userName',
			title: '用户账号',
			width:"5%"
		},{
			field: 'realName',
			title: '用户名称',
			width:'5%'
		},{
			field: 'departName',
			title: '组织机构 ',
			width:"5%",
			formatter:function(value,row,index){
				if(value != null && value != undefined){
                    return "<span title='" + value + "'>" + value + "</span>";
				}
				return "";
			}
		}, {
			field: 'roleName',
			title: '角色名称',
			width:"5%",
			formatter:function(value,row,index){
                if(value != null && value != undefined){
                    return "<span title='" + value + "'>" + value + "</span>";
                }
                return "";
			}
		},{
			field: 'createDate',
			title: '创建时间',
			width:"7%",
            formatter: function (value, row, index) {  
                return changeDateFormat(value) 
            },
		},{
			field: 'status',
			title: '用户状态',
			formatter:function(value,row,index){
				if(value == "1"){
					return "激活";
				}else if(value == "2"){
					return "注销";
				}else if(value == "3"){
					return "新建";
				}else if(value == "4"){
					return "编辑";
				}else if(value == "0"){
					return "锁定";
				}else if(value == "5"){
					return "休眠";
				}
				return value;
			},
			width:"4%"
		},{
			field: 'checkStatus',
			title: '审核状态',
			formatter:function(value,row,index){
				if(value == "1"){
					return "待审核";
				}else if(value == "2"){
					return "审核成功";
				}else if(value == "3"){
					return "审核失败";
				}
				return value;
			},
			width:"4%"
		},{
			field: 'registerType',
			title: '用户来源',
			formatter:function(value,row,index){
				if(value == null || value == "" || value == "00"){
					return "能力开放平台";
				}else if(value == "01"){
					return "车联网";
				}else if(value == "02"){
                    return "智慧能源";
                }
				return value;
			},
			width:"4%"
		},{
        	field:"action",
        	title:"操作",
        	formatter:function(value,row,index){
        		/*if(row.status != 2){
        			return "<button class='btn btn-xs btn-success' style='margin-right:5px' title='修改' onclick='editUser(\"" + row.id + "\",\"" + row.roleName + "\","+row.status+")'><span class='glyphicon glyphicon-pencil'></span></button>"+
            		"<button class='btn btn-xs btn-danger'style='margin-right:5px' title='删除' onclick='deleteDilalog(\"" + row.id + "\")'><span class='glyphicon glyphicon-trash'></span></button>"+
            		"<button class='btn btn-xs btn-info'style='margin-right:5px' title='激活用户' onclick='unlockUser(\"" + row.id + "\","+row.status+")'><span class='glyphicon glyphicon-wrench'></span></button>"+
            		"<button class='btn btn-xs btn-primary' title='查看详情' onclick='queryUser(\"" + row.id + "\")'><span class='table-show-btn'>查看</span></button>"
        		}else{
        			return "";
        		}*/
        		return "<button class='btn btn-xs btn-primary' title='查看详情' onclick='queryUser(\"" + row.id + "\")'><span class='table-show-btn'>查看</span></button>";
        	},
        	width:"4%"
        }],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
			$element.addClass("click-tr-bg");
        }
	});
}
function refreshTable(){
	$("#TsUser").bootstrapTable("refresh");
}
function resetPassword(id){
	var dialog = new BootstrapDialog({
        title: '密码重置',
        type:BootstrapDialog.TYPE_DEFAULT,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        closable: false,
        data: {
            'pageToLoad': 'userController.do?changepasswordforuser&id='+id,
        },
        buttons: [{
            label: '更新',
            cssClass: 'btn-primary',
            action: function(dialogRef){
            	updatePassword(dialog);
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
function deleteDilalog(id){
	Confirm.confirm({ message: "确认要将该用户删除吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "userController.do?del",
            dataType:"json",
            data: {
            	id:id
            },
            success: function (data) {
                if (data.success) {
                	 refreshTable();
	           		 quickNotify("用户删除成功！","success");
                }else{
                	$.notify({
         	            message: "用户删除失败！"
         	         },{
         	            type: "warning",
         	            z_index:9999,
         	            delay:2000
         	         });
                }
            }
        });
    });
}
function queryUser(id){
	var dialog = new BootstrapDialog({
        title: '用户详情',
        type:BootstrapDialog.TYPE_DEFAULT,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        closable: false,
        data: {
            'pageToLoad': 'userController.do?queryUser&id=' + id
        },
        buttons: [{
            label: '关闭',
            cssClass: 'btn-default',
            action: function(dialogRef){
            	dialogRef.close();
            }
        }]
    });
    dialog.open();
}
function editUser(id,roleName,status){
	if(status !=1 && status!=3 && status!=4){
		slowNotify("当前状态不支持编辑！","danger");
		return false;
	}
	if(roleName == '审核管理员'){
		slowNotify("该用户为审核管理员，不允许修改用户信息","danger");
		return false;
	}
	var dialog = new BootstrapDialog({
		title: '编辑用户',
		type:BootstrapDialog.TYPE_DEFAULT,
		message: function(dialog) {
			var $message = $('<div></div>');
			var pageToLoad = dialog.getData('pageToLoad');
			$message.load(pageToLoad);
			return $message;
		},
		closable: false,
		data: {
			'pageToLoad': 'userController.do?editUser&id='+id
		},
		buttons: [{
			label: '更新',
			cssClass: 'btn-primary',
			action: function(dialogRef){
				updateUser(dialog);
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
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
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
            	DepartmentSelect();
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
function DepartmentSelect() {
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
	 $('#search_departName').val(names);
	 $('#search_departName').blur();		
	 $('#search_orgIds').val(ids);		
	}
	  return departId;
}


function unlockUser(id, status) {
	if(status != 0 && status != 5){
		slowNotify("被锁定或者休眠的用户才能激活！","danger");
		return false;
	}
	Confirm.confirm({ message: "确认要将该用户状态激活吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "userController.do?unlockUser",
            dataType:"json",
            data: {
            	id:id
            },
            success: function (data) {
                if (data.success) {
                	 refreshTable();
	           		 quickNotify("用户激活成功！","success");
                }else{
                	$.notify({
         	            message: "用户激活失败！"
         	         },{
         	            type: "warning",
         	            z_index:9999,
         	            delay:2000
         	         });
                }
            }
        });
    });
	
}
