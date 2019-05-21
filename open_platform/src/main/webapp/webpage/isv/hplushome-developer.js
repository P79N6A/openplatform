$(document).ready(function(){
    var option = {
        title : {
        },
        color:["#70b23d","#cc161e","#ccb923"],
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            top:"center",
            data: ['正常','暂停','报警']
        },
        series : [
            {
                name: 'ISP数量',
                type: 'pie',
                radius : '80%',
                center: ['50%', '50%'],
                data:[
                    {value:41, name:'正常'},
                    {value:2, name:'暂停'},
                    {value:0, name:'报警'},
                ],
                // itemStyle: {
                //     emphasis: {
                //         shadowBlur: 10,
                //         shadowOffsetX: 0,
                //         shadowColor: 'rgba(0, 0, 0, 0.5)'
                //     }
                // }
            }
        ]
    };
    // var ispPie = echarts.init(document.getElementById('ispPie'));
    // ispPie.setOption(option);


    option = {
        title : {
        },
        color:["#70b23d","#cc161e","#ccb923"],
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            top:"center",
            data: ['正常','暂停','报警']
        },
        series : [
            {
                name: 'ISV数量',
                type: 'pie',
                radius : '80%',
                center: ['50%', '55%'],
                data:[
                    {value:41, name:'正常'},
                    {value:0, name:'暂停'},
                    {value:2, name:'报警'},
                ],
                // itemStyle: {
                //     emphasis: {
                //         shadowBlur: 10,
                //         shadowOffsetX: 0,
                //         shadowColor: 'rgba(0, 0, 0, 0.5)'
                //     }
                // }
            }
        ]
    };
    var isvPie = echarts.init(document.getElementById('isvPie'));
    isvPie.setOption(option);

    //服务部分图
    option = {
        title : {
        },
        color:["#70b23d","#cc161e","#ccb923"],
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        grid: {
            left: '1%',
            right: '2%',
            bottom: '3%',
            top:"10%"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            top:"center",
            data: ['在运','暂停']
        },
        series : [
            {
                name: 'API数量',
                type: 'pie',
                radius : '80%',
                center: ['50%', '55%'],
                data:[
                    {value:95, name:'在运'},
                    {value:1, name:'暂停'},
                ],
                // itemStyle: {
                //     emphasis: {
                //         shadowBlur: 10,
                //         shadowOffsetX: 0,
                //         shadowColor: 'rgba(0, 0, 0, 0.5)'
                //     }
                // }
            }
        ]
    };
    var servicePie = echarts.init(document.getElementById('servicePie'));
    servicePie.setOption(option);

    option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        color:["#A559F3"],
        grid: {
            left: '1%',
            right: '2%',
            bottom: '10%',
            top:"20%",
            containLabel: true
        },
        xAxis:  {
            type: 'value',
            name:"次数"
        },
        yAxis: {
            type: 'category',
            name:"排名",
            data: [/*'第10','第9','第8','第7','第6',*/'第5','第4','第3','第2','第1']
        },
        series: [
            {
                name: 'API调用排名',
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [/*1775,1852,1986,2098,2241,*/264,296,325,356,427]
            }
        ]
    };
    var serviceBar = echarts.init(document.getElementById('serviceBar'));
    serviceBar.setOption(option);

    //指定图标的数据和配置项
    var date = new Date(); //日期
    var xIndex = [];
    var appNum = [];
    var appFlow = [];
    for (var i = 0; i <= date.getHours(); i++) {
        xIndex.push(i + ":00");
        if(i == 0){
            appNum.push(17);
            appFlow.push(5);
        }else{
            appNum.push(appNum[i - 1] + 46);
            appFlow.push(appFlow[i - 1] + 38);
        }
    }
    option={
        tooltip : {
            trigger : 'axis',
            position : function(pt) {
                return [ pt[0], '10%' ];
            }
        },
        grid: {
            left: '1%',
            right: '2%',
            bottom: '3%',
            top:"20%",
            containLabel: true
        },
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
            name: '应用次数',
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
            data: appNum
        }]
    };
    var appNumLine = echarts.init(document.getElementById('appNumLine'));  //初始化
    appNumLine.setOption(option);

    option={
        tooltip : {
            trigger : 'axis',
            position : function(pt) {
                return [ pt[0], '10%' ];
            }
        },
        grid: {
            left: '1%',
            right: '2%',
            bottom: '3%',
            top:"20%",
            containLabel: true
        },
        xAxis : {
            type : 'category',
            name: '时间(h)',
            boundaryGap : false,
            data : xIndex
        },
        yAxis : [{
            type : 'value',
            name: 'MB',
            minInterval:1,
        }],
        series : [{
            name: "应用流量",
            type: 'line',
            smooth: true,
            symbol: 'none',
            /*itemStyle: {
                normal: {
                    color: '#E51A08'
                }
            },*/
            data: appFlow
        }]
    };
    var appFlowLine = echarts.init(document.getElementById('appFlowLine'));  //初始化
    appFlowLine.setOption(option);
});
//应用部分图形
function intToTwoStr(num){
    if(num < 10){
        return "0" + num;
    }
    return num;
}
function getToday(){
    var date = new Date();
    var day = intToTwoStr(date.getDate());   //28
    var month = intToTwoStr(date.getMonth()+1);   //4  + 1
    var today = date.getFullYear()+"-"+month+"-"+day;
    return today;
}