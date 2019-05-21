<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href=" <%=basePath%>">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- <%@ include file="/common/resource.jsp"%> --%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>电动汽车开放平台</title>
     <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/plugin/bootstrap/css/bootstrap.css" %> type="text/css">--%>

    <link rel="stylesheet" href=<%= request.getContextPath() + "/iconfont/iconfont.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">

    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
    <%--<script src=<%= request.getContextPath() + "/js/main.js" %> type="text/javascript"></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/tb/jquery.js" %> ></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/tb/index.ee0fa508.js" %> ></script>--%>
    <style type="text/css">
    </style>
</head>
<body>
<%@ include file="/common/tb/header.jsp" %>
<!-- banner开始 -->
<div class="banner" data-spm="2" data-spm-max-idx="6">
    <ul class="slider" id="slider">
        <li style="display: list-item;">
            <a href="#" target="_blank" data-spm-anchor-id="a219a.7386653.2.2">
                <img src="images/tb/banner1.png" alt="">
            </a>
        </li>
    </ul>
</div>
<!-- banner结束 -->


<!-- 需求分析 开始 -->
<div class="container" data-spm="3">
    <h2 class="pannel-title">需求分析</h2>
    <div class="open-pannel">
        <section>
            <p>
                电商的崛起，改变了企业传统的销售方式与消费者的购物方式，物流过程是否通畅、高效，物流功能是否完备，将直接影响电商行业运行的效率。
            </p>
            <p>
                • 通知难：物流通知信息发送延时，降低用户体验。
            </p>
            <p>
                • 拒接率高：作为陌生号码，快递员电话可能会被收件人拒接。
            </p>
        </section>
    </div>
</div>
<!-- 使用场景 结束 -->

<!-- 使用能力 开始 -->
<div class="resolve-content" data-spm="4">
<div class="container" data-spm="3">
    <h2 class="pannel-title">使用能力</h2>
    <div class="open-pannel">
        <ul>
            <li>
                <div class="card-content">
                    <i class="iconfont icon-qichepeijian"></i>
                    <a href="ability" style="color: black; text-decoration: none">
                        <div class="card-item-title" href="../ability">能力1</div>
                    </a>
                    <!-- <p class="card-item-intro">技术驱动智慧门店发展，数据重构消费体验升级</p> -->
                </div>
            </li>
            <li>
                <div class="card-content">
                    <i class="iconfont icon-dingyue"></i>
                    <div class="card-item-title">能力2</div>
                    <!-- <p class="card-item-intro">B端智能硬件全产业链生态圈</p> -->
                </div>
            </li>

            <li>
                <div class="card-content">
                    <i class="iconfont icon-31shouye"></i>
                    <div class="card-item-title">能力3</div>
                    <!-- <p class="card-item-intro">分享五亿月活跃用户、移动云基础设施，以及丰富的API、SDK组件等</p> -->
                </div>
            </li>
        </ul>
    </div>
</div>
</div>
<!-- 使用能力 结束 -->

<%@ include file="/common/tb/footer.jsp" %>
</body>
</html>