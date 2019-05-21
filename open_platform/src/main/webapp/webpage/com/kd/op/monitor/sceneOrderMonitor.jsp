<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<t:base type="bootstrap,bootstrap-table,layer"></t:base>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<link rel="stylesheet" href="${webRoot}/plug-in/select2/css/select2.min.css">
<script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.min.js"></script>
</head>
<body>
<div class="search-div row">
        <div class="form-horizontal col-xs-9 pull-left"  style="padding-left: 50px;margin-top: 10px">
            <div class="col-xs-12 col-sm-6 col-md-3">
                <label for="sceneId" style="font-size:14px;margin-top: 10px;">场景：</label><br>
                <select class="form-control" name="sceneId" style="width: 100%"
                        id="sceneId">
                </select>
            </div>
            <div class="col-xs-12 col-sm-6 col-md-3">
                <label style="font-size:14px;margin-top: 10px;">开始日期：</label>
                <input type="text" id="startDate" name="startDate" class="form-control input-sm laydate-date" autocomplete="off"/>
            </div>
            <div class="col-xs-12 col-sm-6 col-md-3">
                <label  style="font-size:14px;margin-top: 10px;">结束日期：</label>
                <input type="text" id="endDate" name="endDate" class="form-control input-sm laydate-date" autocomplete="off"/>
            </div>
        </div>
        <div class="col-xs-3" style="margin-top: 37px">
            <button type="button" class="btn btn-primary" id="search-btn" onclick="searchSceneOrder()">
                <span class="glyphicon glyphicon-search"></span>查询
            </button>
            <button type="button" class="btn btn-warning" id="reset-btn" onclick="resetSceneOrder()">
                <span class="glyphicon glyphicon-refresh"></span>重置
            </button>
        </div>
</div>
<div class="row" style="padding-top:20px">
    <div id="sceneOrderCount" class="col-xs-6" style="height: 500px;">

    </div>
    <div id="sceneOrderRanking" class="col-xs-6" style="height: 500px;">

    </div>
</div>
</body>
<script type="text/javascript">

    $(function () {
        //场景列表
        initSelect();
        //统计
        sceneOrderCount();
        //排名
        sceneOrderRanking();

    })

    //获取场景交易统计
    function sceneOrderCount(){
        var sceneOrderCount = echarts.init(document.getElementById('sceneOrderCount'));  //初始化
        var optionCount={
            tooltip : {
                trigger : 'axis',
                position : function(pt) {
                    return [ pt[0], '10%' ];
                }
            },
            title : {
                left : 'center',
                text : '场景交易统计(最近7日)',
            },
            xAxis : {
                type : 'category',
                name: '时间',
                boundaryGap : false,
                data : ['1','2','3','4','5','6','7','8','9']
            },
            yAxis : [{
                type : 'value',
                name: '交易笔数',
                minInterval:1
            }],
            series : [{
                name: '交易量',
                type: 'line',
                smooth: false,
                symbol: 'none',
                itemStyle: {
                    normal: {
                        color: '#E51A08'
                    }
                },
                data: ['100','70','80','53','50','60','74','89','52']
            }]
        };
        var sceneId = $("#sceneId").val();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        $.ajax({
            async : false,   //设置为false。请求为同步请求
            cache:false,   	//不设置缓存
            type: 'post',
            dataType : "json",
            data:{
                sceneId:sceneId,
                startDate:startDate,
                endDate:endDate
            },
            url: "monitor.do?sceneOrderCount",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success:function(data){ //请求成功后处理函数。
                //解析后台传回来的data
                optionCount.series[0].data = data.seriesData;
                optionCount.xAxis.data = data.xAxisData;
                sceneOrderCount.setOption(optionCount);
            }
        })
    }

    function searchSceneOrder(){
        var endDate=new Date($("#endDate").val())
        var startDate=new Date($("#startDate").val())
        if (endDate-startDate<0) {
            quickNotify("结束日期不能小于开始日期", "warning");
            return;
        }
       sceneOrderCount();
       sceneOrderRanking();
   }

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

    /**
     * 排名统计
     */

    function sceneOrderRanking(){
        var sceneOrderRanking = echarts.init(document.getElementById('sceneOrderRanking'));  //初始化
        var optionRanking = {
            title: {
                text: '场景购买排行',
                left : 'center',
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '10%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value',
                name: '交易笔数',
            },
            yAxis: {
                type: 'category',
                name: '场景名称',
                data: ['巴西','印尼','美国','印度','中国']
            },
            series: [
                {
                    name: '交易笔数',
                    type: 'bar',
                    barWidth:30,
                    data: [18203, 23489, 29034, 104970, 131744, 630230]
                },
            ]
        };
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        $.ajax({
            async : false,   //设置为false。请求为同步请求
            cache:false,   	//不设置缓存
            type: 'post',
            dataType : "json",
            data:{
                startDate:startDate,
                endDate:endDate
            },
            url: "monitor.do?sceneOrderRanking",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success:function(data){ //请求成功后处理函数。
                //解析后台传回来的data
                optionRanking.series[0].data = data.seriesData;
                optionRanking.yAxis.data = data.yAxisData;
                sceneOrderRanking.setOption(optionRanking);
            }
        })
    }

    function initSelect() {
        $('#sceneId').select2();
        //加载场景选择下拉列表
        $.post(
            "monitor.do?invokeSceneDatagrid",
            function (data) {
                var htmlStr="<option value=\"\">全部</option>";
                for (var i=0; i<data.length; i++) {
                    htmlStr+="<option value=\""+data[i].id+"\">"+data[i].sceneName+"</option>";
                }
                $('#sceneId').empty();
                $('#sceneId').html(htmlStr)
            },
            'json'
        );
    }
    function resetSceneOrder(){
        $("#startDate,#endDate,#sceneId").val("")
        initSelect();
        sceneOrderCount();
        sceneOrderRanking();
    }
</script>
</html>
