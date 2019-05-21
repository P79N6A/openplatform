<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap.css" %> type="text/css">
<style>
    .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
        margin-bottom:2px;
    }
    .table{
        table-layout: fixed;
    }
</style>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
<script type="text/javascript">
    var apiCount;
    var groupCount;
    $(document).ready(function(){
        getAPICount();
    });
    function intToTwoStr(num){
        if(num < 10){
            return "0" + num;
        }
        return num;
    }

    //获取台区曲线信息
    function getAPICount(){
        apiCount = echarts.init(document.getElementById('apiCount'));  //初始化
        groupCount = echarts.init(document.getElementById('groupCount'));  //初始化
        //指定图标的数据和配置项
        var date = new Date(); //日期
        var xIndex = [];
        for (var i = 0; i <= date.getHours(); i++) {
            xIndex.push(i + ":00");
        }
        $.ajax({
            async : false,   //设置为false。请求为同步请求
            cache:false,   	//不设置缓存
            type: 'post',
            dataType : "json",
            url: "monitor.do?getMonitorData",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success:function(data){ //请求成功后处理函数。
                //解析后台传回来的data，把它传给纵轴
                var option1={
                    tooltip : {
                        trigger : 'axis',
                        position : function(pt) {
                            return [ pt[0], '10%' ];
                        }
                    },
                    title : {
                        left : 'center',
                        text : 'API调用次数',
                    },
                    legend: {
                        data:['第一天'],
                        left:'center',
                        top:"bottom"
                    },
                    /*grid:{
                        left:30,
                        right:40,
                        top:30
                        bottom:0
                    },*/

                    xAxis : {
                        type : 'category',
                        name: '时间(h)',
                        boundaryGap : false,
                        data : xIndex
                    },
                    yAxis : [{
                        type : 'value',
                        name: '次',
                        minInterval:1,
                    }],
                    series : [{
                        name: '第二天',
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
                        data: data.apiCounts
                    }]
                };
                apiCount.setOption(option1);

                var option2={
                    tooltip : {
                        trigger : 'axis',
                        position : function(pt) {
                            return [ pt[0], '10%' ];
                        }
                    },
                    title : {
                        left : 'center',
                        text : 'API分组调用次数',
                    },
                    legend: {
                        data:[],
                        left:'center',
                        top:"bottom"
                    },
                    /*grid:{
                        left:30,
                        right:40,
                        top:30
                        bottom:0
                    },*/
                    xAxis : {
                        type : 'category',
                        name: '时间(h)',
                        boundaryGap : false,
                        data : xIndex
                    },
                    yAxis : [{
                        type : 'value',
                        name: '次',
                        minInterval:1,
                    }],
                    series : []
                };
                for(var i = 0;i < data.groupNames.length;i++){
                    option2.legend.data.push(data.groupNames[i]);
                }
                for(var i = 0;i < data.groupDatas.length;i++){
                    option2.series.push({
                        name: data.groupNames[i],
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        /*itemStyle: {
                            normal: {
                                color: '#E51A08'
                            }
                        },*/
                        data: data.groupDatas[i]
                    });
                }
                groupCount.setOption(option2);
            }
        })
    }

    function getToday(){
        var date = new Date();
        var day = intToTwoStr(date.getDate());   //28
        var month = intToTwoStr(date.getMonth()+1);   //4  + 1
        var today = date.getFullYear()+"-"+month+"-"+day;
        return today;
    }

</script>
</head>

<body class="gray-bg" style="background:#fff;font-size: 20px;">
    <div class="col-md-12" style="margin-top:20px">
        <div id="apiCount" class="col-md-6" style="height:550px;margin:0 auto;"></div>
        <div id="groupCount" class="col-md-6" style="height:550px;margin:0 auto;"></div>
    </div>

</body>
<script type="text/javascript" src=<%= request.getContextPath() +"/js/jquery-1.11.3.min.js"%>></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<script src="plug-in-ui/hplus/js/content.js"></script>
</html>
