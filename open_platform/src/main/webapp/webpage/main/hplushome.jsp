<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--<link rel="shortcut icon" href="images/favicon.ico">--%>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts-china.js"%>"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts-gl.js"%>"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() + "/plug-in-ui/hplus/js/plugins/echarts/echarts-all.js"%>"></script>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/home/theme.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/home/app1.css" %> type="text/css">
    <script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
</head>

<style>
    .countStyle {
        width: 21%;
        height: 55px;
        border-radius: 5px 5px 5px 5px;
        background-color: #0ac3a9;
        font-size: 35px;
        margin-left: 3px;
        color: white;
        display:inline-block;
    }

    .moneyStyle {
        width: 11%;
        height: 55px;
        border-radius: 5px 5px 5px 5px;
        background-color: #0ac3a9;
        font-size: 35px;
        color: white;
        display:inline-block;
    }

    .headStyle{
        margin-top: 1%;
        width: 20%;
        height: 100%;
    }
</style>

<body style="height:190%;background:white;font-size: 20px;border: 10px solid #edeeed;">
<div class="row col-md-12 "
     style="border-bottom: 10px solid #edeeed;padding-right:0px;padding-left:0px;width: 104%;height: 11%">

    <div class="col-md-2 headStyle" >
        <div class="col-md-12 " style="font-size: 15px;" align="center">能力总数(个)</div>
        <div class="col-md-12" style="margin-top: 2%;height: 50%" id="apiCount" align="center">
        </div>
    </div>
    <div class="col-md-2 headStyle">
        <div class="col-md-12" style="font-size: 15px;" align="center">订阅总数(个)</div>
        <div class="col-md-12" style="margin-top: 2%;height: 50%" id="apiOrderCount" align="center">
        </div>
    </div>
    <div class="col-md-1 headStyle">
        <div class="col-md-12" style="font-size: 15px;" align="center">应用总数(个)</div>
        <div class="col-md-12"style="margin-top: 2%;height: 50%" id="appCount" align="center"></div>

    </div>
    <div class="col-md-4 headStyle" align="left">
        <div class="col-md-12" style="font-size: 15px;" id="apiProductFlowName"  align="center"></div>
        <div class="col-md-12"style="margin-top: 2%;height: 50%" id="apiProductFlow" align="center"></div>
    </div>
    <div class="col-md-3 headStyle">
        <div class="col-md-12" style="font-size: 15px;" id="apiProductCountName" align="center"></div>
        <div class="col-md-12" style="margin-top: 2%;height: 50%" id="apiProductCount" align="center"></div>
    </div>
</div>
<div class="row col-md-12" style="height: 89%">
    <div class="col-md-4 " style="padding-right:0px;padding-left:0px;height: 100%;" align="center">
        <div id="runMonitor" class="col-md-12"
             style="height: 33.3%;border-right: 10px solid #edeeed;border-bottom: 10px solid #edeeed;padding-top:40px "></div>
        <div id="orderMonitor" class="col-md-12"
             style="height: 33.3%;border-right: 10px solid #edeeed;border-bottom: 10px solid #edeeed;padding-top:40px "></div>
        <div id="userMonitor" class="col-md-12"
             style="height: 33.4%;border-right: 10px solid #edeeed;padding-top:40px "></div>
    </div>
    <div class="col-md-4" style="padding-right:0px;padding-left:0px;height: 100%" align="center">
        <div align="center"
             style="border-bottom: 10px solid #edeeed;border-right: 10px solid #edeeed;height: 10%;padding-top:15px">
            <span style="font-size: 15px;" align="center">交易金额 </span>
            <span id="money" align="center"></span>
            <span style="font-size: 15px;" align="center"> 元</span>
        </div>
        <div class="col-md-12" id="apiStatus"
             style="border-right: 10px solid #edeeed;height: 45%;padding-top: 10px"></div>
        <div class="col-md-12" id="appStatus"
             style="border-right: 10px solid #edeeed;height: 45%;padding-top: 10px"></div>
    </div>
    <div class="col-md-4" style="padding-right:0px;padding-left:0px;height: 100%" align="center">
        <div id="flowRank" class="col-md-12"
             style="height: 50%;border-bottom: 10px solid #edeeed;width: 110%;padding-top: 25%"></div>
        <div id="countRank" class="col-md-12" style="height: 50%;width: 110%;padding-top: 25%"></div>
    </div>
