<%--
  Created by IntelliJ IDEA.
  User: wangkun
  Date: 2016/4/23
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>能力开放平台</title>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>

    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/bootstrap/css/bootstrap.css" %> type="text/css">
    <link href="plug-in-ui/hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome.min.css" />
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome-ie7.min.css" />
    <link href="plug-in-ui/hplus/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="plug-in/hplus/smartMenu.css" rel="stylesheet">
<style type="text/css">
.page-tabs a{
	color:#999
}
.page-tabs a:hover{
	color:#f2f2f2;
	background:#24b7a1;
}
.page-tabs a.active:hover{
	background: #24b7a1;
   	color: #ffffff;
}
.page-tabs a.active{
	background: #00CAAB;
   	color: #ffffff;
   	font-weight:bold;
}
.page-tabs a.active i:hover {
   	background: #24b7a1;
   	color: #c00;
}
.navbar-fixed-top, .navbar-static-top,nav.page-tabs{
	background:#ffffff;
}
.dropdown-menu li a{
	border:0px
}
.nav-header{
	padding:0px 0px;
}
</style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden"
	onload="dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);">
<div id="wrapper">
    <nav class="navbar-default navbar-static-side" role="navigation" style="z-index: 1991;">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header" style="">
                    <div class="dropdown profile-element" style="align:center;">
                        <span><img alt="image" style="width:100%;"  src="plug-in/login/images/jeecg-aceplus.png" /></span>
                    </div>
                    <div class="logo-element">OPEN
                    </div>
                </li>
                <t:menu style="hplus" menuFun="${menuMap}"></t:menu>
            </ul>
        </div>
    </nav>
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0;min-height:40px;background-image: url(plug-in/login/images/bj.png);background-size: 100% 100%">
                <div class="navbar-header" style="height: 40px;">
                	<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " style="margin-top:10px" href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" style="height:30px" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group" style="margin-top:11px">
                            <img alt="image" style="" class="img-circle1" src="plug-in/login/images/system-name.png" />
                        </div>
                    </form>
                </div>
                <ul class="nav navbar-top-links navbar-right" style="height:40px">
                    <li class="dropdown" onfocus="bindFrameClick()">
                    	<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#" style="padding:10px 10px;min-height:30px;">
                                <span title="${realRoleName}"><strong class="font-bold">${userName }</strong></span>
                                <%-- <span title="${realRoleName}">${roleName }<b class="caret"></b></span> --%>
                                <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <%--<li>
                                <a href="javascript:changeOwnPwd()">
                                    <t:mutiLang langKey="common.change.password"/>
                                </a>
                            </li>--%>
                            <li><a href="javascript:openUserInfo()">个人信息</a></li>
                        </ul>
                    </li>
                    
                      <li class="dropdown">
                     	<a href="javascript:logout()" style="padding:10px 10px;min-height:30px;max-height:30px;" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
                     </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs" style="border-bottom:solid 1px #00CAAB">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="loginController.do?hplushome">首页</a>
                </div>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            
        </div>
        <div class="row J_mainContent" id="content-main" style="margin-left:-13px;height:calc(100% - 83px)">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="loginController.do?hplushome" frameborder="0" data-id="loginController.do?hplushome" seamless></iframe>
        </div>
    </div>
</div>

<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-dialog.js" %> type="text/javascript"></script>
<script src="plug-in-ui/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="plug-in-ui/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<script src="plug-in-ui/hplus/js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="plug-in/hplus/jquery-smartMenu.js"></script>
<script type="text/javascript" src="plug-in/hplus/contabs.js"></script>
<t:base type="tools"></t:base>
<script src="plug-in-ui/hplus/js/plugins/pace/pace.min.js"></script>
<script src="plug-in-ui/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>

<script type="text/javascript" src="plug-in/hplus/hplus-tab.js"></script>

