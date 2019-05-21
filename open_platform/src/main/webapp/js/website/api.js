var dataTypes = null;
$(document).ready(function(){
    initWidth();
    $(window).resize(function() {
        initWidth();
    });
    if(isMobile()){
        $("body").css("zoom",0.4);
    }

    function initWidth(){
        var containerWidth = $(".content").width();
        containerWidth = containerWidth.toString().replace("px","");
        var marginLeft = $(".content").css("margin-left");
        var marginRight = $(".content").css("margin-left");
        marginLeft = marginLeft.toString().replace("px","");
        marginRight = marginRight.toString().replace("px","");
        var totalWidth = parseInt(containerWidth) + parseInt(marginLeft) + parseInt(marginRight);
        $('.header,body').css('width',totalWidth + "px");
    }
    //树状图展示
    getTree();

	//点击空白处隐藏弹出层，下面为滑动消失效果和淡出消失效果。
	 $(document).click(function(event){
		 var _con = $(".menu-select1");  // 设置目标区域
		 if(!_con.is(event.target) && _con.has(event.target).length === 0){ // Mark 1
			hideDIV();
		 }
	 });
    initTreeGrid("app-table");
    initTreeGrid("return-table");
});
//封装树形数据
function buildTree(parentNode, datas) {
    for (var key in datas) {
        var data = datas[key];
        if (data.pId == parentNode.id) {
            var node = {text: data.name, id: data.id, nodes: [], selectable: true};
            node["state.selected"] = data["state.selected"];
            parentNode.nodes.push(node);
            buildTree(node, datas);
        }
    }

    if (parentNode.nodes.length == 0) {
        delete parentNode.nodes;
    }
}
//形成树形结构
function getTree() {
    $.ajax({
        url: "apiGroupController.do?loadAll", // 请求的URL
        dataType: 'json',
        type: "post",
        success: function (data) {
            var tree = {text: '能力中心', id: '0', nodes: []};
            buildTree(tree, data);
            $('#groupTree').treeview({
                color: "#428bca",
                data: [tree],
                showCheckbox: false,
                multiSelect: false,
                onNodeSelected : function (event, data) {
					$("#knowledgeText").val(data.text);
                    initApiList(data.id);
					//重新渲染选中分组的样式
                    $("#groupTree").find("li").each(function(){
                    	$(this).removeClass("selected-node");
                    	if($(this).attr("id") == data.id){
                    		$(this).removeClass("node-selected");
                    		if(!$(this).hasClass("selected-node")){
                                $(this).addClass("selected-node");
							}
						}
					})
                },
                onNodeUnselected:function (event, data) {
                    hideDIV();
                }
            });
			//选中第一个分组
			if(data != null && data.length > 1){
                var firstGroup = data[1];
                $("#knowledgeText").val(firstGroup.name);
                var firstId = firstGroup.id;
                initApiList(firstId);
			}
        }
    });
}
function initApiList(groupId){
    $.ajax({
        type:"post",
        dataType:"json",
        data:{
            groupId:groupId
        },
        url:"openHome.do?loadApiByGroup",
        success:function(data){
            if(data){
                var html = "";
                $.each(data,function(i,api){
                    html += "<li><a style='cursor:pointer' data-apiid=" + api.id + "><div class='menu-container'>" +
                        "<div class='menu-name' title='" + api.name + "'>" + api.name + "</div>" +
                        "</div></a></li>"
                });
                $(".api-list").html(html);
            }
            hideDIV();
            initApiClick();
            //左侧api接口显示初始化
            var menuList = $(".menu-list").find("a");
            if(menuList != undefined && menuList.length > 0){
                var activeList = $(".menu-list").find("a.active");
                if(activeList == undefined || activeList.length == 0){//如果没有被激活的，则第一项自动激活
                    $(menuList[0]).addClass("active");
                }
                //页面右侧内容初始化
                var activeApi = $($(".menu-list").find("a.active")[0]);
                var apiId = activeApi.data("apiid");
                refreshRightContent(apiId);
            }
        }
    });
}
//左侧api接口点击事件
function initApiClick(){
    $(".menu-list").find("a").bind("click",function(){
        $(".menu-list").find("a").removeClass("active");
        $(this).addClass("active");
        //刷新右侧内容
        var apiId = $(this).data("apiid");
        refreshRightContent(apiId);
    });
}

function hideDIV() {
    $("#hideDiv").hide();
}
//区域外点击事件
function onBodyDownByActionType(event) {
    if (event.target.id.indexOf('hideDiv') == -1){
        // if(event.target.id != 'selectDevType'){
            hideDIV();
        // }
    }
}
function refreshRightContent(apiId){
	$.ajax({
		 type:"post",
		 dataType:"json",
		 url:"openHome.do?loadApi",
		 data:{
		 	apiId:apiId
		 },
		 success:function(data){
			 var headerParams = new Array();
			 var requestParams = new Array();
			 var returnParams = new Array();
			 var apiInfo = {};
			 if(data){
				 headerParams = data.headerParams;
				 requestParams = data.requestParams;
                 returnParams = data.returnParams;
				 apiInfo = data.apiInfo;
                 dataTypes = data.dataTypes;
			 }
			 if(apiInfo){
				 $("#msg-tips").html(apiInfo.apiDesc);
				 $("#args-demo").html(apiInfo.examData);
			 }else{
				 $("#msg-tips").html("");
				 $("#args-demo").html("");
			 }
             // initTreeGrid("args-table",apiId,2);
             refreshTreeGrid("app-table",apiId,0);
             refreshTreeGrid("return-table",apiId,1);
		 }
	 })
}
function refreshTreeGrid(gridId,apiId,dataType){
    var opt = {
        url: "openHome.do?loadParams&apiId=" + apiId + "&dataType=" + dataType
    };
    $("#" + gridId).bootstrapTable('refresh', opt);
}
function initTreeGrid(gridId){
    $("#" + gridId).bootstrapTable({
        striped:true,
        sidePagenation:'server',
        idField:'id',
        treeShowField: 'paramName',
        parentIdField: 'pId',
        // rowAttributes: function (row, index) {
        //     return {
        //         xx:index
        //     };
        // },
        columns: [{
            field: 'id',
            visible:false
        },{
            field: 'paramName',
            title: '名称'
        },{
            field: 'dataType',
            title: '类型',
            formatter:function(value,row,index){
                if(dataTypes == null){
                    return "";
                }
                return dataTypes[value + ""];
            }
        },{
            field: 'paramDesc',
            title: '描述',
            width:'50%'
        },{
            field: 'defaultValue',
            title: '示例值'
        }],
        onLoadSuccess: function(data) {
            $("#" + gridId).treegrid({
                initialState: 'collapsed',
                //收缩
                treeColumn: 0,//指明第几列数据改为树形
                expanderExpandedClass: 'glyphicon glyphicon-chevron-down',
                expanderCollapsedClass: 'glyphicon glyphicon-chevron-right',
                onChange: function() {
                    $("#" + gridId).bootstrapTable('resetWidth');
                }
            });
        }
    });
}