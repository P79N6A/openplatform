$(document).ready(function(){
debugger
    //页面左侧通知中心菜单点击事件
    $(".menu-list").find("a").on("click",function(){
        $(".menu-list").find("a").removeClass("active");
        $(this).addClass("active");//被点击的组激活
        var menuList = $(".menu-list").find("a");
        if(menuList[0].className=="active"){
            loadAllTitle();//点击第一组“所有公告”，显示所有通知题目列表
        }else{
            var groupId = $(this).data("groupid");//获取被点击的组的Id
            console.info(groupId);
            refreshRightList(groupId);//刷新右侧内容，显示指定组通知题目列表
        }
    });

    //页面右侧通知标题列表点击事件（未来事件）
    $("#inform_content").on("click","#title-list li a", function(){
        //$(".msg-content").find("a").removeClass("active");
        //$(this).addClass("active");//被点击的组激活
        var informId = $(this).data("informid");//获取被点击的组的Id
        console.info(informId);
        refreshRightContent(informId);//刷新右侧内容（显示通知具体内容）

    });

    //页面左侧通知中心菜单初始化
    var menuList = $(".menu-list").find("a");
    if(menuList != undefined && menuList.length > 0){
        var activeList = $(".menu-list").find("a.active");
        if(activeList == undefined || activeList.length == 0){//如果没有一组被激活，则默认第一组被激活
            $(menuList[0]).addClass("active");
        }
    //页面右侧通知标题初始化
    //var activeGroup = $($(".menu-list").find("a.active")[0]);
    //var groupId = activeGroup.data("groupid");
    //console.info(groupId);
    //refreshRightList(groupId);//刷新右侧内容
    //     loadAllTitle();//初始显示所有通知标题

        if (document.getElementById("inform_one").innerHTML =="") {//如果inform_one里面没有内容，证明是正常进入，否则是由主页点击新闻直接跳入
            loadAllTitle();//则初始显示所有通知标题
        }
    }
    
    setTimeout(detail,500);
    function detail(){
	     if(nd!=""){
	   	 refreshRightContent(nd);
	    }
   }
   

});

function refreshRightList(groupId){//刷新右侧通知标题列表
    $.ajax({
		 type:"post",
		 dataType:"json",
		 url:"inform/loadInformTitle/" + groupId, //请求Controller
		 success:function(data) {
             var informDetails = null;
             if (data) {
                 informDetails = data.informDetails;
             }
             var titleList = "<ul class='' id='title-list'>";
             if (informDetails && informDetails.length > 0) {
                 for (var i = 0; i < informDetails.length; i++) {
                     var detail = informDetails[i];
                     titleList += "<li class='msg-title'>";
                     titleList += "<a class='' data-informid=" + detail.id + '>';
                     titleList +=  detail.informTitle;
                     titleList += "</a>";
                     titleList += "<span>" + detail.createDate +'</span>';
                     titleList += "</li>";
                 }
                 titleList += "</ul>";
                 $("#inform_content").html(titleList);//写入标题列表
             }
         }
	 })
}



function refreshRightContent(informId){//刷新右侧通知内容（用于未来事件）
    $.ajax({
        type:"post",
        dataType:"json",
        url:"inform/loadInformContent/" + informId, //请求Controller
        success:function(data) {
            var informDetail = null;
            if (data) {
                informDetail = data.informDetail;
            }
            var informContent;
            if (informDetail) {
                informContent = "<section>";
                informContent += "<h2 style='text-align: center'>" + informDetail.informTitle + "</h2>";
                informContent += "<p style='font-size: 15px'>" + informDetail.informContent + "</p>";
                informContent += "</section>";
                $("#inform_content").html(informContent);//写入内容
            }
        }
    })
}

function loadAllTitle(){//显示所有通知标题列表（首次载入页面时）
    $.ajax({
        type:"post",
        dataType:"json",
        url:"inform/loadAll", //请求Controller，加载所有消息
        success:function(data) {
            var informDetails = null;
            if (data) {
                informDetails = data.informDetails;
            }
            var titleList = "<ul class='' id='title-list'>";
            if (informDetails && informDetails.length > 0) {
                for (var i = 0; i < informDetails.length; i++) {
                    var detail = informDetails[i];
                    titleList += "<li class='msg-title'>";
                    titleList += "<a class='' data-informid=" + detail.id + '>';
                    titleList +=  detail.informTitle;
                    titleList += "</a>";
                    titleList += "<span>" + detail.createDate +'</span>';
                    titleList += "</li>";
                }
                titleList += "</ul>";
                $("#inform_content").html(titleList);//写入标题列表
            }
        }
    })
}

