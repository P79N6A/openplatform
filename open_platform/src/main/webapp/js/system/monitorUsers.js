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
		$("#search_state").selectpicker("val","");
		refreshTable();
	})
});
function TableInit() {
	$('#monitorUsers').bootstrapTable({
		url: 'audit.do?getMonitoredUsers',         //请求后台的URL（*）
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
                userName:$("#userName").val(),
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
				return "<span title='" + value + "'>" + value + "</span>";
			}	
		}, {
			field: 'roleName',
			title: '角色名称',
			width:"10%",
			formatter:function(value,row,index){
				return "<span title='" + value + "'>" + value + "</span>";
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
					return "已删除";
				}else if(value == "3"){
					return "新建";
				}else if(value == "4"){
					return "编辑";
				}else if(value == "0"){
					return "锁定";
				}
				return value;
			},
			width:"4%"
		},{
			field: 'deleteFlag',
			title: '在线状态',
			formatter:function(value,row,index){
				if(value == "1"){
					return "在线";
				}else if(value == "0"){
					return "离线";
				}
				return value;
			},
			width:"4%"
		},{
        	field:"action",
        	title:"操作",
        	formatter:function(value,row,index){
        		if(row.status != 2){
        			return "<button class='btn btn-xs btn-primary' title='查看详情' onclick='queryUser(\"" + row.id + "\")'><span class='table-show-btn'>查看</span></button>"
        		}else{
        			return "";
        		}
        	},
        	width:"6%"
        }],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
			$element.addClass("click-tr-bg");
        }
	});
}
function refreshTable(){
	$("#monitorUsers").bootstrapTable("refresh");
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
            'pageToLoad': 'audit.do?queryUser&id=' + id
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

