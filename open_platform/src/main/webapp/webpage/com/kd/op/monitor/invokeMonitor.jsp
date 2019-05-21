<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>日志调用列表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.css" %> type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
    <script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/datetimepicker/jquery.datetimepicker.full.js" %> type="text/javascript"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <link rel="stylesheet" href="${webRoot}/plug-in/select2/css/select2.min.css">
    <script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.min.js"></script>
    <%--<script type="text/javascript" src="<%=request.getContextPath() + "js/com/kd/op/trans/StringTransUtil.js"%>"></script>--%>
</head>
<body>
<style>
    .trg{
        width: 0;
        height: 0;
        border-left: 3px solid transparent;
        border-right: 3px solid transparent;
        border-top: 6px solid black;
        position: absolute;
        left:235px; top:35px;
    }
    .org-select{
        cursor: default;
        z-index: -1;
        width:200px;
    }
</style>
<div class="panel-body" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="invokeForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <label for="appId" style="font-size:14px;">应用：</label><br>
                                <select class="js-data-example-ajax form-control" name="appId" style="width: 100%"
                                        id="appId">
                                    <%--<option value="1"></option>--%>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <label for="sceneId" style="font-size:14px;">场景：</label><br>
                                <select class="js-data-example-ajax form-control" name="sceneId" style="width: 100%"
                                        id="sceneId">
                                    <%--<option value="1"></option>--%>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <div style="position: relative;">
                                    <label style="font-size:14px;">能力中心：</label>
                                    <%--<!-- 模拟select点击框 以及option的text值显示-->--%>
                                    <input id="orgName" class="form-control" style="height: 28px"  readonly />
                                    <%--<!-- 模拟select右侧倒三角 -->--%>
                                    <%--<i class="trg"style="position: absolute;"></i>--%>
                                    <%--<!-- 存储 模拟select的value值 -->--%>
                                    <input id="orgCode" type="hidden" name="groupId" />
                                    <%--<!-- zTree树状图 相对定位在其下方 -->--%>

                                    <div class="ztree" style="display:none;z-index: 1;background-color: white;  position: absolute;border:1px solid #4aa5ff;width:100%; overflow:auto;height: 200px">
                                        <ul id="treeDemo"></ul>
                                    </div>
                                </div>

                                <%--<label for="apiId" style="font-size:14px;">能力：</label>--%>
                                <%--<select class="js-data-example-ajax form-control" name="apiId"--%>
                                <%--id="apiId">--%>
                                <%--</select>--%>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <label style="font-size:14px;">能力名称:</label><br>
                                <select class="js-data-example-ajax form-control" style="width:100%;" name="apiId"
                                        id="apiId">
                                </select>
                                <%--<input type="text" id="appName" name="appName" class="org-select form-control" />--%>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <label for="invokeServiceproviderResult" style="font-size:14px;margin-top: 10px;">调用结果：</label>
                                <select class="selectpicker form-control" name="invokeServiceproviderResult"
                                        id="invokeServiceproviderResult">
                                    <option value="">全部</option>
                                    <option value="0">成功</option>
                                    <option value="1">失败</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <label for="invokeServiceproviderResult" style="font-size:14px;margin-top: 10px;">开始日期：</label>
                                <%--<label for="startDate" style="font-size:14px;margin-top: 25px;margin-right: -30px;" class="col-md-4 control-label near-padding-right">开始日期</label>--%>
                                <input type="text" id="startDate" name="invokeTime_begin" class="form-control" autocomplete="off"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <label for="invokeServiceproviderResult" style="font-size:14px;margin-top: 10px;">结束日期：</label>
                                <%--<label for="startDate" style="font-size:14px;margin-top: 25px;margin-right: -30px;" class="col-md-4 control-label near-padding-right">结束日期</label>--%>
                                <input type="text" id="endDate" name="invokeTime_end" class="form-control" autocomplete="off"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-3">
                                <div class="input-group col-md-12" style="margin-top: 25px;margin-left: 45px">
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
<%--<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">--%>
<%--<div id="toolbar">--%>
<%--&lt;%&ndash;<button id="btn_add" class="btn btn-primary btn-sm">&ndash;%&gt;--%>
<%--&lt;%&ndash;<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 录入&ndash;%&gt;--%>
<%--&lt;%&ndash;</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;<button id="btn_edit" class="btn btn-success btn-sm">&ndash;%&gt;--%>
<%--&lt;%&ndash;<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑&ndash;%&gt;--%>
<%--&lt;%&ndash;</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;<button id="btn_look" class="btn btn-info btn-sm">&ndash;%&gt;--%>
<%--&lt;%&ndash;<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看&ndash;%&gt;--%>
<%--&lt;%&ndash;</button>&ndash;%&gt;--%>
<a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search" onclick="funDate()"> <span
        class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
</div>
<div class="table-responsive">
    <table id="invokeLogList"></table>
