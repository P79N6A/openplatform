<%--
  Created by IntelliJ IDEA.
  User: gongzi_yang
  Date: 2018/11/15
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>余额查询</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
</head>
<body>
<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
    <form id="chargeAccountForm">
        <input id="typename" name="typename" type="hidden" value="${typename}" />
    </form>
    <div class="table-responsive">
        <table id="chargeAccountList"></table>
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
            var type = '${typename}';
            var activeTitle = "服务到期时间";
            switch (type){
                case 'com.kd.openplatform.charge.service.impl.ChargeByPeriodServiceImpl':
                    activeTitle = '服务到期时间';
                    break;
                case 'com.kd.openplatform.charge.service.impl.ChargeByQueryServiceImpl':
                    activeTitle = '剩余调用次数';
                    break;
                case 'com.kd.openplatform.charge.service.impl.ChargeByFlowServiceImpl':
                    activeTitle = '剩余调用流量';
                    break;
            }


            $('#chargeAccountList').bootstrapTable({
                url : 'apiCharge.do?chargeAccountDatagrid', //请求后台的URL（*）
                method : 'post', //请求方式（*）
                contentType : "application/x-www-form-urlencoded",
                striped : true, //是否显示行间隔色
                cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination : true, //是否显示分页（*）
                queryParams : oTableInit.queryParams,//传递参数（*）
                sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber : 1, //初始化加载第一页，默认第一页
                pageSize : 10, //每页的记录行数（*）
                pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
                clickToSelect : true, //是否启用点击选中行
                height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "apiName", //每一行的唯一标识，一般为主键列
                sortName:'apiName',
                sortOrder:'asc',
                columns : [
                    {
                        title : '序号',
                        width : 25,
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
                        field : 'apiName',
                        title : '能力名称',
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
                    },{
                        field : 'restState',
                        title : activeTitle,
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    }],
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
                field : 'restState,apiName,appName'
            };

            var params = $("#chargeAccountForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };

    function reloadTable() {
        $('#chargeAccountList').bootstrapTable('refresh');
    }

</script>
</body>
</html>
