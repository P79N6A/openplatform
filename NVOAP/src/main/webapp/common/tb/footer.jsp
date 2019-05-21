<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%-- <%@ include file="/common/resource.jsp"%> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电动汽车开放平台</title>
<%-- <link rel="stylesheet" href=<%= request.getContextPath() + "/plugin/bootstrap/css/bootstrap.css" %> type="text/css"> --%>
 <%--<link rel="stylesheet" href=<%= request.getContextPath() + "/css/tb/tb.css" %> type="text/css">--%>

<%-- <script src=<%= request.getContextPath() + "/plugin/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plugin/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>--%>
<script src=<%= request.getContextPath() + "/js/footer.js" %> type="text/javascript"></script>

<style type="text/css">
    .footer {
        color: #fff;
        background-color: #242a40;
        padding-top: 40px;

    }


    .footer .footer-column .footer-column-item{
        width: 30%;
        float: left;
        text-align: left;
    }

    .footer .footer-column .footer-column-item div {
        font-size: 18px;
        margin-bottom: 32px
    }

    .footer .footer-column .footer-column-item div a {
        text-decoration: none
    }

    .footer .footer-column .second-link li {
        margin-bottom: 15px;
        font-size: 16px
    }

    .footer .footer-column .second-link a {
        color: #ccc;
        text-decoration: none
    }

    .footer .copyright {
        margin-top: 82px;
        padding-bottom: 15px;
        font-size: 12px;
        text-align:center;
    }

</style>
</head>
<body>
	<div class="footer" data-spm="6">
    <div class="container">
      <ul class="footer-column clearfix">
        <li class="footer-column-item">
        <div>联系我们</div>
        <ul class="second-link">
          <li>邮箱：open.nvoap.com@163.com</li>
          <li>商务QQ：123456789</li>
          <li>客服电话：010-88888888</li>
          <li>地址：北京市海淀区上地南瑞科技大厦29号楼</li>
        </ul>
      </li>

        <li class="footer-column-item">
          <div>支持与帮助</div>
          <ul class="second-link">
            <li>
              <a href="basic" target="_blank">入门指南</a>
            </li>
            <li>
              <a href="basic" target="_blank">常见问题</a>
            </li>
            <li>
              <a href="api/apiInfo?groupId=40289422668f536e01668f54cd440003" target="_blank">API文档</a>
            </li>
          <!--  <li>
              <a href="#" target="_blank">商务合作</a>
            </li>
            <li>
              <a href="#" target="_blank">违规投诉</a>
            </li>-->
          </ul>
        </li>

        <li class="footer-column-item">
          <div><a href="inform/informDetail" target="_blank" style="color: white">最新公告</a></div>
          <ul class="second-link" id = "footer_notice_list">
          </ul>
        </li>
        <!--
        <li class="footer-column-item">
          <div>合作洽谈</div>
          <ul class="second-link">
            <li>
              <a href="">点此与我联系</a>
            </li>
          </ul>
        </li>
        <li class="footer-column-item">
          <div>关注我们</div>
          <ul class="second-link">
            <li>
              <a href="">关注开放平台生活号</a>
              <br />
              <img style="margin-top: 15px;" width="84" height="84" src=""
                alt="">
            </li>
            <li>
              <a href="">打开支付宝扫一扫</a>
            </li>
          </ul>
        </li>
		-->
      </ul>
      <div class="copyright">
        	北京科东电力控制系统有限责任公司 版权所有
      </div>
    </div>
  </div>
</body>
</html>