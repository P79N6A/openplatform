$(document).ready(function(){
    timeInit();

    $("#search").bind("click",function(){
        var api = $("#search_api").selectpicker("val");
        if(api == null || api == undefined || api == ""){
            slowNotify("请先选择一个服务","warning");
            return false;
        }
        getAPICount();
        $('#logList').bootstrapTable("refresh")
        $("#emptyDiv").css("display","none");
    });
    $("#reset-btn").bind("click",function(){
        $('#groupText').val('');
        $('#search_api').html("");
        $('#search_api').append("<option value=''>请选择服务</option>")
        $('#search_api').selectpicker('val', '');
        $('#search_api').selectpicker('refresh');
        timeInit();
        getAPICount();
    });

    $('#logList').bootstrapTable({
        url : 'monitor.do?datagrid', //请求后台的URL（*）
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
                field : 'id,apiId,apiName,appName,invokeTime,invokeIp,methodType,invokeUrl,responseTimeLength,responseFlowSize,requestFlowSize,requestHeader,requestParam,returnParam,returnHeader,invokeResult,'
            };

            var params = $("#subSearchForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        },
        sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber : 1, //初始化加载第一页，默认第一页
        pageSize : 5, //每页的记录行数（*）
        pageList : [ 5,10 ], //可供选择的每页的行数（*）
        strictSearch : true,
        clickToSelect : true, //是否启用点击选中行
        // height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId : "id", //每一行的唯一标识，一般为主键列
        sortName:'id',
        sortOrder:'desc',
        columns : [
            {
                title : '序号',
                width : 25,
                align : 'center',
                switchable : false,
                formatter : function(value,row, index) {
                    return index+1; //序号正序排序从1开始
                    // var pageSize = $('#apiInvokeLogList')
                    //     .bootstrapTable('getOptions').pageSize;
                    // var pageNumber = $('#apiInvokeLogList').bootstrapTable('getOptions').pageNumber;
                    // return pageSize* (pageNumber - 1) + index + 1;
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
                field : 'appName',
                title : '服务名称',
                valign : 'middle',
                width : 120,
                align : 'center',
                switchable : true,
            },
            {
                field : 'requestFlowSize',
                title : '请求流量(KB)',
                valign : 'middle',
                width : 120,
                align : 'center',
                switchable : true,
            },
            {
                field : 'responseFlowSize',
                title : '相应流量(KB)',
                valign : 'middle',
                width : 120,
                align : 'center',
                switchable : true,
            },
            {
                field : 'invokeResult',
                title : '结果',
                valign : 'middle',
                width : 60,
                align : 'center',
                switchable : true,
                formatter : function(value, rec, index) {
                    if(value == 0){
                        return "失败";
                    }else{
                        return "成功";
                    }
                }
            },
        ],
        onLoadSuccess : function(data) { //加载成功时执行
        },
        onLoadError : function() { //加载失败时执行
        }
    });
    getAPICount()
});
//获取台区曲线信息
function getAPICount(){
    var line = echarts.init(document.getElementById('line'));  //初始化
    var nodeType = $("#nodeType").val();
    var nodeId = $("#nodeId").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var appId = $("#appId").val();
    $.ajax({
        async : false,   //设置为false。请求为同步请求
        cache:false,   	//不设置缓存
        type: 'post',
        dataType : "json",
        data:{
            nodeType:nodeType,
            nodeId:nodeId,
            start:startDate,
            end:endDate,
            appId:appId
        },
        url: "monitor.do?getCountNodeData",//后台处理程序,获取显示数据
        error: function () {//请求失败处理函数
            return false;
        },
        success:function(data){ //请求成功后处理函数。
            //解析后台传回来的data，把它传给纵轴
            var option={
                tooltip : {
                    trigger : 'axis',
                    position : function(pt) {
                        return [ pt[0], '10%' ];
                    }
                },
                title : {
                    left : 'center',
                    text : '服务统计',
                },
                /*legend: {
                    data:['第一天'],
                    left:'center',
                    top:"bottom"
                },*/
                /*grid:{
                    left:30,
                    right:40,
                    top:30
                    bottom:0
                },*/
                xAxis : {
                    type : 'category',
                    //name: '时间(h)',
                    boundaryGap : false,
                    data : data.xIndex
                },
                yAxis : [{
                    type : 'value',
                    name: '次',
                    minInterval:1
                }],
                series : [{
                    name: '调用量',
                    type: 'line',
                    smooth: true,
                    symbol: 'none',
                    /* areaStyle: {normal: {
                        opacity:0.5
                    }}, */
                    itemStyle: {
                        normal: {
                            color: '#E51A08'
                        }
                    },
                    data: data.count
                }]
            };
            line.setOption(option);
        }
    })
}
function timeInit(){
    var date = new Date();
    var day = intToTwoStr(date.getDate());
    var month = intToTwoStr(date.getMonth()+1);
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    var today = date.getFullYear()+"-"+month+"-"+day + " " + hour + ":" + minute + ":" + second;
    var yestoday = date.getFullYear()+"-"+month+"-"+ (day - 1) + " " + hour + ":" + minute + ":" + second;
    $('#startDate').datetimepicker({
        //lang:"ch",
        format:	'Y-m-d H:i:s',
        timepicker:true,
        value:yestoday
    });
    $('#endDate').datetimepicker({
        //lang:"ch",
        format:	'Y-m-d H:i:s',
        timepicker:true,
        value:today
    });
    $.datetimepicker.setLocale('ch');
}