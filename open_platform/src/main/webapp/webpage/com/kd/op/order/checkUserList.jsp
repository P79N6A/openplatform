<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>审核员列表</title>
</head>
<body>

<div class="table-responsive">
	<table id="checkUserList"></table>
</div>

<script type="text/javascript">

    $(function() {


    });


    //临时选中的服务map key为baseUserId value为选中行
    var apiTemps = new Map();
    //初始化Table
    $('#checkUserList').bootstrapTable({
        url : 'order.do?checkUserDatagrid', //请求后台的URL（*）
        method : 'post', //请求方式（*）
        contentType : "application/x-www-form-urlencoded",
        striped : true, //是否显示行间隔色
        cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination : true, //是否显示分页（*）
        queryParams :  function(params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize : params.limit, // 每页要显示的数据条数
                offset : params.offset, // 每页显示数据的开始行号
                sort : params.sort, // 排序规则
                order : params.order,
                rows : params.limit, //页面大小
                page : (params.offset / params.limit) + 1, //页码
                pageIndex : params.pageNumber,//请求第几页
                field : 'id,realName,userKey,userName'
            };

            var params = $("#apiSceneForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        },//传递参数（*）
        sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber : 1, //初始化加载第一页，默认第一页
        pageSize : 10, //每页的记录行数（*）
        pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
        strictSearch : true,
        showColumns: false, //是否显示所有的列
        showRefresh: true,//是否显示刷新按钮
        clickToSelect : true, //是否启用点击选中行
        uniqueId : "id", //每一行的唯一标识，一般为主键列
        columns : [
            {
                checkbox : false,
                width:35,
                align : 'center',
                formatter: function () {
                    return "<input class='td-ck' type='radio'/>";
                }
            },
//                    {
//                        title : '序号',
//                        width : "10%",
//                        align : 'center',
//                        switchable : false,
//                        formatter : function(value,row, index) {
//                            //return index+1; //序号正序排序从1开始
//                            var pageSize = 10
//                            var pageNumber = 1;
//                            return pageSize* (pageNumber - 1) + index + 1;
//                        }
//                    },
            {
                field : 'realName',
                title : '真实名称',
                valign : 'middle',
                width : 120,
                align : 'center',
                switchable : true,
                formatter : function(value, rec, index) {
                    return "<span title=" + value + ">" + value + "</span>"
                }
            },{
                field: 'userKey',
                title: '角色名称',
                valign: 'middle',
                width: 100,
                align: 'center',
                switchable: true,
                formatter : function(value, rec, index) {
                    return "<span title=" + value + ">" + value + "</span>"
                }
            },{
                field: 'userName',
                title: '用户名称',
                valign: 'middle',
                width: 100,
                align: 'center',
                switchable: true,
                formatter : function(value, rec, index) {
                    return "<span title=" + value + ">" + value + "</span>"
                }
            }],
        onLoadSuccess : function(data) { //加载成功时执行

        },
        onLoadError : function() { //加载失败时执行
        },
        onClickCell: function (field, value, row, element) {
            if (field != "action") {
                if ($(element).parent().hasClass('success')) {//这里进行了判断是否选中
                    unCheckedTr($(element).parent());
                } else {
                    checkedTr($(element).parent());
                }
            }
        }
    });



    function reloadTable() {
        $('#checkUserList').bootstrapTable('refresh');
    }

    $('#apiSceneForm').find("input[type='radio']").each(function () {
        $(this).attr('checked', false);
    });


    //选中tr
    function checkedTr($tr) {
        $tr.addClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' checked type='radio'/>");
        var tempApi = {};
        tempApi.id= $tr.attr("data-uniqueid");
        tempApi.auditBy = $tr.find('td').eq(3).text();
        apiTemps.set(tempApi.id,tempApi);
    }
    //取消选中tr
    function unCheckedTr($tr) {
        $tr.removeClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' type='radio'/>")
        //从api临时列表删除
        apiTemps.delete($tr.attr("data-uniqueid"));
    }
</script>
</body>
</html>