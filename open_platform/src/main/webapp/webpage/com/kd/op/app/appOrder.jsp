<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<html>
<head>
    <title>订单和订购时选择计费模型</title>
    <style>
        .childTr{
            margin-left:20px;
        }
        .btn-div a{
            border-color:#ccc;
            border-radius:3px;
        }
        #step-1,#step-2,#step-3,#step-4{
            padding-top:10px;
        }
        .table-div{
            overflow:auto;
            min-height: 200px;
            max-height: 400px;
        }
        .table input, select{
            width: 100% !important;
        }
        .table th{
            white-space: nowrap
        }
        body{
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="container" style="width:100%;overflow-x:hidden">

    <div class="panel panel-default">
        <div class="panel-body">
            <form class="form-horizontal" role="form" id="addSceneForm" method="POST" >
                <div id="smartwizard">
                    <ul>
                        <li><a href="#step-1">计费模型<br /></a></li>
                    </ul>
                    <div>
                        <div id="step-1" class="">
                            <input id="appId" name="appId" type="hidden" value="${appId}"/>
                            <input id="chargeId" name="chargeId" type="hidden"/>
                            <ul class="nav nav-tabs nav-pills" role="tablist">
                                <li role="presentation" class="active" data-type="1"><a href="#monthlyPayment" aria-controls="home" role="tab" data-toggle="tab">包年包月</a></li>
                                <%--<li id="payPer-li" role="presentation" data-type="2"><a href="#payPer" aria-controls="profile" role="tab" data-toggle="tab">按次收费</a></li>--%>
                                <%--<li id="payByFlow-li" role="presentation" data-type="3"><a href="#payByFlow" aria-controls="profile" role="tab" data-toggle="tab">按流量收费</a></li>--%>
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
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">

    $(document).ready(function(){
        smartLI();
        var appid = $("#appId").val();
        //根据appid  chargid查出计费模型生成table
        initChargeMode(appid);

        //表单提交
        $("#btn_sub").bind("click",function(){
            var addJson = getFormJson($('#addSceneForm'));
            //处理计费方式的id
            var selectIds = "";
            var monthlyPayment = $("#monthlyPaymentList").bootstrapTable("getSelections");
            if (monthlyPayment != null && monthlyPayment.length > 0) {
                for (var i = 0; i < monthlyPayment.length; i++) {
                    selectIds += monthlyPayment[i].id + ",";
                }
            }
            //提交表单之前判断是否已选择计费模型
            if(selectIds == ""){
                slowNotify("请选择计费模型!","danger");
            }else {
                selectIds = selectIds.substring(0, selectIds.length - 1);
                $("#chargeId").val(selectIds);
                var options = {
                    url: "order.do?saveAppOrder&chargeId="+selectIds+"&orderType="+"app",
                    type: "post",
                    contentType : "application/x-www-form-urlencoded",
                    dataType: "json",
                    beforeSubmit: function () {
                        return true;
                    },
                    success: function (data) {
                        if (data.success) {
                            $(".bootstrap-dialog").modal("hide")
                            quickNotify(data.msg, "success");
                        } else {
                            slowNotify(data.msg, "danger");
                        }
                    },
                }
                $("#addSceneForm").ajaxSubmit(options);
            }
        })
    })
    function smartLI(){
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
            transitionSpeed: '400',
        });
    }
    function initChargeMode(appid) {
        var tableNames = ["monthlyPaymentList", "payPerList", "payByFlowList"];
        for (var i = 0; i < tableNames.length; i++) {
            var tableName = tableNames[i];
            var type = $("#" + tableName).data("type");
            $('#' + tableName).bootstrapTable({
                url: 'order.do?datagridCharge&appId='+appid, //请求后台的URL（*）
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
                columns: [
                    {
                        checkbox: true,
                        align: 'center'
                    }, {
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

    /!*将表单对象变为json对象*!/
    function getFormJson(form) {
        var o = {};
        var a = $(form).serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    }

</script>

</html>
