<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>应用中能力选择</title>
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
    <div id="chargeModeIds" style="display:none">

    </div>
</div>
<script type="text/javascript">
    //全局变量
    var apiInfoListdictsData = {};
    //保存全部的服务与资源监控的映射
    var apiResourceMap = new Map();
    //临时选中的服务map key为apiId value为选中行
    var apiTemps = new Map();
    //保存能力与URL关系
    var urlMap = new Map();
    // var tagMap = new Map();

    var webServiceInfoMap = new Map();

    $(function () {
        /*$('#apiInfoList1').css("display","none");*/
        $(".modal-dialog").css("width", "1200px");
        init();


    });
    var group_name;
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
             var result;

                var appType=${appType};
                /*console.log("appType="+appType);*/
                if( appType==1){

                    group_name="互联互通";
                    result= data.filter((p) => {
                        return p.name=="互联互通";
                    });

                }else if(appType==0||appType==""||appType==undefined){
                    group_name="";
                   result= data.filter((p) => {
                        return p.name!="互联互通";
                    });
                }
               /* console.log(data);
                console.log(data.toJSONString);*/
                zNodes = result;
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

    //处理右侧服务信息表格
    // var promiseArr = [];
    // promiseArr.push(new Promise(function (resolve, reject) {
    initDictByCode(apiInfoListdictsData, "apiSts", function () {
    });
    // }));

    // Promise.all(promiseArr).then(function (results) {
    //
    // }).catch(function (err) {
    // });

    //1.初始化Table
    $('#apiInfoList1').bootstrapTable({
        url: 'order.do?orderApidatagrid&groupName='+group_name, //请求后台的URL（*）
        method: 'post', //请求方式（*）
        contentType: "application/x-www-form-urlencoded",
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
                field: 'id,apiName,apiStatus,groupName,resourceCtrl,apiRunStatus,apiPublishStatus,apiInvokeType,resourceCtrl,pubMode'
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
                checkbox: false,
                align: 'center',
                width: 20,
                formatter: function () {
                    return "<input class='td-ck' type='checkbox'/>";
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
                field: 'apiName',
                title: '能力名称',
                valign: 'middle',
                width: 120,
                align: 'center',
                switchable: true,
            },
            {
                field: 'apiInvokeType',
                title: '能力类型',
                valign: 'middle',
                width: 50,
                align: 'center',
                switchable: true,
                formatter: function (value, rec, index) {
                    if(value == null || value == "" || value == "1"){
                        return "被动能力";
                    }else if(value == "2"){
                        return "主动能力";
                    }
                    return value;
                }
            },
            // {
            //     field: 'apiPublishStatus',
            //     title: '发布状态',
            //     valign: 'middle',
            //     width: 50,
            //     align: 'center',
            //     switchable: true,
            //     formatter: function (value, rec, index) {
            //         if (value == 0) {
            //             return "未发布";
            //         } else if (value == 1) {
            //             return "已发布";
            //         } else if (value == -1) {
            //             return "作废";
            //         }
            //     }
            // },
            {
                field: 'apiStatus',
                title: '能力状态',
                valign: 'middle',
                width: 50,
                align: 'center',
                switchable: true,
                formatter: function (value, rec, index) {
                    return listDictFormat(value, apiInfoListdictsData.apiSts);
                }
            },
            {
                field: 'groupName',
                title: '能力中心',
                valign: 'middle',
                width: 70,
                align: 'center',
                switchable: true,
            }, {
                field: 'apiRunStatus',
                title: '能力运行状态',
                valign: 'middle',
                width: 70,
                align: 'center',
                switchable: true,
                formatter: function (value, rec, index) {
                    if (value == 0) {
                        value = "停止";
                    } else {
                        value = "运行中";
                    }
                    return value;
                }
            },
            {
                title: "操作",
                align: 'left',
                field: 'action',
                valign: 'middle',
                width: 130,
                formatter: function (value, row, index) {
                    if (!row.id) {
                        return '';
                    }
                    var href = '';
                    href += "<button class='btn btn-xs btn-primary' onclick='apiCheck(\"" + row.id + "\")'><span>查看</span></button>&nbsp";
                    href += "<button class='btn btn-xs btn-success' onclick='configCharging(\"" + row.id + "\"," + index + ")'><span>计费方式</span></button>&nbsp";
                    /*if (row.apiInvokeType == 2 && row.pubMode != 4) {
                        href += "<button class='btn btn-xs btn-warning' data-toggle='popover'" +
                            " data-apiid='" + row.id + "' data-index=" + index + "><span>配置URL</span></button>";
                    }*/
                    if (row.apiInvokeType == 2) {
                        // href += "<button class='btn btn-xs btn-warning' data-toggle='popover2'" +
                        //     " data-apiid='" + row.id + "' data-index=" + index + "><span>配置Tag</span></button>";
                        href += "<button class='btn btn-xs btn-success' id='configWevservice' onclick='configAcceptInformation(\"" + row.id + "\")'><span>配置接收信息</span></button>&nbsp";
                        // href += "<button class='btn btn-xs btn-success' id='configWevservice' onclick='apiConfigWevservice(\"" + row.id + "\")'><span>配置接收信息</span></button>&nbsp";
                    }
                    <%--var rCode = "${rCode}";--%>
                    <%--if (row.resourceCtrl == 1 && rCode == "admi") {--%>
                        <%--href += "<button class='btn btn-xs btn-warning' id='source' onclick='configResource(\"" + row.id + "\")'>" +--%>
                            <%--"<span>配置资源控制</span></button>&nbsp";--%>
                    <%--}--%>
                    return href;
                }
            }
        ],
        onLoadSuccess: function () { //加载成功时执行
            //回显选中服务
            apiTemps.forEach(function (item, key, mapObj) {
                if (key != null && key != "") {
                    checkedTr($("tr[data-uniqueid=" + key + "]"));
                }
            });
            $('[data-toggle="popover"]').each(function () {
                var element = $(this);
                var apiid = element.data('apiid');
                var content = "<div class='row'><div class='col-sm-8'>" +
                    "<input class='pop-url form-control' id='urlId' placeholder='请输入URL' data-apiid='" + apiid + "' type='text' /></div>" +
                    "<div clsss='col-sm-4'><button class='btn btn-primary' onclick='saveUrl(this)'>确认</button></div></div>";
                element.popover({
                    trigger: 'manual',
                    placement: 'top', //top, bottom, left or right
                    // title: "配置URL",
                    html: 'true',
                    content: content,
                    trigger: "click"
                }).on("click", function () {
                    var _this = $(this);
                    $('[data-toggle="popover"]').each(function () {
                        if ($(this).data("index") != _this.data("index")) {
                            $(this).popover("hide");
                        }
                    });

                }).on("shown.bs.popover", function () {
                    var $input = $(this).next().find("#urlId");
                    //获取已经配置过的url数据
                    var url = urlMap.get(apiid);
                    if (url != null && url != "" && url != undefined) {
                        $input.val(url);
                    }
                });
            });
            $('[data-toggle="popover2"]').each(function () {
                var element = $(this);
                var apiid = element.data('apiid');
                var content = "<div class='row'><div class='col-sm-8'>" +
                    "<input class='pop-url form-control' id='tagId' placeholder='请输入Tag' data-apiid='" + apiid + "' type='text' /></div>" +
                    "<div clsss='col-sm-4'><button class='btn btn-primary' onclick='saveTag(this)'>确认</button></div></div>";
                element.popover({
                    trigger: 'manual',
                    placement: 'top', //top, bottom, left or right
                    // title: "配置URL",
                    html: 'true',
                    content: content,
                    trigger: "click"
                }).on("click", function () {
                    var _this = $(this);
                    $('[data-toggle="popover2"]').each(function () {
                        if ($(this).data("index") != _this.data("index")) {
                            $(this).popover("hide");
                        }
                    });
                }).on("shown.bs.popover", function () {
                    var $input = $(this).next().find("#tagId");
                    //获取已经配置过的tag数据
                    var tag = tagMap.get(apiid);
                    if (tag != null && tag != "" && tag != undefined) {
                        $input.val(tag);
                    }
                });
            });
        },
        onLoadError: function () { //加载失败时执行
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


    /**
     * 保存主动推送能力的url
     */
    function saveUrl(obj) {
        var input = $(obj).parent().parent().find("#urlId");
        var popover = $(obj).parent().parent().parent().parent().prev();
        var url = input.val();
        if (url == null || url == "") {
            slowNotify("请输入URL", "danger");
            return;
        }
        //将url跟能力关联起来
        var id = popover.data("apiid");
        urlMap.set(id, url);
        //执行保存动作时选中当前行
        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
            checkedTr($("tr[data-uniqueid=" + id + "]"));
        }
        //最后关闭popover
        popover.popover("hide");
        quickNotify("URL配置成功", "success");
    }

    /**
     * 保存tag
     */
    function saveTag(obj) {
        var input = $(obj).parent().parent().find("#tagId");
        var popover = $(obj).parent().parent().parent().parent().prev();
        var tag = input.val();
        if (tag == null || tag == "") {
            slowNotify("请输入Tag", "danger");
            return;
        }
        var id = popover.data("apiid");
        tagMap.set(id, tag);
        //执行保存动作时选中当前行
        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
            checkedTr($("tr[data-uniqueid=" + id + "]"));
        }
        //最后关闭popover
        popover.popover("hide");
        quickNotify("tag配置成功", "success");
    }

    function reloadTable() {

        $('#apiInfoList1').bootstrapTable('refresh');
       /* $('#apiInfoList1').css("display","block");*/

    }

    function apiCheck(id) {
        bootOpenLook("能力查看", 'apiInfoController.do?goUpdate&id=' + id + "&type=3", "wide");
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
                'pageToLoad': "order.do?orderConfigCharging&apiId=" + id + "&selectedId=" + ss
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id: "btn_sub",
                action: function (dialogRef) {
                    //首先判断是否有选中的计费方式
                    var temp = $("#temp").val();
                    if (temp == null || temp == "") {
                        slowNotify("请先选择一个计费方式！", "danger");
                    } else {
                        //如果有选中项就将选中项保存到指定的位置
                        $("#selectedId").val(temp);

                        var selectedId = $("#selectedId").val();
                        if ($("#" + id).length == 0) {
                            $("#chargeModeIds").append("<input id='" + id + "'/>")
                        }
                        $("#" + id).val(selectedId);

                        //执行保存动作时选中当前行
                        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
                            checkedTr($("tr[data-uniqueid=" + id + "]"));
                        }
                        dialogRef.close();
                        quickNotify("计费方式选择成功", "success");
                    }
                }
            }, {
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
            size: "mini",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "resourceController.do?selectResource&id=" + id ,
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id: "btn_sub",
                action: function (dialogRef) {
                    // var checkedResource = getCheckedReource();
                    var checkedResource = getFormData();
                    if (checkedResource.length < formDataLength) {
                        slowNotify("请完成资源控制配置！", "danger");
                    }else {
                        apiResourceMap.set(id, checkedResource);
                        //执行保存动作时选中当前行
                        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
                            checkedTr($("tr[data-uniqueid=" + id + "]"));
                        }
                        dialogRef.close();
                        quickNotify("资源配置成功", "success");
                    }
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }



    /*//配置webService
    function apiConfigWevservice(id) {
        var dia = new BootstrapDialog({
            title: "配置webService",
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "mini",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "order.do?appOrderWebserviceConfig&id=" + id ,
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id: "btn_sub",
                action: function (dialogRef) {
                    // var checkedResource = getCheckedReource();
                    var checkedwebServiceInfo = getFormData();
                    if (checkedwebServiceInfo.length < formDataLength) {
                        slowNotify("请完成webservice配置！", "danger");
                    }else {
                        webServiceInfoMap.set(id, checkedwebServiceInfo);
                        //执行保存动作时选中当前行
                        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
                            checkedTr($("tr[data-uniqueid=" + id + "]"));
                        }
                        dialogRef.close();
                        quickNotify("webservice配置成功", "success");
                    }
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }*/
    //配置接收信息
    function configAcceptInformation(id) {
        var dia = new BootstrapDialog({
            title: "配置接收信息",
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "mini",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "order.do?appOrderWebserviceConfig&id=" + id +"&fromDatabase="+"0",
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id: "btn_sub",
                action: function (dialogRef) {
                    var checkedwebServiceInfo = getFormData();
                    if (checkedwebServiceInfo.length < formDataLength) {
                        slowNotify("请完成接收信息配置！", "danger");
                    }else {
                        webServiceInfoMap.set(id, checkedwebServiceInfo);
                        //执行保存动作时选中当前行
                        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
                            checkedTr($("tr[data-uniqueid=" + id + "]"));
                        }
                        dialogRef.close();
                        quickNotify("接收信息配置成功", "success");
                    }
                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }


    //选中tr
    function checkedTr($tr) {
        $tr.addClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' checked type='checkbox'/>");
        var tempApi = {};
        tempApi.apiId = $tr.attr("data-uniqueid");
        tempApi.apiName = $tr.find('td').eq(1).text();
        if ($tr.find("[data-toggle='popover']").length > 0) {
            tempApi.apiInvokeType = 2;
        } else {
            tempApi.apiInvokeType = 1;
        }
        if ($tr.find("[data-toggle='popover2']").length > 0) {
            tempApi.pubMode = 2;
        } else if($tr.find("[id='configWevservice']").length > 0){
            tempApi.pubMode = 4;
        } else{
            tempApi.pubMode = 1;
        }
        if ($tr.find("[id='source']").length > 0) {
            tempApi.resourceCtrl=1;
        } else {
            tempApi.resourceCtrl=0;
        }
        apiTemps.set(tempApi.apiId, tempApi);
    }

    //取消选中tr
    function unCheckedTr($tr) {
        $tr.removeClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' type='checkbox'/>")
        //从api临时列表删除
        apiTemps.delete($tr.attr("data-uniqueid"));
    }

    $(document).ready(function(){

        $("#btn_sub_l").click(function () {
            location.href="resourceController.do?insertResource";
        });



    });


</script>
</body>
</html>