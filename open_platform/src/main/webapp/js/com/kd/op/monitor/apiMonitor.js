
$(document).ready(function(){
    timeInit();
    //加载应用信息
    $.post(
        "monitor.do?invokeAppDatagrid",
        function (data) {
            var htmlStr="<option value=\"\">全部</option>";
            for (var i=0; i<data.length; i++) {
                htmlStr+="<option value=\""+data[i].id+"\">"+data[i].appName+"</option>";
            }
            $('#appId').empty();
            $('#appId').html(htmlStr)
        },
        'json'
    );

    $("#search-btn").bind("click",function(){
        var appId = $("#appId").selectpicker("val");
        if(appId == null || appId == undefined || appId == ""){
            slowNotify("请先选择一个应用","warning");
            return false;
        }
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        getAPICount();
        $("#emptyDiv").css("display","none");
    });
    $("#reset-btn").bind("click",function(){
        $('#groupText').val('');
        $('#appId').html("");
        $('#appId').append("<option value=''>请选择应用</option>")
        $('#appId').selectpicker('val', '');
        $('#appId').selectpicker('refresh');
        timeInit();
        getAPICount();
    });
    getGroupTree();
});
function getGroupTree() {
    $.ajax({
        url: "apiGroupController.do?getGroupTree", // 请求的URL
        dataType: 'json',
        type: "post",
        // data: params,
        success: function (data) {
            $('#groups').treeview({
                color: "#428bca",
                data: data,
//              showCheckbox: true
                onNodeSelected: function (event, mdata) {
                    $("#groupText").val(mdata.text);
                    $("#hideDiv").hide();
                    var groupId = mdata.groupId;//这就是selected的值
                    $.ajax({
                        type : 'post',
                        url :"monitor.do?getApisByGroup",
                        data:{
                            groupId:groupId
                        },
                        dataType : 'json',
                        success : function(datas) {//返回list数据并循环获取
                            var select = $('#search_api');
                            $('#search_api').html("");
                            select.append("<option value=''>请选择服务</option>")
                            for (var i = 0; i < datas.length; i++) {
                                select.append("<option value='"+datas[i].id+"'>"
                                    + datas[i].name + "</option>");
                            }
                            $('#search_api').selectpicker('val', '');
                            $('#search_api').selectpicker('refresh');
                        }
                    });
                }
            });
        }
    });
}
//获取台区曲线信息
function getAPICount(){
    var count = echarts.init(document.getElementById('count'));  //初始化
    var responseTimeLength = echarts.init(document.getElementById('responseTimeLength'));  //初始化
    var successRate = echarts.init(document.getElementById('successRate'));  //初始化
    var flowSize = echarts.init(document.getElementById('flowSize'));  //初始化
    var api = $("#search_api").selectpicker('val');
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    $.ajax({
        async : false,   //设置为false。请求为同步请求
        cache:false,   	//不设置缓存
        type: 'post',
        dataType : "json",
        data:{
            apiId:api,
            start:startDate,
            end:endDate
        },
        url: "monitor.do?getApiMonitorData",//后台处理程序,获取显示数据
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
                    text : '调用量',
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
            count.setOption(option);

            option.title.text = "响应时间";
            option.yAxis[0].name = "毫秒";
            option.series[0].data = data.responseTimeLength;
            responseTimeLength.setOption(option);

            option.title.text = "正确率";
            option.yAxis[0].name = "%";
            option.series[0].data = data.successRate;
            successRate.setOption(option);

            option.title.text = "流量";
            option.yAxis[0].name = "kb";
            option.series[0].data = data.flowSize;
            flowSize.setOption(option);
        }
    })
}
function timeInit(){
    var date = new Date();
    var day = intToTwoStr(date.getDate());   //28
    var month = intToTwoStr(date.getMonth()+1);   //4  + 1
    var today = date.getFullYear()+"-"+month+"-"+day;
    $('#startDate,#endDate').datetimepicker({
        //lang:"ch",
        format:	'Y-m-d',
        timepicker:false,
        value:today
    });
    $.datetimepicker.setLocale('ch');
}