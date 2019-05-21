<%--
  Created by IntelliJ IDEA.
  User: Tzh
  Date: 2018/11/15
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>电动汽车开放平台</title>

    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/thunder/style.css"%> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/iconfont/iconfont.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">


    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>


    <script src=<%= request.getContextPath() + "/js/thunder/initlang.js"%>type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/js/thunder/statistics.js"%>type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/js/thunder/jquery.js"%> type="text/javascript"></script>
</head>
<body>
<%@ include file="/common/tb/header.jsp" %>
<section>
    <div class="container">
        <div class="section-wrapper">
            <h2 class="title-wrapper2">新闻动态</h2>
        </div>
        <div class="newslist-box">
            <c:forEach var="newsDetail" items="${newsDetails}">
                <div class="newslist">
                    <div class="newslist-date">
                        <h4 class="newslist-title">15日</h4>
                        <span>2018.11</span>
                    </div>
                    <div class="newslist-content">
                        <h4 class="newslist-title"><a target="_blank" data-newsid="${newsDetail.id}">${newsDetail.newsTitle}</a></h4>
                        <p>${newsDetail.newsAbstract}</p>
                        <p class="right"><a href="news/loadNewsContent?newsId=${newsDetail.id}" target="_blank" class="link" data-newsid="${newsDetail.id}">阅读全文&gt;</a></p>
                    </div>
                </div>
            </c:forEach>
            <%--<div class="newslist">--%>
                <%--<div class="newslist-date">--%>
                    <%--<h4 class="newslist-title">11日</h4>--%>
                    <%--<span>2018.11</span>--%>
                <%--</div>--%>
                <%--<div class="newslist-content">--%>
                    <%--<h4 class="newslist-title"><a href="#" target="_blank">测试新闻标题2</a></h4>--%>
                    <%--<p>测试新闻内容2测试新闻内容2测试新闻内容2测试新闻内容2测试新闻内容2测试新闻内容2测试新闻内容2测试新闻内容2</p>--%>
                    <%--<p class="right"><a href="#" target="_blank" class="link">阅读全文&gt;</a></p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="newslist">--%>
                <%--<div class="newslist-date">--%>
                    <%--<h4 class="newslist-title">10日</h4>--%>
                    <%--<span>2018.11</span>--%>
                <%--</div>--%>
                <%--<div class="newslist-content">--%>
                    <%--<h4 class="newslist-title"><a href="#" target="_blank">测试新闻标题3</a></h4>--%>
                    <%--<p>测试新闻内容3测试新闻内容3测试新闻内容3测试新闻内容3测试新闻内容3测试新闻内容3测试新闻内容3测试新闻内容3</p>--%>
                    <%--<p class="right"><a href="#" target="_blank" class="link">阅读全文&gt;</a></p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="newslist">--%>
                <%--<div class="newslist-date">--%>
                    <%--<h4 class="newslist-title">9日</h4>--%>
                    <%--<span>2018.11</span>--%>
                <%--</div>--%>
                <%--<div class="newslist-content">--%>
                    <%--<h4 class="newslist-title"><a href="#" target="_blank">测试新闻标题4</a></h4>--%>
                    <%--<p>测试新闻内容4测试新闻内容4测试新闻内容4测试新闻内容4测试新闻内容4测试新闻内容4测试新闻内容4测试新闻内容4</p>--%>
                    <%--<p class="right"><a href="#" target="_blank" class="link">阅读全文&gt;</a></p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="newslist">--%>
                <%--<div class="newslist-date">--%>
                    <%--<h4 class="newslist-title">10日</h4>--%>
                    <%--<span>2018.09</span>--%>
                <%--</div>--%>
                <%--<div class="newslist-content">--%>
                    <%--<h4 class="newslist-title"><a href="#" target="_blank">测试新闻标题5</a></h4>--%>
                    <%--<p>测试新闻内容5测试新闻内容5测试新闻内容5测试新闻内容5测试新闻内容5测试新闻内容5测试新闻内容5测试新闻内容5</p>--%>
                    <%--<p class="right"><a href="#" target="_blank" class="link">阅读全文&gt;</a></p>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
        <%--<div class="paging">--%>
            <%--<a href="/site/newslist.html?type=2&amp;id=0" class="on">1</a>--%>
            <%--<a href="/site/newslist.html?type=2&amp;id=1">2</a>--%>
            <%--<a href="/site/newslist.html?type=2&amp;id=2">3</a>--%>
        <%--</div>--%>
    </div>
</section>
<%@ include file="/common/tb/footer.jsp" %>
</body>
</html>