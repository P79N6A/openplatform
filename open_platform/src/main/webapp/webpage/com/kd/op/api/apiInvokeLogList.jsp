<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>能力调用日志表</title>
<t:base type="bootstrap,bootstrap-table,layer"></t:base>
<link rel="stylesheet" href="${webRoot}/plug-in/themes/naturebt/css/search-form.css">
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="accordion-group">
			<div id="collapse_search" class="accordion-body collapse">
				<div class="accordion-inner">
					<div class="panel panel-default" style="margin-bottom: 0px;">
						<div class="panel-body">
							<form id="apiInvokeLogForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
											<div class="col-xs-12 col-sm-6 col-md-4">
												<label for="apiName">能力名称：</label>
												<div class="input-group" style="width: 100%">
													<input type="text" class="form-control input-sm" id="apiName" name="apiName"/>
												</div>
											</div>
											<div class="col-xs-12 col-sm-6 col-md-4">
												<label for="invokeTime_begin">调用时间：</label>
												<div class="input-group" style="width: 100%">
													<input type="text" class="form-control input-sm laydate-date" id="invokeTime_begin" name="invokeTime_begin"/>
													<span class="input-group-addon" >
								                        <span class="glyphicon glyphicon-calendar"></span>
								                    </span>
													 <span class="input-group-addon input-sm">~</span> 
													 <input type="text" class="form-control input-sm laydate-date" id="invokeTime_end" name="invokeTime_end"/>
													 <span class="input-group-addon" >
								                        <span class="glyphicon glyphicon-calendar"></span>
								                    </span>
												</div>
											</div>
								
								<div class="col-xs-12 col-sm-6 col-md-4">
									<div class="input-group col-md-12" style="margin-top: 20px">
										<a type="button" onclick="searchList();" class="btn btn-success btn-rounded  btn-bordered btn-sm"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>
										<a type="button" onclick="searchRest();" class="btn btn-warning btn-rounded  btn-bordered btn-sm"><span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
		<div id="toolbar">
				<button id="btn-look" class="btn btn-info btn-sm">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
				</button>
			<a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
		</div>
		<div class="table-responsive">
			<table id="apiInvokeLogList"></table>
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
		var apiInvokeLogListdictsData = {};
		$(function() {
		    $("#btn-look").bind("click",function(){
                var selects = $("#apiInvokeLogList").bootstrapTable("getSelections");
                if(selects == null || selects.length == 0){
                    quickNotify("请先选中一行","warning");
                }else if(selects.length > 1){
                    quickNotify("只能选择一行","warning");
                }else{
                    var id = selects[0].id;
                    bootOpenLook('查看','apiInvokeLogController.do?goUpdate&id=' + id,'wide');
                }
            })

			// var promiseArr = [];
			// promiseArr.push(new Promise(function(resolve, reject) {
				initDictByCode(apiInvokeLogListdictsData,"invoRes",function(){});
			// }));
			
			// Promise.all(promiseArr).then(function(results) {
			//
			// }).catch(function(err) {
			// });
			
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();

			//判断是否选中表格中的数据，选中后可编辑或删除
			$('#apiInvokeLogList').on(
					'check.bs.table uncheck.bs.table load-success.bs.table '
							+ 'check-all.bs.table uncheck-all.bs.table',
					function() {
						$('#btn_delete').prop('disabled',!$('#apiInvokeLogList').bootstrapTable('getSelections').length);
						$('#btn_edit').prop('disabled',$('#apiInvokeLogList').bootstrapTable('getSelections').length != 1);
					});

		});
		
		var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#apiInvokeLogList').bootstrapTable({
									url : 'apiInvokeLogController.do?datagrid', //请求后台的URL（*）
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
									sortName:'id',
									sortOrder:'desc',
									columns : [
											// 复选框
											{
												checkbox : true,
												align : 'center'
											},
// 											{
// 												title : '序号',
// 												width : 5,
// 												align : 'center',
// 												switchable : false,
// 												formatter : function(value,row, index) {
// 													//return index+1; //序号正序排序从1开始
// 													var pageSize = $('#apiInvokeLogList')
// 															.bootstrapTable('getOptions').pageSize;
// 													var pageNumber = $('#apiInvokeLogList').bootstrapTable('getOptions').pageNumber;
// 													return pageSize* (pageNumber - 1) + index + 1;
// 												}
// 											},
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
												field : 'apiName',
												title : '能力名称',
												valign : 'middle',
												width : 120,
												align : 'center',
												switchable : true,
											},
											{
												field : 'invokeTime',
												title : '调用时间',
												valign : 'middle',
												width : 120,
												align : 'center',
												switchable : true,
												formatter : function(value, rec, index) {
													return new Date().format('yyyy-MM-dd hh:mm:ss', value);
												}
											},
											{
												field : 'invokeIp',
												title : '调用IP',
												valign : 'middle',
												width : 120,
												align : 'center',
												switchable : true,
											},
											{
												field : 'methodType',
												title : '请求方法类型',
												valign : 'middle',
												width : 120,
												align : 'center',
												switchable : true,
											},
