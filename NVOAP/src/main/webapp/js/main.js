$(document).ready(function(){

    //通知栏初始化
	refreshNotice();

    //新闻动态初始化
	refreshNews();

	//播放轮播图
	autoMove();
});

function refreshNotice(){
    $.ajax({
        type:"post",
        dataType:"json",
        url:"inform/loadAll", //请求Controller
        success:function(data) {
            var informDetails = null;
            if (data) {
                informDetails = data.informDetails;
            }
            var noticeList = "";
            if (informDetails && informDetails.length > 0) {
                for (var i = 0; i < 3; i++) { //只显示3条消息
                    var detail = informDetails[i];
                    noticeList += "<li>";
                    noticeList += "<a href='inform/quickView?informId=" + detail.id + "'>";
                    noticeList +=  detail.informTitle;
                    noticeList += "<span>" + detail.createDate +'</span>';
                    noticeList +=  "<img class='icon-new' src='images/tb/new.png'>";
                    noticeList += "</a>";
                    noticeList += "</li>";
                }
                $("#notice_list").html(noticeList);//写入标题列表
            }
        }
    })
}




//轮播图函数
function autoMove() {
    var imgs = document.getElementById("slider").children;//定位所有轮播图片
    var spans = document.getElementById("slider-nav").children;//定位所有轮播指示点

    //每次轮播后样式初始化
    function InitMove(index) {
        for (var i = 0; i < imgs.length; i++) {
            imgs[i].style.opacity = '0';//初始化图片透明度，0为不可见
            spans[i].style.background = '#999'; //初始化下方按钮背景色
        }
        imgs[index].style.opacity = '1';//改变指定标签图片透明度，1为可见
        spans[index].style.background = '#fff';//改变下方按钮背景色，白色
    }


    InitMove(0);//第一次初始化

    var count = 1;//count记录轮播序号

    function fMove() { //播放函数
        if (count == imgs.length) {
            count = 0;
        }
        InitMove(count);
        count++;
    }

    function play() {
        var scollMove = setInterval(fMove, 2500);//设置自动轮播定时器；
    }

    play();//自动播放

}

function refreshNews(){ //刷新新闻动态模块
    $.ajax({
        type:"post",
        dataType:"json",
        url:"news/loadAll", //请求Controller
        success:function(data) {
            var newsDetails = null;
            if (data) {
                newsDetails = data.newsDetails;
            }
            var newsList = "";
            if (newsDetails && newsDetails.length > 0) {
                for (var i = 0; i < 3; i++) { //只显示3条消息
                    var detail = newsDetails[i];
                    newsList += "<li>";
                    newsList += "<a href='news/loadNewsContent?newsId=" + detail.id + "' style='color: black; text-decoration: none '>";
                    newsList += "<h2>" + detail.newsTitle  + "</h2>"
                    newsList += "<span >" + detail.createTime + "</span>";
                    newsList += "</a>";
                    newsList += "</li>";
                }
                $("#news_list").html(newsList);//写入标题列表
            }
        }
    })
}