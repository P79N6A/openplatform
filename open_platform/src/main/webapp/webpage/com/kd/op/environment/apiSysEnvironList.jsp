<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>能力应用表</title>
<t:base type="bootstrap,bootstrap-table,layer"></t:base>
<%--<link rel="stylesheet" href="${webRoot}/plug-in/lobipanel/lobipanel.css">--%>
<link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
<script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
<%--<link rel="stylesheet" href="${webRoot}/plug-in/themes/naturebt/css/search-form.css">--%>
	<script src="${webRoot}/js/common.js" type="text/javascript"></script>
</head>
<body>
	<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
		<div id="toolbar">
			<button id="btn_edit" class="btn btn-success btn-sm">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑
			</button>
		</div>
		<div class="table-responsive">
			<table id="apiSysEnvironList"></table>
		</div>
	</div>
	<script type="text/javascript">
		$("#btn_edit").bind("click",function(){
		    var selects = $("#apiSysEnvironList").bootstrapTable("getSelections");
		    if(selects == null || selects.length == 0){
		        quickNotify("请先选中一行","warning");
            }else if(selects.length > 1){
                quickNotify("只能选择一行","warning");
            }else{
		        var id = selects[0].id;
                bootOpenNormal('环境数据编辑','apiSysEnvironController.do?goEdit&id=' + id,'normal');
            }
        })
		$(function() {
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();

			//判断是否选中表格中的数据，选中后可编辑或删除
			$('#apiSysEnvironList').on(
					'check.bs.table uncheck.bs.table load-success.bs.table '
							+ 'check-all.bs.table uncheck-all.bs.table',
					function() {
						$('#btn_edit').prop('disabled',$('#apiSysEnvironList').bootstrapTable('getSelections').length != 1);
					});

		});
		
		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#apiSysEnvironList').bootstrapTable({
					url : 'apiSysEnvironController.do?datagrid', //请求后台的URL（*）
					method : 'post', //请求方式（*）
                    contentType : "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					queryParams : oTableInit.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 10, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					strictSearch : true,
					clickToSelect : true, //是否启用点击选中行
					height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					sortName:'type',
					sortOrder:'desc',
					columns : [
							{
								radio : true,
								width:20,
								align : 'center'
							},
							{
								field : 'id',
								title : 'id',
								valign : 'middle',
								width : 120,
								visible:false,
								align : 'center',
								switchable : true,
							},
							{
								field : 'ip',
								title : 'IP地址',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},
							// {
							// 	field : 'port',
							// 	title : '端口号',
							// 	valign : 'middle',
							// 	width : 120,
							// 	align : 'center',
							// 	switchable : true,
							// },
							{
								field : 'type',
								title : '类型',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
								formatter:function(value,row, index) {
								    if(value == 0){
								        return "测试环境";
									}else if(value == 1){
								        return "正式环境";
									}
									return value;
								}
							}],
					onLoadSuccess : function(data) { //加载成功时执行
					},
					onLoadError : function() { //加载失败时执行
					}
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					pageSize : params.limit, // 每页要显示的数据条数
					offset : params.offset, // 每页显示数据的开始行号
					sort : params.sort, // 排序规则
					order : params.order,
					rows : params.limit, //页面大小
					page : (params.offset / params.limit) + 1, //页码
					pageIndex : params.pageNumber,//请求第几页
					field : 'id,ip,port,type'
				};

				var params = $("#apiAppForm").serializeArray();
				for (x in params) {
					temp[params[x].name] = params[x].value;
				}
				return temp;
			};
			return oTableInit;
		};
        function reloadTable() {
            $('#apiSysEnvironList').bootstrapTable('refresh');
        }
	</script>
</body>
</html>