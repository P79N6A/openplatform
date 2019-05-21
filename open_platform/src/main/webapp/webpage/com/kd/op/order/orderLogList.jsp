<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/select2/css/select2.min.css">
    <script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.min.js"></script>
</head>
<style>
    .trg{
        width: 0;
        height: 0;
        border-left: 3px solid transparent;
        border-right: 3px solid transparent;
        border-top: 6px solid black;;
        position: absolute;
        left:181px;
        top:8px;

    }
    .org-select{
        cursor: default;
        z-index: -1;
        width:200px;
    }
</style>
<body>
<div class="panel-body" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="apiOrderForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="col-sm-5 control-label near-padding-right" for="orderType">订购类型：</label>
                                <div class="col-sm-7 no-padding">
                                    <select class="selectpicker form-control" id="orderType" name="orderType" onchange="orderCharge()" >
                                        <option value="" selected="selected">全部</option>
                                        <option value="0">能力订单</option>
                                        <option value="1">场景订单</option>
                                        <option value="2">应用订单</option>
                                    </select>
                                </div>

                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4" id="detail">
                            </div>

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="input-group col-md-12" style="margin-top: 20px">
                                    <a class='btn btn-success' onclick="searchList();"><span
                                            class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>&nbsp
                                    <a class='btn btn-warning' onclick="searchRest();"><span
                                            class="glyphicon glyphicon-repeat" aria-hidden="true"></span>重置</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
    <div id="toolbar">
        <%--<button id="btn_detail" onclick="goOrderDetailPage()"  class="btn btn-info btn-sm">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
        </button>--%>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="orderLogList"></table>
    </div>
