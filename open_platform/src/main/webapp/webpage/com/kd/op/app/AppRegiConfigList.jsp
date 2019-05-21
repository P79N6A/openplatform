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
        <button id="btn_add" class="btn btn-primary btn-sm">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 录入
        </button>
        <button id="btn_edit" class="btn btn-success btn-sm">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑
        </button>
        <%--<button id="btn_look" class="btn btn-info btn-sm">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
        </button>--%>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="apiAppList"></table>
    </div>
</div>
<script type="text/javascript">


    $("#btn_add").bind("click", function () {
        var dialog = bootOpenNormal('应用录入', 'apiAppController.do?goAdd', 'wide');
        dialog.options.buttons[0].action = function () {
            var options = {
                url: "apiAppController.do?doAdd",
                type: "post",
                dataType: "json",
                beforeSubmit: function () {
                    var appType = $("#appType").val();
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

                    if (appType == null || appType == "" || appType == -1) {
                        slowNotify("应用类型不能为空", "danger");
                        return false;
                    }
                    //如果是互联互通，弹出运营商标识，必须填写运营商标识
                    if (appType == 1) {
                        var operator_id = $("#operator_id").val();
                        if (operator_id == null || operator_id == "") {
                            slowNotify("运营商标识不能为空", "danger");
                            return false;
                        }

                        // 待调试代码  5.13
                        // var workeyFour = $("#workeyFour").val();
                        // if (workeyFour == null || workeyFour == "") {
                        //     slowNotify("外部提供系统TOKEN秘钥不能为空", "danger");
                        //     return false;
                        // }
                        // var workeySix = $("#workeySix").val();
                        // if (workeySix == null || workeySix == "") {
                        //     slowNotify("外部提供系统数据加密秘钥不能为空", "danger");
                        //     return false;
                        // }
                        // var workeyEight = $("#workeyEight").val();
                        // if (workeyEight == null || workeyEight == "") {
                        //     slowNotify("外部提供系统初始化向量不能为空", "danger");
                        //     return false;
                        // }
                        // var workeyTen = $("#workeyTen").val();
                        // if (workeyTen == null || workeyTen == "") {
                        //     slowNotify("外部提供系统签名秘钥不能为空", "danger");
                        //     return false;
                        // }
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
                        console.log(data.msg);
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
            var dialog = bootOpenNormal('应用编辑', 'apiAppController.do?goUpdate&id=' + id, 'wide');
            dialog.options.buttons[0].action = function () {
                var options = {
                    url: "apiAppController.do?doUpdate",
                    type: "post",
                    dataType: "json",
                    beforeSubmit: function () {
                        var appType = $("#appType").val();
                        if (appType == 0) {
                            var passKey = $("#passKey").val();
                            var ivStr = $("#ivStr").val();
                            //用来验证只能是数字和字母的正则表达式
                            var str = /^[A-Za-z0-9]{16}/;
                            if (passKey == null || passKey == "") {
                                slowNotify("passKey不能为空", "danger");
                                return false;
                            }
                            if (ivStr == null || ivStr == "") {
                                slowNotify("ivStr不能为空", "danger");
                                return false;
                            }
                            if (passKey.length != 16 || ivStr.length != 16) {
                                slowNotify("passKey或ivStr长度只能是数字字母组成的16位长度的字符串", "danger");
                                return false;
                            }
                            if (str.test(passKey)) {
                            } else {
                                slowNotify("passKey或ivStr长度只能是数字字母组成的16位长度的字符串", "danger");
                                return false;

                            }
                        }

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


                        if (appType == null || appType == "" || appType == -1) {
                            slowNotify("应用类型不能为空", "danger");
                            return false;
                        }
                        //如果是互联互通，弹出运营商标识，必须填写运营商标识
                        if (appType == 1) {
                            var operator_id = $("#operator_id").val();
                            if (operator_id == null || operator_id == "") {
                                slowNotify("运营商标识不能为空", "danger");
                                return false;
                            }

                            // 待调试代码  5.13
                            // var workeyFour = $("#workeyFour").val();
                            // if (workeyFour == null || workeyFour == "") {
                            //     slowNotify("外部提供系统TOKEN秘钥不能为空", "danger");
                            //     return false;
                            // }
                            // var workeySix = $("#workeySix").val();
                            // if (workeySix == null || workeySix == "") {
                            //     slowNotify("外部提供系统数据加密秘钥不能为空", "danger");
                            //     return false;
                            // }
                            // var workeyEight = $("#workeyEight").val();
                            // if (workeyEight == null || workeyEight == "") {
                            //     slowNotify("外部提供系统初始化向量不能为空", "danger");
                            //     return false;
                            // }
                            // var workeyTen = $("#workeyTen").val();
                            // if (workeyTen == null || workeyTen == "") {
                            //     slowNotify("外部提供系统签名秘钥不能为空", "danger");
                            //     return false;
                            // }
                        }
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
            bootOpenLook('应用查看', 'apiAppController.do?goLook&id=' + id, 'normal');
        }
    })
    var apiAppListdictsData = {};
    $(function () {
        initDictByCode(apiAppListdictsData, "auditSts", function () {
        });

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
                showRefresh: true,
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

                    /* inner_merchantno
                     {
                         field: 'innerMerchantno',
                         title: '车联网商户编号',
                         valign: 'middle',
                         width: 120,
                         align: 'center',
                         switchable: true,
                     },*/
                    {
                        field: 'appName',
                        title: '应用名称',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    }, {
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
                        field: 'appVersion',
                        title: '应用版本',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        width: 100,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            var href = '';
                            href += "<button class='btn btn-xs btn-danger'  title='作废'  onclick='changeApp(\"" + row.id + "\")'>" +
                                "<span class='glyphicon glyphicon-trash'>作废</span></button>&nbsp";
                            /* if (row.id&&row.appType==1) {
                                 href += "<button class='btn btn-primary btn-xs'  title='查看秘钥'  onclick='lookPrimary(\"" + row.id + "\")'>" +
                                     "<span class='glyphicon glyphicon-search'>查看秘钥</span></button>&nbsp";
                             }*/
                            return href;
                        }
                    }
                ],
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
                field: 'id,appName,appId,appDesc,appVersion,auditStatus,auditMsg,appDesc,appType,innerMerchantno,operator_id'
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

    function lookPrimary(id) {
    }


    function changeApp(id) {
        BootstrapDialog.confirm({
            title: '操作提示',
            message: '确定要作废当前应用吗?',
            type: BootstrapDialog.TYPE_DANGER,
            closable: true,
            size: "size-small",
            draggable: true,
            btnCancelLabel: '取消',
            btnOKLabel: '确定', // <-- Default value is 'OK',
            btnOKClass: 'btn-danger',
            callback: function (result) {
                if (result) {
                    $.ajax({
                        type: "post",
                        dataType: "json",
                        url: "appController.do?doDel",
                        data: {id: id},
                        success: function (data) {
                            if (data.success) {
                                quickNotify(data.msg, "success");
                                reloadAppTable();
                            } else {
                                slowNotify(data.msg, "danger");
                            }
                        }
                    })
                }
            }
        });
    }


</script>
</body>
</html>