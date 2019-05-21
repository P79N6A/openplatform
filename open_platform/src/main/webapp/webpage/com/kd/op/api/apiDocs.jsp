<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>能力文档列表</title>
<t:base type="bootstrap,bootstrap-table,layer"></t:base>
<link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
<script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
<script src="${webRoot}/js/common.js" type="text/javascript"></script>
</head>
<body>


	<div class="table-responsive">
		<table id="apiDocList"></table>
	</div>
	<script type="text/javascript">
		$(function() {
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();

			//判断是否选中表格中的数据，选中后可编辑或删除
			$('#apiDocList').on(
					'check.bs.table uncheck.bs.table load-success.bs.table '
							+ 'check-all.bs.table uncheck-all.bs.table',
					function() {
						// $('#btn_delete').prop('disabled',!$('#apiDocList').bootstrapTable('getSelections').length);
						// $('#btn_edit').prop('disabled',$('#apiDocList').bootstrapTable('getSelections').length != 1);
					});

		});

		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#apiDocList').bootstrapTable({
					url : 'apiInfoController.do?getDocs', //请求后台的URL（*）
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
					showColumns : false, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : false, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					showExport : true, //显示到处按钮
					sortName:'createDate',
					sortOrder:'desc',
					columns : [
							{
								title : '序号',
								width : 35,
								align : 'center',
								switchable : false,
								formatter : function(value,row, index) {
									//return index+1; //序号正序排序从1开始
									var pageSize = 10
									var pageNumber = 1;
									return pageSize* (pageNumber - 1) + index + 1;
								}
							},
							{
								field : 'docName',
								title : '文档名称',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
								formatter : function(value, rec, index) {
									return "<span title=" + value + ">" + value + "</span>"
								}
							},
							{
								title: "操作",
								align: 'center',
								valign: 'middle',
								width: 50,
								formatter: function (value, row, index) {
									if (!row.id) {
										return '';
									}
									var href = '';
									href += "<button class='btn btn-xs btn-success' style='margin-left:5px' title='下载' onclick='download(\"" + row.docName + "\")'>" +
										"<span class='glyphicon'></span>下载</button>&nbsp";
									return href;
								}
							} ],
					onLoadSuccess : function() { //加载成功时执行
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
					field : 'id,apiName,apiDesc,apiStatus,reqAddrHttp,reqAddrHsf,groupId,groupName,' +
							'examData,apiPublishStatus,apiAuditStatus'
				};

				var params = $("#apiInfoForm").serializeArray();
				for (x in params) {
					temp[params[x].name] = params[x].value;
				}
				return temp;
			};
			return oTableInit;
		};


        function download(fileName){
            location.href="apiInfoController.do?docDownload&fileName=" + fileName;
			// window.open()
        }

		function searchList() {
			reloadTable();
		}

		function reloadTable() {
			$('#apiDocList').bootstrapTable('refresh');
		}

		function searchRest() {
			reloadTable();
		}
	</script>
</body>
</html>