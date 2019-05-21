<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>资源管理——数据资源访问</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <style type="text/css">
        tr:nth-child(even) {
            background: #f9f9f9;
        }
        td,
        th {
            text-align: center;
        }
    </style>
</head>

<body>

    <div class="container1">
        <div class="container1">
            <%--<div class="local cleafix" style="padding-top:5%">--%>
                <%--<a>资源管理</a>--%>
                    <%--<em>&gt;</em>--%>
                    <%--<a href="#">数据资源访问管理</a>--%>
            <%--</div>--%>
            <br>
            <div class="row" style="padding: 0% 0%">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <button id="btn_add" type="button" class="btn btn-primary" style="background-color:#00caab; border: none">
                        <span class="glyphicon glyphicon-plus Btn"></span>&nbsp;录 入</button>
                    <button id="btn_edit" type="button" class="btn btn-primary" style="background-color:#6ed1f1; border: none">
                        <span class="glyphicon glyphicon-pencil Btn"></span>&nbsp;编 辑</button>
                </div>
            </div>
        </div>
        <div class="container1">
            <table id="resourceInfoList"></table>
        </div>
    </div>
</body>
<%--<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>--%>
<%--<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
<script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<%--<script src="${webRoot}/plug-in/tools/curdtoolsnew.js"></script>--%>
    <script type="text/javascript">

        $(".laydate-datetime").each(function(){
            var _this = this;
            laydate.render({
                elem: this,
                format: 'yyyy-MM-dd HH:mm:ss',
                type: 'datetime'
            });
        });
        $(".laydate-date").each(function(){
            var _this = this;
            laydate.render({
                elem: this
            });
        });
        var apiInfoListdictsData = {};
        $(function() {

            //1.初始化Table
            var oTable = new TableInit();
            oTable.Init();

            //判断是否选中表格中的数据，选中后可编辑或删除
            $('#resourceInfoList').on(
                'check.bs.table uncheck.bs.table load-success.bs.table '
                + 'check-all.bs.table uncheck-all.bs.table',
                function() {
                    $('#btn_delete').prop('disabled',!$('#resourceInfoList').bootstrapTable('getSelections').length);
                    $('#btn_edit').prop('disabled',$('#resourceInfoList').bootstrapTable('getSelections').length != 1);
                });

            $("#btn_edit").bind("click",function(){
                var selects = $("#resourceInfoList").bootstrapTable("getSelections");
                if(selects == null || selects.length == 0){
                    quickNotify("请先选中一行","warning");
                }else if(selects.length > 1){
                    quickNotify("只能选择一行","warning");
                }else{
                    var id = selects[0].id;
                    bootOpenNormal('资源编辑','resourceController.do?dataResourcegoEdit&id=' + id + '','normal');
                }
            })
        });

        var TableInit = function() {
            var oTableInit = new Object();
            //初始化Table
            oTableInit.Init = function() {
                $('#resourceInfoList').bootstrapTable({
                    url : 'resourceController.do?dataResourcedatagrid', //请求后台的URL（*）
                    method : 'post', //请求方式（*）
                    contentType : "application/x-www-form-urlencoded",
                    toolbar : '#toolbar', //工具按钮用哪个容器
                    striped : true, //是否显示行间隔色
                    cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination : true, //是否显示分页（*）
                    queryParams : oTableInit.queryParams,//传递参数（*）
                    sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber : 1, //初始化加载第一页，默认第一页
                    pageSize : 10, //每页的记录行数（*）
                    pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
                    //strictSearch : true,
                    //showColumns : false, //是否显示所有的列
                    //showRefresh : false, //是否显示刷新按钮
                    //minimumCountColumns : 2, //最少允许的列数
                    clickToSelect : true, //是否启用点击选中行
                    //height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId : "id", //每一行的唯一标识，一般为主键列
                    //showToggle : false, //是否显示详细视图和列表视图的切换按钮
                    //cardView : false, //是否显示详细视图
                    //detailView : false, //是否显示父子表
                    //showExport : true, //显示到处按钮
                    sortName:'id',
                    sortOrder:'desc',
                    columns : [
                        // 单选框
                        {
                            radio : true,
                            width:15,
                            align : 'center'
                        },
                        {
                            field : 'id',
                            title : 'id',
                            valign : 'middle',
                            width : 120,
                            visible:false,
                            align : 'center',
                            switchable : true,
                        },
                        {
                            field : 'resourceName',
                            title : '组织机构',
                            valign : 'middle',
                            width : 110,
                            align : 'center',
                            switchable : true,
                            formatter : function(value, rec, index) {
                                return "<span title=" + value + ">" + value + "</span>"
                            }
                        },
                        {
                            field : 'resourcePlace',
                            title : '地点',
                            valign : 'middle',
                            width : 40,
                            align : 'center',
                            switchable : true,
                            formatter : function(value, rec, index) {
                                return "<span title=" + value + ">" + value + "</span>"
                            }
                        },
                        {
                            field : 'resourceSort',
                            title : '类别',
                            valign : 'middle',
                            width : 50,
                            align : 'center',
                            switchable : true,
                            formatter : function(value, rec, index) {
                                return listDictFormat(value,apiInfoListdictsData.apiSts);
                            }
                        },
                        {
                            field : 'resourceStatus',
                            title : '状态',
                            valign : 'middle',
                            width : 40,
                            align : 'center',
                            switchable : true,
                            formatter : function(value, rec, index) {
                                return "<span title=" + value + ">" + value + "</span>"
                            }
                        },
                        {
                            field : 'apiSort',
                            title : '资源访问权限',
                            valign : 'middle',
                            visible:false,
                            width : 150,
                            align : 'center',
                            switchable : true,
                            formatter : function(value, rec, index) {
                                return listDictFormat(value,apiInfoListdictsData.apiSts);
                            }
                        },
                        {
                            title : "操作",
                            align : 'center',
                            valign : 'middle',
                            width : 80,
                            formatter : function(value,row, index) {
                                if (!row.id) {
                                    return '';
                                }
                                var href = '';
                                href += "<button class='btn btn-xs btn-danger' style='margin-right:5px' title='删除' onclick='deleteResourceInfo(\"" + row.id + "\")'>" +
                                    "<span class='glyphicon glyphicon-trash'></span>删除</button>&nbsp";
                                if(row.apiPublishStatus == 1){//已发布的服务才能进行可见操作
                                    //渲染可用状态按钮
                                    if(row.apiStatus == 1){
                                        href += "<input type='checkbox' data-id='" + row.id + "' checked name='use'/>"
                                    }else{
                                        href += "<input type='checkbox' data-id='" + row.id + "' name='use'/>"
                                    }
                                }
                                return href;
                            }
                        } ],
                    onLoadSuccess : function() { //加载成功时执行
                    },
                    onLoadError : function() { //加载失败时执行
                    }
                });
            };

            //得到查询的参数
            oTableInit.queryParams = function(params) {
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    pageSize : params.limit, // 每页要显示的数据条数
                    offset : params.offset, // 每页显示数据的开始行号
                    sort : params.sort, // 排序规则
                    order : params.order,
                    rows : params.limit, //页面大小
                    page : (params.offset / params.limit) + 1, //页码
                    pageIndex : params.pageNumber,//请求第几页
                    field : 'id,resourceName,resourcePlace,resourceSort,apiSort,resourceStatus'
                };

                return temp;
            };
            return oTableInit;

        }

        $("#btn_add").bind("click",function(){
            //quickNotify("请在左侧选择分组","warning");
            bootOpenNormal('资源录入','resourceController.do?dataResourcegoAdd','wide');
            //location.href = "resourceController.do?goAdd";
        })

        function deleteResourceInfo(id){
            BootstrapDialog.confirm({
                title: '删除提示',
                message: '是否确定删除?',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                size:"size-small",
                draggable: true,
                btnCancelLabel: '取消',
                btnOKLabel: '删除', // <-- Default value is 'OK',
                btnOKClass: 'btn-danger',
                callback: function (result) {
                    if(result) {
                        $.ajax({
                            type:"post",
                            dataType:"json",
                            url:"resourceController.do?dataResourcedoDel",
                            data:{id:id},
                            success:function(data){
                                if(data.success){
                                    quickNotify(data.msg,"success");
                                    reloadTable();
                                }else{
                                    slowNotify(data.msg,"danger");
                                }
                            }
                        })
                    }
                }
            });
        }

        function reloadTable() {
            location.href = "resourceController.do?dataResourceList";
        }
    </script>


</html>