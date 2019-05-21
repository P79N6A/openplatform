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
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/api.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/style.css" %> type="text/css">
    <script src=<%= request.getContextPath() + "/js/website/common.js" %> type="text/javascript"></script>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.min.css" %> type="text/css">
    <script src=<%= request.getContextPath() + "/js/website/basicDoc.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.js" %> type="text/javascript"></script>
    <style>
        .none{
            display:none;
        }

        .menu-display-one ul{
            display:none;
        }
        .menu-display-two ul{
            display:none;
        }
        .content-a{
            background: #f4f8fa;
            border: 1px solid #e5e5e5;
            padding: 15px;
            font-size: 12px;
            line-height: 23px;
        }
        .imges{
            width:100%;
        }
        .menu-list a{
            cursor: pointer;
        }
    </style>
</head>
<body style="background-color:#F5F5F5;padding-top:0px;">
<div id="phone-div" >
    <div class="header" style="height:30px;">
        <%@ include file="phone-header.jsp" %>
    </div>
</div>
<div id="pc-div">
    <div class="header">
        <%@ include file="header.jsp" %>
    </div>
</div>
<div class="content api-content" >
    <div class="side-menu">
        <div class="menu-content">
                 <span class="menu-select">
                    <span class="iconfont icon-31leimu">目&nbsp;&nbsp;录</span>
                     <i class="iconfont icon-31xiala" style="font:normal normal normal 14px/4 iconfont"></i>
                 </span>
            <div class="menu-list">
                <ul class="">
                    <li>
                        <a class="">
                            <div class="menu-container">
                                <div class="menu-name menu-display-one" title="文档中心">1.文档中心
                                    <ul>
                                        <li>
                                            <a class="doc-a">
                                                <div class="menu-container">
                                                    <div class="menu-name" data-href="webpage/website/ISPDevelop.jsp"
                                                         title="ISP使用手册">&nbsp;&nbsp;1.1 ISP使用手册</div>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <a class="doc-a">
                                                <div class="menu-container">
                                                    <div class="menu-name" data-href="webpage/website/ISVDevelop.jsp"
                                                         title="ISV使用手册">&nbsp;&nbsp;1.2 ISV使用手册</div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="main-content">
        <div class="msg-content" style="min-height:700px;">
            <iframe id="docIframe" src="webpage/website/ISPDevelop.jsp" height="9600px" width="100%"
                    style="border: 0px;" scrolling="no">
            </iframe>
        </div>
    </div>
</div>
<div class="footer" style="background-color:#000000;">
    <%@ include file="footer.jsp" %>
</div>
</body>
</html>