// 											{
// 												field : 'invokeUrl',
// 												title : '调用地址',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
// 											{
// 												field : 'responseTimeLength',
// 												title : '响应时长',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
// 											{
// 												field : 'responseFlowSize',
// 												title : '响应流量',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
// 											{
// 												field : 'requestFlowSize',
// 												title : '请求流量',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
// 											{
// 												field : 'requestHeader',
// 												title : '请求头',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
// 											{
// 												field : 'requestParam',
// 												title : '请求参数',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
// 											{
// 												field : 'returnParam',
// 												title : '响应参数',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
// 											{
// 												field : 'returnHeader',
// 												title : '响应头',
// 												valign : 'middle',
// 												width : 120,
// 												align : 'center',
// 												switchable : true,
// 											},
											{
												field : 'invokeResult',
												title : '能力调用结果',
												valign : 'middle',
												width : 120,
												align : 'center',
												switchable : true,
												formatter : function(value, rec, index) {
													return listDictFormat(value,apiInvokeLogListdictsData.invoRes);
												}
											},
											],
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
					field : 'id,apiId,apiName,invokeTime,invokeIp,methodType,invokeUrl,responseTimeLength,responseFlowSize,requestFlowSize,requestHeader,requestParam,returnParam,returnHeader,invokeResult,'
				};

				var params = $("#apiInvokeLogForm").serializeArray();
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
			$('#apiInvokeLogList').bootstrapTable('refresh');
		}

		function searchRest() {
			$('#apiInvokeLogForm').find(':input').each(function() {
		    	if("checkbox"== $(this).attr("type")){
		    		$("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked',false);
				}else if("radio"== $(this).attr("type")){
					$("input:radio[name='" + $(this).attr('name') + "']").attr('checked',false);
				}else{
					$(this).val("");
				}
		    });
		    $('#apiInvokeLogForm').find("input[type='checkbox']").each(function() {
		        $(this).attr('checked', false);
		    });
		    $('#apiInvokeLogForm').find("input[type='radio']").each(function() {
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
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
		});

		//导入
		function ImportXls() {
			openuploadwin('Excel导入', 'apiInvokeLogController.do?upload',"apiInvokeLogList");
		}

		//导出
		function ExportXls() {
			JeecgExcelExport("apiInvokeLogController.do?exportXls","apiInvokeLogList");
		}

		//模板下载
		function ExportXlsByT() {
			JeecgExcelExport("apiInvokeLogController.do?exportXlsByT","apiInvokeLogList");
		}

		//列表数据字典项格式化
		function listDictFormat(value,dicts){
			if (!value) return '';
		    var valArray = value.split(',');
		    var showVal = '';
		    if (valArray.length > 1) {
		    	for (var k = 0; k < valArray.length; k++) {
		           if(dicts && dicts.length>0){
		        	   for(var a = 0;a < dicts.length;a++){
		                	if(dicts[a].typecode ==valArray[k]){
		                		showVal = showVal + dicts[a].typename + ',';
		                		 break;
		                	}
		                }
		           }
		        }
		        showVal=showVal.substring(0, showVal.length - 1);
		    }else{
		    	if(dicts && dicts.length>0){
		    	   for(var a = 0;a < dicts.length;a++){
		            	if(dicts[a].typecode == value){
		            		showVal =  dicts[a].typename;
		            		 break;
		            	}
		            }
		       }
		    }
		    if(showVal==''){
		    	showVal = value;
		    }
		    return showVal;
		}
		
		//加载字典数据
		function initDictByCode(dictObj,code,callback){
			if(!dictObj[code]){
				jQuery.ajax({
		            url: "systemController.do?typeListJson&typeGroupName="+code,
		    		type:"POST",
		       		dataType:"JSON",
		            success: function (back) {
		               if(back.success){
		            	   dictObj[code]= back.obj;
		            	  
		               }
		               callback();
		             }
		         });
			}
		}
		//加载form查询数据字典项
		function loadSearchFormDicts(obj,arr,type,name){
			var html = "";
			for(var a = 0;a < arr.length;a++){
				if("select"== type){
					html+="<option value = '"+arr[a].typecode+"'>"+arr[a].typename+"</option>";
				}else{
					if(!arr[a].typecode){
					}else{
						html+="<input name = '"+name+"' type='"+type+"' value = '"+arr[a].typecode+"'>"+arr[a].typename +"&nbsp;&nbsp;";
					}
					
				}
		    }
			obj.html(html);
		}
	</script>
</body>
</html>