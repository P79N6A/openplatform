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
  .header-ul a{
    color:#ffffff;
    font-weight: normal;
    cursor:pointer;
    height:100%;
  }
</style>
    <script>
        function showDocMenu(obj){
            // $(obj).hover();
        }
    </script>
</head>
<body>
  <div class="header">
    <div class="container-content clearfix">
      <h1 class="logo">
        <a href="#">
          <img src="images/website/logo1.png" style="width:50px;height:100%;vertical-align: top;"/>
          <label style="font-size:26px;">智慧车联网</label>
          <label style="font-size:18px;">能力开放平台</label>
        </a>
      </h1>
      <ul class="nav header-ul" style="float:right;margin-right:50px;">
        <li class="nav-item">
          <a href="openHome.do?home">首页</a>
        </li>
        <li class="nav-item" onclick="showDocMenu(this)">
          <a class="docs">开发者文档</a>
          <ul class="sub-nav single-col" style="font-weight: normal">
            <li>
              <a href="openHome.do?basicDocs" target="_blank" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'"
                 style="color:#fff">文档中心</a>
              <a href="openHome.do?apiInfo" target="_blank" onmouseover="this.style.color='#70bdff'"
                 onmouseout="this.style.color='#fff'" style="color:#fff" >能力API文档</a>
            </li>
          </ul>
        </li>
        <li class="nav-item">
          <a target="_blank" href="apiLogin.do?login">进入开放平台</a>
        </li>
      </ul>
      </div>
    </div>
</body>
</html>