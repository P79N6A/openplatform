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
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${webRoot}/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <link rel="stylesheet" href="${webRoot}/plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css">
</head>
<body>
<div class="search-div row">
    <div id="div1" class="form-horizontal col-xs-9 pull-left"  style="padding-left: 50px;margin-top: 10px">
        <div class="col-xs-12 col-sm-6 col-md-3">
            <label style="font-size:14px;margin-top: 10px;">能力中心：</label><br>
            <div style="position: relative;">
                <%--<!-- 模拟select点击框 以及option的text值显示-->--%>
                <input id="groupName" class="form-control" style="height: 28px"  readonly />
                <%--<!-- 模拟select右侧倒三角 -->--%>
                <%--<i class="trg"style="position: absolute;"></i>--%>
                <%--<!-- 存储 模拟select的value值 -->--%>
                <%--<input id="groupId" type="hidden" name="groupId" />--%>
                <%--<!-- zTree树状图 相对定位在其下方 -->--%>
                <div class="ztree" style="display:none;z-index: 1;background-color: white;  position: absolute;border:1px solid #4aa5ff;width:100%;overflow:auto;height: 200px">
                    <ul id="groups"></ul>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-3">
            <label for="apiId" style="font-size:14px;margin-top: 10px;">能力：</label><br>
            <select class="form-control" name="apiId" style="width: 100%"
                    id="apiId">
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
        <button type="button" class="btn btn-primary" id="search-btn" onclick="searchApiInvoke()">
            <span class="glyphicon glyphicon-search"></span>查询
        </button>
        <button type="button" class="btn btn-warning" id="reset-btn" onclick="resetApiInvoke()">
            <span class="glyphicon glyphicon-refresh"></span>重置
        </button>
    </div>
</div>
<div class="row" style="padding-top:20px">
    <div id="apiInvokeCount" class="col-xs-6" style="height: 500px;">

    </div>
    <div id="apiInvokeRanking" class="col-xs-6" style="height: 500px;">

    </div>
</div>
</body>
<script type="text/javascript">

    $(function () {
        initGroupTree();
        //能力列表
        initSelect();
        //统计
        apiInvokeCount();
        //排名
        apiInvokeRanking();

    })
    var groupId = "";
    //获取能力调用统计
    function apiInvokeCount(){
        var apiInvokeCount = echarts.init(document.getElementById('apiInvokeCount'));  //初始化
        var optionCount={
            tooltip : {
                trigger : 'axis',
                position : function(pt) {
                    return [ pt[0], '10%' ];
                }
            },
            title : {
                left : 'center',
                text : '能力调用统计(最近7日)',
            },
            xAxis : {
                type : 'category',
                name: '时间',
                boundaryGap : false,
                data : ['1','2','3','4','5','6','7','8','9']
            },
            yAxis : [{
                type : 'value',
                name: '调用次数',
                minInterval:1
            }],
            series : [{
                name: '调用量',
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
        var apiId = $("#apiId").val();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        $.ajax({
            async : false,   //设置为false。请求为同步请求
            cache:false,   	//不设置缓存
            type: 'post',
            dataType : "json",
            data:{
                apiId:apiId,
                startDate:startDate,
                endDate:endDate,
                apiGroupId:groupId
            },
            url: "monitor.do?apiInvokeCount",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success:function(data){ //请求成功后处理函数。
                //解析后台传回来的data
                optionCount.series[0].data = data.seriesData;
                optionCount.xAxis.data = data.xAxisData;
                apiInvokeCount.setOption(optionCount);
            }
        })
    }

    function searchApiInvoke(){
        var endDate=new Date($("#endDate").val())
        var startDate=new Date($("#startDate").val())
        if (endDate-startDate<0) {
            quickNotify("结束日期不能小于开始日期", "warning");
            return;
        }
        apiInvokeCount();
        apiInvokeRanking();
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

    function apiInvokeRanking(){
        var apiInvokeRanking = echarts.init(document.getElementById('apiInvokeRanking'));  //初始化
        var optionRanking = {
            title: {
                text: '能力调用排行',
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
                name: '调用次数',
            },
            yAxis: {
                type: 'category',
                name: '能力名称',
                data: ['巴西','印尼','美国','印度','中国']
            },
            series: [
                {
                    name: '调用次数',
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
                endDate:endDate,
                apiGroupId:groupId
            },
            url: "monitor.do?apiInvokeRanking",//后台处理程序,获取显示数据
            error: function () {//请求失败处理函数
                return false;
            },
            success:function(data){ //请求成功后处理函数。
                //解析后台传回来的data
                optionRanking.series[0].data = data.seriesData;
                optionRanking.yAxis.data = data.yAxisData;
                apiInvokeRanking.setOption(optionRanking);
            }
        })
    }

    function initSelect() {
        $('#apiId').select2();
        //加载场景选择下拉列表
        $.post(
            "monitor.do?invokeApiDatagrid&groupId="+groupId,
            function (data) {
                var htmlStr="<option value=\"\">全部</option>";
                for (var i=0; i<data.length; i++) {
                    htmlStr+="<option value=\""+data[i].id+"\">"+data[i].apiName+"</option>";
                }
                $('#apiId').empty();
                $('#apiId').html(htmlStr)
            },
            'json'
        );
    }
    function resetApiInvoke(){
        $("#startDate,#endDate,#apiId").val("");
        groupId = "";
        initSelect();
        apiInvokeCount();
        apiInvokeRanking();
        initGroupTree();
        $('#div1').find(':input').each(function () {
            if ("checkbox" == $(this).attr("type")) {
                $("input:checkbox[name='" + $(this).attr('name') + "']").attr('checked', false);
            } else if ("radio" == $(this).attr("type")) {
                $("input:radio[name='" + $(this).attr('name') + "']").attr('checked', false);
            }else {
                $(this).val("");
            }
        });
        $('#div1').find("input[type='checkbox']").each(function () {
            $(this).attr('checked', false);
        });
        $('#div1').find("input[type='radio']").each(function () {
            $(this).attr('checked', false);
        });
    }
    //初始化树操作
    function initGroupTree() {

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
        })
        $.fn.zTree.init($("#groups"), setting, zNodes);

        function onClick(event, treeId, treeNode, clickFlag) {
            $('#groupName').val(treeNode.name);
            if (treeNode.id != 0) {
                groupId = treeNode.id;
            }else {
                groupId = "";
            }
            hideTree();
            initSelect();
        }


    }

    //区域外点击事件
    function onBodyDownByActionType(event) {
        if (event.target.id.indexOf('groups') == -1){
            if(event.target.id != 'selectDevType'){
                hideTree();
            }
        }
    }

    //下拉框显示 隐藏
    $("#groupName").bind("click", function () {
        if($('.ztree').css('display') == 'none'){
            $('.ztree').css('display','block');
        } else{
            $('.ztree').css('display','none');
        }
        $("body").bind("mousedown", onBodyDownByActionType);
    })

    function hideTree() {
        $('.ztree').css('display','none');
        $("body").unbind("mousedown", onBodyDownByActionType);
        return false;
    }


</script>
</html>
