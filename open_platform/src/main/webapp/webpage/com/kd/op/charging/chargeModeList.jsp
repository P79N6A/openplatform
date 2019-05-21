<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>预付费</title>
	<t:base type="bootstrap,bootstrap-table,layer"></t:base>
</head>
<body>
<div class="panel-body" style="padding-top: 0px; padding-bottom: 0px;">
	<form id="chargeModeForm">
		<input id="type" name="type" type="hidden" value="${type}" />
	</form>
	<div id="toolbar">
		<button id="btn-add" class="btn btn-primary btn-sm" onclick="bootOpenNormal('收费方式录入','charging.do?goAdd&type=${type}','normal')">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>录入
		</button>
        <button id="btn-edit" class="btn btn-success btn-sm">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
        </button>
		<%--<a class="btn btn-default btn-sm" data-toggle="collapse" href="#collapse_search" id="btn_collapse_search">--%>
			<%--<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 检索--%>
		<%--</a>--%>
	</div>
	<div class="table-responsive">
		<table id="chargeModeList"></table>
	</div>
</div>
<script type="text/javascript">
    $(function() {
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //判断是否选中表格中的数据，选中后可编辑或删除
        $('#chargeModeList').on(
            'check.bs.table uncheck.bs.table load-success.bs.table '
            + 'check-all.bs.table uncheck-all.bs.table',
            function() {
                // $('#btn-audit').prop('disabled',$('#apiInfoList').bootstrapTable('getSelections').length != 1);
            });

        $("#btn-edit").bind("click",function(){
            var selects = $("#chargeModeList").bootstrapTable("getSelections");
            if(selects == null || selects.length == 0){
                quickNotify("请先选中一行","warning");
            }else if(selects.length > 1){
                quickNotify("只能选择一行","warning");
            }else{
                var id = selects[0].id;
                $.ajax({
                    type:"post",
                    dataType:"json",
                    data:{
                        id:id
                    },
                    url:"charging.do?isUsed",
                    success:function(data){
                        if(data.isUsed == false){
                            bootOpenNormal('收费方式编辑','charging.do?goEdit&type=${type}&id=' + id + '','normal');
                        }else{
                            slowNotify("当前计费方式已被使用，不允许编辑")
                        }
                    }
                })
            }
		})

    });

    var TableInit = function() {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function() {
            $('#chargeModeList').bootstrapTable({
                url : 'charging.do?datagrid', //请求后台的URL（*）
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
                clickToSelect : true, //是否启用点击选中行
                height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", //每一行的唯一标识，一般为主键列
                showColumns : false, //是否显示所有的列
                sortName:'price',
                sortOrder:'asc',
                columns : [
                    // 单选框
                    {
                        radio : true,
                        width:'5%',
                        align : 'center'
                    },{
                        title : '序号',
                        width : '5%',
                        align : 'center',
                        switchable : false,
                        formatter : function(value,row, index) {
                            //return index+1; //序号正序排序从1开始
                            var pageSize = 10
                            var pageNumber = 1;
                            return pageSize* (pageNumber - 1) + index + 1;
                        }
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
                        field : 'num',
                        title : '时间',
                        valign : 'middle',
                        width : '10%',
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'startNum',
                        title : '起始值',
                        valign : 'middle',
                        width : '10%',
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'endNum',
                        title : '截止值',
                        valign : 'middle',
                        width : '10%',
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'unit',
                        title : '单位',
                        valign : 'middle',
                        width : '15%',
                        align : 'center',
                        switchable : true,
                        formatter : function(value,row, index) {
                            if(value == 1){
                                return "周";
							}else if(value == 2){
                                return "月";
                            }else if(value == 3){
                                return "年";
                            }else if(value == 4){
                                return "KB";
                            }else if(value == 5){
                                return "次";
                            }else {
                                return value
							}
                        }
                    },
                    {
                        field : 'price',
                        title : '价格(元)',
                        valign : 'middle',
                        width : '20%',
                        align : 'center',
                        switchable : true,
                    },
                    {
                        title : "操作",
                        align : 'center',
                        valign : 'middle',
                        width : "45%",
                        formatter : function(value,row, index) {
                            if (!row.id) {
                                return '';
                            }
                            var href = '';
                            // href += "<a href='javascript:void(0);'  class='ace_button'  onclick=deleteModel('"
                            //     + row.id+"')>  <i class='glyphicon glyphicon-trash' aria-hidden='true'></i> ";
                            // href += "删除</a>&nbsp;";
							href += "<button class='btn btn-xs btn-danger' style='margin-right:5px' title='删除' onclick='deleteChargeMode(\"" + row.id + "\")'>" +
								"<span class='glyphicon glyphicon-trash'></span>删除</button>";
                            return href;
                        }
                    } ],
                onLoadSuccess : function(data) { //加载成功时执行
                },
                onLoadError : function() { //加载失败时执行
                }
            });
            //处理需要隐藏的列
			var type = $("#type").val();
			if(type == 1){
                $('#chargeModeList').bootstrapTable("hideColumn","startNum");
                $('#chargeModeList').bootstrapTable("hideColumn","endNum");
            }else if(type == 2){
                $('#chargeModeList').bootstrapTable("hideColumn","num");
            }else if(type == 3){
                $('#chargeModeList').bootstrapTable("hideColumn","num");
            }
            // $('#postpaidList').bootstrapTable("showColumn","num1");
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
                field : 'id,type,num,startNum,endNum,unit,price'
            };

            var params = $("#chargeModeForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        };
        return oTableInit;
    };

    // function searchList() {
    //     reloadTable();
    // }
    //
    function reloadTable() {
        $('#chargeModeList').bootstrapTable('refresh');
    }
    function deleteChargeMode(id){
        //首先判断该计费方式是否允许删除
		$.ajax({
			type:"post",
			dataType:"json",
			data:{
				id:id
			},
			url:"charging.do?isUsed",
			success:function(data){
			    if(data.isUsed == false){
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
                                    url:"charging.do?delete",
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
				}else{
			        slowNotify("当前计费方式已被使用，不允许删除")
				}
			}
		})
    }
    //
    // function searchRest() {
    //     $('#apiInfoForm').find(':input').each(function() {
    //         if("checkbox"== $(this).attr("type")){
    //             $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked',false);
    //         }else if("radio"== $(this).attr("type")){
    //             $("input:radio[name='" + $(this).attr('name') + "']").attr('checked',false);
    //         }else{
    //             $(this).val("");
    //         }
    //     });
    //     $('#apiInfoForm').find("input[type='checkbox']").each(function() {
    //         $(this).attr('checked', false);
    //     });
    //     $('#apiInfoForm').find("input[type='radio']").each(function() {
    //         $(this).attr('checked', false);
    //     });
    //     reloadTable();
    // }
    // //高级查询模态框
    // function bootstrapQueryBuilder() {
    //     $('#superQueryModal').modal({
    //         backdrop : false
    //     });
    // }
    // function save(dialog){
    //     dialog.close()
    // }
</script>

</body>
</html>