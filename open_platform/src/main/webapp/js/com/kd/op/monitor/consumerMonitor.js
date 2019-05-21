$(document).ready(function(){
    $("#search_group").on("change",function(){
        reloadTable();
    });
    timeInit();
    
//     $("#search-btn").bind("click",function(){
// //        var api = $("#search_api").selectpicker("val");
// //        if(api == null || api == undefined || api == ""){
// //            slowNotify("请先选择一个服务","warning");
// //            return false;
// //        }
//         getAPICount();
//     });

    $('#consumerTable').bootstrapTable({
        url : 'apiInvokeLogController.do?datagrid', //请求后台的URL（*）
        method : 'post', //请求方式（*）
        contentType : "application/x-www-form-urlencoded",
        toolbar : '#toolbar', //工具按钮用哪个容器
        striped : true, //是否显示行间隔色
        cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination : true, //是否显示分页（*）
        queryParams : function(params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize : params.limit, // 每页要显示的数据条数
                offset : params.offset, // 每页显示数据的开始行号
                sort : params.sort, // 排序规则
                order : params.order,
                rows : params.limit, //页面大小
                page : (params.offset / params.limit) + 1, //页码
                pageIndex : params.pageNumber,//请求第几页
                field : 'id,sceneId,sceneName,apiId,apiName,appName,invokeTime,invokeIp,methodType,invokeUrl,responseTimeLength,responseFlowSize,requestFlowSize,requestHeader,requestParam,returnParam,returnHeader,invokeOpenplatformResult,invokeServiceproviderResult'
            };

            var params = $("#searchForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        },
        sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber : 1, //初始化加载第一页，默认第一页
        pageSize : 10, //每页的记录行数（*）
        pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
        strictSearch : true,
        clickToSelect : true, //是否启用点击选中行
        height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId : "id", //每一行的唯一标识，一般为主键列
        sortName:'invokeTime',
        sortOrder:'desc',
        columns : [
            {
                title : '序号',
                width : 25,
                align : 'center',
                switchable : false,
                formatter : function(value,row, index) {
                    return index+1; //序号正序排序从1开始
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
            // {
            //     field : 'sceneName',
            //     title : '场景名称',
            //     valign : 'middle',
            //     width : 60,
            //     align : 'center',
            //     switchable : true,
            // },
            {
                field : 'apiName',
                title : '服务名称',
                valign : 'middle',
                width : 100,
                align : 'center',
                switchable : true,
            },
            {
                field : 'invokeIp',
                title : '调用IP',
                valign : 'middle',
                width : 50,
                align : 'center',
                switchable : true,
            },
            {
                field : 'invokeTime',
                title : '调用时间',
                valign : 'middle',
                width : 80,
                align : 'center',
                switchable : true,
            },
            // {
            //     field : 'requestFlowSize',
            //     title : '请求流量(KB)',
            //     valign : 'middle',
            //     width : 50,
            //     align : 'center',
            //     switchable : true,
            // },
            {
                field : 'responseFlowSize',
                title : '响应流量(KB)',
                valign : 'middle',
                width : 50,
                align : 'center',
                switchable : true,
            },
            {
                field : 'responseTimeLength',
                title : '响应时长(ms)',
                valign : 'middle',
                width : 50,
                align : 'center',
                switchable : true,
            },
            {
                field : 'invokeServiceproviderResult',
                title : '服务提供者调用结果',
                valign : 'middle',
                width : 60,
                align : 'center',
                switchable : true,
                formatter : function(value, rec, index) {
                    if(value == "1"){
                        return "失败";
                    }else if(value == "0"){
                        return "成功";
                    }else {
                        return "未知异常"
                    }
                }
            },
            {
                field : 'invokeOpenplatformResult',
                title : '开放平台调用结果',
                valign : 'middle',
                width : 60,
                align : 'center',
                switchable : true,
                formatter : function(value, rec, index) {
                    value += "";
                    if(value == "1"){
                        return "失败";
                    }else if(value == "0"){
                        return "成功";
                    }else {
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
            }
        ],
        onLoadSuccess : function(data) { //加载成功时执行
        },
        onLoadError : function() { //加载失败时执行
        }
    });

});
function reloadTable() {
    $('#consumerTable').bootstrapTable('refresh');
}
function timeInit(){
    var date = new Date();
    var day = intToTwoStr(date.getDate());   //28
    var month = intToTwoStr(date.getMonth()+1);   //4  + 1
    var today = date.getFullYear()+"-"+month+"-"+day;
    $('#startDate,#endDate').datetimepicker({
        //lang:"ch",
        format:	'Y-m-d H:00:00',
        formatTime:'H:00:00',
        timepicker:true,
        // value:today
    });
    $.datetimepicker.setLocale('ch');
}

//查看详情
function lookDetail(id) {
        var dialog = bootOpenLook('日志详情', 'apiInvokeLogController.do?goUpdate&id=' + id, 'wide');
}