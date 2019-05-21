<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单中场景选择</title>
</head>
<body>
<div class="table-responsive">
    <table id="apiSceneList"></table>
</div>
<div id="chargeModeIds" style="display:none">

</div>
</body>
<script type="text/javascript">

    //订单详情列表
    var orderDetails;

    //保存全部的能力与资源监控的映射
    var apiResourceMap = new Map();

    //临时选中的场景map key为sceneId value为scene对象
    var secensTemps = new Map();

    $(function () {

        orderDetails = ${orderDetails};
        //缓存计费模型
        $.each(orderDetails, function (i, val) {
            $("#chargeModeIds").append("<input id='" + val.sceneId + "'/>")
            $("#" + val.sceneId).val(val.chargeId);
            var tempScene = {};
            tempScene.sceneId = val.sceneId;
            tempScene.sceneName = val.sceneName;
            secensTemps.set(tempScene.sceneId, tempScene);
            apiResourceMap.set(val.sceneId, val.resourceId);
        })
    });

    //初始化表格
    $('#apiSceneList').bootstrapTable({
        url: 'order.do?datagridAuditScene&orderId=' + '${orderId}', //请求后台的URL（*）
        method: 'get', //请求方式（*）
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
                field: 'id,sceneName,createTime,resourceCtrl'
            };

            var params = $("#apiSceneForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        },//传递参数（*）
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 10, //每页的记录行数（*）
        pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
        strictSearch: true,
        clickToSelect: false, //是否启用点击选中行
//        height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id", //每一行的唯一标识，一般为主键列
        sortName: 'createTime',
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
                field: 'sceneName',
                title: '能力场景名称',
                valign: 'middle',
                width: 100,
                align: 'center',
                switchable: true,
            },
            {
                title: "操作",
                align: 'left',
                valign: 'middle',
                width: 150,
                formatter: function (value, row, index) {
                    if (!row.id) {
                        return '';
                    }
                    var href = '';
                    href += "<button class='btn btn-xs btn-primary' onclick='sceneApiList(\"" + row.id + "\")'><span>查看能力</span></button>&nbsp";
                    href += "<button class='btn btn-xs btn-success' onclick='configCharging(\"" + row.id + "\"," + index + ")'><span>计费方式</span></button>&nbsp";
//                    if (row.resourceCtrl == 1) {
//                        href += "<button class='btn btn-xs btn-success' onclick='configResource(\"" + row.id + "\")'><span>资源控制</span></button>";
//                    }
                    return href;
                }
            }],
        onLoadSuccess: function () { //加载成功时执行
        },
        onLoadError: function () { //加载失败时执行
        },
        onClickCell: function (field, value, row, element) {
        }
    });


    function reloadTable() {
        $('#apiSceneList').bootstrapTable('refresh');
    }

    //配置计费模型
    function configCharging(id, index) {
        var ss = "";
        if ($("#" + id).length != 0) {
            ss = $("#" + id).val();
        }
        var dia = new BootstrapDialog({
            title: "配置计费方式",
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "size-wide",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "order.do?orderConfigCharging&sceneId=" + id + "&selectedId=" + ss +"&orderId="+'${orderId}'
            },
            buttons: [{
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    if ($("#" + id).val() == null || $("#" + id).val() == "") {
                        $("tr[data-uniqueid=" + id + "]").removeClass("success");
                    }
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }

    /**
     * 配置资源控制
     * @param id
     */
    function configResource(id) {
        var dia = new BootstrapDialog({
            title: "配置资源控制",
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "size-wide",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "resourceController.do?selectResource&id=" + id + "&level=2&type=scene"+"&orderId="+'${orderId}'
            },
            buttons: [{
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }

    /**
     * 场景的能力列表
     * @param id
     */
    function sceneApiList(id){
        var dialog = bootOpenLook('场景能力列表', 'order.do?sceneApiList&id=' + id, 'wide');
    }
</script>
</html>
