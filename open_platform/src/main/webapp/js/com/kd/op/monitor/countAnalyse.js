var graphChart;
$(document).ready(function(){
    $("#search-btn").bind("click",function(){
        var api = $("#search_user").selectpicker("val");
        if(api == null || api == undefined || api == ""){
            slowNotify("请先选择一个用户","warning");
            return false;
        }
        getCountAnalyse();
        // $("#emptyDiv").css("display","none");
    });
    $("#reset-btn").bind("click",function(){
        $('#groupText').val('');
        $('#search_api').html("");
        $('#search_api').append("<option value=''>请选择用户</option>")
        $('#search_api').selectpicker('val', '');
        $('#search_api').selectpicker('refresh');
        timeInit();
        getAPICount();
    });
    graphChart = echarts.init(document.getElementById('graph'));  //初始化

    getCountAnalyse();
});
//获取统计分析数据
function getCountAnalyse(){
    var userId = $("#search_user").selectpicker('val');
    if(userId == "" || userId == null){
        quickNotify("请先选择用户","warning");
        return;
    }
    $.ajax({
        async : false,   //设置为false。请求为同步请求
        cache:false,   	//不设置缓存
        type: 'post',
        dataType : "json",
        data:{
            userId:userId,
        },
        url: "monitor.do?getGraphData",//后台处理程序,获取显示数据
        error: function () {//请求失败处理函数
            return false;
        },
        success:function(nodes){ //请求成功后处理函数。
            var legend = ["用户","应用","分组","服务"];
            var categorys = [{name:"用户"},{name:"应用"},{name:"分组"},{name:"服务"}];
            var datas = new Array();
            var links = new Array();
            var num = 0;
            for(var i in nodes){
                var node = nodes[i];
                var data = {};
                data.name = node.name;
                data.value = node.value / 5;
                var category = 0;
                if(node.name.indexOf("app") != -1){
                    category = 1;
                }else if(node.name.indexOf("group") != -1){
                    category = 2;
                }else if(node.name.indexOf("api") != -1){
                    category = 3;
                }
                data.category = category;
                data.symbolSize = node.value * 2;
                data.id = node.name;
                // data.appId = node.temp;

                var tooltip = {};
                tooltip.formatter = /*categorys[category] + "-" + */node.alias;
                data.tooltip = tooltip;
                datas.push(data);

                if(node.target != null && node.target != undefined){
                    var link = {};
                    link.source = node.name;
                    // var prefix = "user";
                    // if(node.name.split("_")[0] == "group"){
                    //     prefix = "app";
                    // }else if(node.name.split("_")[0] == "api"){
                    //     prefix = "group"
                    // }
                    var targetIndex = 0;
                    for(var j in nodes){
                        if(nodes[j].name.split("_")[1] == node.target.split("_")[1]){
                            targetIndex = j;
                            break;
                        }
                    }
                    link.target =  node.target;
                    // link.value = node.value * 3;
                    links.push(link)
                }

                num++;
            }
            var option = {
                legend: {
                    show:true,
                    left:"left",
                    top:"center",
                    itemGap:30,
                    orient:"vercital",
                    data:legend
                },
                animationDuration: 1500,
                animationEasingUpdate: 'quinticInOut',
                lineStyle: {
                    normal:{
                        color: '#000',
                        width:3,
                        // curveness: 0.1
                    },
                    emphasis:{
                        color:"green"
                    }
                },
                series: [{
                    type: 'graph',
                    layout: 'force',
                    label: {
                        normal: {
                            show: true,
                            position: 'top',//设置label显示的位置
                            textStyle: {
                                fontSize: '12rem'
                            },
                            formatter: function(value,params){
                                return value.data.tooltip.formatter;
                            }
                        }
                    },
                    symbolSize:function(value,params){
                        switch (params.data.category) {
                            case 0:return 50;break;
                            case 1:return 30;break;
                            case 2:return 20;break;
                            default:return 10;
                        };
                    },
                    draggable: true,
                    data:datas,
                    force: {
                        edgeLength: 100,
                        repulsion: 500,
                        gravity: 0.1
                    },
                    links:links,
                    categories: categorys,
                    roam: true,
                    focusNodeAdjacency: true,
                    itemStyle: {
                        normal: {
                            borderColor: '#fff',
                            borderWidth: 1,
                            // shadowBlur: 10,
                            // shadowColor: 'rgba(0, 0, 0, 0.3)'
                        }
                    },
                    lineStyle: {
                        normal:{
                            // color: 'source',
                            width:2,
                            // curveness: 0.1
                        },
                        emphasis:{
                            color:"green"
                        }
                    },
                    /* emphasis: {
                         lineStyle: {
                             width: 10
                         }
                     }*/
                }]
            };
            graphChart.setOption(option);
            graphChart.on("click",function(params,a){
                var node = params.data;
                var array = node.name.split("_");
                var type = array[0];
                if(type == "user"){
                    quickNotify("请点击非用户节点","warning");
                    return;
                }
                var id = array[array.length - 1];
                var appId = array[1];
                bootOpenLook("节点统计详情","monitor.do?countNodeData&type=" + type + "&id=" + id + "&appId=" + appId,"wide");
            })
        }
    })
}