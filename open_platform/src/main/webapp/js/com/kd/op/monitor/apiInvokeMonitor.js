$(document).ready(function () {
    timeInit();
    $('#apiId').select2();
    // 加载服务选择下拉列表
    $.ajax({
        async: false,
        cache: false,
        type: 'post',
        dataType: "json",
        url: "monitor.do?invokeApiDatagrid",//后台处理程序,获取显示数据
        error: function () {//请求失败处理函数
            return false;
        },
        success: function (data) {
            var htmlStr="<option value=\"\">全部</option>";
            for (var i=0; i<data.length; i++) {
                htmlStr+="<option value=\""+data[i].id+"\">"+data[i].apiName+"</option>";
            }
            $('#apiId').empty();
            $('#apiId').html(htmlStr)
        },

    });
    getApiSort()
    getApiCount();
    $("#search-btn").bind("click", function () {
        var apiId = $("#apiId").selectpicker("val");
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        getApiCount();
        getApiSort()
    });
    $("#reset-btn").bind("click", function () {
        $('#groupText').val('');
        $('#apiId').html("");
        $("#apiId").html("<option value=\"\" selected=selected>全部</option>");
        $.ajax({
            async: false,
            cache: false,
            type: 'post',
            dataType: "json",
            url: "monitor.do?invokeApiDatagrid",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) {
                var htmlStr="<option value=\"\">全部</option>";
                for (var i=0; i<data.length; i++) {
                    htmlStr+="<option value=\""+data[i].id+"\">"+data[i].apiName+"</option>";
                }
                $('#apiId').empty();
                $('#apiId').html(htmlStr)
            },

        });
        timeInit();
    });
});

//获取应用曲线信息
function   getApiCount() {
    var count = echarts.init(document.getElementById('count'));  //初始化
    var app = $("#apiId").selectpicker('val');
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    $.ajax({
        async: false,   //设置为false。请求为同步请求
        cache: false,   	//不设置缓存
        type: 'post',
        dataType: "json",
        data: {
            appId: app,
            start: startDate,
            end: endDate
        },
        url: "monitor.do?getApiInvokeMonitorData",//后台处理程序,获取显示数据
        error: function () {//请求失败处理函数
            return false;
        },
        success: function (data) { //请求成功后处理函数。
            //解析后台传回来的data，把它传给纵轴
            var option = {
                tooltip: {
                    trigger: 'axis',
                    position: function (pt) {
                        return [pt[0], '10%'];
                    }
                },
                title: {
                    left: 'center',
                    text: '能力调用统计',
                },
                xAxis: {
                    type: 'category',
                    name: '调用时间',
                    boundaryGap: false,
                    data: data.xIndex
                },
                grid:{
                    top:'15%',
                    right:'15%',
                    left:'15%'
                },
                yAxis: [{
                    type: 'value',
                    name: '调用次数(次)',
                    data: data.xIndex
                }],
                series: [{
                    name: '调用次数',
                    type: 'line',
                    smooth: false,
                    symbol: 'none',
                    /* areaStyle: {normal: {
                        opacity:0.5
                    }}, */
                    itemStyle: {
                        normal: {
                            color: '#219171'
                        }
                    },
                    data: data.count
                }]
            };
            count.setOption(option);
        }
    })
}

//获取应用曲线信息
function   getApiSort() {
    var count = echarts.init(document.getElementById('sort'));  //初始化
    var app = $("#apiId").selectpicker('val');
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    $.ajax({
        async: false,   //设置为false。请求为同步请求
        cache: false,   	//不设置缓存
        type: 'post',
        dataType: "json",
        data: {
            appId: app,
            start: startDate,
            end: endDate
        },
        url: "monitor.do?getMonitorSortData&type=apiInvoke",//后台处理程序,获取显示数据
        error: function () {//请求失败处理函数
            return false;
        },
        success: function (data) { //请求成功后处理函数。
            //解析后台传回来的data，把它传给纵轴
            var option = {
                tooltip: {
                    trigger: 'axis',
                    position: function (pt) {
                        return [pt[0], '10%'];
                    }
                },
                title: {
                    left: 'center',
                    text: '能力调用排行榜',
                },
                xAxis: {
                    type: 'value',
                    name: '调用次数(次)',
                    nameLocation:'end',
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                },
                yAxis: [{
                    type: 'category',
                    name: '能力名称',
                    data: data.xIndex
                }],
                grid:{
                    top:'15%',
                    right:'18%',
                    left:'15%'
                },
                series: [{
                    name: '调用次数',
                    type: 'bar',
                    smooth: false,
                    symbol: 'none',
                    /* areaStyle: {normal: {
                        opacity:0.5
                    }}, */
                    barWidth : 35,
                    itemStyle: {
                        normal: {
                            color: '#219171'
                        }
                    },
                    data: data.count
                }]
            };
            count.setOption(option);
        }
    })
}
function timeInit() {
    var date = new Date();
    var day = intToTwoStr(date.getDate());   //28
    var month = intToTwoStr(date.getMonth() + 1);   //4  + 1
    var endDate = date.getFullYear() + "-" + month + "-" + day;
    var startDate = date.getFullYear() + "-" + month + "-" + (day - 7);
    $('#endDate').datetimepicker({
        //lang:"ch",
        format: 'Y-m-d',
        timepicker: false,
        value: endDate
    });
    $('#startDate').datetimepicker({
        //lang:"ch",
        format: 'Y-m-d',
        timepicker: false,
        value: startDate
    });
    $.datetimepicker.setLocale('ch');
}