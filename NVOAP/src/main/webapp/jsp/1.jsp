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
<title>Insert title here</title>
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part1.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part2.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/index-part3.css" %> type="text/css">
</head>
<body>
	<nav class="navbar navbar-default navbar-inverse navbar-fixed-top">
	    <div class="container">
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <h1 class="header-logo">
	            	<a class="navbar-brand" href="#">车联网开放平台</a>
	            </h1>
	        </div>
	
	        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	            <ul class="nav navbar-nav header-title">
	                <li class="active"><a href="#" class="active">首页</a></li>
	                <li><a href="#">解决方案</a></li>
	                <li><a href="#">开发指南</a></li>
	                <li class="dropdown">
	                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
	                       aria-haspopup="true" aria-expanded="false">技术文档<span class="caret"></span></a>
	                    <ul class="dropdown-menu">
	                        <li><a href="#">API文档</a></li>
	                        <li><a href="#">消息文档</a></li>
	                    </ul>
	                </li>
	            </ul>
	            <!-- <div class="col-sm-3" class="header-search">             
			        <div class="input-group" >
			        	<input type="text" class="form-control"/>                 
			        	<span class="input-group-addon"><a href="#"><i class="glyphicon glyphicon-search"></i></a></span>             
			        </div>
		        </div> -->
		        <div class="aop-header-input-container aop-header-inline">
			        <span class="next-input next-input-single next-input-medium">
			        	<input type="text" value="" height="100%">
			        </span>
			        <i class="next-icon next-icon-search next-icon-medium"></i>
		        </div>
	        </div>

	    </div>
	</nav>
	<!--滚动图片 -->
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
	    <!-- Indicators -->
	    <ol class="carousel-indicators">
	        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
	        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
	        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
	        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
	        <li data-target="#carousel-example-generic" data-slide-to="4"></li>
	    </ol>
	
	    <!-- Wrapper for slides -->
	    <div class="carousel-inner" role="listbox">
	        <div class="item active">
	            <img src="../images/chrome-big.jpg" alt="...">
	            <div class="carousel-caption">
	                ...
	            </div>
	        </div>
	        <div class="item">
	            <img src="../images/firefox-big.jpg" alt="...">
	            <div class="carousel-caption">
	                ...
	            </div>
	        </div>
	        <div class="item">
	            <img src="../images/safari-big.jpg" alt="...">
	            <div class="carousel-caption">
	                ...
	            </div>
	        </div>
	        <div class="item">
	            <img src="../images/opera-big.jpg" alt="...">
	            <div class="carousel-caption">
	                ...
	            </div>
	        </div>
	        <div class="item">
	            <img src="../images/ie-big.jpg" alt="...">
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
	<!--tabs-->
	<div>
	
	    <!-- Nav tabs -->
	    <ul class="nav nav-tabs" role="tablist">
	        <li role="presentation" class="active"><a href="#Chrome" aria-controls="home" role="tab" data-toggle="tab">Chrome</a></li>
	        <li role="presentation"><a href="#Firefox" aria-controls="profile" role="tab" data-toggle="tab">Firefox</a></li>
	        <li role="presentation"><a href="#Safari" aria-controls="messages" role="tab" data-toggle="tab">Safari</a></li>
	        <li role="presentation"><a href="#Opera" aria-controls="settings" role="tab" data-toggle="tab">Opera</a></li>
	        <li role="presentation"><a href="#IE" aria-controls="settings" role="tab" data-toggle="tab">IE</a></li>
	    </ul>
	<div class="summary">
	    <!-- Tab panes -->
	    <div class="tab-content">
	        <div role="tabpanel" class="tab-pane active" id="Chrome">
	            <div class="row feature">
	                <div class="col-md-7">
	                    <h2 class="feature-heading">Google Chrome
	                        <span class="text-muted">使用最广的浏览器</span>
	                    </h2>
	                    <p class="lead">
	                        Google Chrome，又称Google浏览器，是一个由Google（谷歌）公司开发的网页浏览器。 该浏览器是基于其他开源软件所撰写，包括WebKit，目标是提升稳定性、速度和安全性，并创造出简单且有效率的使用者界面。
	                    </p>
	                </div>
	                <div class="col-md-5">
	                    <img class="feature-image img-responsive" src="../images/chrome-logo.jpg">
	                </div>
	            </div>
	        </div>
	        <div role="tabpanel" class="tab-pane" id="Firefox">
	            <div class="row feature">
	                <div class="col-md-5">
	                    <img class="feature-image img-responsive" src="../images/firefox-logo.jpg">
	                </div>
	                <div class="col-md-7">
	                    <h2 class="feature-heading">Mozilla Firefox
	                        <span class="text-muted">美丽的狐狸</span>
	                    </h2>
	                    <p class="lead">
	                        Mozilla Firefox，中文名通常称为“火狐”或“火狐浏览器”，是一个开源网页浏览器， 使用Gecko引擎（非ie内核），支持多种操作系统如Windows、Mac和linux。
	                    </p>
	                </div>
	            </div>
	        </div>
	        <div role="tabpanel" class="tab-pane" id="Safari">
	            <div class="row feature">
	                <div class="col-md-7">
	                    <h2 class="feature-heading">Safari
	                        <span class="text-muted">Mac用户首选</span>
	                    </h2>
	                    <p class="lead">
	                        Safari，是苹果计算机的最新操作系统Mac OS X中的浏览器，使用了KDE的KHTML作为浏览器的运算核心。 Safari在2003年1月7日首度发行测试版，并成为Mac OS X v10.3与之后的默认浏览器，也是iPhone与IPAD和iPod touch的指定浏览器。
	                    </p>
	                </div>
	                <div class="col-md-5">
	                    <img class="feature-image img-responsive" src="../images/safari-logo.jpg">
	                </div>
	            </div>
	        </div>
	        <div role="tabpanel" class="tab-pane" id="Opera">
	            <div class="row feature">
	                <div class="col-md-5">
	                    <img class="feature-image img-responsive" src="../images/opera-logo.jpg">
	                </div>
	                <div class="col-md-7">
	                    <h2 class="feature-heading">Opera
	                        <span class="text-muted">小众但易用</span>
	                    </h2>
	                    <p class="lead">
	                        Mozilla Firefox，中文名通常称为“火狐”或“火狐浏览器”，是一个开源网页浏览器， 使用Gecko引擎（非ie内核），支持多种操作系统如Windows、Mac和linux。
	                    </p>
	                </div>
	            </div>
	        </div>
	        <div role="tabpanel" class="tab-pane" id="IE">
	            <div class="row feature">
	                <div class="col-md-7">
	                    <h2 class="feature-heading">IE
	                        <span class="text-muted">你懂的</span>
	                    </h2>
	                    <p class="lead">
	                        Safari，是苹果计算机的最新操作系统Mac OS X中的浏览器，使用了KDE的KHTML作为浏览器的运算核心。 Safari在2003年1月7日首度发行测试版，并成为Mac OS X v10.3与之后的默认浏览器，也是iPhone与IPAD和iPod touch的指定浏览器。
	                    </p>
	                </div>
	                <div class="col-md-5">
	                    <img class="feature-image img-responsive" src="../images/ie-logo.jpg">
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	</div>
</body>
</html>