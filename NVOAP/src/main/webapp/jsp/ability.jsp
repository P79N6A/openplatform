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
    <script src=<%= request.getContextPath() + "/js/main.js" %> type="text/javascript"></script>
    <%--<script src=<%= request.getContextPath() + "/js/tb/jquery.js" %> ></script>--%>
    <%--<script src=<%= request.getContextPath() + "/js/tb/index.ee0fa508.js" %> ></script>--%>
    <style type="text/css">
    </style>
</head>
<body>
<%@ include file="/common/tb/header.jsp" %>
<!-- banner开始 -->
<!-- banner开始 -->
<div class="banner" data-spm="2" data-spm-max-idx="6">
    <div class="slider" id="slider">
        <%--<li>--%>
            <%--<a href="#" target="_blank" data-spm-anchor-id="a219a.7386653.2.1">
                <img src="images/tb/banner1.png" alt="">--%>
            <%--</a>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<a href="#" target="_blank" data-spm-anchor-id="a219a.7386653.2.2">--%>
                <img src="images/about1.jpg" alt="">
            <%--</a>--%>
        <%--</li>--%>
                <img src="images/about2.jpg" alt="">
                <img src="images/about3.jpg" alt="">
                 <img src="images/about4.jpg" alt="">
    </div>

    <ul class="slider-nav" id="slider-nav">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>

    <!--<div class="notice">
        <i class="iconfont icon-voice"></i>
        <ul class="notice-list clearfix" id="notice_list">
        </ul>
        <a class="more-notice" href="inform/informDetail" target="_blank" data-spm-anchor-id="a219a.7386653.2.6">查看更多 &gt;&gt;</a>
    </div>-->
</div>
<!-- banner结束 -->


<!-- 平台发展历史 开始 -->
<div class="container" data-spm="3">
    <h2 class="pannel-title">平台发展历史</h2>
    <div class="open-pannel">
        <section>
            <p>能力开放平台依托车联网中台能力中心，打造面向用户、第三方应用开发商（ISV）、服务提供商（ISP）、平台运营商四方的能力开放生态体系。构建电动汽车能力开放平台，实现公共数据、通用业务模型、通用计算能力、通用人机交互、公共中间件、公共硬件资源及公共安全体系等能力资源开放，借助能力开放，促进产业链业务创新，培育应用市场，构建运营体系，为行业应用快速构建提供能力支撑，实现多方共同受益。</p>
        </section>

    </div>
</div>
<!-- 平台发展历史 结束 -->

<!-- 开发公司信息 开始 -->
<div class="container" data-spm="3">
    <h2 class="pannel-title">开发公司信息</h2>
    <div class="open-pannel">
        <section>
            <p>北京科东电力控制系统有限责任公司是由中国电力科学研究院控股的股份公司。实行董事会领导下的总经理负责制，由总经理全面负责公司运营。公司下设电网调度自动化事业部、应用仿真事业部、电力市场事业部、二次系统安全防护事业部、用电信息事业部、技术开发、市场、销售、人事行政、财务、质量管理、东北分公司等部门。形成了覆盖电网和电厂大部分电气专业的具有从事科研开发和承担工程项目，进行质量检测的综合性企业。</p>
            <p>   科东公司精神是“追求完美、用户满意、达成目标、奉献和群体协作精神、强烈的责任心”。着重实施以建立企业化运营机制为主导的物质文明和精神文明建设，资源配置合理，科技创新能力强，员工队伍保持稳定，综合竞争力不断提高。</p>
   			<p> 公司既有作为博士、硕士研究生导师、屡获国家级省市级嘉奖的著名老专家，也有获得博士、硕士学位具有丰富实践经验的中青年专家。所有技术人员具有大学本科以上学历。</p>
   			<p>北京科东公司已形成了一支老中青相结合的具有高技术水平和创新能力的人才队伍，成为我国电力科研事业的一支重要力量。</p>
        </section>
        
    </div>
</div>

<!--<div class="resolve-content" data-spm="4">
    <div class="container">
        <h2 class="pannel-title">解决方案</h2>
        <ul class="resolve-list clearfix">
            <li class="merch-operation">
                <div class="card-content">
                    <i class="iconfont icon-serviceyuanchengfuwu"></i>
                    <div class="card-item-title">大屏展示</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-serviceyuanchengfuwu"></i>
                    <div class="title">大屏展示</div>
                    <div class="desc">XXXXXXXXXXXXXXXXXXXXX</div>
                    <a href="#" target="_blank">查看详情</a>
                </div>
            </li>
            <li class="istore-production">
                <div class="card-content">
                    <i class="iconfont icon-31shouye"></i>
                    <div class="card-item-title">属地化运营</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-31shouye"></i>
                    <div class="title">属地化运营</div>
                    <div class="desc">XXXXXXXXXXXXXXXXXXXXX</div>
                    <a href="#" target="_blank">查看详情</a>
                </div>
            </li>
            <li class="mtaobao-open">
                <div class="card-content">
                    <i class="iconfont icon-gongnengjianyi"></i>
                    <div class="card-item-title">公交小工具</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-gongnengjianyi"></i>
                    <div class="title">公交小工具</div>
                    <div class="desc">XXXXXXXXXXXXXXXXXXXXX</div>
                    <a href="#" target="_blank">查看详情</a>
                </div>
            </li>
            <li class="full-link">
                <div class="card-content">
                    <i class="iconfont icon-tishi"></i>
                    <div class="card-item-title">车桩企监控</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-tishi"></i>
                    <div class="title">车桩企监控</div>
                    <div class="desc">XXXXXXXXXXXXXXXXXXXXX</div>
                    <a href="#" target="_blank">查看详情</a>
                </div>
            </li>
        </ul>
    </div>
