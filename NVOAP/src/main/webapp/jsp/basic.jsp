<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/11/15
  Time: 22:50
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>电动汽车开放平台</title>

    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">--%>
    <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/tfund.css" %> type="text/css">--%>
    <%--<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>--%>

    <link rel="stylesheet" href=<%= request.getContextPath() + "/plugin/bootstrap/css/bootstrap.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/iconfont/iconfont.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">

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
    <script src=<%= request.getContextPath() + "/js/basic.js" %> type="text/javascript"></script>

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
<body style="background-color:#F5F5F5;padding-top:0px;">

<%@ include file="/common/tb/header_only.jsp" %>






 <%--<%@ include file="/common/tb/header.jsp" %>--%>
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
                                <div class="menu-name" title="平台简介" onclick="refresh(this,0);">平台简介</div>
                            </div>
                        </a>
                    </li>

                    <li>
                        <a class="">
                            <div class="menu-container">
                                <div class="menu-name menu-display-one" title="开发入门">开发入门
                                    <ul>
                                        <li>
                                            <a class="">
                                                <div class="menu-container">
                                                    <div class="menu-name" title="新手指南" onclick="refresh(this,1);">&nbsp;&nbsp;新手指南</div>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <a class="">
                                                <div class="menu-container">
                                                    <div class="menu-name" title="权限说明" onclick="refresh(this,2);">&nbsp;&nbsp;权限说明</div>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <a class="">
                                                <div class="menu-container">
                                                    <div class="menu-name" title="计费说明" onclick="refresh(this,3);">&nbsp;&nbsp;计费说明</div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </a>
                    </li>

                    <li>
                        <a class="">
                            <div class="menu-container">
                                <div class="menu-name menu-display-one" title="开发文档">开发文档
                                    <ul>
                                        <li>
                                            <a class="">
                                                <div class="menu-container">
                                                    <div class="menu-name" title="平台技术">&nbsp;&nbsp;平台技术
                                                        <ul>
                                                            <li>
                                                                <a class="">
                                                                    <div class="menu-container">
                                                                        <div class="menu-name" title="API调用协议" onclick="refresh(this,4);">&nbsp;&nbsp;&nbsp;&nbsp;API调用协议</div>
                                                                    </div>
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="">
                                                                    <div class="menu-container">
                                                                        <div class="menu-name" title="用户授权介绍" onclick="refresh(this,5);">&nbsp;&nbsp;&nbsp;&nbsp;用户授权介绍</div>
                                                                    </div>
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="">
                                                                    <div class="menu-container">
                                                                        <div class="menu-name" title="应用环境介绍" onclick="refresh(this,6);">&nbsp;&nbsp;&nbsp;&nbsp;应用环境介绍</div>
                                                                    </div>
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <a class="">
                                                <div class="menu-container">
                                                    <div class="menu-name" title="平台功能" onclick="refresh(this,7);">&nbsp;&nbsp;平台功能</div>
                                                </div>
                                            </a>
                                        </li>
                                        <li>
                                            <a class="">
                                                <div class="menu-container">
                                                    <div class="menu-name" title="常用工具"  onclick="refresh(this,8);">&nbsp;&nbsp;常用工具</div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </a>
                    </li>

                    <li>
                        <a class="">
                            <div class="menu-container">
                                <div class="menu-name" title="常见问题" onclick="refresh(this,9);">常见问题</div>
                            </div>
                        </a>
                    </li>


                    <%--<li class="menu-display-one">开发入门<i class="iconfont icon-31xiala" style="font:normal normal normal 14px/1 iconfont"></i>--%>
                        <%--<ul>--%>
                            <%--<li class="normal" onclick="refresh(this,1);">&nbsp;&nbsp;新手指南</li>--%>
                            <%--<li class="normal" onclick="refresh(this,2);">&nbsp;&nbsp;权限说明</li>--%>
                            <%--<li class="normal" onclick="refresh(this,3);">&nbsp;&nbsp;计费说明</li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                    <%--<li class="menu-display-one">开发文档<i class="iconfont icon-31xiala" style="font:normal normal normal 14px/1 iconfont"/>--%>
                       <%--<ul>--%>
                           <%--<li class="menu-display-two">&nbsp;&nbsp;平台技术<i class="iconfont icon-31xiala" style="font:normal normal normal 14px/1 iconfont"/>--%>
                               <%--<ul>--%>
                                   <%--<li class="normal" onclick="refresh(this,4);">&nbsp;&nbsp;&nbsp;&nbsp;api调用协议</li>--%>
                                   <%--<li class="normal" onclick="refresh(this,5);">&nbsp;&nbsp;&nbsp;&nbsp;用户授权介绍</li>--%>
                                   <%--<li class="normal" onclick="refresh(this,6);">&nbsp;&nbsp;&nbsp;&nbsp;应用环境介绍</li>--%>
                               <%--</ul>--%>
                           <%--</li>--%>
                           <%--<li class="normal" onclick="refresh(this,7);">&nbsp;&nbsp;平台功能</li>--%>
                           <%--<li class="normal" onclick="refresh(this,8);">&nbsp;&nbsp;常用工具</li>--%>
                       <%--</ul>--%>
                    <%--</li>--%>
                    <%--<li class="normal" onclick="refresh(this,9);">常见问题</li>--%>

                </ul>
            </div>
        </div>
    </div>
    <div class="main-content">
        <div class="msg-content">
            <a class="msg-title">基础文档</a>
            <div class="content-a">
                <div class="content-f">
                    <div id="Content0">
                        <section>
                            <h2>平台简介</h2>
                            <h3>一、什么是电动汽车开放平台</h3>
                            <p>电动汽车开放平台是基于电动汽车各类电子商务业务的开放平台，提供外部合作伙伴参与服务用户的各类原材料,例如API、账号体系、数据安全等。它是电动汽车电子商务基础服务的重要开放途径，将推动各行各业定制、创新、进化，并最终促成新商业文明生态圈的建立。</p>
                            <p>我们的使命是把电动汽车的一系列电子商务基础服务，像水、电、煤一样输送给有需要的商家、开发者、社区媒体和各行各业。</p>
                            <h3>二、平台特点</h3>
                            <h4>开放规模大、程度深、限制小</h4>
                            <h4>盈利模式清晰</h4>
                            <h4>合作形式丰富</h4>
                            <h3>三、平台服务</h3>
                            <h4>卖家服务</h4>
                            <h4>买家服务</h4>
                        </section>
                    </div>

                    <div id="Content1" class="none">
                        <section>
                            <h2>新手入门</h2>
                            <h3>入驻成为开发者（ISV）：</h3>
                            <p>进入应用管理界面，可看到已有应用名称及其相关信息如下表：
                            <img class="imges" src="images/conduct1.png"></p>
                            <p>录入应用信息：
                            <img class="imges" src="images/conduct2.png"></p>
                            <p>订购服务：
                            <img class="imges" src="images/conduct3.png"></p>
                            <p>查看服务基本信息：
                            <img class="imges" src="images/conduct4.png"></p>
                            <p>配置计费方式：
                            <img class="imges" src="images/conduct5.png"></p>
                            <p>等待审核</p>
                        </section>
                    </div>

                    <div id="Content2" class="none">
                        <section>
                            <h2>权限设置说明</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>

                    <div id="Content3" class="none">
                        <section>
                            <h2>计费策略说明</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>

                    <div id="Content4" class="none">
                        <section>
                            <h2>API调用协议</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>

                    <div id="Content5" class="none">
                        <section>
                            <h2>用户授权</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>

                    <div id="Content6" class="none">
                        <section>
                            <h2>应用环境</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>

                    <div id="Content7" class="none">
                        <section>
                            <h2>平台功能</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>

                    <div id="Content8" class="none">
                        <section>
                            <h2>常用工具</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>

                    <div id="Content9" class="none">
                        <section>
                            <h2>常见问题</h2>
                            <h3>一</h3>
                            <p>段落</p>
                            <h3>二</h3>
                            <p>段落</p>
                        </section>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>

<div class="menu-drop-down">
    <ul style="top:123px">
            <li>基础技术</li>
    </ul>
</div>


<%@ include file="/common/tb/footer.jsp" %>
</body>

</html>

