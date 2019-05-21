$(document).ready(function() {

    //左侧菜单初始化
    var menuList = $(".menu-list").find("a");
    if(menuList != undefined && menuList.length > 0) {
        var activeList = $(".menu-list").find("a.active");
        if (activeList == undefined || activeList.length == 0) {//如果没有被激活的，则第一项自动激活
            $(menuList[0]).addClass("active");
        }
    }


    //api菜单下拉菜单控制
    $(".menu-drop-down").find("ul").css("display", "none");

    // 取消事件冒泡
    $('span.menu-select').click(function (event) {
        event.stopPropagation();
        // 按钮的toggle,如果div是可见的,点击按钮切换为隐藏的;如果是隐藏的,切换为可见的。
        $(".menu-drop-down").find("ul").toggle('fast');
        return false;
    });

    //点击空白处隐藏弹出层，下面为滑动消失效果和淡出消失效果。
    $(document).click(function (event) {
        var _con = $(".menu-drop-down");  // 设置目标区域
        if (!_con.is(event.target) && _con.has(event.target).length === 0) { // Mark 1
            //$('#divTop').slideUp('slow');  //滑动消失
            $(".menu-drop-down").find("ul").hide(200);     //淡出消失
        }
    });

    //点击事件，被点击到的组展开
    $("div.menu-name").click(function(event) {
        if($(this).has("ul")) {
            event.stopPropagation(); //阻止冒泡事件
            $(this).children("ul").toggle("false");
        }
    });

    // 点击事件，被点击到的组激活高亮
    $(".menu-list").find("a").bind("click",function(){
        $(".menu-list").find("a").removeClass("active");//所有组移除激活状态
        $(this).addClass("active");//激活被点击的组
    });

    // $(".menu-list").find("a").on("click",function(){
    //     //$(".menu-list").find("li").removeClass("active");
    //     //$(".menu-list").find("li").style.backgroundColor="#ffffff";
    //     $(".menu-list").find("a").css({'background-color':'#ffffff'})
    //     $(this).css({'color':'#000000'})
    //     //$(this).addClass("active");
    //     //$(this).style.backgroundColor="#0851fc";
    //     $(this).css({'background-color':'#0851fc'})
    //     $(this).css({'color':'#'})
    //
    //
    // });

});


//刷新右侧内容
function refresh(thisObj,nnum){
    // if(thisObj.className != "active"){

    // var tabObj = $(".menu-ll");
    // // console.log(tabObj);
    // // debugger;
    // var tabList =tabObj.children("li");
    // debugger;
    for( var i=0; i<10; i++)
    {
        if (i == nnum)
        {
            // tabList[i].className = "active";
            document.getElementById("Content"+i).style.display = "block";
        }else{
            // tabList[i].className = "normal";
            document.getElementById("Content"+i).style.display = "none";
        }
    }
}