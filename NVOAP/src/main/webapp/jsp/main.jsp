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
    .container-content {
        width: 1240px;
        margin: 0 auto;
    }
    .resolve-content, .flow-content {
         width: 1240px; 
        margin: 0 auto;
    }
    .pannel-title {
        text-align: center;
        font-size: 36px;
        font-weight: 400;
        position: relative;
        color: #03537B;
    }
    .resolve-content .resolve-list_new {
        overflow: hidden;
    }
    ul {
        margin: 0;
        padding: 0;
    }
    .resolve-content .resolve-list_new li {
        float: left;
        width: 380px;
        height: 230px;
        display: table;
        position: relative;
    }
    .resolve-content li {
        margin-top: 20px;
        background-color: #fff;
        margin-left: 10px;
        margin-right: 10px;
        margin-bottom: 10px;
    }
    .card-content {
        text-align: center;
        padding-top: 60px;
    }
    .resolve-content .resolve-list_new li .card-item-title {
        font-size: 24px;
        margin-top: 20px;
        font-weight: bold;
    }
    .resolve-content .resolve-list_new li .card-item-content {
        font-size: 14px;
        margin-top: 20px;
        margin-left: 15%;
        margin-right: 15%;
    }
    </style>
</head>
<body>
<%@ include file="/common/tb/header.jsp" %>
<!-- banner开始 -->
<!-- banner开始 -->
<div class="banner" data-spm="2" data-spm-max-idx="6">
    <div class="slider" id="slider">
        <%--<li>--%>
            <%--<a href="#" target="_blank" data-spm-anchor-id="a219a.7386653.2.1">--%>
                <img src="images/banner1.jpg" alt="">
            <%--</a>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<a href="#" target="_blank" data-spm-anchor-id="a219a.7386653.2.2">--%>
                <img src="images/capital.jpg" alt="">
            <%--</a>--%>
        <%--</li>
                <img src="images/index/first.jpg" alt="">
                <img src="images/index/second.jpg" alt="">--%>
    </div>

    <ul class="slider-nav" id="slider-nav" style="left:730px">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
   
	    <a href="/ability" target="_blank" style="vertical-align:middle;">
		<img id="banner-btn" src="images/xqdj.png"
			 style="width:172px;height:52px;position: absolute;left:588px;top:300px" alt=""></a>
	

    <div class="notice">
        <i class="iconfont icon-voice"></i>
        <ul class="notice-list clearfix" id="notice_list">
        </ul>
        <a class="more-notice" href="inform/informDetail" target="_blank" data-spm-anchor-id="a219a.7386653.2.6">查看更多 &gt;&gt;</a>
    </div>
</div>
<!-- banner结束 -->