</div>-->
<!-- 开发公司信息 结束 -->


<!-- 成功案例+合作伙伴 开始 -->
<div class="container" data-spm="3">
   <!--  <h2 class="pannel-title">成功案例</h2>
    <div class="open-pannel">
        <section>
            <h2>成功案例1</h2>
            <p>能力开放平台（ThunderChain）是由国网电动汽车开发的一款对外开放接口的平台，第三方公司可以通过这个平台订阅该接口，能力开放平台有三种计费方式，通过流量计费，通过次数进行计费，包年包月计费三种计费方式，大大为第三方合作商调用接口提供了便利，无平台，不能力；无能力，不平台。</p>
        </section>
        <section>
            <h2>成功案例2</h2>
            <p>能力开放平台（ThunderChain）是由国网电动汽车开发的一款对外开放接口的平台，第三方公司可以通过这个平台订阅该接口，能力开放平台有三种计费方式，通过流量计费，通过次数进行计费，包年包月计费三种计费方式，大大为第三方合作商调用接口提供了便利，无平台，不能力；无能力，不平台。</p>
        </section>
    </div>-->

    <h2 class="pannel-title" >合作伙伴</h2>
    <div class="open-pannel">
        <ul>
            <li>
                <div class="card-content">
                    <%--<i class="iconfont icon-qichepeijian"></i>--%>
                    <img src="images/partner.png" height="120" width="120">
                    <div class="card-item-title">合作伙伴1</div>
                </div>
            </li>

            <li>
                <div class="card-content">
                    <%--<i class="iconfont icon-dingyue"></i>--%>
                    <img src="images/partner.png" height="120" width="120">
                    <div class="card-item-title">合作伙伴2</div>
                </div>
            </li>

            <li>
                <div class="card-content">
                    <%--<i class="iconfont icon-31shouye"></i>--%>
                    <img src="images/partner.png" height="120" width="120">
                    <div class="card-item-title">合作伙伴3</div>
                </div>
            </li>

            <li>
                <div class="card-content">
                    <%--<i class="iconfont icon-tianmaodianbaobao"></i>--%>
                    <img src="images/partner.png" height="120" width="120">
                    <div class="card-item-title">合作伙伴4</div>
                </div>
            </li>
        </ul>
    </div>
  <!--  <div class="more-news">
        <a href="index/partner" target="_blank">了解更多合作伙伴信息</a>
    </div>-->
    <%--<div class="open-pannel">--%>
        <%--<ul>--%>
            <%--<li>--%>
                <%--<div class="card-content">--%>
                    <%--<img src="../images/chrome-logo-small.jpg" alt="">--%>
                <%--</div>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<div class="card-content">--%>
                    <%--<img src="../images/firefox-logo-small.jpg" alt="">--%>
                <%--</div>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<div class="card-content">--%>
                    <%--<img src="../images/safari-logo-small.jpg" alt="">--%>
                <%--</div>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<div class="card-content">--%>
                    <%--<img src="../images/chrome-logo-small.jpg" alt="">--%>
                <%--</div>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</div>--%>
</div>
<!-- 成功案例 结束 -->

<!-- 新闻动态 开始 
<div class="resolve-content" data-spm="4">
    <div class="container">
        <h2 class="pannel-title">新闻动态</h2>
            <ul id="news_list">
                <%--<li><h2>人民创投与迅雷共建的基础技术创新实验室揭牌</h2></li>--%>
                <%--<li><h2>技术创新再受肯定 网心科技获“2018创新企业奖”</h2></li>--%>
                <%--<li><h2>迅雷与新大陆达成战略合作 联手推动数字公民建设</h2></li>--%>
                <%--<li><h2>全国政协原副主席齐续春莅临迅雷网心视察指导工作</h2></li>--%>
                <%--<li><h2>迅雷链再添重磅应用，区块链+共享便民服务落地</h2></li>--%>
            </ul>

        <div class="more-news">
            <a href="news/news" target="_blank">查看更多</a>
        </div>
    </div>
</div>-->
<!-- 新闻动态 结束 -->

<!-- 接入流程 开始 -->

<!-- 接入流程 结束 -->
<%@ include file="/common/tb/footer.jsp" %>
</body>
</html>