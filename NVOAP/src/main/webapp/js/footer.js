$(document).ready(function(){

    //公告栏初始化
	refreshFooterNotice();
});

function refreshFooterNotice(){
    $.ajax({
        type:"post",
        dataType:"json",
        url:"inform/loadAll", //请求Controller
        success:function(data) {
            var informDetails = null;
            if (data) {
                informDetails = data.informDetails;
            }
            var footerNoticeList = "";
            if (informDetails && informDetails.length > 0) {
                for (var i = 0; i < 4; i++) { //只显示4条消息
                    var detail = informDetails[i];
                    footerNoticeList += "<li>";
                    footerNoticeList += "<a target = '_blank' data-informid=" + detail.id + '>';
                    footerNoticeList +=  detail.informTitle;
                    footerNoticeList += "<span>" + detail.createDate +'</span>';
                    footerNoticeList += "</a>";
                    footerNoticeList += "</li>";
                }
                $("#footer_notice_list").html(footerNoticeList);//写入标题列表
            }
        }
    })
}