<!-- 平台特色 开始 -->
<div class="container" data-spm="3">
    <h2 class="pannel-title">平台特色</h2>
    <div class="open-pannel">
        <section>
           <!-- <p>迅雷链（ThunderChain）是由迅雷旗下网心科技打造的超级区块链平台，是全球最大规模ToC区块链商业生态，赋能实体经济，致力于成为ToC现象级区块链应用的摇篮。基于迅雷深耕十几年，拥有多项专利的分布式技术，创造性地将共享计算和区块链相结合，具备全球领先的百万TPS高并发、秒级确认的处理能力。</p>
            <p>基于玩客云搭建的分布式云计算平台星域云，是全球首个拥有百万级参与用户的现象级区块链商业应用，通过用户共享闲置资源，有效降低了企业的运营成本，形成价值循环，赋能实体经济。</p>-->
        </section>
        <ul>
            <li>
                <div class="card-content">
                 <!--<i class="iconfont icon-qichepeijian"></i>-->
                    <img src="images/nengli.png">
                    <div style="font-size:22px;margin-top:20px;">能力强大 持续开放</div>
                    <p class="card-item-intro"></p> 
                </div>
                <div class="card-item-more">
                    <div class="title">能力强大 持续开放</div>
                    <div class="desc">提供业务、数据、资源等多种能力开放，并持续拓展智慧车联网的能力开放范围。</div>
                 <!--   <a href="https://istore.tmall.com" target="_blank">详情 &gt;</a>-->
                </div>
            </li>
            <li>
                <div class="card-content">
                   <!-- <i class="iconfont icon-dingyue"></i>-->
                     <img src="images/jieru.png">
                    <div style="font-size:22px;margin-top:20px;">一站接入 便捷灵活</div>
                     <p class="card-item-intro"></p> 
                </div>
                <div class="card-item-more">
                    <div class="title">一站接入 便捷灵活</div>
                    <div class="desc">将需求对接、接口设计、开发测试、上线部署、应用发布、运营迭代统一集成至能力开放平台，提供线上一站式服务，快捷合作，降本增效。</div>
                    <!-- <a href="#" target="_blank">详情 &gt;</a>-->
                </div>
            </li>

            <li>
                <div class="card-content">
                  <!--  <i class="iconfont icon-31shouye"></i>-->
                     <img src="images/fuwu.png">
                    <div style="font-size:22px;margin-top:20px;">服务之本 开放共赢</div>
                    <!-- <p class="card-item-intro">分享五亿月活跃用户、移动云基础设施，以及丰富的API、SDK组件等</p> -->
                </div>
                <div class="card-item-more">
                    <div class="title">服务之本 开放共赢</div>
                    <div class="desc">强大运营团队支持与合作伙伴的定制化开放合作，融合创新。</div>
                    <!-- <a href="#" target="_blank">详情 &gt;</a> -->
                </div>
            </li>
            <li>
                <div class="card-content">
                   <!-- <i class="iconfont icon-tianmaodianbaobao"></i>-->
                     <img src="images/kaifang.png">
                    <div style="font-size:22px;margin-top:20px;">技术领航 安全保障</div>
                    <!--  <p class="card-item-intro">提供高可用网关，保障网络安全、通信安全和接入稳定性</p> -->
                </div>
                <div class="card-item-more">
                    <div class="title">技术领航 安全保障</div>
                    <div class="desc">基于“云平台+微服务” 互联网架构，具备协议转换、流量控制、权限分配、安全加密等强大技术支撑，实现能力开放安全、可视、可控。</div>
                   <!--   <a href="#" target="_blank">详情 &gt;</a>-->
                </div>
            </li>
        </ul>
    </div>
</div>
<!-- 平台特色结束 -->

<!-- 开放场景 开始 -->


<div class="resolve-content">
<div class="container-content">
	<h2 class="pannel-title">开放场景</h2>
	<ul class="resolve-list_new clearfix" style="background-color:#DAE2F3;">
		<li class="merch-operation" style="background: url(images/shudihua.png) no-repeat top">
			<div class="card-content">
			</div>
		</li>
		<li class="">
			<div class="card-content">
				<div class="card-item-title">充电</div>
				<div class="card-item-content">实现地图找桩、启停充电等业务功能的集成</div>
			</div>
		</li>
		<li class="merch-operation" style="background: url(images/neiwaiwang.png) no-repeat top">
			<div class="card-content">
			</div>
		</li>
		<li class="">
			<div class="card-content">
				<div class="card-item-title">属地化定制</div>
				<div class="card-item-content">为大客户提供业务和数据能力，支撑合资公司业务拓展</div>
			</div>
		</li>
		<li class="merch-operation" style="background: url(images/chongdian.png) no-repeat top">
			<div class="card-content">
			</div>
		</li>
		<li class="">
			<div class="card-content">
				<div class="card-item-title">内外网贯通</div>
				<div class="card-item-content">支撑信息内外网的业务和数据贯通，实现系统间集成</div>
			</div>
		</li>
	</ul>
</div>
</div>



