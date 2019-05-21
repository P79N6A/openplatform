$(document).ready(function(){
	
	 jeDate("#beginDate",{
        format: "YYYY-MM-DD hh:mm:ss"
    });
	 jeDate("#endDate",{
		 format: "YYYY-MM-DD hh:mm:ss"
	 });
	 //1.初始化Table
	TableInit();
    //2.初始化Button的点击事件
	//查询按钮
	$("#search-btn").bind("click",function(){
		refreshTable();
	})
	/* $(".form_datetime").datetimepicker({
        format: "dd MM yyyy - hh:ii"
    });*/
	//重置按钮
	$("#reset-btn").bind("click",function(){
		var name=$("#username").readOnly;
		$('#searchForm').get(0).reset();
		$("#search_loglevel").selectpicker("val","");
		$("#search_userName").selectpicker("val","");
		$("#search_Type").selectpicker("val","");
		refreshTable();
	})
	$("#load-btn").bind("click",function(){
		var loglevel=$("#search_loglevel").val();
		var beginDate=$("#beginDate").val();
		var endDate=$("#endDate").val();
		window.location.href = 'logController.do?exportExcel&loglevel='+loglevel+'&beginDate='+beginDate+'&endDate='+endDate;
	})
	
});
function refreshTable(){
	$('#TSLog').bootstrapTable("refresh");
}
function TableInit() {
	$('#TSLog').bootstrapTable({
		url: 'logController.do?logList',         //请求后台的URL（*）
		method: 'post',                      //请求方式（*）
		contentType: "application/x-www-form-urlencoded",
		toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination: true,                   //是否显示分页（*）
		sortable: true,                     //是否启用排序
		sortOrder: "desc",                   //排序方式
		sortName:"operatetime",
		queryParams: function (params){//传递参数（*）
			return {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit: params.pageSize,   //页面大小
                offset: params.pageNumber, //页码
                loglevel:$("#search_loglevel").val(),
                beginDate:$("#beginDate").val(),
                endDate:$("#endDate").val(),
                type:$("#search_Type").val(),
                userId:$("#search_userName").val(),
                sortName: params.sortName,
                sortOrder: params.sortOrder,
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
			field: 'logLevel',
			title: '日志类型',
			width:"7%",
			sortable:true,
			formatter:function(value,row,index){
				if(value == "1"){
					return "登录";
				}else if(value == "2"){
					return "退出";
				}else if(value == "3"){
					return "插入";
				}else if(value == "4"){
					return "删除";
				}else if(value == "5"){
					return "更新";
				}else if(value == "6"){
					return "上传";
				}else if(value == "7"){
					return "其他";
				}else if(value == "8"){
					return "基础配置";
				}else if(value == "9"){
					return "交互配置";
				}else if(value == "10"){
					return "系统配置";
				}else if(value == "11"){
					return "越权访问";
				}else if(value == "12"){
					return "IP变动过大";
				}else if(value == "13"){
					return "连续登录失败";
				}else if(value == "14"){
					return "模型管理";
				}else if(value == "15"){
					return "运行管理";
				}else if(value == "16"){
					return "有序充电管理";
				}
				return value;
			}
		},{
			field: 'logContent',
			title: '日志内容',
			width:'30%',
			formatter:function(value,row,index){
				return "<span title='" + value + "'>" + value + "</span>";
			},
		},{
			field: 'ip',
			title: '操作IP',
			width:"8%"
		},{
			field: 'successFlag',
			title: '标记',
			formatter:function(value,row,index){
				if(value == "1"){
					return "成功";
				}else if(value == "0"){
					return "失败";
				}
				return value;
			},
			width:"4%"
		}/*, {
			field: 'userId',
			title: '操作人角色',
			width:"8%"
				
		}*/,{
			field: 'userName',
			title: '操作人',
			width:"6%",
			sortable:true	
		},{
			field: 'operatetime',
			title: '操作时间',
			sortable: true,  
            //——修改——获取日期列的值进行转换  
            formatter: function (value, row, index) {  
                return changeDateFormat(value) 
            },
            width:"10%",
            sortable:true
		}, {
			field: 'broswer',
			title: '浏览器',
			width:"6%"
		},{
            field: 'isSystem',
            title: '日志类别',
            width:'5%'
        },{
        	field:"action",
        	title:"操作",
        	formatter:function(value,row,index){
        		return "<button class='btn btn-xs btn-primary' style='margin-right:5px' title='查看详情' onclick='queryLog(\"" + row.id + "\")'><span class='table-show-btn'>查看</span></button>" 
        	},
        	width:"5%"
        }],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
			$element.addClass("click-tr-bg");
        }
	});
}

function queryLog(id){
	var dialog = new BootstrapDialog({
        title: '日志详情',
        type:BootstrapDialog.TYPE_DEFAULT,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            return $message;
        },
        data: {
            'pageToLoad': 'logController.do?queryTSLog&id=' + id
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


