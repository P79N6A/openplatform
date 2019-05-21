<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/webpage/common/resource.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
		margin-bottom:2px;
	}
</style>
<%--<script src="${pageContext.request.contextPath}/js/system/approvalUserList.js" type="text/javascript"></script>--%>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-6 form-group no-padding">                  	
                   	 		<label class="control-label col-sm-5" for="userName">用户名</label>
	                        <div class="col-sm-7 no-padding">
	                            <input type="text" class="form-control " placeholder="用户名" id="userName">
	                        </div>
	                 </div>       
	             </div>
	             <input type="text" value="解决bootstrap与AJAX异步提交表单的冲突" hidden />
	       </div>  
	       <div class="col-xs-3 pull-left no-padding">
				<button type="button" class="btn btn-primary" id="btn_query">
					<span class="glyphicon glyphicon-search"></span>查询
				</button>
				<button type="button" class="btn btn-warning" id="reset-btn">
					<span class="glyphicon glyphicon-refresh"></span>重置
				</button>
			</div>
		 </form>
    </div>
    <table id="approvalUsers"></table>
        <div class="modal" id="approvalUserModal">
	    <div class="modal-dialog" style="">
	        <div class="modal-content">
	          
	        </div>
	    </div>
	</div>
<script type="text/javascript">
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
        $('#approvalUsers').bootstrapTable({
            url: 'userController.do?getapprovalAppBuyerUserList',         //请求后台的URL（*）
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
                    userName:$("#userName").val(),
                };
            },
            queryParamsType:"page",
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 20, 50, 100],        //可供选择的每页的行数（*）
            strictSearch: true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
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
                field: 'userName',
                title: '用户账号',
                width:'10%'
            },{
                field: 'realName',
                title: '用户名称',
                width:'10%'
            },{
                field: 'roleName',
                title: '角色名称',
                width:'10%'
            },{
                field: 'status',
                title: '用户状态',
                width:'10%',
                formatter:function(status,row,index){
                    if(status == "1"){
                        return "激活";
                    }else if(status == "2"){
                        return "删除";
                    }else if(status == "3"){
                        return "新建";
                    }else if(status == "4"){
                        return "编辑";
                    }
                }
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
                field:"action",
                title:"操作",
                width:'5%',
                formatter:function(value,row,index){
                    return "<button class='btn btn-xs btn-success' style='margin-right:5px' title='审核' onclick='approvalUser(\"" + row.id + "\",\"" + row.state + "\")'>审核</button>"
                }
            } ],
            onClickRow:function(row,$element,field){
                $element.parent().find("tr").removeClass("click-tr-bg");
                $element.addClass("click-tr-bg");
            }
        });

    };

    function refreshTable(){
        $('#approvalUsers').bootstrapTable("refresh");
    }

    function approvalUser(id, state){
        var dialog = new BootstrapDialog({
            title: '审核用户',
            type:BootstrapDialog.TYPE_DEFAULT,
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': 'userController.do?editApprovalAppBuyerUser&id=' + id
            },
            buttons: [{
                label: '更新',
                cssClass: 'btn-primary',
                action: function(dialogRef){
                    updateApprovalUser(dialog);
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


</script>
</body>
</html>