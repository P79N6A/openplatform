<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务订阅审核</title>
<t:base type="bootstrap,bootstrap-table,layer"></t:base>
</head>
<body>
	<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
		<div class="table-responsive">
			<table id="apiAppList" class="table table-bordered"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(".laydate-datetime").each(function(){
			var _this = this;
			laydate.render({
			  elem: this,
			  format: 'yyyy-MM-dd HH:mm:ss',
			  type: 'datetime'
			});
		});
		$(".laydate-date").each(function(){
			var _this = this;
			laydate.render({
			  elem: this
			});
		});
		var apiAppListdictsData = {};
		$(function() {
			// var promiseArr = [];
            // promiseArr.push(new Promise(function(resolve, reject) {
                initDictByCode(apiAppListdictsData,"auditSts",function(){});
            // }));
			
			// Promise.all(promiseArr).then(function(results) {
			//
			// }).catch(function(err) {
			// });
			
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();

			//判断是否选中表格中的数据，选中后可编辑或删除
			$('#apiAppList').on(
					'check.bs.table uncheck.bs.table load-success.bs.table '
							+ 'check-all.bs.table uncheck-all.bs.table',
					function() {
						$('#btn_delete').prop('disabled',!$('#apiAppList').bootstrapTable('getSelections').length);
						$('#btn_edit').prop('disabled',$('#apiAppList').bootstrapTable('getSelections').length != 1);
					});

		});
		
		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#apiAppList').bootstrapTable({
					url : 'apiAppController.do?getAppAuditList', //请求后台的URL（*）
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
					strictSearch : false,
					showColumns : false, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : false, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					showExport : false, //显示到处按钮
					sortName:'createDate',
					sortOrder:'desc',
					columns : [
							// 复选框
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
								field : 'appName',
								title : '应用名称',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},
							{
								field : 'appDesc',
								title : '应用描述',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},
							{
								field : 'appVersion',
								title : '应用版本',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},
							{
								field : 'auditStatus',
								title : '审核状态',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
								formatter : function(value, rec, index) {
									return listDictFormat(value,apiAppListdictsData.auditSts);
								}
							},
							{
								title : "操作",
								align : 'center',
								valign : 'middle',
								width : 100,
								formatter : function(value,row, index) {
									if (!row.id) {
										return '';
									}
									var href = '';
									href += "<button class='btn btn-xs btn-success' onclick='checkPermissions(\"" + row.id + "\")'><span>查看订购</span></button>&nbsp";
                                    href += "<button class='btn btn-xs btn-primary' onclick='audit(\"" + row.id + "\")'><span>审核</span></button>";
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
					field : 'id,appName,appId,appDesc,appVersion,auditStatus,auditMsg'
				};

				var params = $("#apiAppForm").serializeArray();
				for (x in params) {
					temp[params[x].name] = params[x].value;
				}
				return temp;
			};
			return oTableInit;
		};
		
		function searchList() {
			reloadTable();
		}

		function reloadTable() {
			$('#apiAppList').bootstrapTable('refresh');
		}

		function searchRest() {
			$('#apiAppForm').find(':input').each(function() {
		    	if("checkbox"== $(this).attr("type")){
		    		$("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked',false);
				}else if("radio"== $(this).attr("type")){
					$("input:radio[name='" + $(this).attr('name') + "']").attr('checked',false);
				}else{
					$(this).val("");
				}
		    });
		    $('#apiAppForm').find("input[type='checkbox']").each(function() {
		        $(this).attr('checked', false);
		    });
		    $('#apiAppForm').find("input[type='radio']").each(function() {
		        $(this).attr('checked', false);
		    });
			reloadTable();
		}
		//高级查询模态框
		function bootstrapQueryBuilder() {
			$('#superQueryModal').modal({
				backdrop : false
			});
		}
        function checkPermissions(id){
        	bootOpenLook('服务查看','apiAppController.do?goApiAppRelaList&type=2&id=' + id,'wide')
        }
        function audit(id){
            var dialog = new BootstrapDialog({
                title: '审核应用',
                type:BootstrapDialog.TYPE_DEFAULT,
                size:"size-default",
                message: function(dialog) {
                    var $message = $('<div></div>');
                    var pageToLoad = dialog.getData('pageToLoad');
                    $message.load(pageToLoad);
                    return $message;
                },
                closable: true,
                data: {
                    'pageToLoad': "apiAppController.do?goAuditApp&id=" + id
                },
                buttons: [{
                    label: '保存',
                    cssClass: 'btn-primary',
                    action: function(dialogRef){
                        auditApp(dialog);
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