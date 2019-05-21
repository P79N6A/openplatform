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
	$('#sysSafeParams').bootstrapTable({
        url: 'sysSafeParam.do?getSysSafeParamList',         //请求后台的URL（*）
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
                sysSafeCode:$("#sysSafeCode").val(),
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
            field: 'sysSafeCode',
            title: '安全参数类型',
            width:'10%'
        }, {
            field: 'sysSafeValue',
            title: '安全参数值',
            width:'10%'
        },{
            field: 'remark',
            title: '备注',
            width:'15%',
            formatter:function(value,row,index){
            	if(value != null && value != ""){
            		return "<span style='width:200px;display:inline-block' title='" + value + "'>" + value + "</span>";
            	}
            	return value;
            },
        },{
        	field:"action",
        	title:"操作",
        	width:'5%',
        	formatter:function(value,row,index){
        		return "<button class='btn btn-xs btn-success' style='margin-right:5px' title='编辑' onclick='editSysSafeParam(\"" + row.id + "\",\"" + row.state + "\")'><span class='glyphicon glyphicon-pencil'></span></button>"
        	}
        } ],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
			$element.addClass("click-tr-bg");
        }
    });
 	
};

function refreshTable(){
	$('#sysSafeParams').bootstrapTable("refresh");
}

function editSysSafeParam(id, state){
		var dialog = new BootstrapDialog({
	        title: '编辑安全参数信息',
	        type:BootstrapDialog.TYPE_DEFAULT,
	        message: function(dialog) {
	            var $message = $('<div></div>');
	            var pageToLoad = dialog.getData('pageToLoad');
	            $message.load(pageToLoad);
	            return $message;
	        },
	        closable: false,
	        data: {
	            'pageToLoad': 'sysSafeParam.do?editSysSafeParam&id=' + id
	        },
	        buttons: [{
	            label: '更新',
	            cssClass: 'btn-primary',
	            action: function(dialogRef){
	            	updateSysSafeParam(dialog);
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