</div>


<script type="text/javascript">
    $(document).ready(function () {
        //查询首页展示统计数据
        $.post(
            "hplushome.do?getCountData",
            function (data) {
                var apiProductCount;
                var apiProductFlow
                if (data.apiProductCount/10000 > 1) {
                    var result  = Math.round(data.apiProductCount/100) / 100;
                    apiProductCount=result.toString().split("");
                    $("#apiProductCountName").text("能力提供总次数(万个)")
                }else {
                    apiProductCount = data.apiProductCount.toString().split("");
                    $("#apiProductCountName").text("能力提供总次数(个)")
                }
                if ( data.apiProductFlow/1024>1) {
                    var result  = Math.round(data.apiProductFlow/1024*10) / 10;
                    apiProductFlow=result.toString().split("");
                    $("#apiProductFlowName").text("能力提供总流量(GB)")
                }else {
                    apiProductFlow = data.apiProductFlow.toString().split("");
                    $("#apiProductFlowName").text("能力提供总流量(MB)")
                }
                var apiCount = data.apiCount.toString().split("");
                var apiOrderCount = data.apiOrderCount.toString().split("");
                var appCount = data.appCount.toString().split("");
                var money = data.money.toString().split("");
                var apiCountHtml = "";
                var apiOrderCountHtml = "";
                var apiProductFlowHtml = "";
                var appCountHtml = "";
                var apiProductCountHtml = "";
                var moneyHtml = "";
                for (var i = 0; i < apiCount.length; i++) {
                    apiCountHtml += "<span  class='countStyle' align=\"center\">" + apiCount[i] + "</span>"
                }
                for (var i = 0; i < apiOrderCount.length; i++) {
                    apiOrderCountHtml += "<span class='countStyle' align=\"center\">" + apiOrderCount[i] + "</span>"
                }
                for (var i = 0; i < apiProductFlow.length; i++) {
                    apiProductFlowHtml += "<span class='countStyle' align=\"center\">" + apiProductFlow[i] + "</span>"
                }
                for (var i = 0; i < appCount.length; i++) {
                    appCountHtml += "<span class='countStyle' align=\"center\">" + appCount[i] + "</span>"
                }
                for (var i = 0; i < apiProductCount.length; i++) {
                    apiProductCountHtml += "<span class='countStyle' align=\"center\">" + apiProductCount[i] + "</span>"
                }
                for (var i = 0; i < money.length; i++) {
                    moneyHtml += "<span class='moneyStyle' align=\"center\">" + money[i] + "</span>"
                }
                $('#apiCount').html(apiCountHtml);
                $('#apiOrderCount').html(apiOrderCountHtml)
                $('#apiProductFlow').html(apiProductFlowHtml)
                $('#apiProductCount').html(appCountHtml)
                $('#apiProductCount').html(apiProductCountHtml)
                $('#appCount').html(appCountHtml)
                $('#money').html(moneyHtml)
            },
            'json'
        );
        runMonitorFun();
        orderMonitorFun();
        userMonitorFun();
        apiStatusMonitorFun();
        appStatusMonitorFun();
        countRankFun();
        flowRankFun();
    });

    function runMonitorFun() {
        var runMonitorChart = echarts.init(document.getElementById('runMonitor'));
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,   	//不设置缓存
            type: 'post',
            dataType: "json",
            url: "hplushome.do?getRunMonitorData",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) {
                var runMonitorOption = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['调用流量(M)', '调用次数(个)', '异常个数(个)']
                    },
                    grid: {
                        left: '3%',
                        right: '15%',
                        bottom: '20%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        name: '时间',
                        boundaryGap: false,
                        data: data.xIndex,
                        // type: 'category'
                        // boundaryGap: false,
                        // data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
                    },
                    yAxis: {
                        type: 'value',
                        data: data.xIndex
                    },
                    series: [
                        {
                            name: '调用流量(M)',
                            type: 'line',
                            itemStyle: {
                                normal: {
                                    color: '#6c40a4'
                                }
                            },
                            data: data.flowData
                        },
                        {
                            name: '调用次数(个)',
                            type: 'line',
                            itemStyle: {
                                normal: {
                                    color: '#559271'
                                }
                            },
                            data: data.countData
                        },
                        {
                            name: '异常个数(个)',
                            type: 'line',
                            itemStyle: {
                                normal: {
                                    color: '#cfb14e'
                                }
                            },
                            data: data.exceptionData
                        }]
                };
                runMonitorChart.setOption(runMonitorOption);
            }
        });
    }

    function orderMonitorFun() {
        var orderMonitorChart = echarts.init(document.getElementById('orderMonitor'));
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,  //不设置缓存
            type: 'post',
            dataType: "json",
            url: "hplushome.do?getOrderMonitorData",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) {
                var orderMonitorOption = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['能力数量', '订阅数量',]
                    },
                    grid: {
                        left: '5%',
                        right: '16%',
                        bottom: '20%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        name: '时间',
                        data: data.xIndex,
                    },
                    yAxis: {
                        type: 'value',
                        name: '数量（个）',
                        data: data.xIndex,
                    },
                    series: [
                        {
                            name: '能力数量',
                            type: 'line',
                            itemStyle: {
                                normal: {
                                    color: '#6c40a4'
                                }
                            },
                            data: data.apiCount
                        },
                        {
                            name: '订阅数量',
                            type: 'line',
                            itemStyle: {
                                normal: {
                                    color: '#559271'
                                }
                            },
                            data: data.orderCount
                        }]
                };
                orderMonitorChart.setOption(orderMonitorOption);
            }

        });
    }

    function userMonitorFun() {
        var userMonitorChart = echarts.init(document.getElementById('userMonitor'));
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,  //不设置缓存
            type: 'post',
            dataType: "json",
            url: "hplushome.do?getUserMonitorData",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) {
                var userMonitorOption = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['能力订阅者数量', '能力提供者数量',]
                    },
                    grid: {
                        left: '5%',
                        right: '16%',
                        bottom: '20%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        name: '时间',
                        data: data.xIndex,
                    },
                    yAxis: {
                        type: 'value',
                        name: '数量（个）',
                        data: data.xIndex,
                    },
                    series: [
                        {
                            name: '能力订阅者数量',
                            type: 'line',
                            itemStyle: {
                                normal: {
                                    color: '#6c40a4'
                                }
                            },
                            data: data.isvCount
                        },
                        {
                            name: '能力提供者数量',
                            type: 'line',
                            itemStyle: {
                                normal: {
                                    color: '#559271'
                                }
                            },
                            data: data.ispCount
                        }]
                };
                userMonitorChart.setOption(userMonitorOption);
            }
        })
    }

    function apiStatusMonitorFun() {
        var apiStatusChart = echarts.init(document.getElementById('apiStatus'));
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,  //不设置缓存
            type: 'post',
            dataType: "json",
            url: "hplushome.do?getApiAppStatusData&type=api",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) {
                apiStatusOption = {
                    title: {
                        text: '能力现状',
                        x: 'center',
                        textStyle: {
                            fontFamily: 'Arial',
                            fontSize: 15,
                            color: '#0ac3a9',
                            fontStyle: 'normal',
                            fontWeight: 'normal',
                        },
                    },
                    legend: {
                        top: 25,
                        orient: 'vertical',
                        left: 'left',
                        data: ['正常', '暂停', '作废']
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    color: ['#559271', '#cfb14e', '#a42b34',],
                    series: [
                        {
                            name: '能力数量',
                            type: 'pie',
                            radius: '50%',
                            center: ['50%', '50%'],
                            data: [
                                {value: data.normalCount, name: '正常'},
                                {value: data.stopCount, name: '暂停'},
                                {value: data.cancelCount, name: '作废'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                apiStatusChart.setOption(apiStatusOption);
            }
        })
    }

    function appStatusMonitorFun() {
        var appStatusChart = echarts.init(document.getElementById('appStatus'));
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,  //不设置缓存
            type: 'post',
            dataType: "json",
            url: "hplushome.do?getApiAppStatusData&type=app",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) {
                appStatusOption = {
                    title: {
                        text: '应用现状',
                        x: 'center',
                        textStyle: {
                            fontFamily: 'Arial',
                            fontSize: 15,
                            color: '#0ac3a9',
                            fontStyle: 'normal',
                            fontWeight: 'normal',
                        },
                    },
                    legend: {
                        top: 25,
                        orient: 'vertical',
                        left: 'left',
                        data: ['正常', '暂停', '作废']
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    color: ['#559271', '#cfb14e', '#a42b34',],
                    series: [
                        {
                            name: '应用数量',
                            type: 'pie',
                            radius: '50%',
                            center: ['50%', '50%'],
                            data: [
                                {value: data.normalCount, name: '正常'},
                                {value: data.stopCount, name: '暂停'},
                                {value: data.cancelCount, name: '作废'}
                            ],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                appStatusChart.setOption(appStatusOption);
            }
        })
    }

    function countRankFun() {
        var countRankChart = echarts.init(document.getElementById('countRank'));
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,   	//不设置缓存
            type: 'post',
            dataType: "json",
            url: "hplushome.do?getCountRankData",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) { //请求成功后处理函数。
                var countRankOption = {
                    title: {
                        left: 'center',
                        text: '访问次数排名',
                        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
                            fontFamily: 'Arial',
                            fontSize: 15,
                            color: '#0ac3a9',
                            fontStyle: 'normal',
                            fontWeight: 'normal',
                        },
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    grid: {
                        left: '5%',
                        right: '15%',
                        bottom: '40%',
                        containLabel: true
                    },
                    yAxis: {
                        type: 'value',
                        name: '次数(次)',
                        nameLocation: 'end',
                        data: data.xIndex
                    },
                    xAxis: [{
                        // axisLabel: {
                        //     show: false
                        // },
                        type: 'category',
                        name: '名称',
                        data: data.xIndex
                    }],
                    series: [{
                        name: '交易次数',
                        type: 'bar',
                        smooth: false,
                        symbol: 'none',
                        barWidth: 20,
                        itemStyle: {
                            normal: {
                                color: '#6c40a4'
                            }
                        },
                        data: data.countRankData
                    }]
                };
                countRankChart.setOption(countRankOption);
            }
        })
    }

    function flowRankFun() {
        var flowRankChart = echarts.init(document.getElementById('flowRank'));
        $.ajax({
            async: false,   //设置为false。请求为同步请求
            cache: false,   	//不设置缓存
            type: 'post',
            dataType: "json",
            url: "hplushome.do?getFlowRankData",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success: function (data) { //请求成功后处理函数。
                var flowRankOption = {
                    title: {
                        left: 'center',
                        text: '访问流量排名',
                        textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
                            fontFamily: 'Arial',
                            fontSize: 15,
                            color: '#0ac3a9',
                            fontStyle: 'normal',
                            fontWeight: 'normal',
                        },
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    grid: {
                        left: '5%',
                        right: '15%',
                        bottom: '40%',
                        containLabel: true
                    },
                    yAxis: {
                        type: 'value',
                        name: '流量(M)',
                        nameLocation: 'end',
                        data: data.xIndex
                    },
                    xAxis: [{
                        type: 'category',
                        name: '名称',
                        data: data.xIndex
                    }],
                    series: [{
                        name: '访问流量',
                        type: 'bar',
                        smooth: false,
                        symbol: 'none',
                        barWidth: 20,
                        itemStyle: {
                            normal: {
                                color: '#cfb14e'
                            }
                        },
                        data: data.flowRankData
                    }]
                };
                flowRankChart.setOption(flowRankOption);
            }
        })
    }
</script>
</body>
<script type="text/javascript" src=<%= request.getContextPath() + "/js/jquery-1.11.3.min.js"%>></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<script src="plug-in-ui/hplus/js/content.js"></script>
</html>
