var graphChart;
$(document).ready(function(){
    //树状图展示
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPid: null
            }
        },
        callback: {
            onClick: zTreeOnClick,
        }
    };
    var orgList =[];
    $.ajax({
        async: false,
        type: "post",
        dataType: "json",
        url: "apiGroupController.do?loadAll",
        success: function (data) {
            orgList = data;
        }
    })
    //节点点击事件
    function zTreeOnClick(event, treeId, treeNode) {
        $('#orgName').val(treeNode.name);
        $('#orgCode').val(treeNode.id);
        hideTree();
        // 加载能力选择下拉列表
        refreshApi(treeNode.id);
    };
    //初始组织树状图
    $.fn.zTree.init($("#treeDemo"), setting, orgList);
    //下拉框显示 隐藏
    $("#orgName").bind("click", function () {
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
    //区域外点击事件
    function onBodyDownByActionType(event) {
        if (event.target.id.indexOf('treeDemo') == -1){
            if(event.target.id != 'selectDevType'){
                hideTree();
            }
        }
    }

    $("#search-btn").bind("click",function(){
        var api = $("#search_api").selectpicker("val");
        if(api == null || api == undefined || api == ""){
            slowNotify("请先选择一个能力","warning");
            return false;
        }
        getAPIGraph();
    });
    $("#reset-btn").bind("click",function(){
        $('#groupText').val('');
        $('#search_api').html("");
        $('#search_api').append("<option value=''>请选择能力</option>")
        $('#search_api').selectpicker('val', '');
        $('#search_api').selectpicker('refresh');
        getAPIGraph();
    });
    // var selectedGroup = $("#search_group").children('option:selected').val();
    refreshApi("");
    graphChart = echarts.init(document.getElementById('graph'));  //初始化
});
function refreshApi(groupId){
    $.ajax({
        type : 'post',
        url :"monitor.do?invokeApiDatagrid",
        data:{
            groupId:groupId
        },
        dataType : 'json',
        success : function(datas) {//返回list数据并循环获取
            var select = $('#search_api');
            $('#search_api').html("");
            for (var i = 0; i < datas.length; i++) {
                select.append("<option value='"+datas[i].id+"'>"
                    + datas[i].apiName + "</option>");
            }
            $('#search_api').selectpicker('val', '');
            $('#search_api').selectpicker('refresh');

            getAPIGraph();
        }
    });
}
//获取服务关系信息
function getAPIGraph(){
    var api = $("#search_api").children('option:selected').val();
    $.ajax({
        async : false,   //设置为false。请求为同步请求
        cache:false,   	//不设置缓存
        type: 'post',
        dataType : "json",
        data:{
            apiId:api,
        },
        url: "monitor.do?getApiGraph",//后台处理程序,获取显示数据
        error: function () {//请求失败处理函数
            return false;
        },
        success:function(nodes){ //请求成功后处理函数。
            var legend = ["能力","应用","用户"];
            var categorys = [{name:"能力"},{name:"应用"},{name:"用户"}];
            var datas = new Array();
            var links = new Array();
            var num = 0;
            for(var i in nodes){
                var node = nodes[i];
                var data = {};
                data.name = node.name;
                data.value = node.value / 5;
                var category = 0;
                var color = "red"
                if(node.name.indexOf("app") != -1){
                    color = "#d4cc17";
                    category = 1;
                }else if(node.name.indexOf("user") != -1){
                    color = "#2e26b8";
                    category = 2;
                }
                data.category = category;
                data.symbolSize = node.value * 2;
                data.id = node.name;
                // data.appId = node.temp;

                var tooltip = {};
                tooltip.formatter = /*categorys[category] + "-" + */node.alias;
                data.tooltip = tooltip;

                var itemStyle = {normal:{color:color}};
                // data.itemStyle = itemStyle;
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
                        color: '#48cc4e',
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
                            color: 'source',
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
        }
    })
}