$(document).ready(function(){
 if(nd!="-18438671903") {
			
	 $(".menu-list").find("a").removeClass("active");
		$("a[data-apiid="+ nd+"]").addClass("active");
		 //刷新右侧内容
		 refreshRightContent(nd); 
}
debugger
	//api菜单下拉菜单控制
	$(".menu-drop-down").find("ul").css("display","none");
	$('span.menu-select').click(function(event) {
		// 取消事件冒泡
		event.stopPropagation();
		// 按钮的toggle,如果div是可见的,点击按钮切换为隐藏的;如果是隐藏的,切换为可见的。
		$(".menu-drop-down").find("ul").toggle('fast');
//		$('#hideDiv').show()
		return false;
	}); 
	//点击空白处隐藏弹出层，下面为滑动消失效果和淡出消失效果。
	 $(document).click(function(event){
		 var _con = $(".menu-drop-down");  // 设置目标区域
		 if(!_con.is(event.target) && _con.has(event.target).length === 0){ // Mark 1
			//$('#divTop').slideUp('slow');  //滑动消失
			$(".menu-drop-down").find("ul").hide(200);     //淡出消失
		 }
	 });
	 
	 //左侧api接口点击事件
	 $(".menu-list").find("a").bind("click",function(){
	 debugger
		 $(".menu-list").find("a").removeClass("active");
		 $(this).addClass("active");
		 //刷新右侧内容
		 var apiId = $(this).data("apiid");//取数据
		 refreshRightContent(apiId);
	 });
	 
	 
	 
	 
	 $("#return-table").bootstrapTable({
	    //url: "loadReturnParams/" + apiId,
	    striped: true,
	    sidePagenation: 'server',
	    idField: 'id',
	    columns: [{
	        field: 'id',
	        visible:false
	    },{
	        field: 'paramName',
	        title: '名称'
	    },{
	        field: 'dataType',
	        title: '类型'
	    },{
	        field: 'paramDesc',
	        title: '描述'
	    },{
	        field: 'defaultValue',
	        title: '示例值'
	    }],
	    treeShowField: 'paramName',
	    parentIdField: 'pId',
	    onLoadSuccess: function(data) {
	        $("#return-table").treegrid({
	            initialState: 'collapsed',
	            //收缩                       
	            treeColumn: 0,//指明第几列数据改为树形                        
	            expanderExpandedClass: 'glyphicon glyphicon-chevron-down',                        
	            expanderCollapsedClass: 'glyphicon glyphicon-chevron-right',                        
	            onChange: function() {                            
	            	$("#return-table").bootstrapTable('resetWidth');                        
	            }                    
	        });                
	     },
	     onLoadError:function(data){
	    	 console.info(data)
	     }
	});
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
	
	 //服务分组下拉选项点击事件
	 $(".menu-drop-down li").bind("click",function(){
		 var groupId = $(this).data("groupId");
		 console.info(groupId);
		 window.location.href = "api/apiInfo?groupId=" + groupId;
	 });
	 
	
	 
	 
});


