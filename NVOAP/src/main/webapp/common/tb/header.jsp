<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--<%--%>
  <%--String path = request.getContextPath();--%>
  <%--String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()--%>
          <%--+ path;--%>
<%--%>--%>

<!DOCTYPE html>
<html>
<head>
<%--<base href=" <%=basePath%>">--%>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- <%@ include file="/common/resource.jsp"%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电动汽车开放平台</title>
<%-- <link rel="stylesheet" href=<%= request.getContextPath() + "/iconfont/iconfont.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">

<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap.js" %> type="text/javascript"></script> --%>
<%-- <script src=<%= request.getContextPath() + "/js/index.js" %> type="text/javascript"></script> --%>
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
</style>
</head>
<body>
  <div class="header" data-spm="1" data-spm-max-idx="27">
    <div class="container clearfix">
      <h1 class="logo">
        <a href="#" data-spm-anchor-id="a219a.7386653.1.1">
        <img src="images/e.png" style="width:50px;height:81%;vertical-align: middle;"/><font size="5">智慧车联网</font> 能力开放平台</a>
      </h1>
      <ul class="nav" style="padding-left:84px">
        <li class="nav-item">
          <a href="index/main" target="_self" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.2" onmouseout="this.style.color='#fff'" style="color:#fff">首页</a>
        </li>
        <li class="nav-item">
        <a href="javascript:;" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.19" onmouseout="this.style.color='#fff'" style="color:#fff">开发者文档</a>
        <ul class="sub-nav single-col">
          <li>
            <a href="basic" target="_self" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.20" onmouseout="this.style.color='#fff'" style="color:#fff">文档中心</a>
            <a href="api/apiInfo?groupId=40289422668f536e01668f54cd440003" target="_self" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'" style="color:#fff" data-spm-anchor-id="a219a.7386653.1.21">能力API文档</a>
          </li>
        </ul>
      </li>
      <li class="nav-item">
      <a href="javascript:;" data-spm-anchor-id="a219a.7386653.1.22" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'" style="color:#fff">平台信息</a>
      <ul class="sub-nav single-col">
      <li>
        <a href="../inform/informDetail" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.23" onmouseout="this.style.color='#fff'" style="color:#fff">平台公告</a>
        <a href="/ability" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.24" onmouseout="this.style.color='#fff'" style="color:#fff">关于我们</a>
      </li>
    </ul>
      <!--<ul class="sub-nav single-col">
        <li>
          <a href="../inform/informDetail" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.23" onmouseout="this.style.color='#fff'" style="color:#fff">能力发布</a>
          <a href="../inform/informDetail" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.24" onmouseout="this.style.color='#fff'" style="color:#fff">维护公告</a>
          <a href="../inform/informDetail" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.25" onmouseout="this.style.color='#fff'" style="color:#fff">协议变更</a>
          <a href="../inform/informDetail" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.28" onmouseout="this.style.color='#fff'" style="color:#fff">处罚公告</a>
        </li>
      </ul>-->
    </li>
        <li class="nav-item">
          <a href="javascript:;" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.3" onmouseout="this.style.color='#fff'" style="color:#fff;">能力信息</a>
          <ul class="sub-nav single-col">
          <li>
            <a href="ability"  onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.4" onmouseout="this.style.color='#fff'" style="color:#fff">能力API数据</a>
           </li>
          </ul> 
          
          <!--  <ul class="sub-nav single-col">
            <li>
              <a href="ability" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.4" onmouseout="this.style.color='#fff'" style="color:#fff">智慧出行</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'" style="color:#fff" data-spm-anchor-id="a219a.7386653.1.5">无线开放</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.6" onmouseout="this.style.color='#fff'" style="color:#fff">属地运营</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.7" onmouseout="this.style.color='#fff'" style="color:#fff">电子场站</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.8" onmouseout="this.style.color='#fff'" style="color:#fff">智能硬件</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.9" onmouseout="this.style.color='#fff'" style="color:#fff">工控采集</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.10" onmouseout="this.style.color='#fff'" style="color:#fff">大屏监视</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.11" onmouseout="this.style.color='#fff'" style="color:#fff">e充电插件</a>
            </li>
          </ul>-->
          <!-- <ul class="sub-nav">
            <li>
              <div class="col-title">新零售开放</div>
              <a href="//istore.tmall.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.3">智慧门店</a>
              <a href="//open.taobao.com/doc.htm?docId=108905&amp;docType=1" target="_blank" data-spm-anchor-id="a219a.7386653.1.4">智能硬件</a>
            </li>
            <li>
              <div class="col-title">电商开放</div>
              <a href="//open.taobao.com/business.htm?cid=25" target="_blank" data-spm-anchor-id="a219a.7386653.1.5">第三方工具</a>
              <a href="https://alimarket.taobao.com/markets/qnww/application-platform" target="_blank" data-spm-anchor-id="a219a.7386653.1.6">千牛插件</a>
            </li>
            <li>
              <div class="col-title">无线开放</div>
              <a href="https://open.m.taobao.com/solution/index?name=brand" target="_blank" data-spm-anchor-id="a219a.7386653.1.7">品牌号</a>
              <a href="https://open.m.taobao.com/solution/index?name=item" target="_blank" data-spm-anchor-id="a219a.7386653.1.8">C2B</a>
              <a href="https://open.m.taobao.com/solution/index?name=game" target="_blank" data-spm-anchor-id="a219a.7386653.1.9">互动营销</a>
              <a href="https://open-ar.bot.tmall.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.10">天猫精灵</a>
            </li>
            <li>
              <div class="col-title">国际化</div>
              <a href="https://developers.aliexpress.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.11">AliExpress</a>
              <a href="https://open.alibaba.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.12">ICBU</a>
              <a href="https://open.lazada.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.13">Lazada</a>
            </li>
            <li>
              <div class="col-title">其他</div>
              <a href="https://open.dingtalk.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.14">钉钉</a>
              <a href="https://open.alitrip.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.15">飞猪</a>
              <a href="https://open.hemaos.com/" target="_blank" data-spm-anchor-id="a219a.7386653.1.16">盒马</a>
              <a href="https://tbk.bbs.taobao.com/detail.html?appId=45301&amp;postId=8127005" target="_blank" data-spm-anchor-id="a219a.7386653.1.17">淘宝客</a>
            </li>
          </ul> -->
        </li>
        <li class="nav-item">
          <a href="/question" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.18" onmouseout="this.style.color='#fff'" style="color:#fff;">常见问题</a>
          <!-- <ul class="sub-nav single-col">
            <li>
              <a href="resolve" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.12" onmouseout="this.style.color='#fff'" style="color:#fff">大屏显示</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'" style="color:#fff" data-spm-anchor-id="a219a.7386653.1.13">属地化运营</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.14" onmouseout="this.style.color='#fff'" style="color:#fff">公交小工具</a>
              <a href="#" target="_blank" onmouseover="this.style.color='#70bdff'" data-spm-anchor-id="a219a.7386653.1.15" onmouseout="this.style.color='#fff'" style="color:#fff">车桩企监控</a>
            </li>
          </ul>-->
        </li>
      
       
        <li class="nav-item">
          <a href="https://open.echargenet.com/apiLogin.do?login" target="_blank" data-spm-anchor-id="a219a.7386653.1.26" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'" style="color:#fff">进入开放平台</a>
        </li>
      </ul>
      <!-- <div class="user-info">
                        <span>
                  <a href="login" title="" id="open-login" data-spm-anchor-id="a219a.7386653.1.27" onmouseover="this.style.color='#70bdff'" onmouseout="this.style.color='#fff'" style="color:#fff">登录</a>
              </span>
                  </div>--> 
      </div>
    </div>
  </div>
</body>
</html>