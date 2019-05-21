<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/webpage/common/websiteResources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智慧车联网能力开放平台</title>
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/iconfont/iconfont.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/tb.css" %> type="text/css">
<%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/media.css" %> type="text/css">--%>
<script src=<%= request.getContextPath() + "/js/website/index.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/js/website/common.js" %> type="text/javascript"></script>
	<style>
		.card-content p{
			/*font-weight:bold;*/
		}
		.resolve-content li{
			margin-top: 20px;
			background-color: #fff;
			margin-left: 10px;
			margin-right: 10px;
			margin-bottom:10px;
		}
		.card-item-intro{
			font-size:normal;
		}
		.resolve-content,.flow-content{
			width: 1240px;
			margin: 0 auto;
		}
	</style>
	<script>
		$(document).ready(function(){
			if(isMobile()){
                $("#phone-div").show();
                $("#pc-div").hide();
                var windowWidth = $(window).width();
                $(".header").find(".container-content").width(windowWidth);
                $("#banner-btn-div").css("left",((windowWidth-52)/2) + "px");
                // $("#banner-btn-div").css("top",((bannerHeight-16)/2) + "px");
            }else{
                initWidth();
                // $(window).resize(function() {
                //     initWidth();
                // });
                $("#phone-div").hide();
                $("#pc-div").show();
            }
            function initWidth(){
                var containerWidth = $(".container-content").width();
                containerWidth = containerWidth.toString().replace("px","");
                var marginLeft = $(".container-content").css("margin-left");
                var marginRight = $(".container-content").css("margin-left");
                marginLeft = marginLeft.toString().replace("px","");
                marginRight = marginRight.toString().replace("px","");
                var totalWidth = parseInt(containerWidth) + parseInt(marginLeft) + parseInt(marginRight);
                $('.banner,.header,body').css('width',totalWidth + "px");
                // $('.header').css('width',totalWidth + "px");
				//处理banner中的按钮位置
				var bannerWidth = $("#slider").find("li").width();
				var bannerHeight = $("#slider").find("li").height();
				$("#banner-btn").css("left",((bannerWidth-172)/2) + "px");
                $("#banner-btn").css("top",((bannerHeight-132)/2) + "px");
			}
		})
	</script>