<!-- <div class="resolve-content" data-spm="4">
    <div class="container">
        <h2 class="pannel-title">开放场景</h2>
        <ul class="resolve-list clearfix">
            <li class="merch-operation">
                <div class="card-content">
                    <i class="iconfont icon-serviceyuanchengfuwu"></i>
                    <div style="font-size:22px;margin-top:20px;">充电</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-serviceyuanchengfuwu"></i>
                    <div class="title">充电</div>
                    <div class="desc">实现地图找桩、启停充电等业务功能的集成</div>
                  
                </div>
            </li>
            <li class="istore-production">
                <div class="card-content">
                    <i class="iconfont icon-31shouye"></i>
                    <div style="font-size:22px;margin-top:20px;">属地化定制</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-31shouye"></i>
                    <div class="title">属地化定制</div>
                    <div class="desc">为大客户提供业务和数据能力，支撑合资公司业务拓展</div>
                </div>
            </li>
            <li class="mtaobao-open">
                <div class="card-content">
                    <i class="iconfont icon-gongnengjianyi"></i>
                    <div style="font-size:22px;margin-top:20px;">内外网贯通</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-gongnengjianyi"></i>
                    <div class="title">内外网贯通</div>
                    <div class="desc">支撑信息内外网的业务和数据贯通，实现系统间集成</div>
                </div>
            </li>
             <li class="mtaobao-open">
                <div class="card-content">
                    <i class="iconfont icon-gongnengjianyi"></i>
                    <div style="font-size:22px;margin-top:20px;">公交小工具</div>
                </div>
                <div class="card-item-more">
                    <i class="iconfont icon-gongnengjianyi"></i>
                    <div class="title">公交小工具</div>
                    <div class="desc">提供便利化服务</div>
                   <!-- <a href="#" target="_blank">查看详情</a>-->
                </div>
            </li>
        </ul>
       
    </div>
</div>-->
<!-- 开放场景 结束 -->

<!-- 行业应用 开始 -->

<div class="container" data-spm="3">
    <h2 class="pannel-title">行业应用</h2>
    <div class="open-pannel">
        <section>
           <!-- <p>迅雷链（ThunderChain）是由迅雷旗下网心科技打造的超级区块链平台，是全球最大规模ToC区块链商业生态，赋能实体经济，致力于成为ToC现象级区块链应用的摇篮。基于迅雷深耕十几年，拥有多项专利的分布式技术，创造性地将共享计算和区块链相结合，具备全球领先的百万TPS高并发、秒级确认的处理能力。</p>
            <p>基于玩客云搭建的分布式云计算平台星域云，是全球首个拥有百万级参与用户的现象级区块链商业应用，通过用户共享闲置资源，有效降低了企业的运营成本，形成价值循环，赋能实体经济。</p>-->
        </section>
        <ul>
            <li>
                <div class="card-content">
                 <!--<i class="iconfont icon-qichepeijian"></i>-->
                    <img src="images/nengli.png">
                    <div style="font-size:22px;margin-top:20px;">车辆运营管理平台</div>
                    <p class="card-item-intro"></p> 
                </div>
                <div class="card-item-more">
                    <div class="title">车辆运营管理平台</div>
                    <div class="desc">打通车辆行驶数据与充电数据，综合电池信息、行驶习惯、驾驶路线、充电数据共同挖掘用户行为特征，为用户提供个性化充电服务、车辆保养服务。</div>
                 <!--   <a href="https://istore.tmall.com" target="_blank">详情 &gt;</a>-->
                </div>
            </li>
            <li>
                <div class="card-content">
                   <!-- <i class="iconfont icon-dingyue"></i>-->
                     <img src="images/jieru.png">
                    <div style="font-size:22px;margin-top:20px;">充电桩生产企业</div>
                     <p class="card-item-intro"></p> 
                </div>
                <div class="card-item-more">
                    <div class="title">充电桩生产企业</div>
                    <div class="desc">开放充电桩运行、运维数据，帮助充电桩企业利用故障告警等异常信息判断充电桩设计制造需提升的部分，提高充电桩设计制造质量，利用评价信息，提高充电桩的用户体验。</div>
                    <!-- <a href="#" target="_blank">详情 &gt;</a>-->
                </div>
            </li>

            <li>
                <div class="card-content">
                  <!--  <i class="iconfont icon-31shouye"></i>-->
                     <img src="images/fuwu.png">
                    <div style="font-size:22px;margin-top:20px;">车辆租赁平台</div>
                    <!-- <p class="card-item-intro">分享五亿月活跃用户、移动云基础设施，以及丰富的API、SDK组件等</p> -->
                </div>
                <div class="card-item-more">
                    <div class="title">车辆租赁平台</div>
                    <div class="desc">共享电动汽车分时租赁平台的车辆运行信息、停车站点信息，开放充电等业务能力，拓宽双方业务渠道，提升租车用户的充电便利性。</div>
                    <!-- <a href="#" target="_blank">详情 &gt;</a> -->
                </div>
            </li>
            <li>
                <div class="card-content">
                   <!-- <i class="iconfont icon-tianmaodianbaobao"></i>-->
                     <img src="images/kaifang.png">
                    <div style="font-size:22px;margin-top:20px;">地图服务商</div>
                    <!--  <p class="card-item-intro">提供高可用网关，保障网络安全、通信安全和接入稳定性</p> -->
                </div>
                <div class="card-item-more">
                    <div class="title">地图服务商</div>
                    <div class="desc">开放充电站点位置及状态等数据能力，共享车辆驾驶路线及周边商业服务信息，利用地图服务商渠道进行引流，提升充电体验的同时，获取更多用户。</div>
                   <!--   <a href="#" target="_blank">详情 &gt;</a>-->
                </div>
            </li>
        </ul>
    </div>
