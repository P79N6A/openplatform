<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>订单或订购中场景能力的计费模型列表</title>
	<t:base type="bootstrap,bootstrap-table,layer"></t:base>
</head>
<body>
<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
	<form id="chargeModeForm">
		<input id="type" name="type" type="hidden" value="${type}" />
		<input id="apiId" name="apiId" type="hidden" value="${apiId}" />
		<input id="sceneId" name="sceneId" type="hidden" value="${sceneId}" />
		<input id="orderId" name="orderId" type="hidden" value="${orderId}" />
	</form>
	<div class="table-responsive">
		<table id="chargeModeList" data-charge="${type}"></table>
	</div>
	<input id="ss" type="hidden" value="${ss}"/>
</div>
<script type="text/javascript">
    var type = $("#type").val();
    $(function() {
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
    });

    var asd = null;
    var TableInit = function() {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function() {
            $('#chargeModeList').bootstrapTable({
                url : 'order.do?datagridOrderCharge', //请求后台的URL（*）
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
                clickToSelect : false, //是否启用点击选中行
                // height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", //每一行的唯一标识，一般为主键列
                sortName:'price',
                sortOrder:'asc',
                columns : [
                    /*{
                        radio : true,
                        align : 'center'
                    },*/{
                        title : '序号',
                        width : "10%",
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
                        field : 'id',
                        title : 'id',
                        valign : 'middle',
                        width : 120,
                        visible:false,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        field : 'num',
                        title : '时间',
                        valign : 'middle',
                        width : "20%",
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'startNum',
                        title : '起始值',
                        valign : 'middle',
                        width : "20%",
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'endNum',
                        title : '截止值',
                        valign : 'middle',
                        width : "20%",
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'unit',
                        title : '单位',
                        valign : 'middle',
                        width : "30%",
                        align : 'center',
                        switchable : true,
                        formatter : function(value,row, index) {
                            if(value == 1){
                                return "周";
                            }else if(value == 2){
                                return "月";
                            }else if(value == 3){
                                return "年";
                            }else if(value == 4){
                                return "KB";
                            }else if(value == 5){
                                return "次";
                            }else {
                                return value
                            }
                        }
                    },
                    {
                        field : 'price',
                        title : '价格(元)',
                        valign : 'middle',
                        width : "50%",
                        align : 'center',
                        switchable : true,
                    }],
                onLoadSuccess : function(data) { //加载成功时执行
                    //将已经选中的计费方式再次选中
					var ss = $("#ss").val()
                    if(ss != null && ss != ""){
                        $("#chargeModeList").bootstrapTable("checkBy", {field:"id", values:[ss]})
                        asd = ss;
                        $("tr[data-uniqueid=" + ss + "]").addClass("success");
                    }
                },
                onLoadError : function(e) { //加载失败时执行
                },
                onClickRow:function(row,$element,$field){
                    chargeModeCheck(row);
                },
                onCheck: function (row, $element) {
                    // chargeModeCheck(row);
                }
            });
            //处理需要隐藏的列
            if(type == 1){
                $('#chargeModeList').bootstrapTable("hideColumn","startNum");
                $('#chargeModeList').bootstrapTable("hideColumn","endNum");
            }else if(type == 2){
                $('#chargeModeList').bootstrapTable("hideColumn","num");
            }else if(type == 3){
                $('#chargeModeList').bootstrapTable("hideColumn","num");
            }
            // $('#postpaidList').bootstrapTable("showColumn","num1");
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
                field : 'id,type,num,startNum,endNum,unit,price'
            };

            var params = $("#chargeModeForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };
    function reloadTable() {
        $('#chargeModeList').bootstrapTable('refresh');
    }
    function chargeModeCheck(row){
        var selected = row;
        if(asd == null){
            $('#chargeModeList').bootstrapTable("checkBy", {field:"id", values:[row.id]})
            asd = row.id;
            $("#chargeModeList").find("tr").removeClass("success");
            $("tr[data-uniqueid=" + row.id + "]").addClass("success");
        }else{
            if(asd == row.id){
                $('#chargeModeList').bootstrapTable("uncheckBy", {field:"id", values:[row.id]})
                asd = null;
                selected = null;
                $("tr[data-uniqueid=" + row.id + "]").removeClass("success");
            }else{
                $('#chargeModeList').bootstrapTable("checkBy", {field:"id", values:[row.id]})
                asd = row.id;
                $("#chargeModeList").find("tr").removeClass("success");
                $("tr[data-uniqueid=" + row.id + "]").addClass("success");
            }
        }
        var chargeType = $("tr[data-uniqueid=" + row.id + "]").parent().parent().data("charge");
        var iframeId = $($(window.parent.document).contents().find("iframe")[type - 1]).attr("id");
        parent.refreshSelected(iframeId,selected,chargeType);
	}
</script>

</body>
</html>