</div>
<script type="text/javascript">




    var systemOrderTypeData = {

    };
    $(function(){
        //初始化数据字典
        initDictByCode(systemOrderTypeData, "order", function () {
        });
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
    })

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#orderLogList').bootstrapTable({
                url: 'orderLogController.do?orderClassifyDatagrid', //请求后台的URL（*）
                method: 'post', //请求方式（*）
                contentType : "application/x-www-form-urlencoded",
                toolbar: '#toolbar', //工具按钮用哪个容器
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true, //是否显示分页（*）
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 10, //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                strictSearch: true,
                showColumns: false, //是否显示所有的列
                showRefresh: true, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                showToggle: false, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                showExport: false, //显示到处按钮
                sortName: 'createDate',
                sortOrder: 'desc',
                columns: [
                    // 复选框
                    {
                        radio: true,
                        width:20,
                        align: 'center'
                    },
                    {
                        field: 'orderId',
                        title: '订单编号',
                        valign: 'middle',
                        width: 140,
                        visible: true,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'order_type',
                        title: '订单类型',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return listDictFormat(value, systemOrderTypeData.order);
                        }
                    },
                    {
                        field: 'api_name',
                        title: '能力名称',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'scene_name',
                        title: '场景名称',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'app_name',
                        title: '应用名称',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'pay_status',
                        title: '支付状态',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                        formatter : function(value, rec, index) {
                            if(value == 0){
                                return "未支付";
                            }else if(value == 1){
                                return "已支付";
                            }
                            return value;
                        }
                    },
                    {
                        field: 'create_name',
                        title: '创建人名称',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'create_date',
                        title: '创建日期',
                        valign: 'middle',
                        width:100,
                        align: 'center',
                        switchable: true,
                        formatter : function(value) {

                            return formatDate(value);
                        }
                    }],
                onLoadSuccess: function () { //加载成功时执行
                },
                onLoadError: function () { //加载失败时执行
                }
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sort: params.sort, // 排序规则
                order: params.order,
                rows: params.limit, //页面大小
                page: (params.offset / params.limit) + 1, //页码
                pageIndex: params.pageNumber,//请求第几页
                field:'orderId,order_type,api_name,scene_name,app_name,create_name,create_date,pay_status'
            };

            var params = $("#apiOrderForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };

    function searchList() {
        reloadTable();
    }
    function reloadTable() {
        $('#orderLogList').bootstrapTable('refresh');
    }

    function orderCharge(){
        var options = $("#orderType option:selected"); //获取选中的项
        //  alert(options.val());   //拿到选中项的值
        switch (options.val()){
            case "0":
                /*  $("#detail").html("<ul id=\"treeDemo\" class=\"ztree\"></ul>")*/
                $("#detail").html(" <label for=\"logdetail\" class=\"col-sm-5 control-label near-padding-right\">能力订购详细：</label>\n" +
                    "                                <div class=\"col-sm-7 no-padding\">\n" +
                    "    <input id=\"orgName\" class=\"selectpicker form-control\" onclick=\"showTree()\"  readonly > \n" +
                    "    <!-- 模拟select点击框 以及option的text值显示-->\n" +
                    "    <!--<i class=\"trg\"style=\"position: absolute;\"></i>-->\n" +
                    "    <!-- 模拟select右侧倒三角 -->\n" +
                    "    <input id=\"orgCode\" type=\"hidden\" name=\"orgCode\" />\n" +
                    "    <!-- 存储 模拟select的value值 -->\n" +
                    "\n" +
                    "    <!-- zTree树状图 相对定位在其下方 -->\n" +
                    "    <div class=\"ztree\"  style=\"display:none; z-index: 1;background-color: white;position: absolute;border:1px solid #4aa5ff;width:200px;\">\n" +
                    "        <ul id=\"treeDemo\"></ul>\n" +
                    "    </div>  \n" +
                    "</div>\n" );
                initTree();
                break;
            case "1":
             /*   $("#detail").html("<input type=\"text\" class=\"form-control input-sm\" id=\"sceneDetail\"/>");*/
               $("#detail").html(" <label for=\"sceneDetail\" class=\"col-sm-5 control-label near-padding-right\">场景订购详细：</label>\n" +
                   "                                <div class=\"col-sm-7 no-padding\">\n" +
                   "                                    <select class=\"js-data-example-ajax form-control\" name=\"sceneDetail\" id=\"sceneDetail\"></select>\n" +
                   "                                </div>");
                addScene();
                $("#sceneDetail").select2();
                break;
            case "2":
             /*   $("#detail").html("<input type=\"text\" class=\"form-control input-sm\" id=\"appDetail\"/>");*/
                $("#detail").html(" <label for=\"appDetail\" class=\"col-sm-5 control-label near-padding-right\">应用订购详细：</label>\n" +
                    "                                <div class=\"col-sm-7 no-padding\">\n" +
                    "                                    <select class=\"js-data-example-ajax form-control\" name=\"appDetail\" id=\"appDetail\"></select>\n" +
                    "                                </div>");


                addAppList();
                $("#appDetail").select2();
                break;
            default:

                break;

        }
    }

    function addAppList() {
        //加载应用选择下拉列表
        $.post(
            "monitor.do?invokeAppDatagrid",
            function (data) {
                var htmlStr = "<option value=\"\">全部</option>";
                for (var i = 0; i < data.length; i++) {
                    htmlStr += "<option value=\"" + data[i].id + "\">" + data[i].appName + "</option>";
                }
                $('#appDetail').empty();
                $('#appDetail').html(htmlStr)
            },
            'json'
        );
    }

    function addScene(){
        //加载场景选择下拉列表
        $.post(
            "monitor.do?invokeSceneDatagrid",
            function (data) {
                var htmlStr="<option value=\"\">全部</option>";
                for (var i=0; i<data.length; i++) {
                    htmlStr+="<option value=\""+data[i].id+"\">"+data[i].sceneName+"</option>";
                }
                $('#sceneDetail').empty();
                $('#sceneDetail').html(htmlStr)
            },
            'json'
        );
    }

    function initTree(){
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    }


    //树状图展示
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
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        //回调
        callback: {
            onClick: zTreeOnClick
        },
        view: {
            fontCss: { fontSize: "14px" }
        }
    };
    //节点点击事件
    function zTreeOnClick(event, treeId, treeNode) {
        $('#orgName').val(treeNode.name);
        $('#orgCode').val(treeNode.id)
        hideTree();
    };
    //下拉框显示 隐藏
    function showTree(){
        if($('.ztree').css('display') == 'none'){
            $('.ztree').css('display','block');
        } else{
            $('.ztree').css('display','none');
        }
        $("body").bind("mousedown", onBodyDownByActionType);
    }
    function hideTree() {
        $('.ztree').css('display','none');
        $("body").unbind("mousedown", onBodyDownByActionType);
        return false;
    }

    //区域外点击事件
    function onBodyDownByActionType(event) {
        if (event.target.id.indexOf('treeDemo') == -1){
            if(event.target.id != 'selectDevType'){
                hideTree();
            }
        }
    }
    function searchRest() {
     /*  $("#appDetail").html("<option value=\"\" selected=selected>全部</option>");
       $("#sceneDetail").html("<option value=\"\" selected=selected>全部</option>");*/
       $("#detail").val("");
       $("#detail").html("");
       $("#orderType").val("");
       $("#orderType").trigger("change");
        reloadTable();
    }

</script>
</body>
</html>
