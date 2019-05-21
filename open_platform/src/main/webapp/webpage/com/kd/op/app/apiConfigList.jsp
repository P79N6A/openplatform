<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>服务应用表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
<div class="panel-body" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="apiAppForm" onkeydown="if(event.keyCode==13){searchAppList();return false;}">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label for="appName_search">应用名称：</label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" class="form-control input-sm" id="appName_search"
                                           name="appName"/>
                                </div>
                            </div>

                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="input-group col-md-12" style="margin-top: 20px">
                                    <a class='btn btn-success' onclick="searchAppList();"><span
                                            class="glyphicon glyphicon-search" aria-hidden="true"></span>查询</a>&nbsp
                                    <a class='btn btn-warning' onclick="searchAppRest();"><span
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
        <%--<button id="btn_add" class="btn btn-primary btn-sm">--%>
        <%--<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 录入--%>
        <%--</button>--%>
        <%--<button id="btn_edit" class="btn btn-success btn-sm">--%>
        <%--<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑--%>
        <%--</button>--%>
        <%--<button id="btn_look" class="btn btn-info btn-sm">--%>
        <%--<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看--%>
        <%--</button>--%>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="apiAppList"></table>
    </div>
</div>
<script type="text/javascript">


    $("#btn_add").bind("click", function () {
        var dialog = bootOpenNormal('应用录入', 'apiAppController.do?goAdd', 'default');
        dialog.options.buttons[0].action = function () {
            var options = {
                url: "apiAppController.do?doAdd",
                type: "post",
                dataType: "json",
                beforeSubmit: function () {
                    var appName = $("#appName").val();
                    var appVersion = $("#appVersion").val();
                    if (appName == null || appName == "") {
                        slowNotify("应用名称不能为空", "danger");
                        return false;
                    }
                    if (appVersion == null || appVersion == "") {
                        slowNotify("版本号不能为空", "danger");
                        return false;
                    }
                    if (appVersion.lenth > 8) {
                        slowNotify("版本号长度不能超过8位", "danger");
                        return false;
                    }
                    // for(var i = 0;i < appVersion.length;i++){
                    //     var a = appVersion.charAt(i);
                    //     var regPos = / ^\d+$/; // 非负整数
                    //     if(!regPos.test(a) || a != "."){
                    //         slowNotify("版本号只能包含数字和英文句号.","danger");
                    //         return false;
                    //     }
                    // }
                },
                success: function (data) {
                    if (data.success) {
                        reloadAppTable();
                        dialog.close();
                        quickNotify(data.msg, "success");
                    } else {
                        slowNotify(data.msg, "danger");
                    }
                },
            }
            $("#addForm").ajaxSubmit(options);
        }
    })
    $("#btn_edit").bind("click", function () {
        var selects = $("#apiAppList").bootstrapTable("getSelections");
        if (selects == null || selects.length == 0) {
            quickNotify("请先选中一行", "warning");
        } else if (selects.length > 1) {
            quickNotify("只能选择一行", "warning");
        } else {
            var id = selects[0].id;
            var dialog = bootOpenNormal('应用编辑', 'apiAppController.do?goUpdate&id=' + id, 'normal');
            dialog.options.buttons[0].action = function () {
                var options = {
                    url: "apiAppController.do?doUpdate",
                    type: "post",
                    dataType: "json",
                    beforeSubmit: function () {
                        return true;
                    },
                    success: function (data) {
                        if (data.success) {
                            reloadAppTable();
                            dialog.close();
                            quickNotify(data.msg, "success");
                        } else {
                            slowNotify(data.msg, "danger");
                        }
                    },
                }
                $("#addForm").ajaxSubmit(options);
            }
        }
    })
    $("#btn_look").bind("click", function () {
        var selects = $("#apiAppList").bootstrapTable("getSelections");
        if (selects == null || selects.length == 0) {
            quickNotify("请先选中一行", "warning");
        } else if (selects.length > 1) {
            quickNotify("只能选择一行", "warning");
        } else {
            var id = selects[0].id;
            var dialog = bootOpenLook('应用查看', 'apiAppController.do?goLook&id=' + id, 'normal');
            dialog.options.buttons[0].action = function () {
                var options = {
                    url: "apiAppController.do?doLook",
                    type: "post",
                    dataType: "json",
                    beforeSubmit: function () {
                        return true;
                    },
                    success: function (data) {
                        if (data.success) {
                            reloadAppTable();
                            dialog.close();
                            quickNotify(data.msg, "success");
                        } else {
                            slowNotify(data.msg, "danger");
                        }
                    },
                }
                $("#lookForm").ajaxSubmit(options);
            }
        }
    })
    var apiAppListdictsData = {};
    $(function () {
        // var promiseArr = [];
        // promiseArr.push(new Promise(function (resolve, reject) {
        initDictByCode(apiAppListdictsData, "auditSts", function () {
        });
        // }));

        // Promise.all(promiseArr).then(function (results) {
        //
        // }).catch(function (err) {
        // });

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#apiAppList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function () {
                $('#btn_delete').prop('disabled', !$('#apiAppList').bootstrapTable('getSelections').length);
                $('#btn_edit').prop('disabled', $('#apiAppList').bootstrapTable('getSelections').length != 1);
                $('#btn_look').prop('disabled', $('#apiAppList').bootstrapTable('getSelections').length != 1);
            });

    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#apiAppList').bootstrapTable({
                url: 'apiAppController.do?datagrid&type=isv', //请求后台的URL（*）
                method: 'post', //请求方式（*）
                contentType: "application/x-www-form-urlencoded",
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
                clickToSelect: true, //是否启用点击选中行
                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                sortName: 'createDate',
                sortOrder: 'desc',
                columns: [
                    // 单选框
                    {
                        radio: true,
                        width: 35,
                        align: 'center'
                    },
                    {
                        field: 'id',
                        title: '应用id',
                        valign: 'middle',
                        width: 170,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'appType',
                        title: "应用类型",
                        align: 'center',
                        valign: 'middle',
                        width: 60,
                        switchable: true,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            if (value == 0) {
                                return "普通应用";
                            } else if (value == 1) {
                                return "互联互通";
                            }
                        }
                    },
                    {
                        field: 'appName',
                        title: '应用名称',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'appDesc',
                        title: '应用描述',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return "<span title=" + value + ">" + value + "</span>"
                        }
                    },
                    {
                        field: 'createDate',
                        title: '创建日期',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return formatDate(value);
                        }
                    },
                    {
                        field: 'appVersion',
                        title: '应用版本',
                        valign: 'middle',
                        width: 100,
                        align: 'center',
                        switchable: true,
                    },
