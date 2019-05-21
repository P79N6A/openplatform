<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="shortcut icon" href="images/favicon.ico">
<style>
</style>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/home/theme.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/home/app1.css" %> type="text/css">
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/jquery.min.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/js/common.js"%>"></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/webpage/main/hplushome.js"%>"></script>
</head>

<body class="gray-bg" style="background:#fff;font-size: 20px;">
    <div class="col-md-12 monitor-summary-module" style="margin-top:20px">
        <div class="row summary-modules ng-scope">
            <div class="col-lg-12 margin-t init">
                <div class="panel pannel-default monitor-summary-module-title">
                    <div class="pannel-body">
                        <div class="col-lg-2 summary-icon">
                        <span>
                           <img ng-src="/images/ECS.png" src="images/ECS.png">
                           <span class="ng-binding">&nbsp;ECS</span>
                        </span>
                        </div>
                        <div href="#" class="col-lg-2 summary-module display-inline">
                            <span class="summary-font alarmFontMargin">总数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-instance({tabid: product.type})" class="color-a alarmNumberSize ng-binding" href="#/app/monitor-cloud-product?tabid=ECS">106</a>
                        </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">ISP数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({tabid: product.type})" class="color-b alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?tabid=ECS">84</a>
                        </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">ISV数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({status: 'ALARM', tabid: product.type})" class="color-c alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?status=ALARM&amp;tabid=ECS">22</a>
                        </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">告警数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({status: 'INSUFFICIENT_DATA', tabid: product.type})" class="color-d alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?status=INSUFFICIENT_DATA&amp;tabid=ECS">9</a>
                        </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font border-r alarmFontMargin">暂停数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({startStatus: 'false', tabid: product.type})" class="color-e alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?tabid=ECS&amp;startStatus=false">3</a>
                        </span>
                        </div>
                    </div>
                </div>
            </div>
            <div ng-repeat="region in product.regions" ng-if="region.name !='全部'" class="col-lg-6 mt-lg width-min ng-scope">
                <div class="panel panel-default region-box monitor-summary-module-content">
                    <div class="panel-heading">
                        <h5 class="span ng-binding">ISP</h5>
                    </div>
                    <div class="panel-body">
                        <div class="region-content clearfix" style="height:100%">
                            <div id="ispPie" style="width:100%;height:100%;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div ng-repeat="region in product.regions" ng-if="region.name !='全部'" class="col-lg-6 mt-lg width-min ng-scope">
                <div class="panel panel-default region-box monitor-summary-module-content">
                    <div class="panel-heading">
                        <h5 class="span ng-binding">ISV</h5>
                    </div>
                    <div class="panel-body">
                        <div class="region-content clearfix" style="height:100%">
                            <div id="isvPie" style="width:100%;height:100%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row summary-modules ng-scope">
            <div class="col-lg-12 margin-t init">
                <div class="panel pannel-default monitor-summary-module-title">
                    <div class="pannel-body">
                        <div class="col-lg-2 summary-icon">
                        <span>
                           <img ng-src="/images/ECS.png" src="images/SLB.png">
                           <span class="ng-binding">&nbsp;能力</span>
                        </span>
                        </div>
                        <div href="#" class="col-lg-2 summary-module display-inline">
                            <span class="summary-font alarmFontMargin">能力总数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-instance({tabid: product.type})" class="color-a alarmNumberSize ng-binding" href="#/app/monitor-cloud-product?tabid=ECS">596</a>
                        </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">能力中心数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({tabid: product.type})" class="color-b alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?tabid=ECS">12</a>
                        </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">在运能力数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({status: 'ALARM', tabid: product.type})" class="color-c alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?status=ALARM&amp;tabid=ECS">596</a>
                        </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">故障/暂停数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({status: 'INSUFFICIENT_DATA', tabid: product.type})" class="color-d alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?status=INSUFFICIENT_DATA&amp;tabid=ECS">3</a>
                        </span>
                        </div>
                        <%--<div class="col-lg-2 summary-module">
                            <span class="summary-font border-r alarmFontMargin">暂停</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({startStatus: 'false', tabid: product.type})" class="color-e alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?tabid=ECS&amp;startStatus=false">3</a>
                        </span>
                        </div>--%>
                    </div>
                </div>
            </div>
            <div ng-repeat="region in product.regions" ng-if="region.name !='全部'" class="col-lg-6 mt-lg width-min ng-scope">
                <div class="panel panel-default region-box monitor-summary-module-content">
                    <div class="panel-heading">
                        <h5 class="span ng-binding">服务总览</h5>
                    </div>
                    <div class="panel-body">
                        <div class="region-content clearfix" style="height:100%">
                            <div id="servicePie" style="width:100%;height:100%;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div ng-repeat="region in product.regions" ng-if="region.name !='全部'" class="col-lg-6 mt-lg width-min ng-scope">
                <div class="panel panel-default region-box monitor-summary-module-content">
                    <div class="panel-heading">
                        <h5 class="span ng-binding">服务调用排名</h5>
                    </div>
                    <div class="panel-body">
                        <div class="region-content clearfix" style="height:100%">
                            <div id="serviceBar" style="width:100%;height:100%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row summary-modules ng-scope">
            <div class="col-lg-12 margin-t init">
                <div class="panel pannel-default monitor-summary-module-title">
                    <div class="pannel-body">
                        <div class="col-lg-2 summary-icon">
                            <span>
                               <img ng-src="/images/ECS.png" src="images/OSS.png">
                               <span class="ng-binding">&nbsp;应用</span>
                            </span>
                        </div>
                        <div href="#" class="col-lg-2 summary-module display-inline">
                            <span class="summary-font alarmFontMargin">总数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-instance({tabid: product.type})" class="color-a alarmNumberSize ng-binding" href="#/app/monitor-cloud-product?tabid=ECS">6</a>
                            </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">调用数</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({tabid: product.type})" class="color-b alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?tabid=ECS">141350</a>
                            </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">流量总计</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({status: 'ALARM', tabid: product.type})" class="color-c alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?status=ALARM&amp;tabid=ECS">11.3GB</a>
                            </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font alarmFontMargin">成功</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({status: 'INSUFFICIENT_DATA', tabid: product.type})" class="color-d alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?status=INSUFFICIENT_DATA&amp;tabid=ECS">141170</a>
                            </span>
                        </div>
                        <div class="col-lg-2 summary-module">
                            <span class="summary-font border-r alarmFontMargin">失败</span>
                            <span class="summary-num"><a ui-sref="app.cloud-monitor-alarm({startStatus: 'false', tabid: product.type})" class="color-e alarmNumberSize ng-binding" href="#/app/monitor-alarmmngt-allproduct?tabid=ECS&amp;startStatus=false">180</a>
                        </span>
                    </div>
                </div>
            </div>
            <div ng-repeat="region in product.regions" ng-if="region.name !='全部'" class="col-lg-6 mt-lg width-min ng-scope">
                <div class="panel panel-default region-box monitor-summary-module-content">
                    <div class="panel-heading">
                        <h5 class="span ng-binding">实时次数统计</h5>
                    </div>
                    <div class="panel-body">
                        <div class="region-content clearfix" style="height:100%">
                            <div id="appNumLine" style="width:100%;height:100%;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div ng-repeat="region in product.regions" ng-if="region.name !='全部'" class="col-lg-6 mt-lg width-min ng-scope">
                <div class="panel panel-default region-box monitor-summary-module-content">
                    <div class="panel-heading">
                        <h5 class="span ng-binding">实时流量统计</h5>
                    </div>
                    <div class="panel-body">
                        <div class="region-content clearfix" style="height:100%">
                            <div id="appFlowLine" style="width:100%;height:100%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src=<%= request.getContextPath() +"/js/jquery-1.11.3.min.js"%>></script>
<script type="text/javascript" src="<%=request.getContextPath() + "/plug-in/echarts/echarts.js"%>"></script>
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap.js" %> type="text/javascript"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<script src="plug-in-ui/hplus/js/content.js"></script>
</html>
