<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>场景订单中的能力列表查看</title>
</head>
<body>
<div class="col-sm-2">
    <ul id="groups" class="ztree"></ul>
</div>
<div class="panel-body col-sm-10" style="padding-top: 0px; padding-bottom: 0px;">
    <form id="apiInfoForm">
        <div>
            <input type="hidden" id="groupId" name="groupId"/>
        </div>
    </form>
    <div class="table-responsive">
        <table id="apiInfoList1"></table>
    </div>
</div>
<script type="text/javascript">

    //解析字典
    var apiInfoListdictsData = {};
    // var promiseArr = [];
    // promiseArr.push(new Promise(function (resolve, reject) {
        initDictByCode(apiInfoListdictsData, "apiSts", function(){});
    // }));

    $(function () {
        init();
    });

    //初始化操作
    function init() {
        //处理左侧的分组树
        //构建树  开始
        var setting = {
            view: {
                showLine: true,
                expandSpeed: "fast"
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPid: null
                }
            },
            callback: {
                onClick: onClick
            }
        };

        var zNodes = [];
        $.ajax({
            async: false,
            type: "post",
            dataType: "json",
            url: "apiGroupController.do?loadAll",
            success: function (data) {
                zNodes = data;
            }
        })
        $.fn.zTree.init($("#groups"), setting, zNodes);
    }


    function onClick(event, treeId, treeNode, clickFlag) {
        if (treeNode.id == 0) {
            $("#groupId").val(null);
        } else {
            $("#groupId").val(treeNode.id);
        }
        reloadTable();
    }
    //构建树  结束


    //1.初始化Table
    $('#apiInfoList1').bootstrapTable({
        url: 'order.do?datagridSceneApiList&sceneId=${sceneId}' , //请求后台的URL（*）
        method: 'post', //请求方式（*）
        contentType : "application/x-www-form-urlencoded",
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        queryParams: function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sort: params.sort, // 排序规则
                order: params.order,
                rows: params.limit, //页面大小
                page: (params.offset / params.limit) + 1, //页码
                pageIndex: params.pageNumber,//请求第几页
                field: 'id,apiName,apiStatus,groupName,resourceCtrl,createDate'
            };
            var params = $("#apiInfoForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        },
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 10, //每页的记录行数（*）
        pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
        strictSearch: true,
        showColumns: false, //是否显示所有的列
        showRefresh: true, //是否显示刷新按钮
        minimumCountColumns: 2, //最少允许的列数
        clickToSelect: false, //是否启用点击选中行
        uniqueId: "id", //每一行的唯一标识，一般为主键列
        showToggle: false, //是否显示详细视图和列表视图的切换按钮
        cardView: false, //是否显示详细视图
        detailView: false, //是否显示父子表
        showExport: true, //显示到处按钮
        sortName: 'createDate',
        sortOrder: 'desc',
        columns: [
            {
                field: 'id',
                title: 'id',
                valign: 'middle',
                width: 120,
                visible: false,
                align: 'center',
                switchable: true,
            },
            {
                field: 'apiName',
                title: '能力名称',
                valign: 'middle',
                width: 120,
                align: 'center',
                switchable: true,
            },
            {
                field: 'apiStatus',
                title: '能力状态',
                valign: 'middle',
                width: 60,
                align: 'center',
                switchable: true,
                formatter: function (value, rec, index) {
                    return listDictFormat(value, apiInfoListdictsData.apiSts);
                }
            },
            {
                field: 'groupName',
                title: '能力中心名称',
                valign: 'middle',
                width: 60,
                align: 'center',
                switchable: true,
            },
//            {
//                title: "操作",
//                field: "action",
//                align: 'left',
//                valign: 'middle',
//                width: 150,
//                formatter: function (value, row, index) {
//                    var href='';
//                    if (!row.id) {
//                        return '';
//                    }
//                    return href;
//                }
//            }
        ],
        onLoadSuccess: function () { //加载成功时执行
        },
        onLoadError: function () { //加载失败时执行
        }
    });

    function reloadTable() {
        $('#apiInfoList1').bootstrapTable('refresh');
    }

</script>
</body>
</html>