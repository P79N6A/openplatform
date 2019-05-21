<%--
  Created by IntelliJ IDEA.
  User: Tzh
  Date: 2018/11/19
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>电动汽车开放平台</title>

    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/thunder/style.css"%> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">

    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>

    <script src=<%= request.getContextPath() + "/js/thunder/initlang.js"%>type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/js/thunder/statistics.js"%>type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/js/thunder/jquery.js"%> type="text/javascript"></script>
</head>
<body>
<%@ include file="/common/tb/header.jsp" %>
<section class="gray">
    <div class="container">
        <%--<div class="bread-nav clear">--%>
            <%--<div class="left">--%>
                <%--<a href="/">首页</a>--%>
                <%--<span class="segment">|</span>--%>
                <%--<a href="/site/newslist.html?type=2">新闻</a>--%>
            <%--</div>--%>
            <%--<div class="right">--%>
                <%--<span class="segment">&nbsp;</span>--%>
                <%--<a href="/site/news.html?type=2&amp;id=1537345389KnepWTJN7Ox4Eo2Ff8w3PVQybXgh9a0M">下一篇</a>--%>
            <%--</div>--%>
        <%--</div>--%>
        <article class="article-news">
            <h1 class="article-title1">${newsDetail.newsTitle}</h1>
            <p>${newsDetail.newsContent}</p>
            <p><br></p>
        </article>
    </div>
</section>

<%@ include file="/common/tb/footer.jsp" %>
</body>
</html>
