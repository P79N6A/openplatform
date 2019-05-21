<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>能力信息表</title>
	<t:base type="bootstrap,bootstrap-table,layer"></t:base>
	<link rel="stylesheet" href="${webRoot}/plug-in/themes/naturebt/css/search-form.css">
	<script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
	<link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
</head>
<body>
<div class="col-sm-3">
	<table class="table table-bordered table-hover table-striped app-table" style="margin-top:54px;">
		<tr>
			<th>应用名</th>
			<th>应用描述</th>
		</tr>
		<tr data-app-id="402894226641f2a7016641f2a7070000">
			<td>111</td>
			<td title="111">111</td>
		</tr>
		<tr data-app-id="40289422664227f00166422daa530001">
			<td>222</td>
			<td title="222">222</td>
		</tr>
	</table>
</div>
<div class="panel-body col-sm-9" style="padding-top: 0px; padding-bottom: 0px;">
	<div id="toolbar">
		<button id="btn-audit" class="btn btn-success btn-sm">
			<span class="glyphicon glyphicon-pencile" aria-hidden="true"></span>批量审核
		</button>
		<a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search">
			<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索
		</a>
	</div>
	<div class="table-responsive">
		<table id="apiInfoList"></table>
	</div>
	<form id="apiInfoForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
		<input id="appId" name="appId" type="hidden"/>
	</form>
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
    var apiInfoListdictsData = {};
    $(function() {
        //处理左侧的应用表格
		$(".app-table").find("tr").bind("click",function(){
		    var oldAppId = $("#appId").val();
		    var appId = $(this).data("appId");
		    if(appId != oldAppId){
                $("#appId").val(appId);
                $(".app-table").find("tr").css("background-color","#ffffff");
                $(this).css("background-color","#DAE9DB");
                reloadTable();
			}
		})
        //处理右侧服务信息表格
        // var promiseArr = [];
        // promiseArr.push(new Promise(function(resolve, reject) {
            initDictByCode(apiInfoListdictsData,"apiSts",function(){});
        // }));

        // Promise.all(promiseArr).then(function(results) {
        //
        // }).catch(function(err) {
        // });

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#apiInfoList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function() {
                // $('#btn-audit').prop('disabled',$('#apiInfoList').bootstrapTable('getSelections').length != 1);
            });

    });

    var TableInit = function() {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function() {
            $('#apiInfoList').bootstrapTable({
                url : 'apiAppController.do?appOrderList', //请求后台的URL（*）
                method : 'POST', //请求方式（*）
                contentType : "application/x-www-form-urlencoded",
                toolbar : '#toolbar', //工具按钮用哪个容器
                striped : true, //是否显示行间隔色
                cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination : true, //是否显示分页（*）
                queryParams : oTableInit.queryParams,//传递参数（*）
                sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber : 1, //初始化加载第一页，默认第一页
                showRefresh: true,
                pageSize : 10, //每页的记录行数（*）
                pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
                clickToSelect : true, //是否启用点击选中行
                height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", //每一行的唯一标识，一般为主键列
                sortName:'createDate',
                sortOrder:'desc',
                columns : [
                    // 复选框
                    {
                        checkbox : true,
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
                        field : 'apiName',
                        title : '能力名称',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        field : 'apiDesc',
                        title : '能力描述',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
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
                            href += "<a href='javascript:void(0);'  class='ace_button'  onclick=bootOpenLook('查看能力','apiInfoController.do?goUpdate&id="
                                + row.id+"','wide')>  <i class='glyphicon glyphicon-search' aria-hidden='true'></i> ";
                            href += "查看</a>&nbsp;";
                            return href;
                        }
                    } ],
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
                field : 'id,apiName,apiDesc,apiStatus,reqAddrHttp,reqAddrHsf,groupId,groupName,examData,appId'
            };

            var params = $("#apiInfoForm").serializeArray();
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
        $('#apiInfoList').bootstrapTable('refresh');
    }

    function searchRest() {
        $('#apiInfoForm').find(':input').each(function() {
            if("checkbox"== $(this).attr("type")){
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked',false);
            }else if("radio"== $(this).attr("type")){
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked',false);
            }else{
                $(this).val("");
            }
        });
        $('#apiInfoForm').find("input[type='checkbox']").each(function() {
            $(this).attr('checked', false);
        });
        $('#apiInfoForm').find("input[type='radio']").each(function() {
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

</body>
</html>