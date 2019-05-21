<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<base href=" <%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- <%@ include file="/common/resource.jsp"%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电动汽车开放平台</title>
<link rel="stylesheet" href=<%= request.getContextPath() + "/plugin/bootstrap/css/bootstrap.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/iconfont/iconfont.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">

<link rel="stylesheet" href=<%= request.getContextPath() + "/css/copystyle.css" %> type="text/css">

<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part1.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part2.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part3.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/header.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/style.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/api.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plugin/bootstrap/css/bootstrap-table.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plugin/jquery-plugs/treegrid/jquery.treegrid.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plugin/bootstrap-treeview/bootstrap-treeview.min.css" %> type="text/css">

<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap-table.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap-table-zh-CN.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap-table-treegrid.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/jquery-plugs/treegrid/jquery.treegrid.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/bootstrap-treeview/bootstrap-treeview.js" %> type="text/javascript"></script>
<script>
var nd="${nd}";



</script>
<script src=<%= request.getContextPath() + "/js/inform.js" %> type="text/javascript"></script>
<style>
.search-wrap {
    background-color: #343d4e;
    height: 122px;
    position: relative;
}
.sub-thumb {
    position: absolute;
    cursor: pointer;
    width: 48px;
    height: 48px;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAeCAYAAABE4bxTAAAAAXNSR…Q7xIHRTh2GYdCZoW2xsT2HOEOPQb9SLpfHXNed53jg2j8jDblN4aT9/QAAAABJRU5ErkJggg==) no-repeat 50%;
    background-size: 20px;
    z-index: 99999;
    transition: all .5s cubic-bezier(.4,0,.2,1);
}
.nav-search {
    width: 614px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
}
.nav-search .search-field {
    position: relative;
}

.nav-search .search-input {
    width: 100%;
    height: 40px;
    outline: none;
    padding: 0 10px;
    border-radius: 4px;
    border: 1px solid #fff;
}
button, input {
    overflow: visible;
}
.nav-search .search-btn {
    width: 100px;
    height: 40px;
    line-height: 40px;
    background-color: #269bff;
    color: #fff;
    font-size: 16px;
    border: none;
    position: absolute;
    right: -2px;
    top: 0;
    outline: none;
    border-radius: 0 4px 4px 0;
}
.nav-search .search-hot-word {
    display: -ms-flexbox;
    display: flex;
    font-size: 14px;
    margin-top: 8px;
}
.nav-search .search-hot-word span {
    color: #999;
}
a:visited {
    color: #1270a8;
}
a:link {
    color: #269bff;
}
.nav-search .search-hot-word ul {
    display: -ms-flexbox;
    display: flex;
}
ol, ul {
    list-style: none;
}
.menu-list ul, li {
    margin: 0;
    padding-left: 10px;
    list-style: none;
}
.wenzi{
  color: #269bff;
}
</style>






</head>
<body style="background-color:#F5F5F5;padding-top:0px">
<%@ include file="/common/tb/header_only.jsp" %>
	<!-- <iframe src="../common/tb/header.jsp" width="100%" height="100%" style="overflow-y:none;height:50px;"></iframe> -->
	<div class="search-wrap" data-spm-anchor-id="a219a.7386797.0.i1.314c669aS4yCiH">
	<div class="sub-thumb"></div>
		<div class="nav-search">
			<div class="search-field" >

				<input class="search-input" placeholder="全站搜索" id="__text__input__search" value data-spm-anchor-id="a219a.7386797.0.i0.314c669aS4yCiH">
					<button class="search-btn">搜索</button>

			</div>
		<div class="search-hot-word">
			<span style="margin-top: 1px;">搜索热词   :</span>
				<ul>
					<li><p class="wenzi">电动汽车</p></li>
					<li><p class="wenzi">科东电力</p></li>
					<li><p class="wenzi">能力开放</p></li>
				</ul>

		</div>
	</div>
</div>
	
	
	<div class="content api-content">
		<div class="side-menu">
			<div class="menu-content">
				<span class="menu-select">
					<span class="iconfont icon-31leimu">通知中心</span>
				</span>
				<div class="menu-list">
					<ul class="">
						<li>
							<a class="">
								<div class="menu-container">
									<div class="menu-name">所有公告</div>
								</div>
							</a>
						</li>
                        <c:forEach var="informGroup" items="${informGroups}">
                            <li>
                                <a class="" data-groupid=${informGroup.id}>
                                    <div class="menu-container">
                                        <div class="menu-name" title="${informGroup.groupName}">${informGroup.groupName}</div>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
						<!--<li>
							<a class="" href="#" >
								<div class="menu-container">
									<div class="menu-name">能力发布</div>
								</div>
							</a>
						</li>
						<li>
							<a class="" href="#" >
								<div class="menu-container">
									<div class="menu-name">维护公告</div>
								</div>
							</a>
						</li>
						<li>
							<a class="" href="#" >
								<div class="menu-container">
									<div class="menu-name">协议变更</div>
								</div>
							</a>
						</li>-->
					</ul>
				</div>
			</div>
		</div>
		<div class="main-content">
			<div class="msg-content" id="inform_content">
					<section>
						<h2 id="inform_one" style="text-align: center">${informDetail.informTitle}</h2>
						<p style="font-size: 15px">${informDetail.informContent}</p>
					</section>
			</div>
		</div>
	</div>
	<%@ include file="/common/tb/footer.jsp" %>
</body>
<script>
$(function(){
	
	$('.search-btn').click(function(){
		var valz=$("#__text__input__search").val();
		window.location.href="/searchform/inform?valz="+valz;
      
   });
	
})

</script>
</html>