</div>
</div>
<script type="text/javascript">
    $(function () {
        $('#apiId').select2();
        $('#sceneId').select2();
        $('#appId').select2();
        //树状图展示
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPid: null
                }
            },
            callback: {
                onClick: zTreeOnClick,
            }
        };
        var orgList =[];
        $.ajax({
            async: false,
            type: "post",
            dataType: "json",
            url: "apiGroupController.do?loadAll",
            success: function (data) {
                orgList = data;
            }
        })
        //节点点击事件
        function zTreeOnClick(event, treeId, treeNode) {
            $('#orgName').val(treeNode.name);
            $('#orgCode').val(treeNode.id);
            hideTree();
            // 加载能力选择下拉列表
            var groupId=$("#orgCode").val()
            $.post(
                "monitor.do?invokeApiDatagrid&groupId="+groupId,
                function (data) {
                    var htmlStr="<option value=\"\" id='apiAll'>全部</option>";
                    for (var i=0; i<data.length; i++) {
                        htmlStr+="<option value=\""+data[i].id+"\">"+data[i].apiName+"</option>";
                    }
                    $('#apiId').empty();
                    $('#apiId').html(htmlStr)
                },
                'json'
            );
        };
        $(document).ready(function () {
            //初始组织树状图
            $.fn.zTree.init($("#treeDemo"), setting, orgList);
        });
        //下拉框显示 隐藏
        $("#orgName").bind("click", function () {
            if($('.ztree').css('display') == 'none'){
                $('.ztree').css('display','block');
            } else{
                $('.ztree').css('display','none');
            }
            $("body").bind("mousedown", onBodyDownByActionType);
        })
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
    });
    function timeInit(){
        var date = new Date();
        var day = intToTwoStr(date.getDate());   //28
        var month = intToTwoStr(date.getMonth()+1);   //4  + 1
        var today = date.getFullYear()+"-"+month+"-"+day;
        $('#startDate,#endDate').datetimepicker({
            //lang:"ch",
            format:	'Y-m-d H:00:00',
            formatTime:'H:00:00',
            // showUpDown: true,
            timepicker:true,
            // value:today
        });
        $.datetimepicker.setLocale('ch');
    }

    function funDate() {
        //加载应用选择下拉列表
        $.post(
            "monitor.do?invokeAppDatagrid",
            function (data) {
                var htmlStr="<option value=\"\">全部</option>";
                for (var i=0; i<data.length; i++) {
                    htmlStr+="<option value=\""+data[i].id+"\">"+data[i].appName+"</option>";
                }
                $('#appId').empty();
                $('#appId').html(htmlStr)
            },
            'json'
        );

        //加载场景选择下拉列表
        $.post(
            "monitor.do?invokeSceneDatagrid",
            function (data) {
                var htmlStr="<option value=\"\">全部</option>";
                for (var i=0; i<data.length; i++) {
                    htmlStr+="<option value=\""+data[i].id+"\">"+data[i].sceneName+"</option>";
                }
                $('#sceneId').empty();
                $('#sceneId').html(htmlStr)
            },
            'json'
        );
        // 加载能力选择下拉列表
        $.post(
            "monitor.do?invokeApiDatagrid",
            function (data) {
                var htmlStr="<option value=\"\" id='apiAll'>全部</option>";
                for (var i=0; i<data.length; i++) {
                    htmlStr+="<option value=\""+data[i].id+"\">"+data[i].apiName+"</option>";
                }
                $('#apiId').empty();
                $('#apiId').html(htmlStr)
            },
            'json'
        );
    }
    $(function () {
        timeInit();
        var invokeLogListdictsData = {};
        $(function () {
            initDictByCode(invokeLogListdictsData, "auditSts", function () {
            });
            //1.初始化Table
            var oTable = new TableInit();
            oTable.Init();

        });
        var TableInit = function () {
            var oTableInit = new Object();
            oTableInit.Init = function () {
                //初始化Table
                $('#invokeLogList').bootstrapTable({
                    url: 'apiInvokeLogController.do?invokeDatagrid', //请求后台的URL（*）
                    method: 'post', //请求方式（*）
                    contentType: "application/x-www-form-urlencoded",
                    toolbar: '#toolbar', //工具按钮用哪个容器
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
                            field: 'id,appId,sceneId,sceneName,apiId,apiName,appName,invokeTime,invokeIp,methodType,invokeUrl,responseTimeLength,responseFlowSize,requestFlowSize,requestHeader,requestParam,returnParam,returnHeader,invokeOpenplatformResult,invokeServiceproviderResult'
                        };

                        var params = $("#invokeForm").serializeArray();
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
                    clickToSelect: true, //是否启用点击选中行
                    height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId: "id", //每一行的唯一标识，一般为主键列
                    sortName: 'invokeTime',
                    sortOrder: 'desc',
                    columns: [
                        // 单选框
                        {
                            title: '序号',
                            width: 25,
                            align: 'center',
                            switchable: false,
                            formatter: function (value, row, index) {
                                return index + 1; //序号正序排序从1开始
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
                            formatter : function(value, rec, index) {
                                return "<span title=" + value + ">" + value + "</span>"
                            }
                        },
                        {
                            field: 'appName',
                            title: '应用名称',
                            valign: 'middle',
                            width:  60,
                            align: 'center',
                            switchable: true,
                            formatter : function(value, rec, index) {
                                if(value==null || value==''){
                                    return "<span title=\"无\">无</span>"
                                }
                                return "<span title=" + value + ">" + value + "</span>"
                            }
                        },
                        {
                            field : 'sceneName',
                            title : '场景名称',
                            valign : 'middle',
                            width : 60,
                            align : 'center',
                            switchable : true,
                            formatter : function(value, rec, index) {
                                if(value==null || value==''){
                                    return "<span title=\"无\">无</span>"
                                }
                                return "<span title=" + value + ">" + value + "</span>"
                            }
                        },
                        {
                            field: 'apiName',
                            title: '能力名称',
                            valign: 'middle',
                            width: 100,
                            align: 'center',
                            switchable: true,
                            formatter : function(value, rec, index) {
                                if(value==null || value==''){
                                    return "<span title=\"无\">无</span>"
                                }
                                return "<span title=" + value + ">" + value + "</span>"
                            }
                        },
                        {
                            field: 'invokeIp',
                            title: '调用IP',
                            valign: 'middle',
                            width: 80,
                            align: 'center',
                            switchable: true,
                        },
                        {
                            field: 'invokeTime',
                            title: '调用时间',
                            valign: 'middle',
                            width: 80,
                            align: 'center',
                            switchable: true,
                            formatter: function (value, rec, index) {
                                return value.substring(0,value.length-2)
                            }
                        },

                        // {
                        //     field : 'requestFlowSize',
                        //     title : '请求流量(KB)',
                        //     valign : 'middle',
                        //     width : 50,
                        //     align : 'center',
                        //     switchable : true,
                        // },

                        // {
                        //     field: 'responseFlowSize',
                        //     title: '响应流量(KB)',
                        //     valign: 'middle',
                        //     width: 50,
                        //     align: 'center',
                        //     switchable: true,
                        // },
                        // {
                        //     field: 'responseTimeLength',
                        //     title: '响应时长(ms)',
                        //     valign: 'middle',
                        //     width: 50,
                        //     align: 'center',
                        //     switchable: true,
                        // },
                        {
                            field: 'invokeServiceproviderResult',
                            title: '能力提供者调用结果',
                            valign: 'middle',
                            width: 80,
                            align: 'center',
                            switchable: true,
                            formatter: function (value, rec, index) {
                                if (value == "1") {
                                    return "失败";
                                } else if (value == "0") {
                                    return "成功";
                                } else {
                                    return "未知异常"
                                }
                            }
                        },
                        {
                            field: 'invokeOpenplatformResult',
                            title: '开放平台调用结果',
                            valign: 'middle',
                            width: 80,
                            align: 'center',
                            switchable: true,
                            formatter: function (value, rec, index) {
                                value += "";
                                if (value == "1") {
                                    return "失败";
                                } else if (value == "0") {
                                    return "成功";
                                } else {
                                    return "未知异常"
                                }
                            }
                        },
                        {
                            title: "操作",
                            align: 'center',
                            valign: 'middle',
                            width: 30,
                            formatter: function (value, row, index) {
                                if (!row.id) {
                                    return '';
                                }
                                var href = '';
                                href += "<button class='btn btn-xs btn-primary' onclick='lookDetail(\"" + row.id + "\")'>" +
                                    "<span>查看</span></button>";
                                return href;
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
                    field: 'id,appName,appId,appDesc,appVersion,auditStatus,auditMsg'
                };

                var params = $("#invokeForm").serializeArray();
                for (x in params) {
                    temp[params[x].name] =params[x].value;
                }
                return temp;
            };
            return oTableInit;
        };
    })
    function searchList() {
        var endDate=new Date($("#endDate").val())
        var startDate=new Date($("#startDate").val())
        if (endDate-startDate<0) {
            quickNotify("结束日期不能小于开始日期", "warning");
            return;
        }
        reloadTable();
    }

    function reloadTable() {
        $('#invokeLogList').bootstrapTable('refresh');
    }
    function searchRest() {
        $('#invokeForm').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else {
                $(this).val("");
            }
        });
        $('#invokeForm').find("input[type='checkbox']").each(function () {
            $(this).attr('checked', false);
        });
        $('#invokeForm').find("input[type='radio']").each(function () {
            $(this).attr('checked', false);
        });
        $('#invokeForm').find("select").each(function () {
            $(this).selectpicker('val', '');
        });
        $("#apiId").html("<option value=\"\" selected=selected>全部</option>");
        $("#appId").html("<option value=\"\" selected=selected>全部</option>");
        $("#sceneIdId").html("<option value=\"\" selected=selected>全部</option>");
        funDate();
        reloadTable();
    }
    //查看详情
    function lookDetail(id) {
        var dialog = bootOpenLook('日志详情', 'apiInvokeLogController.do?goUpdate&id=' + id, 'wide');
    }
</script>
</body>
</html>