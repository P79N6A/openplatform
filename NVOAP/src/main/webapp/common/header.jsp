<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- <%@ include file="/common/resource.jsp"%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href=<%= request.getContextPath() + "/iconfont/iconfont.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part1.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part2.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part3.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/header.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/style.css" %> type="text/css">
<script type="text/javascript">
	$(document).ready(function(){
		$("a.docs").mouseover(function(){
			$(".docs-list").find("ul").css("display","block");
		})
		$(document).mouseover(function(event){
			var docsDisplay = $(".docs-list").find("ul").css("display");
			if(docsDisplay != "none"){
				var _con = $(".docs-list");  // 设置目标区域
				var tgt = $("a.docs");
				if(!_con.is(event.target) && _con.has(event.target).length === 0
						&& !tgt.is(event.target) && tgt.has(event.target).length === 0){ // Mark 1
					$(".docs-list").find("ul").css("display","none");
				}
			}
		});
	})
</script>
</head>
<body>
	<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
	    <div class="container">
			<div class="ocms-fusion-1688-pc-aop-new-header b2b-ocms-fusion-comp aop-header"
				data-reactroot="">
					<div class="aop-header-inner">
						<h1 class="aop-header-title aop-header-inline">
							<a href="#">
								电动汽车开放平台
							</a>
							<p>
								NVOAP
							</p>
						</h1>
						<ul class="aop-header-nav aop-header-inline">
							<li>
								<a href="#" class="aop-header-current">
									首页
								</a>
							</li>
							<li>
								<a href="#" class="">
									解决方案
								</a>
							</li>
							<li data-reactid="11">
								<a href="#" class="">
									开发指南
								</a>
							</li>
							<li>
								<a class="docs" style="cursor: pointer;">
									技术文档
								</a>
								<i class="next-icon next-icon-arrow-down next-icon-xs aop-header-dropmenu-status">
								</i>
							</li>
						</ul>
						<div class="aop-header-input-container aop-header-inline">
							<span class="next-input next-input-single next-input-medium" >
								<input type="text" value="" height="100%">
							</span>
							<i class="next-icon next-icon-search next-icon-medium">
							</i>
						</div>
						<div class="aop-header-extra aop-header-inline">
							<a href="#" class="aop-header-message-notice-wrapper">
								<i class="aop-header-icon icon-notice">
								</i>
							</a>
							<a href="#">
								支持中心
							</a>
							<a class="aop-header-btn" href="#">
								控制中心
							</a>
						</div>
					</div>
				</div>
	    </div>
	    <div class="docs-list">
			<ul>
				<li>API文档</li>
				<li>消息文档</li>
			</ul>
		</div>
	</nav>
</body>
</html>