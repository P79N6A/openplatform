<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    //保存全部的服务与资源监控的映射
    var apiResourceMap = new Map();

    //临时选中的场景map key为sceneId value为scene对象
    var secensTemps = new Map();

    $(function() {

        orderDetails = ${orderDetails};
        //缓存计费模型
        $.each(orderDetails,function(i,val){
            $("#chargeModeIds").append("<input id='" + val.sceneId + "'/>")
            $("#" + val.sceneId).val(val.chargeId);
            var tempScene = {};
            tempScene.sceneId= val.sceneId;
            tempScene.sceneName = val.sceneName;
            secensTemps.set(tempScene.sceneId,tempScene);
            apiResourceMap.set(val.sceneId,val.resourceId);
        })
    });

    //初始化表格
    $('#apiSceneList').bootstrapTable({
        url : 'order.do?orderSceneDatagrid', //请求后台的URL（*）
        method : 'get', //请求方式（*）
        striped : true, //是否显示行间隔色
        cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination : true, //是否显示分页（*）
        queryParams : function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize : params.limit, // 每页要显示的数据条数
                offset : params.offset, // 每页显示数据的开始行号
                sort : params.sort, // 排序规则
                order : params.order,
                rows : params.limit, //页面大小
                page : (params.offset / params.limit) + 1, //页码
                pageIndex : params.pageNumber,//请求第几页
                field : 'id,sceneName,resourceCtrl'
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
        clickToSelect : false, //是否启用点击选中行
//        height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId : "id", //每一行的唯一标识，一般为主键列
        sortName:'createTime',
        sortOrder:'desc',
        columns : [
            {
                checkbox: false,
                align: 'center',
                width: 20,
                formatter: function () {
                    return "<input class='td-ck' type='radio' name='secensCheck' />";
                }
            },
//            {
//                field : 'id',
//                title : 'id',
//                valign : 'middle',
//                width : 120,
//                visible:false,
//                align : 'center',
//                switchable : true,
//            },
            {
                field : 'sceneName',
                title : '服务场景名称',
                valign : 'middle',
                width : 100,
                align : 'center',
                switchable : true,
            },
            {
                title: "操作",
                align: 'left',
                valign: 'middle',
                field: 'action',
                width: 150,
                formatter: function (value, row, index) {
                    if (!row.id) {
                        return '';
                    }
                    var href = '';
                    href += "<button class='btn btn-xs btn-primary' style='' onclick='sceneApiList(\"" + row.id + "\")'><span>查看服务</span></button>&nbsp";
                    href += "<button class='btn btn-xs btn-success' onclick='configCharging(\"" + row.id + "\"," + index + ")'><span>计费方式</span></button>&nbsp";
//                    if(row.resourceCtrl==1){
//                        href += "<button class='btn btn-xs btn-success' onclick='configResource(\"" + row.id + "\")'><span>资源控制</span></button>";
//                    }
                    return href;
                }
            } ],
        onLoadSuccess : function() { //加载成功时执行
            //回显选场景
            secensTemps.forEach(function (item, key, mapObj) {
                if (key != null && key != "") {
                    rollbackTr($("tr[data-uniqueid=" + key + "]"));
                }
            });
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
        $('#apiSceneList').bootstrapTable('refresh');
    }

    $('#apiSceneForm').find("input[type='radio']").each(function() {
        $(this).attr('checked', false);
    });


    //配置计费模型
    function configCharging(id,index){
        var ss = "";
        if($("#" + id).length != 0){
            ss = $("#" + id).val();
        }
        var dia = new BootstrapDialog({
            title: "配置计费方式",
            type:BootstrapDialog.TYPE_DEFAULT,
            size:"size-wide",
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "order.do?orderConfigCharging&sceneId=" + id + "&selectedId=" + ss
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id:"btn_sub",
                action: function(dialogRef){
                    //首先判断是否有选中的计费方式
                    var temp = $("#temp").val();
                    if(temp == null || temp == ""){
                        slowNotify("请先选择一个计费方式！","danger");
                    }else{
                        //如果有选中项就将选中项保存到指定的位置
                        $("#selectedId").val(temp);

                        var selectedId = $("#selectedId").val();
                        if($("#" + id).length == 0){
                            $("#chargeModeIds").append("<input id='" + id + "'/>")
                        }
                        $("#" + id).val(selectedId);
                        dialogRef.close();

                        //先把所有的选中都清除
                        $("tr[data-uniqueid=" + id + "]").parent().find("tr").each(function(){
                            if($(this).hasClass("success")){
                                $(this).removeClass("success");
                            }
                        })
                        //执行保存动作时选中当前行
                        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
                            checkedTr($("tr[data-uniqueid=" + id + "]"));
                        }
                        quickNotify("计费方式选择成功","success");
                    }
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialogItself){
                    if($("#" + id).val() == null || $("#" + id).val() == ""){
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
    function configResource(id){
        var dia = new BootstrapDialog({
            title: "配置资源控制",
            type:BootstrapDialog.TYPE_DEFAULT,
            size:"size-wide",
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "resourceController.do?selectResource&id=" + id + "&level=2&type=scene",
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id:"btn_sub",
                action: function(dialogRef){
                    var checkedResource = getCheckedReource();
                    apiResourceMap.set(id,checkedResource)
                    dialogRef.close();
                    //执行保存动作时选中当前行
                    if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
                        rollbackTr($("tr[data-uniqueid=" + id + "]"));
                    }
                    quickNotify("资源配置成功","success");
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }

    /**
     * 场景的服务列表
     * @param id
     */
    function sceneApiList(id){
        var dialog = bootOpenLook('场景服务列表', 'order.do?sceneApiList&id=' + id, 'wide');
    }

    //回显选中tr
    function rollbackTr($tr) {
        $tr.addClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' checked type='radio' name='secensCheck'/>");
    }

    //选中tr
    function checkedTr($tr) {
        $tr.addClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' checked type='radio' name='secensCheck'/>");
        //先清空保证唯一性
        if(secensTemps!=null){
            secensTemps.clear();
        }
        var tempScene = {};
        tempScene.sceneId= $tr.attr("data-uniqueid");
        tempScene.sceneName = $tr.find('td').eq(1).text();
        secensTemps.set(tempScene.sceneId,tempScene);
    }
    //取消选中tr
    function unCheckedTr($tr) {
        $tr.removeClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' type='radio' name='secensCheck' />")
        //从api临时列表删除
        secensTemps.delete($tr.attr("data-uniqueid"));
    }
</script>
</html>