</head>
<body style="background-color:#F5F5F5;width:100%;">
	<div id="phone-div" >
		<div class="header" style="height:30px;">
			<%@ include file="phone-header.jsp" %>
		</div>
		<div class="container" style="margin-left:0;margin-right:0px;padding:0;width:100%;">
			<div class="col-sm-12" style="margin:0;padding:0;">
				<img src="images/website/all.jpg" style="width:100%"/>
			</div>
		</div>
		<div id="banner-btn-div" style="position: absolute;top:95px;">
			<a href="apiLogin.do?login" target="_blank" style="vertical-align:middle;">
				<img src="images/website/banner-btn.png"
					 style="width:52px;height:16px;position: absolute" alt="">
			</a>
		</div>
	</div>
	<div id="pc-div" style="">
		<div class="header">
			<%@ include file="header.jsp" %>
		</div>
		<%-- banner开始 --%>
		<div class="banner">
			<ul class="slider" id="slider">
				<li style="display: list-item;;">
					<div>
						<img src="images/website/banner1.jpg" alt="">
					</div>
					<%--<div>--%>
					<a href="apiLogin.do?login" target="_blank" style="vertical-align:middle;">
						<img id="banner-btn" src="images/website/banner-btn.png"
							 style="width:172px;height:52px;position: absolute" alt="">
					</a>
					<%--</div>--%>
				</li>
				<%--<li style="display: list-item;">--%>
				<%--<a href="#" target="_blank" >--%>
				<%--<img src="../images/website/banner2.jpg" alt="">--%>
				<%--</a>--%>
				<%--</li>--%>
			</ul>
			<%--<ul class="slider-nav" id="slider-nav">--%>
			<%--<li class=""></li>--%>
			<%--<li class="on"></li>--%>
			<%--</ul>--%>
		</div>
		<%-- banner结束 --%>
		<%-- 平台特色 开始 --%>
		<div class="container-content">
			<h2 class="pannel-title">平台特色</h2>
			<div class="open-pannel">
				<ul>
					<li>
						<div class="card-content">
							<img src="images/website/nengli.png" >
							<div class="card-item-title">能力强大 持续开放</div>
							<p class="card-item-intro">提供业务、数据、资源等多种能力开放，并持续拓展智慧车联网的能力开放范围。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/jieru.png" >
							<div class="card-item-title">一站接入 便捷灵活</div>
							<p class="card-item-intro">将需求对接、接口设计、开发测试、上线部署、应用发布、运营迭代统一集成至能力开放平台，提供线上一站式服务，快捷合作，降本增效。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/fuwu.png" >
							<div class="card-item-title">服务之本 开放共赢</div>
							<p class="card-item-intro">强大运营团队支持与合作伙伴的定制化开放合作，融合创新。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/kaifang.png" >
							<div class="card-item-title">技术领航 安全保障</div>
							<p class="card-item-intro">基于“云平台+微服务” 互联网架构，具备协议转换、流量控制、权限分配、安全加密等强大技术支撑，实现能力开放安全、可视、可控。</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<%-- 开放能力 结束 --%>
		<%-- 省级应用 开始 --%>
		<div class="resolve-content">
			<div class="container-content">
				<h2 class="pannel-title">开放场景</h2>
				<h2 style="text-align: center;font-size:24px;">省级应用</h2>
				<ul class="resolve-list clearfix" style="background-color:#DAE2F3;">
					<li class="merch-operation" style="background: url(images/website/shudihua.png) no-repeat top">
						<div class="card-content">
						</div>
					</li>
					<li class="">
						<div class="card-content">
							<div class="card-item-title">充电</div>
							<div class="card-item-content">实现地图找桩、启停充电等业务功能的集成</div>
						</div>
					</li>
					<li class="merch-operation" style="background: url(images/website/neiwaiwang.png) no-repeat top">
						<div class="card-content">
						</div>
					</li>
					<li class="">
						<div class="card-content">
							<div class="card-item-title">属地化定制</div>
							<div class="card-item-content">为大客户提供业务和数据能力，支撑合资公司业务拓展</div>
						</div>
					</li>
					<li class="merch-operation" style="background: url(images/website/chongdian.png) no-repeat top">
						<div class="card-content">
						</div>
					</li>
					<li class="">
						<div class="card-content">
							<div class="card-item-title">内外网贯通</div>
							<div class="card-item-content">支撑信息内外网的业务和数据贯通，实现系统间集成</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<%-- 省级应用 结束 --%>
		<%-- 行业应用 开始 --%>
		<div class="container-content" style="margin-bottom:30px;">
			<h2 class="pannel-title">开放场景</h2>
			<h2 style="text-align: center;font-size:24px;">行业应用</h2>
			<div class="open-pannel hangye">
				<ul>
					<li>
						<div class="card-content">
							<img src="images/website/nengli.png" >
							<div class="card-item-title">车辆运营管理平台</div>
							<p class="card-item-intro">打通车辆行驶数据与充电数据，综合电池信息、行驶习惯、驾驶路线、充电数据共同挖掘用户行为特征，为用户提供个性化充电服务、车辆保养服务。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/jieru.png" >
							<div class="card-item-title">充电桩生产企业</div>
							<p class="card-item-intro">开放充电桩运行、运维数据，帮助充电桩企业利用故障告警等异常信息判断充电桩设计制造需提升的部分，提高充电桩设计制造质量，利用评价信息，提高充电桩的用户体验。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/fuwu.png" >
							<div class="card-item-title">车辆租赁平台</div>
							<p class="card-item-intro">共享电动汽车分时租赁平台的车辆运行信息、停车站点信息，开放充电等业务能力，拓宽双方业务渠道，提升租车用户的充电便利性。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/kaifang.png" >
							<div class="card-item-title">地图服务商</div>
							<p class="card-item-intro">开放充电站点位置及状态等数据能力，共享车辆驾驶路线及周边商业服务信息，利用地图服务商渠道进行引流，提升充电体验的同时，获取更多用户。</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<%-- 行业应用 结束 --%>
		<%-- 解决方案省级应用 开始 --%>
		<div class="container-content" style="margin-bottom:30px;">
			<h2 class="pannel-title">应用案例</h2>
			<h2 style="text-align: center;font-size:24px;">省级应用</h2>
			<div class="open-pannel hangye">
				<ul>
					<li>
						<div class="card-content">
							<img src="images/website/shandonggongjiao.png" >
							<div class="card-item-title">山东公交小工具</div>
							<p class="card-item-intro">数据能力开放的典型应用，开放订单数据、用户数据、支付数据，为山东合资公司大客户提供属地化运营支撑。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/yuexing.png" >
							<div class="card-item-title">渝e行</div>
							<p class="card-item-intro">业务能力开放的典型应用，开放用户管理、找桩充电，为e充电、e约车在重庆地区的子品牌建设提供支撑。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/95598.png" >
							<div class="card-item-title">国网客服中心业务系统</div>
							<p class="card-item-intro">数据能力开放的典型应用，开放工单数据，实现国网电动汽车与国网客服中心的内外网数据双向贯通。</p>
						</div>
					</li>
					<li>
						<div class="card-content">
							<img src="images/website/huandianzhankong.png" >
							<div class="card-item-title">地市公司换电站站控系统</div>
							<p class="card-item-intro">业务能力开放的典型应用，开放资产管理、订单管理，接入内网换电站管理平台资产、订单。</p>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<%-- 解决方案省级应用 结束 --%>
		<%-- 接入流程 开始 --%>
		<div class="flow-content">
			<div class="container-content" style="padding-left:100px;padding-right:100px;">
				<h2 class="pannel-title">接入流程</h2>
				<h2 style="text-align: center;font-size:24px;">ISP(能力提供者)</h2>
				<ul class="resolve-list clearfix">
					<li>
						<div class="card-content" style="height:232px;">
							<div class="card-item-index">①</div>
							<div class="card-item-title">注册授权</div>
							<div class="card-item-content">填写表单注册成为开放平台的发布者</div>
						</div>
					</li>
					<li>
						<div class="card-content" style="height:232px;">
							<div class="card-item-index">②</div>
							<div class="card-item-title">能力发布准备</div>
							<div class="card-item-content">维护计费策略、流量控制策略、安全控制策略</div>
						</div>
					</li>
					<li>
						<div class="card-content" style="height:232px;">
							<div class="card-item-index">③</div>
							<div class="card-item-title">发布能力</div>
							<div class="card-item-content">填写能力信息，发布能力并申请审核</div>
						</div>
					</li>
					<li>
						<div class="card-content" style="height:232px;">
							<div class="card-item-index">④</div>
							<div class="card-item-title">创建业务场景</div>
							<div class="card-item-content">自由组合API，形成业务开放场景解决方案</div>
						</div>
					</li>
					<li>
						<div class="card-content" style="height:232px;">
							<div class="card-item-index">⑤</div>
							<div class="card-item-title">配合测试&迭代</div>
							<div class="card-item-content">配合ISV开发、测试，更新迭代接口</div>
						</div>
					</li>
				</ul>
				<h2 style="text-align: center;font-size:24px;margin-top: 40px;">ISV(应用开发者)</h2>
				<ul class="resolve-list clearfix">
					<li>
						<div class="card-isv">
							<div class="card-item-index">①</div>
							<div class="card-item-title">注册认证</div>
							<div class="card-item-content">填写表单注册成为开放平台的开发者</div>
						</div>
					</li>
					<li>
						<div class="card-isv">
							<div class="card-item-index">②</div>
							<div class="card-item-title">创建应用</div>
							<div class="card-item-content">当您完成入驻，选择应用类型进行创建应用</div>
						</div>
					</li>
					<li>
						<div class="card-isv">
							<div class="card-item-index">③</div>
							<div class="card-item-title">开发&测试</div>
							<div class="card-item-content">您需要订阅能力开始进行开发</div>
						</div>
					</li>
					<li>
						<div class="card-isv">
							<div class="card-item-index">④</div>
							<div class="card-item-title">发布上线</div>
							<div class="card-item-content">完成开发后发布到应用市场</div>
						</div>
					</li>
				</ul>
				<h2 style="text-align: center;font-size:24px;margin-top: 40px;">用户</h2>
				<ul class="resolve-list clearfix">
					<li>
						<div class="card-user">
							<div class="card-item-index">①</div>
							<div class="card-item-title">注册登录</div>
							<div class="card-item-content">填写表单注册成为开放平台的消费者</div>
						</div>
					</li>
					<li>
						<div class="card-user">
							<div class="card-item-index">②</div>
							<div class="card-item-title">订购应用</div>
							<div class="card-item-content">在应用市场选择应用场景订购应用</div>
						</div>
					</li>
					<li>
						<div class="card-user">
							<div class="card-item-index">③</div>
							<div class="card-item-title">应用部署授权</div>
							<div class="card-item-content">开放平台自动将应用部署至商户平台、移动终端或其他渠道</div>
						</div>
					</li>
					<li>
						<div class="card-user">
							<div class="card-item-index">④</div>
							<div class="card-item-title">使用应用反馈</div>
							<div class="card-item-content">用户登录应用使用应用</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<%-- 接入流程 结束 --%>
		<div class="footer" style="background-color:#000000;">
			<%@ include file="footer.jsp" %>
		</div>
	</div>
</body>
</html>