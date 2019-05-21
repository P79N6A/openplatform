<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单和订购时选择计费模型</title>
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
    </ul>

    <div class="tab-content" style="height:calc(100% - 8px)">
        <div role="tabpanel" class="tab-pane active" id="monthlyPayment">
            <c:if test="${not empty appId}">
                <iframe id="monthlyPaymentFrame" height="100%" width="100%"
                        src="appOrderController.do?orderConfigChargingList&appId=${appId}"></iframe>
            </c:if>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#btn_sub").bind("click",function(){
            var selected = $("#appId").val();
            if(selected == null){
                slowNotify("尚未选择计费方式", "danger");
            }else{
                $.ajax({
                    type: "post",
                    dataType: "json",
                    data: {
                        appId: id,
                        orderType: 'app',
                        apiData: JSON.stringify(array)
                    },
                    url: "appOrderController.do?saveOrder",
                    success: function (data) {
                        if (data.success) {
                            dialog.close()
                            quickNotify(data.msg, "success");
                        } else {
                            slowNotify(data.msg, "danger");
                        }
                    }
                })
            }
        })
    })
</script>
</body>
</html>