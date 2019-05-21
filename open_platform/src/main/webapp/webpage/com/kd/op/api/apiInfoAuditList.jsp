<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>能力信息表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <%--<link rel="stylesheet" href="${webRoot}/plug-in/lobipanel/lobipanel.css">--%>
    <link rel="stylesheet" href="${webRoot}/plug-in/bootstrap/css/bootstrap-dialog.css">
    <script src="${webRoot}/plug-in/bootstrap/js/bootstrap-dialog.js" type="text/javascript"></script>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
<div class="col-sm-2">
    <ul id="groups" class="ztree"></ul>
</div>
<div class="panel-body col-sm-10" style="padding-bottom: 0px;">
    <div class="accordion-group">
        <div id="collapse_search" class="accordion-body collapse">
            <div class="accordion-inner">
                <div class="panel panel-default" style="margin-bottom: 0px;">
                    <div class="panel-body">
                        <form id="apiInfoForm" onkeydown="if(event.keyCode==13){reloadTable();return false;}">
                            <div class="col-xs-6 col-sm-3 col-md-3">
                                <label for="apiName">能力名称：</label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" class="form-control input-sm" id="apiName" name="apiName"/>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-3 col-md-3">
                                <label for="apiAuditStatus">审核状态：</label>
                                <select class="selectpicker form-control" name="apiAuditStatus" id="apiAuditStatus">
                                    <option value="1">待审核</option>
                                    <option value="2">审核通过</option>
                                    <option value="3">审核未通过</option>
                                </select>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="input-group col-md-12" style="margin-top: 20px">
                                    <a type="button" onclick="searchList();" class="btn btn-success">
                                        <span class="glyphicon glyphicon-search"></span>查询
                                    </a>&nbsp
                                    <a type="button" onclick="searchRest();" class="btn btn-warning">
                                    <span class="glyphicon glyphicon-repeat"></span>重置
                                    </a>
                                </div>
                            </div>
                            <input name="groupId" id="groupId" type="hidden"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="padding-top: 0px; padding-bottom: 0px;">
        <div id="toolbar">
            <a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search"
               id="btn_collapse_search"> <span
                    class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索 </a>
        </div>
        <div class="table-responsive">
            <table id="apiInfoList"></table>
        </div>
    </div>
</div>
<script type="text/javascript">
    var apiInfoListdictsData = {};
    $(function () {
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
                zNodes = data;
            }
        });

        $.fn.zTree.init($("#groups"), setting, zNodes);

        function onClick(event, treeId, treeNode, clickFlag) {
            if (treeNode.id == 0) {
                $("#groupId").val(null);
            } else {
                $("#groupId").val(treeNode.id);
            }

            reloadTable();
        }

        //构建树  结束

        //处理右侧能力信息表格
        initDictByCode(apiInfoListdictsData, "auditSts", function () {
        });
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#apiInfoList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function () {
            });

    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#apiInfoList').bootstrapTable({
                url: 'apiInfoController.do?datagrid', //请求后台的URL（*）
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
                    {
                        title: '序号',
                        width: 25,
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
                        field: 'apiName',
                        title: '能力名称',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
                    },{
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
                    {
                        field: 'apiPublishStatus',
                        title: '发布状态',
                        valign: 'middle',
                        width: 50,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            if (value == 0) {
                                return "未发布";
                            } else if (value == 1) {
                                return "已发布";
                            } else if (value == -1) {
                                return "作废";
                            }
                        }
                    },
                    {
                        field: 'apiAuditStatus',
                        title: '审核状态',
                        valign: 'middle',
                        width: 60,
                        align: 'center',
                        switchable: true,
                        formatter: function (value, rec, index) {
                            return listDictFormat(value, apiInfoListdictsData.auditSts);
                        }
                    },
                    {
                        field: 'groupName',
                        title: '能力中心名称',
                        valign: 'middle',
                        width: 120,
                        align: 'center',
                        switchable: true,
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
                        title: "操作",
                        align: 'left',
                        valign: 'middle',
                        halign: "center",
                        width: 80,
                        formatter: function (value, row, index) {
                            if (!row.id) {
                                return '';
                            }
                            var href = '';
                            href += "<a href='javascript:void(0);' style='background-color:#63B8FF;border-color:#63B8FF;margin-left:30px;' class='ace_button'  onclick=goApiInfoDetail('" + row.id + "')> " +
                                "<i class='glyphicon' aria-hidden='true'></i>查看</a>&nbsp;";
                            if (row.apiAuditStatus == 1) {
                                href += "<a href='javascript:void(0);' style='background-color:#00CAAB;border-color:#00CAAB' class='ace_button'  onclick=goApiInfoAudit('" + row.id + "')> " +
                                    "<i class='glyphicon' aria-hidden='true'></i>审核</a>&nbsp;";
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
                field: 'id,apiName,apiDesc,apiStatus,groupId,groupName,apiPublishStatus,apiAuditStatus,createName,apiInvokeType'
            };

            var params = $("#apiInfoForm").serializeArray();
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
        $('#apiInfoList').bootstrapTable('refresh');
    }

    function searchRest() {
        $('#apiInfoForm').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else {
                $(this).val("");
            }
        });
        $('#apiInfoForm').find("input[type='checkbox']").each(function () {
            $(this).attr('checked', false);
        });
        $('#apiInfoForm').find("input[type='radio']").each(function () {
            $(this).attr('checked', false);
        });
        $('#apiInfoForm').find("select").each(function () {
            $(this).selectpicker('val', '1');
        });
        reloadTable();
    }

    function goApiInfoDetail(id) {
        bootOpenLook('能力查看', 'apiInfoController.do?goUpdate&type=4&id=' + id, 'wide')
    }
    function goApiInfoAudit(id) {
        bootOpenNormal("能力审核", "apiInfoController.do?goApiInfoAudit&id=" + id, "normal");
    }
</script>

</body>
</html>