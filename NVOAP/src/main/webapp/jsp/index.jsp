<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/common/resource.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电动汽车开放平台</title>
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/iconfont/iconfont.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part1.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part2.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part3.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/header.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/footer.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/style.css" %> type="text/css">
<script src=<%= request.getContextPath() + "/js/index.js" %> type="text/javascript"></script>
<style type="text/css">
.carousel-caption{
	right: 20%;
    left: 10%;
    width:420px;
    height:150px;
    /* bottom:40px; */
    top:20%;
}
</style>
</head>
<body>
	<%@ include file="/common/header.jsp" %>
	<!--滚动图片 -->
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="2000">
	    <!-- Indicators -->
	    <ol class="carousel-indicators">
	        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
	        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
	    </ol>
	
	    <!-- Wrapper for slides -->
	    <div class="carousel-inner" role="listbox">
	        <div class="item active">
	            <img src="../images/index/first.jpg" alt="...">
	            <div class="carousel-caption">
	                <h1 style="padding-right:14%">开放商业思维，连接企业价值</h1>
					<ul type="disc">
						<li>API开放</li>
						<li>场景开放</li>
						<li>云环境集成</li>
						<li>技术资源</li>
					</ul>
	            </div>
	        </div>
	        <div class="item">
	            <img src="../images/index/second.jpg" alt="...">
	            <div class="carousel-caption">
	                ...
	            </div>
	        </div>
	    </div>
	
	    <!-- Controls -->
	    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
	        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
	        <span class="sr-only">Previous</span>
	    </a> 
	    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
	        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
	        <span class="sr-only">Next</span>
	    </a>
	</div>
	<div class="main-content1">
		<!-- <div class="business">
			<h1>业务场景解决方案</h1>
			<h5>深度开放B2B业务场景，完整输出整套解决方案，实现ISV可以“拎包入住”</h5>
			<div class=""></div>
		</div> -->
		<div class="develop">
			<div class="develop-main">
				<div>
					<div class="develop-bg">DEVELOP</div>
					<h1>API开发能力</h1>
					<h5>模块化的接口集合，清晰的接口分类，让开发伙伴对于平台基础接口一目了然</h5>
				</div>
				<div style="margin:0 auto;width:700px">
					<a class="develop-item " href="../api/apiInfo?groupId=40289422668f536e01668f54cd440003" target="_blank" style="font-size:50px">
						<i class="iconfont icon-ego-vip"></i>
						<p>用户</p>
					</a>
					<a class="develop-item " href="../api/apiInfo?groupId=40289422668f536e01668f55d04a0005" target="_blank" style="font-size:50px">
						<i class="iconfont icon-quanbudingdan1"></i>
						<p>订单</p>
					</a>
					<a class="develop-item " href="../api/apiInfo?groupId=40289422668f536e01668f56054c0007" target="_blank" style="font-size:50px">
						<i class="iconfont icon-jiage"></i>
						<p>支付</p>
					</a>
					<a class="develop-item " href="#" target="_blank" style="font-size:50px">
						<i class="iconfont icon-search"></i>
						<p>搜索</p>
					</a>
				</div>
			</div>
		</div>
		<!-- <div class="success">
			<div class="success-main">
				<div class="success-main-text">
					<div class="success-bg" >SUCCESS</div>
					<h2>成功案例</h2>
					<p>千万中小企业，因你而改变！<br> 以开放的心态，迎接全球服务开发者。</p>
					<p>已合作服务商 <em >1600+</em></p>
				</div>
			</div>
			<div class="success-main-item">
				<div class="success-item">
					<div class="success-item-image">
						<img src="https://img.alicdn.com/tfs/TB1GjgeihTI8KJjSspiXXbM4FXa-108-108.jpg">
					</div>
					<h3>深圳市华通易点信息技术有限公司</h3>
					<p>深圳市华通易点信息技术有限公司是国内首个专注于跨平台多任务网店管理的电商技术及应用服务提供商。公司以技术为本，自2010年起，致力于服务众多网商快捷方便地管理不同电商平台上的业务，享受电商带来的乐趣。核心产品有：甩手工具箱，易掌柜，甩手铺货等</p>
				</div>
				<div class="success-item">
					<div class="success-item-image">
						<img src="https://img.alicdn.com/tfs/TB1TpXairYI8KJjy0FaXXbAiVXa-108-108.jpg">
					</div>
					<h3>济南唯慕信息技术有限公司</h3>
					<p>济南唯慕信息技术有限公司创立于2013年，是最早一批入驻阿里开放平台的服务商之一，秉承提供最优质的服务和体验为宗旨，主要为客户提供流量推广、企业通等软件应用服务。</p>
				</div>
				<div class="success-item">
					<div class="success-item-image">
						<img src="https://img.alicdn.com/tfs/TB1Qv.vilHH8KJjy0FbXXcqlpXa-108-108.jpg">
					</div>
					<h3>北京其乐融融科技有限公司</h3>
					<p>北京其乐融融科技有限公司成立于2010年，是国内最早一批开发电商应用软件的企业。现已入驻1688、淘宝等10多个主流电商平台，其产品“快递打印助手”专注打单发货，服务50W+用户，致力于为用户提供专业的软件及技术支持。</p>
				</div>
			</div>
		</div> -->
	</div>
	<%@ include file="/common/footer.jsp" %>
</body>
</html>