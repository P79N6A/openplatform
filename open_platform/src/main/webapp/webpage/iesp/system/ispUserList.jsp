<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%--<script src=<%= request.getContextPath() + "/js/system/UserList.js" %> type="text/javascript"></script>--%>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_name">组织机构</label>
						<div class="col-sm-7 no-padding">
							<input type="text" class="form-control" placeholder="组织机构" id="search_departName" onclick="openDepartmentSelect()" readonly="readonly">
							<input type="hidden" class="form-control" id="search_orgIds"  />
						</div>
					</div>
					 <div class="col-xs-4 form-group no-padding">
						<label class="col-sm-5 control-label near-padding-right" for="search_state">状态</label>
						<div class="col-sm-7 no-padding">
							<select class="selectpicker form-control" id="search_state">
                            	<option value=""selected>全部</option>
                            	<option value="0">锁定</option>
                            	<option value="1">激活</option>
							    <option value="2">注销</option>
                            	<option value="3">新建</option>
                            	<option value="4">编辑</option>
                            	<option value="5">休眠</option>
                            </select>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-3 pull-left no-padding">
				<button type="button" class="btn btn-primary" id="search-btn">
					<span class="glyphicon glyphicon-search"></span>查询
				</button>
				<button type="button" class="btn btn-warning" id="reset-btn">
					<span class="glyphicon glyphicon-refresh"></span>重置
				</button>
			</div>
		</form>
	</div>
    <table id="TsUser"></table>
	<script type="text/javascript">
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
        });
        function TableInit() {
            $('#TsUser').bootstrapTable({
                url: 'userController.do?getIspUsers',         //请求后台的URL（*）
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
                showRefresh: true,
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
                        if (value !== undefined && value !==null) {
                            return "<span title='" + value + "'>" + value + "</span>";
                        }else {
                            return "<span title='" + value + "'>" + "</span>";
						}
                    }
                }, {
                    field: 'roleName',
                    title: '角色名称',
                    width:"5%",
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
                            return "审核未通过";
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
                        if(row.status != 2){
                            return"<button class='btn btn-xs btn-primary' title='查看详情' onclick='queryUser(\"" + row.id + "\")'><span class='table-show-btn'>查看</span></button>"
                        }else{
                            return "";
                        }
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



	</script>
</body>
</html>