<script type="text/javascript">
var isAdmin = <%=request.getAttribute("isAdmin")%>;
</script>
<script>
	function openNewTab(url,title){
		openTab(url,title);
	}
    function logout(){
        layer.confirm('您确定要注销吗？', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            location.href="loginController.do?logout";
        }, function(){
            return;
        });
    }
    function clearLocalstorage(){
        var storage=$.localStorage;
        if(!storage)
            storage=$.cookieStorage;
        storage.removeAll();
        //bootbox.alert( "浏览器缓存清除成功!");
        layer.msg("浏览器缓存清除成功!");
    }
    function toJeecgYun(){
    	window.open("http://yun.jeecg.org","_blank");
    }

    $(document).ready(function(){
    	//DwrMessagePush.sendMessageAuto();
    	/* var isAdmin = ${isAdmin};
    	var isAudit = ${isAudit};
    	if(isAdmin == true || isAudit == true){
    		var url = "userController.do?getExceptionList";
    		if(isAudit == true){
    			url = "audit.do?getExceptionList";
    		}
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:url,
    			data:{
    				isAdmin:isAdmin,
    				isAudit:isAudit
    			},
    			success:function(data){
    				//循环弹出异常告警
    				for(var i = 0;i < data.length;i++){
    					var exception = data[i];
    					var id = exception.id;
    					var dialog = new BootstrapDialog({
        	                title: exception.title,
        	                type:BootstrapDialog.TYPE_DEFAULT,
        	                message: function(dialog) {
        	                	var createUser = exception.createUser;
        	                	if(createUser == null || createUser == "null"){
        	                		createUser = "无";
        	                	}
        	                    var $message = $("<div><input type='hidden' value='" + id + "' /><table class='table'><tr><td style='width:19%'>操作人：</td><td>" + createUser +"</td></tr>"+
        	                    		"<tr><td>操作时间：</td><td>" + changeDateFormat(exception.createTime) +"</td></tr>"+
        	                    		"<tr><td>操作时间：</td><td>" + exception.exceptionContent +"</td></tr>"+
        	                    		"</table></div>");
        	                    return $message;
        	                },
        	                closable: false,
        	                buttons: [{
        	                    label: '确认',
        	                    cssClass: 'btn-primary',
        	                    action: function(dialog){
        	                        confirmException(dialog);
        	                    }
        	                }]
        	            });
        	            dialog.open();
    				}
    			}
    		}); 
    	} */
    });
    
    function confirmException(dialog){
    	var isAdmin = ${isAdmin};
    	var isAudit = ${isAudit};
    	var id = $(dialog.$modalBody[0].innerHTML).find("input").val();
    	var url = "userController.do?updateExceptionRead";
    	if(isAudit == true){
    		url = "audit.do?updateExceptionRead";
    	}
    	$.ajax({
			type:"post",
			dataType:"json",
			url:url,
			data:{exceptionId:id},
			success:function(data){
				dialog.close();
			}
		}); 
    }
    function changeDateFormat(cellval) {
        var dateVal = cellval + "";
        if (cellval != null) {
            var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            
            var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            
            return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
        }
    }
    
    function showMessage(data){
    	alert(data.message);
    	window.location.href="loginController.do?logout";
    }
    
    function receiveMessages(autoMessage){
    	$.messager.alert('消息',autoMessage.message,'info');
    	var oldIp = obj.ip;
    	if(oldIp != null && oldIp != ""){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:"logController.do?saveClientLog&ip=" + obj.ip + "&sessionId=" + obj.sessionId,
    			success:function(data){
    			}
    		});
    	}
    }
    dwr.engine._errorHandler = function(message,ex){
    	dwr.engine._debug;
    }
    function changeOwnPwd(){
    	var dialog = new BootstrapDialog({
            title: '修改密码',
            type:BootstrapDialog.TYPE_DEFAULT,
            message: function(dialog) {
                var $message = $("<div></div>");
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': 'systemController.do?changePwd'
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                action: function(dialogRef){
                	changePassword(dialog);
                }
            }, {
                label: '取消',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
        dialog.open();
    }
    
    function openUserInfo(){
    	var dialog = new BootstrapDialog({
            title: '个人信息',
            type:BootstrapDialog.TYPE_DEFAULT,
            message: function(dialog) {
                var $message = $("<div></div>");
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': 'userController.do?showUserInfo'
            },
            buttons: [{
                label: '关闭',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
        dialog.open();
    }

    function goAllNotice(){
        var addurl = "noticeController.do?noticeList";
        createdetailwindow("公告", addurl, 800, 400);
    }

    function goNotice(id){
        var addurl = "noticeController.do?goNotice&id="+id;
        createdetailwindow("通知公告详情", addurl, 750, 600);
    }

    /* function goAllMessage(){
    	var dialog = new BootstrapDialog({
            title: '异常详情',
            type:BootstrapDialog.TYPE_DEFAULT,
            size:"size-wide",
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            data: {
                'pageToLoad': 'exception.do?exception'
            },
            buttons: [{
                label: '关闭',
                cssClass: 'btn-default',
                action: function(dialogRef){
                	dialogRef.close();
                }
            }]
        });
        dialog.open();
    } */

    function goMessage(id){
        var title = $("#"+id+"_title").val();
        var content = $("#"+id+"_content").val();
        $("#msgId").val(id);
        $("#msgTitle").html(title);
        $("#msgContent").html(content);
        var status = $("#"+id+"_status").val();
        if(status==1){
            $("#msgStatus").html("未读");
        }else{
            $("#msgStatus").html("已读");
        }

        $('.theme-popover-mask').fadeIn(100);
        $('.theme-popover').slideDown(200);
    }

    function readMessage(){
        var msgId = $("#msgId").val();
        var url = "tSSmsController.do?readMessage";
        $.ajax({
            url:url,
            type:"GET",
            dataType:"JSON",
            data:{
                messageId:msgId
            },
            success:function(data){
                if(data.success){
                    $("#msgStatus").html("已读");
                    $("#"+msgId+"_status").val('2');
                }
            }
        });
    }

    //个人信息弹出层回缩
    function frameBodyClick(){ 
		$(".count-info").attr("aria-expanded","false").parent().removeClass("open");
	}
    //新增iframe中绑定click事件回调父级函数
    function bindFrameClick(){
    	$(".J_iframe").contents().find("body").attr("onclick", "parent.frameBodyClick()"); 
    }
</script>
</body>

</html>