function refreshRightContent(apiId){
	$.ajax({
		 type:"post",
		 dataType:"json",
		 url:"api/loadApiInfo/" + apiId,
		 success:function(data){
			 var headerParams = null;
			 var requestParams = null;
			 var apiInfo = null;
			 var testPrefix = null;
             var officialPrefix = null;

			 if(data){
				 headerParams = data.headerParams;
				 requestParams = data.requestParams;
				 apiInfo = data.apiInfo;
				 testPrefix = data.testPrefix;//测试环境前缀
                 officialPrefix = data.officialPrefix;//正式环境前缀
			 }
             var apiTable;
             var test = "https://" + testPrefix.ip + ":" + testPrefix.port + "/protocolTrans/openapi/";
             var official = "https://" + officialPrefix.ip + ":" + officialPrefix.port + "/protocolTrans/openapi/";
			 if(apiInfo){
                 apiTable = "<tr><td>API名称：</td><td>" + apiInfo.apiName + "</td></tr>" +
					        "<tr><td>描述：</td><td>" + apiInfo.apiDesc + "</td></tr>" +
                            "<tr><td>请求方式：</td><td>HTTP方式</td></tr>" +
                            "<tr><td>HTTPS请求地址（测试环境）：</td><td>" + test + apiInfo.reqAddrHttp + "</td></tr>" +
                            "<tr><td>HTTPS请求地址（正式环境）：</td><td>" + official + apiInfo.reqAddrHttp + "</td></tr>";

                 /*var mod;
                 if(apiInfo.pubMode != 1) {
                     mod = "HSF方式";
                     apiTable += "<td>" + mod + "</td></tr>" +
                                 "<tr><td>HSF请求地址：</td><td>" + apiInfo.reqAddrHsf + "</td></tr>" +
                                 "<tr><td>类名：</td><td>" + apiInfo.apiClassName + "</td></tr>" +
                                 "<tr><td>方法名：</td><td>" + apiInfo.apiMethodName + "</td></tr>" +
                                 "<tr><td>HSF分组：</td><td>" + apiInfo.hsfGroup + "</td></tr>" +
                                 "<tr><td>版本信息：</td><td>" + apiInfo.version + "</td></tr>";
                 } else{
                     mod = "HTTP方式";
                     apiTable += "<td>" + mod + "</td></tr>" +
                                 "<tr><td>HTTP请求地址：</td><td>" + apiInfo.reqAddrHttp + "</td></tr>";
                 }*/
                 //$("#pub-mode").html(mod);//请求方式
                 //$("#api-name").html(apiInfo.apiName);//接口名称
                 //$("#msg-tips").html(apiInfo.apiDesc);//描述
                 //$("#api-classname").html(apiInfo.apiClassName);//类名
                 //$("#api-methodname").html(apiInfo.apiMethodName);//方法名
                 //$("#req-addr-hsf").html(apiInfo.reqAddrHsf);//HSF请求地址
                 //$("#hsf-group").html(apiInfo.hsfGroup);//HSF分组
                 //$("#version").html(apiInfo.version);//HSF版本信息
                 //$("#req-addr-http").html(apiInfo.reqAddrHttp);//HTTP请求地址
				 //$("#args-demo").html(apiInfo.examData);//示例
			 } else{
                 apiTable = "<tr><td colspan='4' class='no-data' style='vertical-align: middle;'>没有数据</td></tr>";
			 }
             $("#api-table").html(apiTable);//写API基本信息表

			 var headerHtml = "<tr><th>名称</th><th>类型</th><th>是否必须</th><th>描述</th></tr>";
			 if(headerParams && headerParams.length > 0){
				 for(var i = 0;i < headerParams.length;i++){
					 var header = headerParams[i];
					 var need = "是";
					 if(header.paramNeed == 0){
						 need = "否";
					 }
					 headerHtml += "<tr>";
					 headerHtml += "<td>" + header.paramName + "</td><td>" + header.dataTypeName + "</td><td>" + need + "</td><td>" + header.paramDesc + "</td>";
					 headerHtml += "</tr>";
				 }
			 }else{
				 headerHtml += "<tr><td colspan='4' class='no-data' style='vertical-align: middle;'>没有数据</td></tr>";
			 }
			 $("#args-table").html(headerHtml);//写入系统级输入参数表
			 
			 var requestHtml = "<tr><th>名称</th><th>类型</th><th>是否必须</th><th>描述</th><th>示例值</th></tr>";
			 if(requestParams && requestParams.length > 0){
				 for(var i = 0;i < requestParams.length;i++){
					 var req = requestParams[i];
					 var need = "是";
					 if(req.paramNeed == 0){
						 need = "否";
					 }
					 requestHtml += "<tr>";
					 requestHtml += "<td>" + req.paramName + "</td><td>" + req.dataTypeName + "</td><td>" + need + "</td><td>" + req.paramDesc + "</td><td>" + req.defaultValue + "</td>";
					 requestHtml += "</tr>";
				 }
			 }else{
				 requestHtml += "<tr><td colspan='5' class='no-data' style='vertical-align: middle;'>没有数据</td></tr>";
			 }
			 $("#app-table").html(requestHtml);//写入应用级输入参数表
		 }
	 });
	var opt = {
        url: "api/loadReturnParams/" + apiId
    };
    $("#return-table").bootstrapTable('refresh', opt);

}