<!-- 行业应用 结束 -->


<!-- 应用案例 开始 -->

<div class="container" data-spm="3">
    <h2 class="pannel-title">应用案例</h2>
    <div class="open-pannel">
        <section>
           <!-- <p>迅雷链（ThunderChain）是由迅雷旗下网心科技打造的超级区块链平台，是全球最大规模ToC区块链商业生态，赋能实体经济，致力于成为ToC现象级区块链应用的摇篮。基于迅雷深耕十几年，拥有多项专利的分布式技术，创造性地将共享计算和区块链相结合，具备全球领先的百万TPS高并发、秒级确认的处理能力。</p>
            <p>基于玩客云搭建的分布式云计算平台星域云，是全球首个拥有百万级参与用户的现象级区块链商业应用，通过用户共享闲置资源，有效降低了企业的运营成本，形成价值循环，赋能实体经济。</p>-->
        </section>
        <ul>
            <li>
                <div class="card-content">
                 <!--<i class="iconfont icon-qichepeijian"></i>-->
                    <img src="images/shandonggongjiao.png">
                    <div style="font-size:22px;margin-top:20px;">山东公交小工具</div>
                    <p class="card-item-intro"></p> 
                </div>
                <div class="card-item-more">
                    <div class="title">山东公交小工具</div>
                    <div class="desc">数据能力开放的典型应用，开放订单数据、用户数据、支付数据，为山东合资公司大客户提供属地化运营支撑。</div>
                 <!--   <a href="https://istore.tmall.com" target="_blank">详情 &gt;</a>-->
                </div>
            </li>
            <li>
                <div class="card-content">
                   <!-- <i class="iconfont icon-dingyue"></i>-->
                     <img src="images/yuexing.png">
                    <div style="font-size:22px;margin-top:20px;">渝e行</div>
                     <p class="card-item-intro"></p> 
                </div>
                <div class="card-item-more">
                    <div class="title">渝e行</div>
                    <div class="desc">业务能力开放的典型应用，开放用户管理、找桩充电，为e充电、e约车在重庆地区的子品牌建设提供支撑。</div>
                    <!-- <a href="#" target="_blank">详情 &gt;</a>-->
                </div>
            </li>

            <li>
                <div class="card-content">
                  <!--  <i class="iconfont icon-31shouye"></i>-->
                     <img src="images/95598.png">
                    <div style="font-size:22px;margin-top:20px;">国网客服中心业务系统</div>
                    <!-- <p class="card-item-intro">分享五亿月活跃用户、移动云基础设施，以及丰富的API、SDK组件等</p> -->
                </div>
                <div class="card-item-more">
                    <div class="title">国网客服中心业务系统</div>
                    <div class="desc">数据能力开放的典型应用，开放工单数据，实现国网电动汽车与国网客服中心的内外网数据双向贯通。</div>
                    <!-- <a href="#" target="_blank">详情 &gt;</a> -->
                </div>
            </li>
            <li>
                <div class="card-content">
                   <!-- <i class="iconfont icon-tianmaodianbaobao"></i>-->
                     <img src="images/huandianzhankong.png">
                    <div style="font-size:22px;margin-top:20px;">地市公司换电站站控系统</div>
                    <!--  <p class="card-item-intro">提供高可用网关，保障网络安全、通信安全和接入稳定性</p> -->
                </div>
                <div class="card-item-more">
                    <div class="title">地市公司换电站站控系统</div>
                    <div class="desc">业务能力开放的典型应用，开放资产管理、订单管理，接入内网换电站管理平台资产、订单。</div>
                   <!--   <a href="#" target="_blank">详情 &gt;</a>-->
                </div>
            </li>       
        </ul>
    </div>
