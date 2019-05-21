<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>主表</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <script src="${webRoot}/plug-in/lhgDialog/lhgdialog.min.js"></script>
    <script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
    <script src="${webRoot}/plug-in/tools/curdtools.js"></script>
    <link rel="stylesheet" href="plug-in/select2/css/select2.min.css" type="text/css"/>
    <script type="text/javascript" src="plug-in/select2/js/select2.full.min.js" > </script>
    <style>
        .childTr{
            margin-left:20px;
        }
        .btn-div a{
            border-color:#ccc;
            border-radius:3px;
        }
        #step-1,#step-2,#step-3,#step-4,#step-5{
            padding-top:10px;
        }
        .table-div{
            overflow:auto;
            min-height: 200px;
            max-height: 300px;
        }
    </style>
</head>
<body>
<div style="index:9999">
    <div class="container" style="width:100%;overflow-x:hidden;">
        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-horizontal" role="form" id="addForm" method="POST">
                    <input id="apiId" name="apiInfo.id" value="${apiInfoPage.id}" type="hidden"/>
                    <input type="hidden" name="headers" id="headers" value=""/>
                    <input type="hidden" name="requests" id="requests" />
                    <input type="hidden" name="returns" id="returns" />
                    <div id="smartwizard">
                        <ul>
                            <li><a href="#step-1">基本信息<br /></a></li>
                            <li><a href="#step-2">参数信息<br /></a></li>
                            <li><a href="#step-3">计费模型<br /></a></li>
                            <li><a href="#step-4">流量控制<br /></a></li>
                            <%--<li><a href="#step-5">资源控制<br /></a></li>--%>
                        </ul>
                        <div>
                            <div id="step-1" class="">
                                <fieldset>
                                    <div class="main-form">
                                        <div class="row">
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        能力ID：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <input class="form-control" name="apiInfo.apiName" value = "${apiInfoPage.id}"  type="text" class="form-control input-sm" maxlength="100"  ignore="ignore"  />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        能力名称：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <input name="apiInfo.apiName" value = "${apiInfoPage.apiName}"  type="text" class="form-control" maxlength="100"  ignore="ignore"  />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        能力描述：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <input class="form-control" name="apiInfo.apiDesc" value = "${apiInfoPage.apiDesc}" type="text" class="form-control input-sm" maxlength="200"  ignore="ignore"  />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        能力中心：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <input id="groupName" name="apiInfo.groupId"  type="hidden"  value = "${apiInfoPage.groupId}" />
                                                        <input id="groupSelect" name="apiInfo.groupName" value = "${apiInfoPage.groupName}" type="text" class="form-control" maxlength="30"  ignore="ignore"  disabled="disabled"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        能力调用类型：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <c:if test="${apiInfoPage.apiInvokeType == 1 || empty apiInfoPage.apiInvokeType}">
                                                            <input value = "ISP被动调用能力" type="text" class="form-control" readonly="readonly"  />
                                                        </c:if>
                                                        <c:if test="${apiInfoPage.apiInvokeType == 2}">
                                                            <input value = "ISP主动推送能力" type="text" class="form-control" readonly="readonly"  />
                                                        </c:if>
                                                    </div>
                                                    <span class="col-lg-1 col-md-1 col-sm-1  Range"><span style="color:red">*</span></span>
                                                </div>
                                            </div>
                                            <c:if test="${apiInfoPage.apiInvokeType == 2}">
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12"
                                                     id="apiTopicDiv">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label" id="apiTopicHead">
                                                            Topic：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input id="topicInput" name="apiInfo.apiClassName"
                                                                   value="${apiInfoPage.apiClassName}"
                                                                   type="text"
                                                                   class="form-control" maxlength="200"
                                                                   ignore="ignore"/>
                                                        </div>
                                                        <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                                style="color:red">*</span></span>
                                                    </div>
                                                </div>
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12" id="apiTagDiv">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label" id="apiTagHead">
                                                            tag：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input id="tagInput" name="apiInfo.apiMethodName" value="${apiInfoPage.apiMethodName}"
                                                                   type="text" class="form-control" maxlength="100"
                                                                   ignore="ignore"/>
                                                        </div>
                                                        <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                                style="color:red">*</span></span>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <c:if test="${apiInfoPage.apiInvokeType == 1}">
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12"
                                                     id="webServiceUrlDiv">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label" id="httpAddrHead">
                                                            http请求地址：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input id="endpoint" name="apiInfo.reqAddrHttp" value="${apiInfoPage.reqAddrHttp}" type="text"
                                                                   class="form-control" maxlength="200" ignore="ignore"/>
                                                        </div>
                                                        <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                                style="color:red">*</span></span>
                                                    </div>
                                                </div>
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12"
                                                     id="hsfAddrDiv">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label" id="hsfAddrHead">
                                                            hsf请求地址：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input name="apiInfo.reqAddrHsf" type="text" value="${apiInfoPage.reqAddrHsf}"
                                                                   class="form-control" maxlength="200"
                                                                   ignore="ignore"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12"
                                                     id="hsfGroupDiv">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                            HSF分组：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input id="hsfGroup" name="apiInfo.hsfGroup" value="${apiInfoPage.hsfGroup}" type="text"
                                                                   class="form-control" maxlength="30" ignore="ignore"/>
                                                        </div>
                                                        <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                                style="color:red">*</span></span>
                                                    </div>
                                                </div>
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                            版本号：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input id="version" name="apiInfo.version" value="${apiInfoPage.version}" type="text"
                                                                   class="form-control" maxlength="30" ignore="ignore"/>
                                                        </div>
                                                        <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                                style="color:red">*</span></span>
                                                    </div>
                                                </div>
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12"
                                                     id="apiClassNameDiv">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label"
                                                             id="apiClassNameHead">
                                                            类名称：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input id="apiClassName" name="apiInfo.apiClassName"
                                                                   value="${apiInfoPage.apiClassName}"
                                                                   type="text"
                                                                   class="form-control" maxlength="200"
                                                                   ignore="ignore"/>
                                                        </div>
                                                        <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                                style="color:red">*</span></span>
                                                    </div>
                                                </div>
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12"
                                                     id="apiMethondNameDiv">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label"
                                                             id="apiMethondNameHead">
                                                            方法名称：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <input id="apiInfo_apiMethodName" name="apiInfo.apiMethodName" value="${apiInfoPage.apiMethodName}"
                                                                   type="text" class="form-control" maxlength="100"
                                                                   ignore="ignore"/>
                                                        </div>
                                                        <span class="col-lg-1 col-md-1 col-sm-1  Range"><span
                                                                style="color:red">*</span></span>
                                                    </div>
                                                </div>
                                            </c:if>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        发布状态：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <t:dictSelect  readonly="readonly" field="apiPublishStatus" defaultVal = "${apiInfoPage.apiPublishStatus}" type="select" hasLabel="false" title="发布状态" extendJson="{class:'form-control'}"  typeGroupCode="pubSts" ></t:dictSelect>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        审核状态：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <t:dictSelect readonly="readonly" field="apiAuditStatus" defaultVal = "${apiInfoPage.apiAuditStatus}" type="select" hasLabel="false" title="审核状态" extendJson="{class:'form-control'}"  typeGroupCode="auditSts" ></t:dictSelect>
                                                    </div>
                                                </div>
                                            </div>
                                            <%--<div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                            是否可见：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <t:dictSelect readonly="readonly" field="apiVisibleStatus" defaultVal = "${apiInfoPage.apiVisibleStatus}" type="select" hasLabel="false" title="是否可见" extendJson="{class:'form-control input-sm'}"  typeGroupCode="visiSts" ></t:dictSelect>
                                                    </div>
                                                </div>
                                            </div>--%>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        是否可用：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <t:dictSelect readonly="readonly" field="apiStatus" defaultVal = "${apiInfoPage.apiStatus}" type="select" hasLabel="false" title="是否可用" extendJson="{class:'form-control'}"  typeGroupCode="apiSts" ></t:dictSelect>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        运行状态：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <c:if test="${apiInfoPage.apiRunStatus == 1}">
                                                            <input value = "运行中" type="text" class="form-control" />
                                                        </c:if>
                                                        <c:if test="${apiInfoPage.apiRunStatus == 0}">
                                                            <input value = "停止" type="text" class="form-control" />
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        发布方式：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <label class="checkbox-inline">
                                                            <input  type="checkbox" id="apiInfo_pubMode1" name="apiInfo.pubMode" <c:if test="${fn:contains(apiInfoPage.pubMode, '0')}">checked</c:if> value="0">HTTPS
                                                        </label>
                                                        <label class="checkbox-inline">
                                                            <input type="checkbox" id="apiInfo_pubMode2" name="apiInfo.pubMode" <c:if test="${fn:contains(apiInfoPage.pubMode, '1')}">checked</c:if> value="1">HSF
                                                        </label>
                                                        <label class="checkbox-inline">
                                                            <input type="checkbox" id="apiInfo_pubMode3" name="apiInfo.pubMode" <c:if test="${fn:contains(apiInfoPage.pubMode, '2')}">checked</c:if> value="2">WebService
                                                        </label>
                                                        <label class="checkbox-inline" style="margin-left:0px;">
                                                            <input type="checkbox" id="apiInfo_pubMode4" name="apiInfo.pubMode" <c:if test="${fn:contains(apiInfoPage.pubMode, '3')}">checked</c:if> value="3">Dubbo
                                                        </label>

                                                        <label class="checkbox-inline" style="margin-left:0px;">
                                                            <input type="checkbox" id="apiInfo_pubMode5" name="apiInfo.pubMode" <c:if test="${fn:contains(apiInfoPage.pubMode, '4')}">checked</c:if> value="4">MQ
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                <div class="row">
                                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                        示例数据：
                                                    </div>
                                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                        <textarea rows="4"  name="apiInfo.examData" class="form-control">${apiInfoPage.examData}</textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${apiInfoPage.apiAuditStatus == 2 || apiInfoPage.apiAuditStatus == 3}">
                                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                    <div class="row">
                                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                            审核意见：
                                                        </div>
                                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                            <textarea rows="4"  name="apiInfo.apiAuditMsg" class="form-control">${apiInfoPage.apiAuditMsg}</textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                            <div id="step-2" class="">
                                <c:if test="${type == 2}">
                                    <ul class="nav nav-tabs nav-pills" style="margin-bottom:0" id="subTabs">
                                        <li class="active"><a href="#headerParams" data-toggle="tab">请求头参数</a></li>
                                        <li><a href="#requestParams" data-toggle="tab">请求参数</a></li>
                                        <li><a href="#returnParams" data-toggle="tab">响应参数</a></li>
                                    </ul>
                                    <div class="tab-content" style="background-color:#fff;padding-top:10px;">
                                        <div class="tab-pane fade in active" id="headerParams" >
                                            <iframe id="headerParamFrame" style="width:100%;min-height:300px" src="apiInfoController.do?paramPage&loadType=3&paramType=2&apiId=${apiInfoPage.id}"></iframe>
                                        </div>
                                        <div class="tab-pane fade in" id="requestParams">
                                            <iframe id="requestParamFrame" style="width:100%;min-height:300px" src="apiInfoController.do?paramPage&loadType=3&paramType=0&apiId=${apiInfoPage.id}"></iframe>
                                        </div>
                                        <div class="tab-pane fade in" id="returnParams">
                                            <iframe id="returnParamFrame" style="width:100%;min-height:300px" src="apiInfoController.do?paramPage&loadType=3&paramType=1&apiId=${apiInfoPage.id}"></iframe>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${type == 4}">
                                    <ul class="nav nav-tabs nav-pills" style="margin-bottom:0" id="subTabs">
                                        <li class="active"><a href="#headerParamsS" data-toggle="tab">请求头参数</a></li>
                                        <li><a href="#requestParamsS" data-toggle="tab">请求参数</a></li>
                                        <li><a href="#returnParamsS" data-toggle="tab">响应参数</a></li>
                                    </ul>
                                    <div class="tab-content" style="background-color:#fff;padding-top:10px;">
                                        <div class="tab-pane fade in active" id="headerParamsS" >
                                            <iframe id="headerParamFrameS" style="width:100%;min-height:300px" src="apiInfoController.do?paramPage&loadType=4&paramType=2&apiId=${apiInfoPage.id}"></iframe>
                                        </div>
                                        <div class="tab-pane fade in" id="requestParamsS">
                                            <iframe id="requestParamFrameS" style="width:100%;min-height:300px" src="apiInfoController.do?paramPage&loadType=4&paramType=0&apiId=${apiInfoPage.id}"></iframe>
                                        </div>
                                        <div class="tab-pane fade in" id="returnParamsS">
                                            <iframe id="returnParamFrameS" style="width:100%;min-height:300px" src="apiInfoController.do?paramPage&loadType=4&paramType=1&apiId=${apiInfoPage.id}"></iframe>
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                            <div id="step-3" class="">
                                <input id="chargeModeIds" name="chargeModeIds" type="hidden" value="${chargeModeIds}"/>
                                <ul class="nav nav-tabs nav-pills" role="tablist">
                                    <li role="presentation" class="active" data-type="1"><a href="#monthlyPayment" aria-controls="home" role="tab" data-toggle="tab">包年包月</a></li>
                                    <li id="payPer-li" role="presentation" data-type="2"><a href="#payPer" aria-controls="profile" role="tab" data-toggle="tab">按次收费</a></li>
                                    <li id="payByFlow-li" role="presentation" data-type="3"><a href="#payByFlow" aria-controls="profile" role="tab" data-toggle="tab">按流量收费</a></li>
                                </ul>

                                <div class="tab-content" style="height:calc(100% - 48px)">
                                    <div role="tabpanel" class="tab-pane active" id="monthlyPayment">
                                        <table id="monthlyPaymentList" data-type="1"></table>
                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="payPer">
                                        <table id="payPerList" data-type="2"></table>
                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="payByFlow">
                                        <table id="payByFlowList" data-type="3"></table>
                                    </div>
                                </div>
                            </div>
                            <div id="step-4" class="">
                                <input  id="flowModeIds" name="flowModeIds" type="hidden" value="${flowModeIds}"/>
                                <ul class="nav nav-tabs nav-pills" role="tablist">
                                    <li role="presentation" class="active" data-type="1"><a href="#tactictsByPer" aria-controls="home" role="tab" data-toggle="tab">按次计算</a></li>
                                    <li role="presentation" data-type="2"><a href="#tactictsByFlow" aria-controls="profile" role="tab" data-toggle="tab">按流量计算</a></li>
                                </ul>

                                <div class="tab-content" style="height:calc(100% - 48px)">
                                    <div role="tabpanel" class="tab-pane active" id="tactictsByPer">
                                        <table id="tactictsByPerList" data-type="1"></table>
                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="tactictsByFlow">
                                        <table id="tactictsByFlowList" data-type="2"></table>
                                    </div>
                                </div>
                            </div>
                            <div id="step-5" class="" id="resourceInfo">
                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    function refurbish() {
        if ($("#apiInfo_pubMode1").get(0).checked) {
            $("#webServiceUrlDiv").removeAttr("hidden")
            $("#apiClassNameDiv").removeAttr("hidden")
            $("#httpAddrHead").text("http请求地址：")
            $("#hsfAddrDiv").attr("hidden", "hidden")
            $("#apiTopicDiv").attr("hidden", "hidden")
            $("#apiTagDiv").attr("hidden", "hidden")
            $("#hsfGroupDiv").attr("hidden", "hidden")
            $("#apiClassNameHead").text("类名称：")
            $("#apiMethondNameHead").text("方法名称：")
        }
        if ($("#apiInfo_pubMode2").get(0).checked) {
            $("#apiTopicDiv").attr("hidden", "hidden")
            $("#apiTagDiv").attr("hidden", "hidden")
            $("#hsfAddrDiv").removeAttr("hidden")
            $("#hsfGroupDiv").removeAttr("hidden")
            $("#apiClassNameDiv").removeAttr("hidden")
            $("#apiMethondNameDiv").removeAttr("hidden")
            $("#webServiceUrlDiv").attr("hidden", "hidden")
            $("#apiClassNameHead").text("类名称：")
            $("#apiMethondNameHead").text("方法名称：")
        }
        if ($("#apiInfo_pubMode3").get(0).checked) {
            $("#hsfAddrDiv").removeAttr("hidden")
            $("#apiTopicDiv").attr("hidden", "hidden")
            $("#apiTagDiv").attr("hidden", "hidden")
            $("#hsfGroupDiv").attr("hidden", "hidden")
            $("#webServiceUrlDiv").removeAttr("hidden")
            $("#apiClassNameDiv").removeAttr("hidden")
            $("#apiMethondNameDiv").removeAttr("hidden")
            $("#httpAddrHead").text("webservice发布地址：")
            $("#apiClassNameHead").text("命名空间URL：")
            $("#apiMethondNameHead").text("方法名称：")
            $("#hsfAddrHead").text("webservice接口标识：")
        }

        if ($("#apiInfo_pubMode4").get(0).checked) {
            $("#apiTopicDiv").attr("hidden", "hidden")
            $("#apiTagDiv").attr("hidden", "hidden")
            $("#webServiceUrlDiv").attr("hidden", "hidden")
            $("#hsfAddrDiv").attr("hidden", "hidden")
            $("#hsfGroupDiv").attr("hidden", "hidden")
            $("#apiClassNameDiv").removeAttr("hidden")
            $("#apiMethondNameDiv").removeAttr("hidden")
            $("#apiClassNameHead").text("类名称：")
            $("#apiMethondNameHead").text("方法名称：")
        }

        if ($("#apiInfo_pubMode5").get(0).checked) {
            $("#webServiceUrlDiv").attr("hidden", "hidden")
            $("#hsfAddrDiv").attr("hidden", "hidden")
            $("#hsfGroupDiv").attr("hidden", "hidden")
            $("#apiTopicDiv").removeAttr("hidden")
            $("#apiTagDiv").removeAttr("hidden")
            $("#apiClassNameDiv").removeAttr("hidden")
            $("#apiMethondNameDiv").removeAttr("hidden")
            $("#apiClassNameHead").text("Topic：")
            $("#apiMethondNameHead").text("Tag：")
        }

    }
    $(document).ready(function() {
        refurbish()
        $(".modal-dialog").css("width","1200px");
        $('#smartwizard').smartWizard({
            cycleSteps: true, // Allows to cycle the navigation of steps
            lang: {  // Language variables
                next: '下一步',
                previous: '上一步'
            },
            anchorSettings: {
                anchorClickable: true, // Enable/Disable anchor navigation
                enableAllAnchors: true, // Activates all anchors clickable all times
                markDoneStep: true, // Add done css
                markAllPreviousStepsAsDone: true, // When a step selected by url hash, all previous steps are marked done
                removeDoneStepOnNavigateBack: false, // While navigate back done step after active step will be cleared
                enableAnchorOnDoneStep: true // Enable/Disable the done steps navigation
            },
            theme: 'dots',
            transitionEffect: 'fade', // Effect on navigation, none/slide/fade
            transitionSpeed: '400'
        });
        $("#smartwizard").find("li:first").find("a").click();

        formControlInit();
        $(".firstTr").data("key",dateFtt("hhmmssS",new Date()));

        $("#groupSelect").on("select2:select",function(e){
            var groupName = e.params.data.text;
            $("#groupName").val(groupName);
        });

        function collectParams(tableId){
            var returns = new Array();
            var index = 1;
            $("#" +tableId + " tbody").find("tr").each(function(){
                var key = $(this).data("key");
                if(key && key != undefined){
                    var ret = new Object();
                    ret["paramName"] = $(this).find("input[name$='paramName']").val();
                    ret["paramDesc"] = $(this).find("input[name$='paramDesc']").val();
                    ret["paramVisible"] = $(this).find("select[name$='paramVisible']").val();
                    ret["dataType"] = $(this).find("select[name$='dataType']").val();
                    ret["defaultValue"] = $(this).find("input[name$='defaultValue']").val();
                    //处理子节点
                    var children = new Array();
                    var sort = 1;
                    $("#" +tableId + " tbody").find("tr").each(function(){
                        if($(this).data("parent-key") == key){
                            var child = new Object();
                            child["paramName"] = $(this).find("input[name$='paramName']").val();
                            child["paramDesc"] = $(this).find("input[name$='paramDesc']").val();
                            child["paramVisible"] = $(this).find("select[name$='paramVisible']").val();
                            child["dataType"] = $(this).find("select[name$='dataType']").val();
                            child["defaultValue"] = $(this).find("input[name$='defaultValue']").val();
                            child["sort"] = sort;
                            sort++;
                            children.push(child);
                        }
                    })
                    ret["children"] = children;
                    ret["sort"] = index;
                    index++;
                    returns.push(ret);
                }
            })
            return returns;
        }

        initChargeMode();
        initFlowCtrl();
        //初始化资源控制判断开关
        if($("#resourceCtrl").val() == 1){
            initResourceCtrl();
        }
        $("#resourceCtrlBtn").bootstrapSwitch({
            size : "mini",
            onText : "启用",
            offText : "禁用",
            onColor : "success",
            offColor : "danger",
            // labelWidth: "8",//设置label宽度
            readonly:true,
        })

        $("#addForm :input").attr("readonly","true");
        $("#addForm :checkbox").attr("disabled","true");
        $("#addForm :radio").attr("disabled","true");

        $('#smartwizard').attr("isDone",1);

    });
    function initChargeMode(){
        var tableNames = ["monthlyPaymentList","payPerList","payByFlowList"];
        var chargeModeIds = $("#chargeModeIds").val();
        var apiId = '${apiInfoPage.id}';
        for(var i = 0;i < tableNames.length;i++){
            var tableName = tableNames[i];
            var type = $("#" + tableName).data("type");
            $('#' +tableName).bootstrapTable({
                url : 'charging.do?datagrid&apiId='+apiId, //请求后台的URL（*）
                method : 'post', //请求方式（*）
                contentType : "application/x-www-form-urlencoded",
                striped : false, //是否显示行间隔色
                cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination : true, //是否显示分页（*）
                queryParams : function(params) {
                    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                        pageSize : params.limit, // 每页要显示的数据条数
                        offset : params.offset, // 每页显示数据的开始行号
                        sort : params.sort, // 排序规则
                        order : params.order,
                        rows : params.limit, //页面大小
                        page : (params.offset / params.limit) + 1, //页码
                        pageIndex : params.pageNumber,//请求第几页
                        field : 'id,type,num,startNum,endNum,unit,price'
                    };

                    // var params = $("#chargeModeForm").serializeArray();
                    // for (x in params) {
                    //     temp[params[x].name] = params[x].value;
                    // }
                    temp["type"] = type;
                    return temp;
                },
                sidePagination : "server", //分页方式：client客户端分页，server能力端分页（*）
                pageNumber : 1, //初始化加载第一页，默认第一页
                pageSize : 10, //每页的记录行数（*）
                pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
                clickToSelect : true, //是否启用点击选中行
                // height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", //每一行的唯一标识，一般为主键列
                sortName:'price',
                sortOrder:'asc',
                tableId:tableName,
                columns : [
                    {
                        title : '序号',
                        width : 45,
                        align : 'center',
                        switchable : false,
                        formatter : function(value,row, index) {
                            //return index+1; //序号正序排序从1开始
                            var pageSize = 10
                            var pageNumber = 1;
                            return pageSize* (pageNumber - 1) + index + 1;
                        }
                    },
                    {
                        field : 'id',
                        title : 'id',
                        valign : 'middle',
                        width : 120,
                        visible:false,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        field : 'num',
                        title : '时间',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'startNum',
                        title : '起始值',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'endNum',
                        title : '截止值',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'unit',
                        title : '单位',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                        formatter : function(value,row, index) {
                            if(value == 1){
                                return "周";
                            }else if(value == 2){
                                return "月";
                            }else if(value == 3){
                                return "年";
                            }else if(value == 4){
                                return "KB";
                            }else if(value == 5){
                                return "次";
                            }else {
                                return value
                            }
                        }
                    },
                    {
                        field : 'price',
                        title : '价格(元)',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    }],
                onLoadSuccess : function(data) { //加载成功时执行
                    //将已经选中的计费方式再次选中
                    if(chargeModeIds != null && chargeModeIds != ""){
                        var ids = chargeModeIds.split(",");
                        $("#" + $(this)[0]["tableId"]).bootstrapTable("checkBy", {field:"id", values:ids})
                    }
                },
                onLoadError : function() { //加载失败时执行
                }
            });
            //处理需要隐藏的列
            if(type == 1){
                $("#" + tableName).bootstrapTable("hideColumn","startNum");
                $("#" + tableName).bootstrapTable("hideColumn","endNum");
            }else if(type == 2){
                $("#" + tableName).bootstrapTable("hideColumn","num");
            }else if(type == 3){
                $("#" + tableName).bootstrapTable("hideColumn","num");
            }
        }
    }

    //加载流量控制策略
    function initFlowCtrl(){
        var tableNames = ["tactictsByPerList","tactictsByFlowList"];
        var flowModeIds = $("#flowModeIds").val();
        var apiId = '${apiInfoPage.id}';
        for(var i = 0;i < tableNames.length;i++){
            var tableName = tableNames[i];
            var type = $("#" + tableName).data("type");
            $('#' +tableName).bootstrapTable({
                url : 'resourceController.do?flowdatagrid&apiId='+apiId, //请求后台的URL（*）
                method : 'post', //请求方式（*）
                contentType : "application/x-www-form-urlencoded",
                striped : true, //是否显示行间隔色
                cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination : true, //是否显示分页（*）
                queryParams : function(params) {
                    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                        pageSize : params.limit, // 每页要显示的数据条数
                        offset : params.offset, // 每页显示数据的开始行号
                        sort : params.sort, // 排序规则
                        order : params.order,
                        rows : params.limit, //页面大小
                        page : (params.offset / params.limit) + 1, //页码
                        pageIndex : params.pageNumber,//请求第几页
                        field : 'id,type,maxNum,unit'
                    };

                    temp["type"] = type;
                    return temp;
                },

                sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber : 1, //初始化加载第一页，默认第一页
                pageSize : 10, //每页的记录行数（*）
                pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
                clickToSelect : true, //是否启用点击选中行
                //height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId : "id", //每一行的唯一标识，一般为主键列
                sortName:'unit',
                sortOrder:'asc',
                tableId:tableName,
                columns : [
                    {
                        title : '序号',
                        width : 35,
                        align : 'center',
                        switchable : false,
                        formatter : function(value,row, index) {
                            //return index+1; //序号正序排序从1开始
                            var pageSize = 10
                            var pageNumber = 1;
                            return pageSize* (pageNumber - 1) + index + 1;
                        }
                    },
                    {
                        field : 'id',
                        title : 'id',
                        valign : 'middle',
                        width : 120,
                        visible:false,
                        align : 'center',
                        switchable : true,
                    },
                    {
                        field : 'maxNum',
                        title : '最大访问限制',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                    },{
                        field : 'unit',
                        title : '单位',
                        valign : 'middle',
                        width : 120,
                        align : 'center',
                        switchable : true,
                        formatter : function(value,row, index) {
                            if(value == 1){
                                return "次/秒";
                            }else if(value == 2){
                                return "次/分";
                            }else if(value == 3){
                                return "次时";
                            }else if(value == 4){
                                return "次/天";
                            }else if(value == 5){
                                return "MB/秒";
                            }else if(value == 6){
                                return "MB/分";
                            }else if(value == 7){
                                return "MB/时";
                            }else if(value == 8){
                                return "MB/天";
                            }else {
                                return value
                            }
                        }
                    }],
                onLoadSuccess : function(data) { //加载成功时执行
                    //将已经选中的方式再次选中
                    if(flowModeIds != null && flowModeIds != ""){
                        var ids = flowModeIds.split(",");
                        $("#" + $(this)[0]["tableId"]).bootstrapTable("checkBy", {field:"id", values:ids})
                    }
                },
                onLoadError : function() { //加载失败时执行
                }
            });
        }
    }


    //加载资源控制
    function initResourceCtrl() {
        //构建树
        var setting = {
            view: {
                showLine:true,
                expandSpeed:"fast"
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey:"id",
                    pIdKey:"pId",
                    rootPid:0
                }
            },
            check: {
                enable: true,
                checkStyle:'checkbox',
                chkboxType: { "Y": "s", "N": "s" }
            },
            callback:{
                // onCheck:zTreeOnClick
            }
        };
        var nodes = null;
        var checked = null;
        $.ajax({
            async : false,   //设置为false。请求为同步请求
            cache:false,   	//不设置缓存
            type: 'post',
            dataType : "json",
            data:{
                level:"1",
                type:"api",
                id:$("#apiId").val()
            },
            url: "resourceController.do?getCorpTree",//后台处理程序
            error: function () {//请求失败处理函数
                return false;
            },
            success:function(data){ //请求成功后处理函数。
                nodes = data.nodes; //把后台封装好的简单Json格式赋给treeNodes
                checked = data.checked;//已经被选中的树节点

                $.fn.zTree.init($("#resources"), setting,nodes);
                //初始化树之后将选中的节点在树结构上选中
                var treeObj = $.fn.zTree.getZTreeObj("resources");
                for(var i = 0;i < checked.length;i++){
                    var node =  treeObj.getNodeByParam('id',checked[i]);
                    if(node != null){
                        treeObj.checkNode(node, true);
                        if(node.isParent){
                            treeObj.expandNode(node, true, true, true);
                        }
                    }
                }
            }
        });
    }
</script>
