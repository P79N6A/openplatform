<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单和订购是选择计费模型</title>
    <style>
        .fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns {
            margin-bottom: 2px;
        }
        html, body {
            height: 100%;
            width: 100%;
        }
        .row div {
            border: 1px solid black;
        }
        .tab-pane {
            height: 100%
        }
    </style>
</head>
<body>
<div style="height:100%">

    <ul class="nav nav-tabs nav-pills" role="tablist">
        <li role="presentation" class="active" data-type="1"><a href="#monthlyPayment" aria-controls="home" role="tab"
                                                                data-toggle="tab">包年包月</a></li>
        <li id="payPer-li" role="presentation" data-type="2"><a href="#payPer" aria-controls="profile" role="tab"
                                                                data-toggle="tab">按次收费</a></li>
        <li id="payByFlow-li" role="presentation" data-type="3"><a href="#payByFlow" aria-controls="profile" role="tab"
                                                                   data-toggle="tab">按流量收费</a></li>
    </ul>

    <div class="tab-content" style="height:calc(100% - 8px)">
        <div role="tabpanel" class="tab-pane active" id="monthlyPayment">
            <c:if test="${not empty apiId}">
                <iframe id="monthlyPaymentFrame" height="100%" width="100%"
                        src="order.do?orderConfigChargingList&type=1&ss=${ss}&apiId=${apiId}&orderId=${orderId}"></iframe>
            </c:if>
            <c:if test="${not empty sceneId}">
                <iframe id="monthlyPaymentFrame" height="100%" width="100%"
                        src="order.do?orderConfigChargingList&type=1&ss=${ss}&sceneId=${sceneId}&orderId=${orderId}"></iframe>
            </c:if>

        </div>
        <div role="tabpanel" class="tab-pane" id="payPer">

        </div>
        <div role="tabpanel" class="tab-pane" id="payByFlow">

        </div>
    </div>
    <input id="selectedId" type="hidden" value="${ss}"/>
    <input id="temp" type="hidden" value="${ss}"/>
</div>
<script>
    $(document).ready(function () {
        $("#payPer-li,#payByFlow-li").bind("click", function () {
            var contentDiv = $(this).find("a:first-child").attr("href");
            contentDiv = contentDiv.replace("#", "");
            var html = $("#" + contentDiv).html().trim();
            if (html == null || html == undefined || html == "") {
                var type = $(this).data("type");
                if('${apiId}'!=null && '${apiId}'!=''){
                    $("#" + contentDiv).html("<iframe id='" + contentDiv + "Frame' height=\"100%\" width=\"100%\" src=\"order.do?orderConfigChargingList&type=" + type + "&ss=${ss}&apiId=${apiId}&orderId=${orderId}\"></iframe>");
                }else if('${sceneId}'!=null && '${sceneId}'!=''){
                    $("#" + contentDiv).html("<iframe id='" + contentDiv + "Frame' height=\"100%\" width=\"100%\" src=\"order.do?orderConfigChargingList&type=" + type + "&ss=${ss}&sceneId=${sceneId}&orderId=${orderId}\"></iframe>");
                }
            }
        });
    })
    function refreshSelected(iframeId, selected,chargeType) {
        if (selected == null || selected == undefined) {
            $("#temp").val("");
        } else {
            //将其他iframe中的选中项取消掉
            $(".tab-content").find("iframe").each(function(){
                $(this).contents().find("table").each(function(){
                    var charge = $(this).data("charge")
                    if(charge != undefined){
                        if(charge != chargeType){
                            $(this).find("tr").removeClass("success");
                        }
                    }
                })
            })
            var iframes = ["monthlyPaymentFrame", "payByFlowFrame", "payPerFrame"];
            $.each(iframes, function (i, val) {
                if (val != iframeId) {
                    var temp = $("#temp").val();
                    if (temp != null && temp != "") {
                    }
                }
            })
            var table = $("#" + iframeId).contents().find("#chargeModeList").bootstrapTable("getSelections");
            $("#temp").val(selected.id);
        }
    }
</script>
</body>
</html>