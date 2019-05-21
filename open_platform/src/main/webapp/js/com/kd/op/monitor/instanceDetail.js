$(document).ready(function(){
    var date = new Date(); //日期
    var xIndex = [];
    var cpu = [];
    var memory = [];
    var io = [];
    var ioUse = [];
    $("#count").width('100%');
    for (var i = 0; i <= date.getHours(); i++) {
        xIndex.push(i + ":00");
        cpu.push(0.65);
        memory.push(27.56);
        io.push(2.35);
        ioUse.push(24.86);
    }

    var dom1 = document.getElementById('count');
    var dom2 = document.getElementById('responseTimeLength');
    var dom3 = document.getElementById('successRate');
    var dom4 = document.getElementById('flowSize');
    //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
    var resizeMainContainer = function () {
        var a = 0.45;
        var b = 0.45;
        dom1.style.width = $(".modal-dialog").width() * a +'px';
        dom1.style.height = $(".modal-dialog").width() *b+'px';

        dom2.style.width = $(".modal-dialog").width() * a +'px';
        dom2.style.height = $(".modal-dialog").width() *b+'px';

        dom3.style.width = $(".modal-dialog").width() * a +'px';
        dom3.style.height = $(".modal-dialog").width() *b+'px';

        dom4.style.width = $(".modal-dialog").width() * a +'px';
        dom4.style.height = $(".modal-dialog").width() *b+'px';
    };
    //设置div容器高宽
    resizeMainContainer();
    // 初始化图表
    var count = echarts.init(dom1);  //初始化
    var responseTimeLength = echarts.init(dom2);  //初始化
    var successRate = echarts.init(dom3);  //初始化
    var flowSize = echarts.init(dom4);  //初始化
    $(window).on('resize',function(){//
        //屏幕大小自适应，重置容器高宽
        resizeMainContainer();
        count.resize();
    });

    var option={
        tooltip : {
            trigger : 'axis',
            position : function(pt) {
                return [ pt[0], '10%' ];
            }
        },
        title : {
            left : 'center',
            text : 'CPU使用率',
        },
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
            data : xIndex
        },
        yAxis : [{
            type : 'value',
            name: '%',
            minInterval:1
        }],
        series : [{
            name: '调用量',
            type: 'line',
            smooth: false,
            symbol: 'none',
            /* areaStyle: {normal: {
                opacity:0.5
            }}, */
            itemStyle: {
                normal: {
                    color: '#5155f9'
                }
            },
            data: cpu
        }]
    };
    count.setOption(option);
    window.addEventListener("resize", function () {
        option.chart.resize();
    });

    option.title.text = "内存使用率";
    option.yAxis[0].name = "%";
    option.series[0].data = memory;
    responseTimeLength.setOption(option);

    option.title.text = "磁盘IO读";
    option.yAxis[0].name = "千字节/秒";
    option.series[0].data = io;
    successRate.setOption(option);

    option.title.text = "磁盘使用率";
    option.yAxis[0].name = "%";
    option.series[0].data = ioUse;
    flowSize.setOption(option);
});