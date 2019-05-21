<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>能力参数选择模型</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
</head>
<body>
<div class="container" style="width:100%;overflow-x:hidden">
    <table class="table " id="models">
    </table>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('#models').bootstrapTable({
            url: 'apiParamModelController.do?datagrid', //请求后台的URL（*）
            method: 'post', //请求方式（*）
            contentType: "application/x-www-form-urlencoded",
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            queryParams:function(params){
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    pageSize: params.limit, // 每页要显示的数据条数
                    offset: params.offset, // 每页显示数据的开始行号
                    sort: params.sort, // 排序规则
                    order: params.order,
                    rows: params.limit, //页面大小
                    page: (params.offset / params.limit) + 1, //页码
                    pageIndex: params.pageNumber,//请求第几页
                    field: 'id,modelName,modelDesc,modelStatus,modelType,createName,createDate'
                };
                return temp;
            },//传递参数（*）
            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
            pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
            strictSearch: true,
            showColumns: false, //是否显示所有的列
            showRefresh: true, //是否显示刷新按钮
            minimumCountColumns: 2, //最少允许的列数
            clickToSelect: true, //是否启用点击选中行
            height: $(window).height() - 235, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id", //每一行的唯一标识，一般为主键列
            showToggle: false, //是否显示详细视图和列表视图的切换按钮
            cardView: false, //是否显示详细视图
            detailView: false, //是否显示父子表
            showExport: true, //显示到处按钮
            sortName: 'createDate',
            sortOrder: 'desc',
            columns: [
                {
                    radio: true,
                    width: 20,
                    align: 'center'
                },
                {
                    field: 'modelName',
                    title: '模型名称',
                    valign: 'middle',
                    width: 120,
                    align: 'center',
                    switchable: true,
                    formatter: function (value, rec, index) {
                        return "<span title=" + value + ">" + value + "</span>"
                    }
                }],
        });
    });
    function getSelectedModel(){
        var selected = $('#models').bootstrapTable("getSelections");
        return selected;
    }
</script>

</body>
</html>