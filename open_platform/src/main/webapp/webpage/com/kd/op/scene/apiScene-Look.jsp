<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>场景表-查看</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <script src="${webRoot}/plug-in/lhgDialog/lhgdialog.min.js"></script>
    <script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
    <script src="${webRoot}/plug-in/tools/curdtools.js"></script>
    <link rel="stylesheet" href="plug-in/select2/css/select2.min.css" type="text/css"/>
    <script type="text/javascript" src="plug-in/select2/js/select2.full.min.js"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <style>
        .childTr {
            margin-left: 20px;
        }

        .btn-div a {
            border-color: #ccc;
            border-radius: 3px;
        }

        #step-1, #step-2, #step-3, #step-4, #step-5 {
            padding-top: 10px;
        }

        .table input, select {
            width: 100% !important;
        }

        .table th {
            white-space: nowrap
        }

        body {
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="container" style="width:100%;overflow-x:hidden">
    <div class="panel panel-default">
        <div class="panel-body">
            <form class="form-horizontal" role="form" id="addForm" method="POST">
                <input id="apiSceneId" name="apiScene.id" value="${apiScene.id}" type="hidden"/>
                <div id="smartwizard">

                    <ul>
                        <li><a href="#step-1">基本信息<br/></a></li>
                        <li><a href="#step-2">分配能力<br/></a></li>
                        <li><a href="#step-3">计费模型<br/></a></li>
                        <%--<li><a href="#step-4">资源控制<br /></a></li>--%>
                    </ul>
                    <div>
                        <div id="step-1" class="">
                            <div class="main-form">
                                <div class="row">
                                    <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="row">
                                            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                场景名称：
                                            </div>
                                            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                <input id="apiScene_sceneName" name="apiScene.sceneName"
                                                       value="${apiScene.sceneName}" type="text"
                                                       class="form-control input-sm" maxlength="100" ignore="ignore"/>
                                            </div>
                                            <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                    style="color:red">*</span></span>
                                        </div>
                                    </div>
                                    <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="row">
                                            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                场景描述：
                                            </div>
                                            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                <input name="apiScene.sceneDes" value="${apiScene.sceneDes}" type="text"
                                                       class="form-control input-sm" maxlength="200" ignore="ignore"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="row">
                                            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                审核状态：
                                            </div>
                                            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                <t:dictSelect readonly="readonly" field="apiAuditStatus" defaultVal = "${apiScene.sceneAuditStatus}" type="select" hasLabel="false" title="审核状态" extendJson="{class:'form-control input-sm'}"  typeGroupCode="auditSts" ></t:dictSelect>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="row">
                                            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                审核意见：
                                            </div>
                                            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                <textarea rows="4"  name="apiScene.sceneAuditMsg" readonly="readonly"  class="form-control input-sm">${apiScene.sceneAuditMsg}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="step-2" class="">
                            <div class="col-sm-2">
                                <ul id="groups" class="ztree"></ul>
                            </div>
                            <div class="panel-body col-sm-10" style="padding-top: 0px; padding-bottom: 0px;">
                                <div class="table-responsive">
                                    <table id="apiInfoList"></table>
                                </div>

                            </div>
                        </div>


                        <div id="step-3" class="">
                            <input id="chargeModeIds" name="chargeModeIds" type="hidden" value="${chargeModeIds}"/>
                            <ul class="nav nav-tabs nav-pills" role="tablist">
                                <li role="presentation" class="active" data-type="1"><a href="#monthlyPayment"
                                                                                        aria-controls="home" role="tab"
                                                                                        data-toggle="tab">包年包月</a></li>
                                <li id="payPer-li" role="presentation" data-type="2"><a href="#payPer"
                                                                                        aria-controls="profile"
                                                                                        role="tab" data-toggle="tab">按次收费</a>
                                </li>
                                <li id="payByFlow-li" role="presentation" data-type="3"><a href="#payByFlow"
                                                                                           aria-controls="profile"
                                                                                           role="tab" data-toggle="tab">按流量收费</a>
                                </li>
                            </ul>

                            <div class="tab-content" style="height:calc(100% - 48px)">
                                <div role="tabpanel" class="tab-pane active" id="monthlyPayment">
                                    <table id="monthlyPaymentList" data-type="1"></table>
                                </div>
                                <div role="tabpanel" class="tab-pane" id="payPer">
                                    <table id="payPerList" data-type="2"></table>
                                </div>
                                <div role="tabpanel" class="tab-pane" id="payByFlow">
                                    <table id="payByFlowList" data-type="3"></table>
                                </div>
                            </div>
                        </div>

                        <div id="step-4" class="" id="resourceInfo">
                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="col-md-2 col-sm-2 col-xs-2 bt-label" style="padding-top:3px;">
                                        启用资源控制：
                                    </div>
                                    <div class="switch">
                                        <input type="checkbox" id="resourceCtrlBtn"
                                               <c:if test="${apiScene.resourceCtrl == 1}">checked</c:if>/>
                                    </div>
                                </div>
                                <hr>
                                <div style="max-height:400px;overflow:auto;margin-bottom:20px;">
                                    <ul class="ztree" id="resources"></ul>
                                </div>
                            </div>
                            <input type="hidden" id="resourceCtrl" name="apiScene.resourceCtrl"
                                   value="${apiScene.resourceCtrl}"/>
                            <input id="resourceAccessIds" name="resourceAccessIds" type="hidden"
                                   value="${resourceAccessIds}"/>
                        </div>

                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#smartwizard').smartWizard({
            keyNavigation: false,   //禁用键盘左右键激活上一步、下一步
            cycleSteps: true, // Allows to cycle the navigation of steps
            lang: {  // Language variables
                next: '下一步',
                previous: '上一步'
            },
            anchorSettings: {
                anchorClickable: true, // Enable/Disable anchor navigation
                enableAllAnchors: true, // Activates all anchors clickable all times
                markDoneStep: true, // Add done css
                markAllPreviousStepsAsDone: true, // When a step selected by url hash, all previous steps are marked done
                removeDoneStepOnNavigateBack: false, // While navigate back done step after active step will be cleared
                enableAnchorOnDoneStep: true // Enable/Disable the done steps navigation
            },
            theme: 'dots',
            transitionEffect: 'fade', // Effect on navigation, none/slide/fade
            transitionSpeed: '400'
        });
        $("#smartwizard").find("li:first").find("a").click();

        formControlInit();
        $(".firstTr").data("key", dateFtt("hhmmssS", new Date()));

        $("#addForm :input").attr("readonly", "true");
        $("#addForm :checkbox").attr("disabled", "true");
        $("#addForm :radio").attr("disabled", "true");

        $('#smartwizard').attr("isDone", 1);

        inirGroupTree();
        initApiInfo();//加载分配服务
        initChargeMode();
        // initResourceCtrl();

        //初始化资源控制判断开关
        if ($("#resourceCtrl").val() == 1) {
            initResourceCtrl();//如果选项框开启，加载资源配置
        }
        $("#resourceCtrlBtn").bootstrapSwitch({
            size: "mini",
            onText: "启用",
            offText: "禁用",
            onColor: "success",
            offColor: "danger",
            // labelWidth: "8",//设置label宽度
            readonly: true,
        })

        $("#addForm :input").attr("readonly", "true");
        $("#addForm :checkbox").attr("disabled", "true");
        $("#addForm :radio").attr("disabled", "true");

        $('#smartwizard').attr("isDone", 1);

    });

    function initApiInfo() {

        $('#apiInfoList').bootstrapTable({
            url: 'apiSceneController.do?datagridSceneApiList', //请求后台的URL（*）
            method: 'post', //请求方式（*）
            contentType: "application/x-www-form-urlencoded",
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            queryParams: function (params) {
                var temp = { //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
                    pageSize: params.limit, // 每页要显示的数据条数
                    offset: params.offset, // 每页显示数据的开始行号
                    sort: params.sort, // 排序规则
                    order: params.order,
                    rows: params.limit, //页面大小
                    page: (params.offset / params.limit) + 1, //页码
                    pageIndex: params.pageNumber,//请求第几页
                    field: 'id,apiName,apiDesc,apiStatus,reqAddrHttp,reqAddrHsf,groupId,groupName,examData',
                    sceneId:'${apiScene.id}'
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
            clickToSelect: true, //是否启用点击选中行
// 				height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                    visible: false,//不可见
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
                    field: 'apiDesc',
                    title: '能力描述',
                    valign: 'middle',
                    width: 120,
                    align: 'center',
                    switchable: true,
                },
                {
                    field: 'groupName',
                    title: '能力中心名称',
                    valign: 'middle',
                    width: 120,
                    align: 'center',
                    switchable: true,
                }
            ]

        });
    }

    //加载计费模型
    function initChargeMode() {
        var tableNames = ["monthlyPaymentList", "payPerList", "payByFlowList"];
        var chargeModeIds = $("#chargeModeIds").val();
        var sceneId = '${apiScene.id}'; //注意：sceneId当做apiId用
        for (var i = 0; i < tableNames.length; i++) {
            var tableName = tableNames[i];
            var type = $("#" + tableName).data("type");
            $('#' + tableName).bootstrapTable({
                url: 'charging.do?datagridScene&sceneId=' + sceneId, //请求后台的URL（*）
                method: 'post', //请求方式（*）
                contentType: "application/x-www-form-urlencoded",
                striped: false, //是否显示行间隔色
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
                        field: 'id,type,num,startNum,endNum,unit,price'
                    };

                    // var params = $("#chargeModeForm").serializeArray();
                    // for (x in params) {
                    //     temp[params[x].name] = params[x].value;
                    // }
                    temp["type"] = type;
                    return temp;
                },
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 10, //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                clickToSelect: true, //是否启用点击选中行
                // height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                sortName: 'price',
                sortOrder: 'asc',
                tableId: tableName,
                columns: [
                    {
                        title: '序号',
                        width: 45,
                        align: 'center',
                        switchable: false,
                        formatter: function (value, row, index) {
                            //return index+1; //序号正序排序从1开始
                            var pageSize = 10
                            var pageNumber = 1;
                            return pageSize * (pageNumber - 1) + index + 1;
                        }
                    },
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
                        field: 'num',
                        title: '时间',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    }, {
                        field: 'startNum',
                        title: '起始值',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    }, {
                        field: 'endNum',
                        title: '截止值',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    }, {
                        field: 'unit',
                        title: '单位',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return "周";
                            } else if (value == 2) {
                                return "月";
                            } else if (value == 3) {
                                return "年";
                            } else if (value == 4) {
                                return "KB";
                            } else if (value == 5) {
                                return "次";
                            } else {
                                return value
                            }
                        }
                    },
                    {
                        field: 'price',
                        title: '价格(元)',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    }],
                onLoadSuccess: function (data) { //加载成功时执行
                    //将已经选中的计费方式再次选中
                    if (chargeModeIds != null && chargeModeIds != "") {
                        var ids = chargeModeIds.split(",");
                        $("#" + $(this)[0]["tableId"]).bootstrapTable("checkBy", {field: "id", values: ids})
                    }
                },
                onLoadError: function () { //加载失败时执行
                }
            });
            //处理需要隐藏的列
            if (type == 1) {
                $("#" + tableName).bootstrapTable("hideColumn", "startNum");
                $("#" + tableName).bootstrapTable("hideColumn", "endNum");
            } else if (type == 2) {
                $("#" + tableName).bootstrapTable("hideColumn", "num");
            } else if (type == 3) {
                $("#" + tableName).bootstrapTable("hideColumn", "num");
            }
        }
    }


    //加载资源控制
    function initResourceCtrl() {
        //构建树
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
                    rootPid: 0
                }
            },
            check: {
                enable: true,
                checkStyle: 'checkbox',
                chkboxType: {"Y": "s", "N": "s"}
            },
            callback: {
                // onCheck:zTreeOnClick
            }
        };
        var nodes = null;
        var checked = null;
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,   	//不设置缓存
            type: 'post',
            dataType: "json",
            data: {
                level: "1",
                type: "scene",
                id: $("#apiSceneId").val()
            },
            url: "resourceController.do?getCorpTree",//后台处理程序
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) { //请求成功后处理函数。
                nodes = data.nodes; //把后台封装好的简单Json格式赋给treeNodes
                checked = data.checked;//已经被选中的树节点

                $.fn.zTree.init($("#resources"), setting, nodes);
                //初始化树之后将选中的节点在树结构上选中
                var treeObj = $.fn.zTree.getZTreeObj("resources");
                for (var i = 0; i < checked.length; i++) {
                    var node = treeObj.getNodeByParam('id', checked[i]);
                    if (node != null) {
                        treeObj.checkNode(node, true);
                        if (node.isParent) {
                            treeObj.expandNode(node, true, true, true);
                        }
                    }
                }
            }
        });
    }

    var groupId = "";
    //初始化能力中心树
    function inirGroupTree(){
        //构建树  开始
        var setting = {
            view : {
                showLine : true,
                expandSpeed : "fast"
            },
            data : {
                simpleData : {
                    enable : true,
                    idKey : "id",
                    pIdKey : "pId",
                    rootPid : null
                }
            },
            callback : {
                onClick:onClick
            }
        };

        var zNodes =[];

        $.ajax({
            async: false,
            type:"post",
            dataType:"json",
            url:"apiGroupController.do?loadAll",
            success:function(data){
                zNodes = data;
            }
        })

        $.fn.zTree.init($("#groups"), setting, zNodes);

        function onClick(event, treeId, treeNode, clickFlag) {
            if (treeNode.id != 0) {
                groupId =treeNode.id;
            }else {
                groupId ="";
            }
            reloadApiInfo()
        }
    }

    function reloadApiInfo() {
        var param = {url: "apiSceneController.do?datagridSceneApiList&groupId="+ groupId};
        $('#apiInfoList').bootstrapTable('refresh',param);
    }
</script>
</body>
</html>