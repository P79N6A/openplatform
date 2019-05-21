$(document).ready(function(){
	 //1.初始化Table
	TableInit();
})

function TableInit() {
	$('#logConfigs').bootstrapTable({
        url: 'userController.do?getLogConfigList',         //请求后台的URL（*）
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
            field: 'configName',
            title: '操作名称',
            width:'10%'
        }, {
            field: 'code',
            title: '操作编码',
            width:'10%'
        },{
            field: 'flag',
            title: '是否记录',
            width:'15%',
            formatter:function(value,row,index){
            	if(value == "1"){
					return "是";
				}else if(value == "0"){
					return "否";
				}
				return value;
            },
        },{
        	field:"action",
        	title:"操作",
        	width:'5%',
        	formatter:function(value,row,index){
        		return "<button class='btn btn-xs btn-success' title='记录/不记录' onclick='recordOrNot(\"" + row.id + "\",\"" + row.flag + "\")'><span class='glyphicon glyphicon-wrench'></span></button>"
        	}
        } ],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
			$element.addClass("click-tr-bg");
        }
    });
 	
};

function refreshTable(){
	$('#logConfigs').bootstrapTable("refresh");
}

function editlogConfig(id, state){
		var dialog = new BootstrapDialog({
	        title: '编辑日志配置',
	        type:BootstrapDialog.TYPE_DEFAULT,
	        message: function(dialog) {
	            var $message = $('<div></div>');
	            var pageToLoad = dialog.getData('pageToLoad');
	            $message.load(pageToLoad);
	            return $message;
	        },
	        closable: false,
	        data: {
	            'pageToLoad': 'userController.do?editLogConfig&id=' + id
	        },
	        buttons: [{
	            label: '更改',
	            cssClass: 'btn-primary',
	            action: function(dialogRef){
	            	updateLogConfig(dialog);
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

function recordOrNot(id,flag){
		var flagName = "记录";
		var targetState = 0;
		if(flag == 1){
			flagName = "不记录";
		}
		Confirm.confirm({ message: "确认此操作" + flagName + "日志吗？"}).on(function (e) {
	        if (!e) {
	            return;
	        }
	        $.ajax({
	            type: "post",
	            url: "userController.do?updateLogConfigFlag",
	            dataType:"json",
	            data: {
	            	flag:flag,
	            	id:id
	            },
	            success: function (data) {
	                if (data.result) {
	                	 refreshTable();
		           		quickNotify("状态更新成功！","success");
	                }else{
	                	$.notify({
	         	            message: "状态更新失败！"
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