//                    {
//                        field: 'auditStatus',
//                        title: '审核状态',
//                        valign: 'middle',
//                        width: 120,
//                        align: 'center',
//                        switchable: true,
//                        formatter: function (value, rec, index) {
//                            return listDictFormat(value, apiAppListdictsData.auditSts);
//                        }
//                    },
                    {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        width: 120,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            //
                          /*  var innerappType;
                            if(row.appType==""||row.appType==undefined||row.appType==null||row.appType==0){
                                innerappType==0;
                            }else{
                                innerappType==1;
                            }*/
                            var href = '';
                            href += "<button class='btn btn-xs btn-success' onclick='orderApi(\"" + row.id + "\",\"" +  row.appType+ "\")'>" +
                                "<span>订购能力</span></button>&nbsp";
                            href += "<button class='btn btn-xs btn-primary' onclick='orderScene(\"" + row.id + "\")'>" +
                                "<span>订购场景</span></button>";
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
                field: 'id,appName,appId,createDate,appDesc,appVersion,auditStatus,auditMsg,appType'
            };

            var params = $("#apiAppForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };

    function searchAppList() {
        reloadAppTable();
    }

    function reloadAppTable() {
        $('#apiAppList').bootstrapTable('refresh');
    }

    function searchAppRest() {
        $('#apiAppForm').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else {
                $(this).val("");
            }
        });
        $('#apiAppForm').find("input[type='checkbox']").each(function () {
            $(this).attr('checked', false);
        });
        $('#apiAppForm').find("input[type='radio']").each(function () {
            $(this).attr('checked', false);
        });
        reloadAppTable();
    }


    //订购服务
    function orderApi(id,appType) {
        var dialog = bootOpenNormal('订购能力', 'order.do?appApiSelect&appId=' + id+'&appType='+appType, 'wide');
        dialog.options.buttons[0].action = function (dialog) {
            var array = [];
            //用来校验计费模型
            var chargeModeId = [];
            var apiNames = "";
            //用来校验URL配置
            var url = "";
            var apiNames2 = "";
            //用来校验资源控制
            var apiResource = [];
            var apiNames3 = "";
            //用来校验tag
            var tag = [];
            var apiNames4 = "";

            //用来校验webService
            var webServiceInfo = [];
            var apiNames5 = "";
            apiTemps.forEach(function (item, key, mapObj) {
                if (key != null && key != '') {
                    item.chargeModeId = $("#" + item.apiId).val();
                    // item.resourceIds = apiResourceMap.get(item.apiId);
                    if (item.chargeModeId == null) {
                        chargeModeId = null;
                        apiNames += item.apiName + ",";
                    }
                   /* //如果为主动推送能力，需要校验是否配置了url
                    if (item.apiInvokeType == 2) {
                        url = urlMap.get(item.apiId);
                        if (url == null || url == undefined || url == "") {
                            url = null;
                            apiNames2 += item.apiName + ",";
                        } else {
                            item.url = url;
                        }
                    }*/
                    if (item.resourceCtrl == 1) {
                        apiResource = apiResourceMap.get(item.apiId);
                        if (apiResource == null || apiResource == undefined || apiResource == "") {
                            apiResource = null
                            apiNames3 += item.apiName + ",";
                        } else {
                            item.apiResource = apiResource;
                        }
                    }
                    if (item.pubMode != 1 || item.apiInvokeType != 1) {
                        // tag = tagMap.get(item.apiId);
                        // if (tag == null || tag == undefined || tag == "") {
                        //     tag = null
                        //     apiNames4 += item.apiName + ",";
                        // } else {
                        //     item.tag = tag;
                        // }

                        webServiceInfo = webServiceInfoMap.get(item.apiId);
                        if (webServiceInfo == null || webServiceInfo == undefined || webServiceInfo == "") {
                            webServiceInfo = null
                            apiNames5 += item.apiName + ",";
                        } else {
                            item.webServiceInfo = webServiceInfo;
                        }
                    }
                array.push(item);
            }
        }
    );
    //至少选择一个服务
    if (array.length <= 0) {
        slowNotify("请至少订购一个能力", "danger");
    } else if (chargeModeId == null) {
        apiNames = apiNames.substring(0, apiNames.length - 1);
        slowNotify("[" + apiNames + "]尚未选择计费方式", "danger");
    } else if (url == null) {
        apiNames2 = apiNames2.substring(0, apiNames2.length - 1);
        slowNotify("[" + apiNames2 + "]尚未配置URL", "danger");
    } else if (apiResource == null) {
        slowNotify("[" + apiNames3 + "]尚未配置资源控制", "danger");
    }  else if (webServiceInfo == null) {
        slowNotify("[" + apiNames5 + "]尚未配置接收信息", "danger");
    }
    // else if (tag == null) {
    //     slowNotify("[" + apiNames4 + "]尚未配置tag", "danger");
    // }
    else {
        $.ajax({
            type: "post",
            dataType: "json",
            data: {
                appId: id,
                orderType: 'api',
                apiData: JSON.stringify(array)
            },
            url: "order.do?saveOrder",
            success: function (data) {
                if (data.success) {
                    dialog.close()
                    quickNotify(data.msg, "success");
                } else {
                    slowNotify(data.msg, "danger");
                }
            }
        })
    }
    }
    }

    //订购场景
    function orderScene(id) {
        var dialog = bootOpenNormal('订购场景', 'order.do?appSceneSelect&appId=' + id, 'wide');
        dialog.options.buttons[0].action = function (dialog) {
            var array = [];
            var chargeModeId = [];
            var sceneNames = "";
            secensTemps.forEach(function (item, key, mapObj) {
                if (key != null && key != '') {
                    item.chargeModeId = $("#" + item.sceneId).val();
                    item.resourceIds = apiResourceMap.get(item.apiId);
                    if (item.chargeModeId == null) {
                        chargeModeId = null;
                        sceneNames += item.sceneName + ",";
                    }
                    array.push(item);
                }
            });
            //至少选择一个场景
            if (array.length <= 0) {
                slowNotify("请订购一个场景", "danger");
            } else if (chargeModeId == null) {
                sceneNames = sceneNames.substring(0, sceneNames.length - 1);
                slowNotify("[" + sceneNames + "]尚未选择计费方式", "danger");
            } else {
                $.ajax({
                    type: "post",
                    dataType: "json",
                    data: {
                        appId: id,
                        orderType: 'scene',
                        apiData: JSON.stringify(array)
                    },
                    url: "order.do?saveOrder",
                    success: function (data) {
                        if (data.success) {
                            dialog.close()
                            quickNotify(data.msg, "success");
                        } else {
                            slowNotify(data.msg, "danger");
                        }
                    }
                })
            }
        }
    }
</script>
</body>
</html>