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
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/partner.css" %> type="text/css">
    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
    <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<%--<script src=<%= request.getContextPath() + "/js/main.js" %> type="text/javascript"></script>--%>

</head>
<body style="background-color:#F5F5F5;padding-top:0px;">

<!-- <iframe src="../common/tb/header.jsp" width="100%" height="100%" style="overflow-y:none;height:50px;"></iframe> -->
<%@ include file="/common/tb/header.jsp" %>
<div class="container">
    <div class="page-header">
        <h1>合作伙伴信息</h1>
    </div>
    <div class="con-list">
        <div class="container con-detail">
            <img class="detail-img" src="images/partner.png">
            <div class="detail-par">
                <div class="detail-par-head">
                    <h2 class="pannel detail-par-head-h" ><a href="" style="color: #000000">合作伙伴1科技有限公司</a></h2>
                </div>

                <div class="detail-x">
                    <span>通过接入电动车开放平台的……一起实现……</span></div>
                <div class="detail-par-con">
                    <div class="par-con-title">涉及技术</div>
                    <div class="par-content">付费api</div>
                    <div class="par-content">定位api</div>
                </div>
            </div>
        </div>

        <div class="container con-detail">
            <img class="detail-img" src="images/partner.png">
            <div class="detail-par">
                <div class="detail-par-head">
                    <h2 class="pannel detail-par-head-h" ><a href="" style="color: #000000">合作伙伴2科技有限公司</a></h2>
                </div>

                <div class="detail-x">
                    <span>通过接入电动车开放平台的……一起实现……</span></div>
                <div class="detail-par-con">
                    <div class="par-con-title">涉及技术</div>
                    <div class="par-content">付费api</div>
                    <div class="par-content">定位api</div>
                </div>
            </div>
        </div>

        <div class="container con-detail">
            <img class="detail-img" src="images/partner.png">
            <div class="detail-par">
                <div class="detail-par-head">
                    <h2 class="pannel detail-par-head-h" ><a href="" style="color: #000000">合作伙伴3科技有限公司</a></h2>
                </div>

                <div class="detail-x">
                    <span>通过接入电动车开放平台的……一起实现……</span></div>
                <div class="detail-par-con">
                    <div class="par-con-title">涉及技术</div>
                    <div class="par-content">付费api</div>
                    <div class="par-content">定位api</div>
                </div>
            </div>
        </div>

        <div class="container con-detail">
            <img class="detail-img" src="images/partner.png">
            <div class="detail-par">
                <div class="detail-par-head">
                    <h2 class="pannel detail-par-head-h" ><a href="" style="color: #000000">合作伙伴4科技有限公司</a></h2>
                </div>

                <div class="detail-x">
                    <span>通过接入电动车开放平台的……一起实现……</span></div>
                <div class="detail-par-con">
                    <div class="par-con-title">涉及技术</div>
                    <div class="par-content">付费api</div>
                    <div class="par-content">定位api</div>
                </div>
            </div>
        </div>
    </div>

    <div class="par-foot">
        <div class="par-footer">
            <ul class="pagination">
                <li><a href="#">&laquo;</a></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </div>

    </div>


</div>
<%@ include file="/common/tb/footer.jsp" %>
</body>
</html>
