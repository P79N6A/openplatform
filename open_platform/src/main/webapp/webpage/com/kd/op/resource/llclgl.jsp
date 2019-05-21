<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <%--<link rel="stylesheet" href="${webRoot}/plug-in/themes/naturebt/css/search-form.css">--%>
    <style>
        .fixed-table-toolbar .bs-bars,.fixed-table-toolbar .search,.fixed-table-toolbar .columns
        {
            margin-bottom: 2px;
        }
        html,body{
            height:100%;
            width:100%;
        }
        .row div{
            border:1px solid black;
        }
        .tab-pane{
            height:100%
        }
    </style>
</head>
<body>
<div style="height:100%">

    <ul class="nav nav-tabs nav-pills" role="tablist">

        <li class="active" role="presentation" data-type="1"><a href="#payPer" aria-controls="profile" role="tab" data-toggle="tab">按次计算</a></li>
        <li id="payByFlow-li" role="presentation" data-type="2"><a href="#payByFlow" aria-controls="profile" role="tab" data-toggle="tab">按流量计算</a></li>
    </ul>

    <div class="tab-content" style="height:calc(100% - 48px)">
        <div role="tabpanel" class="tab-pane active" id="payPer">
            <iframe height="100%" width="100%" src="resourceController.do?flowModeList&type=1"></iframe>
        </div>

        <div role="tabpanel" class="tab-pane" id="payByFlow">

        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#payByFlow-li").bind("click",function(){
            var contentDiv = $(this).find("a:first-child").attr("href");
            var html = $(contentDiv).html().trim();
            if(html == null || html == undefined || html == ""){
                var type = $(this).data("type");
//                $(contentDiv).html("<iframe height=\"100%\" width=\"100%\" src=\"resourceController.do?flowModeList&type=2\"></iframe>");
                $(contentDiv).html("<iframe height=\"100%\" width=\"100%\" src=\"resourceController.do?flowModeList&type="+ type + "\"></iframe>");
            }
        });
    })
</script>
</body>
</html>