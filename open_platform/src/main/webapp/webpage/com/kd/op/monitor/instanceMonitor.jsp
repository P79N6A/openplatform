<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实例列表</title>
<t:base type="bootstrap,bootstrap-table,layer"></t:base>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
	</div>
	<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
		<div id="toolbar">
		</div>
		<div class="table-responsive">
				<table id="apiInvokeLogList"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
		});
		
		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#apiInvokeLogList').bootstrapTable({
					url : 'monitor.do?getInstanceList', //请求后台的URL（*）
					method : 'POST', //请求方式（*）
                    contentType : "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					// queryParams : oTableInit.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 10, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					strictSearch : true,
					clickToSelect : true, //是否启用点击选中行
					height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					sortName:'id',
					sortOrder:'desc',
					columns : [
//							{
//								checkbox : true,
//								align : 'center'
//							},
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
								field : 'name',
								title : '实例名称',
								valign : 'middle',
								width : 120,
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
								field : 'state',
								title : '状态',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},
							{
								field : 'cpu',
								title : 'CPU使用率(%)',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},
							{
								field : 'memory',
								title : '内存使用率(%)',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},{
								field : 'pluginStatus',
								title : 'ECS插件状态',
								valign : 'middle',
								width : 120,
								align : 'center',
								switchable : true,
							},{
								title : "操作",
								align : 'center',
								valign : 'middle',
								width : 100,
								formatter : function(value,row, index) {
									var href = '';
									href += "<button class='btn btn-xs btn-success' style='margin-right:5px' title='删除' onclick='instanceDetail(\"" + row.id + "\")'>" +
										"<span class='glyphicon'></span>详情</button>";
                                    if(row.state == '运行中'){
                                        href += "<input type='checkbox' data-id='" + row.id + "' checked name='run'/>&nbsp"
                                    }else{
                                        href += "<input type='checkbox' data-id='" + row.id + "' name='run'/>&nbsp"
                                    }
									return href;
								}
							}
                    ],
					onLoadSuccess : function() { //加载成功时执行
                        initSwitch();
					},
					onLoadError : function() { //加载失败时执行
					}
				});
			};

			//得到查询的参数
			// oTableInit.queryParams = function(params) {
			// 	var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			// 		pageSize : params.limit, // 每页要显示的数据条数
			// 		offset : params.offset, // 每页显示数据的开始行号
			// 		sort : params.sort, // 排序规则
			// 		order : params.order,
			// 		rows : params.limit, //页面大小
			// 		page : (params.offset / params.limit) + 1, //页码
			// 		pageIndex : params.pageNumber,//请求第几页
			// 		field : 'id,name,appName,invokeTime,invokeIp,methodType,invokeUrl,responseTimeLength,responseFlowSize,requestFlowSize,requestHeader,requestParam,returnParam,returnHeader,invokeResult,'
			// 	};
            //
			// 	var params = $("#apiInvokeLogForm").serializeArray();
			// 	for (x in params) {
			// 		temp[params[x].name] = params[x].value;
			// 	}
			// 	return temp;
			// };
			return oTableInit;
		};
		
		// function searchList() {
		// 	reloadTable();
		// }
        //
		// function reloadTable() {
		// 	$('#apiInvokeLogList').bootstrapTable('refresh');
		// }
        //
		// function searchRest() {
		// 	$('#apiInvokeLogForm').find(':input').each(function() {
		//     	if("checkbox"== $(this).attr("type")){
		//     		$("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked',false);
		// 		}else if("radio"== $(this).attr("type")){
		// 			$("input:radio[name='" + $(this).attr('name') + "']").attr('checked',false);
		// 		}else{
		// 			$(this).val("");
		// 		}
		//     });
		//     $('#apiInvokeLogForm').find("input[type='checkbox']").each(function() {
		//         $(this).attr('checked', false);
		//     });
		//     $('#apiInvokeLogForm').find("input[type='radio']").each(function() {
		//         $(this).attr('checked', false);
		//     });
		// 	reloadTable();
		// }
		// //高级查询模态框
		// function bootstrapQueryBuilder() {
		// 	$('#superQueryModal').modal({
		// 		backdrop : false
		// 	});
		// }
		function instanceDetail(id){
		    bootOpenLook("实例详情","monitor.do?instanceDetail","wide");
		}

        function initSwitch(){
            $("input[name='run']").bootstrapSwitch({
                size : "mini",
                onText : "启动",
                offText : "停止",
                onColor : "success",
                offColor : "danger",
                disabled:false,
                onSwitchChange : function(event, state) {
                    var title = "确定停止吗";
                    var run = 1;
                    if(!state){
                        title = "确定启动吗";
                        run = 0;
                    }
                    var id = $(this).attr("data-id");

//                    $.ajax({
//                        type:"post",
//                        dataType:"json",
//                        url:"apiInfoController.do?changeRun",
//                        data:{
//                            id:id,
//                            apiRunStatus:run
//                        },
//                        success:function(data){
//                            if(data.success){
//                                quickNotify(data.msg,"success");
//                                reloadTable();
//                            }else{
//                                slowNotify(data.msg,"danger");
//                            }
//                        }
//                    })
                }
            });

        }
	</script>

</body>
</html>