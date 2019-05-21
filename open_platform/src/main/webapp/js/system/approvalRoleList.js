$(document).ready(function(){
	 //1.初始化Table
	TableInit();
    //2.初始化Button的点击事件
	//查询按钮
	$("#btn_query").bind("click",function(){
		refreshTable();
	})
	//重置按钮
	$("#reset-btn").bind("click",function(){
		
		$('#searchForm').get(0).reset();
		refreshTable();
	})
})

function TableInit() {
	$('#approvalRoles').bootstrapTable({
        url: 'check.do?getApprovalRoleList',         //请求后台的URL（*）
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
                offset: params.pageNumber,  //页码
                roleName:$("#roleName").val(),
            };
        },
        queryParamsType:"page",
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 20, 50, 100],        //可供选择的每页的行数（*）
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
//        height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                   //是否显示父子表
        searchOnEnterKey:false,
        columns: [{  
            field: 'Number',  
            formatter: function (value, row, index) {  
            	return index+1;  
            },
            width:"2%",
        },{
            field: 'roleCode',
            title: '角色编码',
            width:'10%'
        },{
            field: 'roleName',
            title: '角色名称',
            width:'10%'
        },{
            field: 'checkStatus',
            title: '审核状态',
            width:'10%',
            formatter:function(checkStatus,row,index){
            	if(checkStatus == "1"){
            		return "待审核";
            	}else if(checkStatus == "2"){
            		return "审核通过";
            	} else if (checkStatus == "3"){
            		return "审核未通过";
            	}
            }
        },{ 
        	field:"show",
        	title:"待审核权限",
        	width:'10%',
        	formatter:function(value,row,index){
        		return "<button class='btn btn-xs btn-success' onclick='approvalconfigPermissions(\"" + row.id + "\")'><span>待审核权限</span></button>";
        	}
        },{
        	field:"action",
        	title:"操作",
        	width:'5%',
        	formatter:function(value,row,index){
        		return "<button class='btn btn-xs btn-success' style='margin-right:5px' title='审核' onclick='approvalRole(\"" + row.id + "\",\"" + row.state + "\")'><span class='glyphicon glyphicon-wrench'></span></button>"
        	}
        } ],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
			$element.addClass("click-tr-bg");
        }
    });
 	
};

function refreshTable(){
	$('#approvalRoles').bootstrapTable("refresh");
}

//显示待审核权限树
function approvalconfigPermissions(id){
	var dialog = new BootstrapDialog({
        title: '待审核权限',
        type:BootstrapDialog.TYPE_DEFAULT,
        size:"size-small",
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        closable: false,
        data: {
            'pageToLoad': 'check.do?approvalConfigFun&roleId=' + id
        },
        buttons: [{
            label: '取消',
            cssClass: 'btn-default',
            action: function(dialogItself){
                dialogItself.close();
            }
        }]
    });
    dialog.open();
}

function approvalRole(id, state){
		var dialog = new BootstrapDialog({
	        title: '审核角色',
	        type:BootstrapDialog.TYPE_DEFAULT,
	        message: function(dialog) {
	            var $message = $('<div></div>');
	            var pageToLoad = dialog.getData('pageToLoad');
	            $message.load(pageToLoad);
	            return $message;
	        },
	        closable: false,
	        data: {
	            'pageToLoad': 'check.do?editApprovalRole&id=' + id
	        },
	        buttons: [{
	            label: '更新',
	            cssClass: 'btn-primary',
	            action: function(dialogRef){
	            	updateApprovalRole(dialog);
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

