<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>能力信息表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
</head>
<body>
<div class="panel-body col-sm-12" style="padding-top: 0px; padding-bottom: 0px;">
    <div class="panel-body" style="padding-bottom: 0px;">
        <div class="accordion-group">
            <div id="collapse_search" class="accordion-body collapse">
                <div class="accordion-inner">
                    <div class="panel panel-default" style="margin-bottom: 0px;">
                        <div class="panel-body">
                            <form id="apiParamModelForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
                                <div class="col-xs-6 col-sm-3 col-md-4">
                                    <label for="modelName">模型名称：</label>
                                    <div class="input-group" style="width: 100%">
                                        <input type="text" class="form-control input-sm" id="modelName"
                                               name="modelName"/>
                                    </div>
                                </div>
                                <div class="col-xs-6 col-sm-3 col-md-4">
                                    <div class="input-group col-md-12" style="margin-top: 20px;margin-left: 45px">
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

    <div id="toolbar">
        <button id="btn_add" class="btn btn-primary btn-sm">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 录入
        </button>
        <button id="btn_edit" class="btn btn-success btn-sm">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑
        </button>
        <button id="btn_look" class="btn btn-info btn-sm">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查看
        </button>
        <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search"> <span
                class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
    </div>
    <div class="table-responsive">
        <table id="apiParamModelList"></table>
    </div>
</div>
<script type="text/javascript">

    var apiInfoListdictsData = {};
    $(function () {
        initDictByCode(apiInfoListdictsData, "apiSts", function () {
        });
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#apiModelParamList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function () {
                $('#btn_look').prop('disabled', !$('#apiInfoList').bootstrapTable('getSelections').length != 1);
                $('#btn_edit').prop('disabled', $('#apiInfoList').bootstrapTable('getSelections').length != 1);
            });
    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#apiParamModelList').bootstrapTable({
                url: 'apiParamModelController.do?datagrid', //请求后台的URL（*）
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
                showColumns: false, //是否显示所有的列
                showRefresh: true, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
                height: $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                showToggle: false, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                showExport: true, //显示到处按钮
                sortName: 'createDate',
                sortOrder: 'desc',
                columns: [
                    // 单选框
                    {
                        radio: true,
                        width: 20,
                        align: 'center'
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
                        field: 'modelName',
                        title: '模型名称',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return "<span title=" + value + ">" + value + "</span>"
                        }
                    },
                    {
                        field: 'modelDesc',
                        title: '模型描述',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        visible: true,
                        switchable: true,
                    },
                    {
                        field: 'modelStatus',
                        title: '模型状态',
                        valign: 'middle',
                        width: 50,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            if (value == -1) {
                                return '已作废';
                            }
                            return listDictFormat(value, apiInfoListdictsData.apiSts) == value ?
                                '未知' : listDictFormat(value, apiInfoListdictsData.apiSts);
                        }
                    },
                    {
                        field: 'createName',
                        title: '创建人',
                        valign: 'middle',
                        width: 70,
                        align: 'center',
                        switchable: true,
                    },
                    {
                        field: 'createDate',
                        title: '创建时间',
                        valign: 'middle',
                        width: 70,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, row, index) {
                            if (value != '') {
                                return value.substring(0, value.length - 1);
                            }
                        }
                    },
                    {
                        title: "操作",
                        align: 'center',
                        valign: 'middle',
                        width: 50,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            var href = '';
                            if (row.modelStatus != -1) {
                                href += "<button class='btn btn-xs btn-danger' style='margin-left:5px' title='作废' " +
                                    "onclick='deleteParamModel(\"" + row.id + "\")'>" +
                                    "<span class='glyphicon glyphicon-trash'></span>作废</button>&nbsp";
                            }
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
                field: 'id,modelName,modelDesc,modelStatus,modelType,createName,createDate'
            };

            var params = $("#apiParamModelForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };

    function deleteParamModel(id) {
        BootstrapDialog.confirm({
            title: '操作提示',
            message: '确定要删除当前模型吗?',
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
                        url: "apiParamModelController.do?doDel",
                        data: {id: id},
                        success: function (data) {
                            if (data.success) {
                                quickNotify(data.msg, "success");
                                reloadTable();
                            } else {
                                slowNotify(data.msg, "danger");
                            }
                        }
                    })
                }
            }
        });
    }

    function searchList() {
        reloadTable();
    }

    function reloadTable() {
        $('#apiParamModelList').bootstrapTable('refresh');
    }

    function searchRest() {

        $('#apiParamModelForm').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else {
                $(this).val("");
            }
        });
        reloadTable();
    }

</script>

<script type="text/javascript">
    $("#btn_edit").bind("click", function () {
        var selects = $("#apiParamModelList").bootstrapTable("getSelections");
        if (selects == null || selects.length == 0) {
            quickNotify("请先选中一行", "warning");
        } else if (selects.length > 1) {
            quickNotify("只能选择一行", "warning");
        } else {
            var id = selects[0].id;
            bootOpenNormal('参数模型编辑', 'apiParamModelController.do?goUpdate&type=1&id=' + id, 'wide');
        }
    })
    $("#btn_look").bind("click", function () {
        var selects = $("#apiParamModelList").bootstrapTable("getSelections");
        if (selects == null || selects.length == 0) {
            quickNotify("请先选中一行", "warning");
        } else if (selects.length > 1) {
            quickNotify("只能选择一行", "warning");
        } else {
            var id = selects[0].id;
            bootOpenLook('参数模型查看', 'apiParamModelController.do?goUpdate&type=2&id=' + id, 'wide')
        }
    })
    $("#btn_add").bind("click", function () {
        bootOpenNormal('参数模型录入', 'apiParamModelController.do?goAdd', 'wide');
    })
</script>
</body>
</html>