<!--应用案例 结束 -->



<!-- 接入流程 开始 -->

<div class="container" data-spm="3">
    <h2 class="pannel-title">接入流程</h2>
     <h3  style="text-align:center">ISP(能力提供者)</h3>
    <div class="open-pannel-n">
        <section>
           <!-- <p>迅雷链（ThunderChain）是由迅雷旗下网心科技打造的超级区块链平台，是全球最大规模ToC区块链商业生态，赋能实体经济，致力于成为ToC现象级区块链应用的摇篮。基于迅雷深耕十几年，拥有多项专利的分布式技术，创造性地将共享计算和区块链相结合，具备全球领先的百万TPS高并发、秒级确认的处理能力。</p>
            <p>基于玩客云搭建的分布式云计算平台星域云，是全球首个拥有百万级参与用户的现象级区块链商业应用，通过用户共享闲置资源，有效降低了企业的运营成本，形成价值循环，赋能实体经济。</p>-->
        </section>
        <ul>
            <li>
                <div class="card-content"style="background: url(../images/ISP_bj1.png) no-repeat top;height: 249px;padding-top: 20px;margin-top: 20px;">
                <div style="font-size: 24px;padding-top: 20px;color:#fff;">①</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">注册授权</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 15%;margin-right: 15%;color:#fff;">填写表单注册成为开放平台的发布者</div>
                </div>
             
            </li>
            <li>
                <div class="card-content"style="background: url(../images/ISP_bj1.png) no-repeat top;height: 249px;padding-top: 20px;margin-top: 20px;">
                <div style="font-size: 24px;padding-top: 20px;color:#fff;">②</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">能力发布准备</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 15%;margin-right: 15%;color:#fff;">维护计费策略、流量控制策略、安全控制策略</div>
                </div>
             
            </li>

            <li>
                <div class="card-content"style="background: url(../images/ISP_bj1.png) no-repeat top;height: 249px;padding-top: 20px;margin-top: 20px;">
                <div style="font-size: 24px;padding-top: 20px;color:#fff;">③</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">发布能力</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 15%;margin-right: 15%;color:#fff;">填写能力信息，发布能力并申请审核</div>
                </div>
            
            </li>
            <li>
               <div class="card-content"style="background: url(../images/ISP_bj1.png) no-repeat top;height: 249px;padding-top: 20px;margin-top: 20px;">
                <div style="font-size: 24px;padding-top: 20px;color:#fff;">④</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">创建业务场景</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 15%;margin-right: 15%;color:#fff;">自由组合API，形成业务开放场景解决方案</div>
                </div>
            </li>   
            <li>
                <div class="card-content"style="background: url(../images/ISP_bj1.png) no-repeat top;height: 249px;padding-top: 20px;margin-top: 20px;">
                <div style="font-size: 24px;padding-top: 20px;color:#fff;">⑤</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">配合测试&迭代</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 15%;margin-right: 15%;color:#fff;">配合ISV开发、测试，更新迭代接口</div>
                </div>       
            </li>               
        </ul>
       </div> 
        
        
        
        
        
    <h3  style="text-align:center">ISV(应用开发者)</h3>
    <div class="open-pannel">
        <section>
           <!-- <p>迅雷链（ThunderChain）是由迅雷旗下网心科技打造的超级区块链平台，是全球最大规模ToC区块链商业生态，赋能实体经济，致力于成为ToC现象级区块链应用的摇篮。基于迅雷深耕十几年，拥有多项专利的分布式技术，创造性地将共享计算和区块链相结合，具备全球领先的百万TPS高并发、秒级确认的处理能力。</p>
            <p>基于玩客云搭建的分布式云计算平台星域云，是全球首个拥有百万级参与用户的现象级区块链商业应用，通过用户共享闲置资源，有效降低了企业的运营成本，形成价值循环，赋能实体经济。</p>-->
        </section>
        <ul>
            <li>
                <div class="card-content"style="background: url(../images/ISP_bj2.png) no-repeat top;height: 249px;margin-top: 30px;">
                <div style="font-size: 24px;padding-top: 20px;margin-top:-60px;color:#fff;">①</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">注册认证</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 20%;margin-right: 20%;color:#fff;">填写表单注册成为开放平台的开发者</div>
                </div>
             
            </li>
            <li>
                <div class="card-content"style="background: url(../images/ISP_bj2.png) no-repeat top;height: 249px;margin-top: 30px;">
                <div style="font-size: 24px;padding-top: 20px;margin-top:-60px;color:#fff;">②</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">创建应用</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 20%;margin-right: 20%;color:#fff;">当您完成入驻，选择应用类型进行创建应用</div>
                </div>
             
            </li>

            <li>
                <div class="card-content"style="background: url(../images/ISP_bj2.png) no-repeat top;height: 249px;margin-top: 30px;">
                <div style="font-size: 24px;padding-top: 20px;margin-top:-60px;color:#fff;">③</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">开发&测试</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 30%;margin-right: 30%;color:#fff;">您需要订阅能力开始进行开发</div>
                </div>
            
            </li>
            <li>
               <div class="card-content"style="background: url(../images/ISP_bj2.png) no-repeat top;height: 249px;margin-top: 30px;">
                <div style="font-size: 24px;padding-top: 20px;margin-top:-60px;color:#fff;">④</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">发布上线</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 30%;margin-right: 30%;color:#fff;">完成开发后发布到应用市场</div>
                </div>
            </li>            
        </ul>
        </div>
        
        
    <h3  style="text-align:center">用户</h3>
    <div class="open-pannel">
        <section>
           <!-- <p>迅雷链（ThunderChain）是由迅雷旗下网心科技打造的超级区块链平台，是全球最大规模ToC区块链商业生态，赋能实体经济，致力于成为ToC现象级区块链应用的摇篮。基于迅雷深耕十几年，拥有多项专利的分布式技术，创造性地将共享计算和区块链相结合，具备全球领先的百万TPS高并发、秒级确认的处理能力。</p>
            <p>基于玩客云搭建的分布式云计算平台星域云，是全球首个拥有百万级参与用户的现象级区块链商业应用，通过用户共享闲置资源，有效降低了企业的运营成本，形成价值循环，赋能实体经济。</p>-->
        </section>
        <ul>
            <li>
                <div class="card-content"style="background: url(../images/ISP_bj3.png) no-repeat top;height: 200px;margin-top: 30px;">
                <div style="font-size: 24px;margin-top: -20px;color:#fff;    margin-top: -30px;">①</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">注册登录</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 25%;margin-right: 25%;color:#fff;">填写表单注册成为开放平台的消费者</div>
                </div>
             
            </li>
            <li>
                <div class="card-content"style="background: url(../images/ISP_bj3.png) no-repeat top;height: 249px;margin-top: 30px;">
                <div style="font-size: 24px;margin-top: -20px;color:#fff;    margin-top: -30px;">②</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">订购应用</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 25%;margin-right: 25%;color:#fff;">在应用市场选择应用场景订购应用</div>
                </div>
             
            </li>

            <li>
                <div class="card-content"style="background: url(../images/ISP_bj3.png) no-repeat top;height: 249px;margin-top: 30px;">
                <div style="font-size: 24px;margin-top: -20px;color:#fff;    margin-top: -30px;">③</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">应用部署授权</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 25%;margin-right: 25%;color:#fff;">开放平台自动将应用部署至商户平台、移动终端或其他渠道</div>
                </div>
            
            </li>
            <li>
               <div class="card-content"style="background: url(../images/ISP_bj3.png) no-repeat top;height: 249px;margin-top: 30px;">
                <div style="font-size: 24px;margin-top: -20px;color:#fff;    margin-top: -30px;">④</div>
                 <div style="font-size: 24px;margin-top: 20px;color:#fff;">用应用反馈</div>
                 <div style="font-size: 14px;margin-top: 20px;margin-left: 25%;margin-right: 25%;color:#fff;">用户登录应用使用应用</div>
                </div>
            </li>            
        </ul>
    </div>
   </div>


<!--接入流程 结束 -->

<%@ include file="/common/tb/footer_only.jsp" %>

</body>
</html>