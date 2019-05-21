<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>智慧车联网能力开放平台</title>
<style type="text/css">
.nav-item{
	background-color:transparent;
}
.nav-item:hover{
	background-color:transparent;
}
.nav > li > a{
	padding:0px 15px;
}
.nav > li > a:hover{
	background-color:transparent;
}
.header{
  /*background-color:transparent;*/
}
.header a{
  /*color:#000;*/
}
  .nav{
    font-weight:bold;
    font-size:17px;
  }
  .phone-header-ul a{
    color:#ffffff;
    font-weight: normal;
    height:30px;
    cursor:pointer;
  }
  .phone-header-ul li{
    padding:0;
  }
.header .logo{
  line-height:30px;
}
</style>
</head>
<body>
  <div class="header" style="height:30px;line-height:30px;">
    <div class="container-content clearfix">
      <h1 class="logo" style="margin-right:10px;width:43%;height:30px;padding-top: 6px;">
        <a href="#" style="position:absolute;top:-5px;">
          <img src="images/website/logo1.png" style="width:30px;height:50%;vertical-align: middle;"/>
          <label style="font-size:12px;">智慧车联网</label>
          <label style="font-size:8px;">能力开放平台</label>
        </a>
      </h1>
      <ul class="nav phone-header-ul" style="float:right;width:50%;font-size:8px;">
        <li class="nav-item" style="padding:0">
          <a href="openHome.do?home" style="padding: 0px 4px;">首页</a>
        </li>
        <li class="nav-item" style="padding:0">
          <a class="docs" style="padding: 0px 4px;">开发者文档</a>
          <ul class="sub-nav single-col" style="font-weight: normal;top:30px;">
            <li>
              <a href="openHome.do?basicDocs" target="_blank" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'"
                 style="color:#fff">文档中心</a>
              <a href="openHome.do?apiInfo" target="_blank" onmouseover="this.style.color='#70bdff'"
                 onmouseout="this.style.color='#fff'" style="color:#fff" >能力API文档</a>
            </li>
          </ul>
        </li>
        <li class="nav-item" style="padding:0">
          <a target="_blank" href="apiLogin.do?login" style="padding: 0px 4px;">进入开放平台</a>
        </li>
      </ul>
      </div>
    </div>
</body>
</html>