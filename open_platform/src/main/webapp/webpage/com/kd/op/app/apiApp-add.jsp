<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>能力应用表</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/select2/css/select2.min.css" type="text/css"/>
    <script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.full.min.js"></script>
    <style>
        .control-label {
            padding-right: 0px;
        }
    </style>
</head>
<body style="overflow:hidden;overflow-y:auto;">
<div class="container" style="width:100%;">
    <div class="panel-heading"></div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="addForm" method="POST">
            <input type="hidden" id="btn_sub" class="btn_sub"/>
            <input type="hidden" id="id" name="id"/>

            <div class="form-group">
                <label for="appType" class="col-sm-5 control-label" style="margin-left: -80px">应用类型：</label>
                <div class="col-sm-7">
                    <select class="form-control" id="appType" name="appType" onchange="change()">
                        <option value="-1" disabled selected hidden>请选择应用类型</option>
                        <option value="0">普通应用</option>
                        <option value="1">互联互通</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="appName" class="col-sm-5 control-label" style="margin-left: -80px">应用名称：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="appName" name="appName" type="text" maxlength="100" class="form-control input-sm"
                               placeholder="请输入应用名称" ignore="ignore"/>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="appVersion" class="col-sm-5 control-label" style="margin-left: -80px">应用版本：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="appVersion" name="appVersion" type="text" maxlength="10"
                               class="form-control input-sm" placeholder="请输入应用版本" ignore="ignore"/>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="appDesc" class="col-sm-5 control-label" style="margin-left: -80px">应用描述：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <textarea id="appDesc" name="appDesc" maxlength="200" rows="4" class="form-control input-sm"
                                  placeholder="请输入应用描述" ignore="ignore"></textarea>
                    </div>
                </div>
            </div>

            <div id="operator_group00" style="display: none">
                <div class="form-group" id="operator_group1">
                    <label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">车联网商户号：</label>
                    <div class="col-sm-7">
                        <div class="input-group" style="width:100%">
                            <input id="innerMerchantno" name="innerMerchantno" type="text" maxlength="50"
                                   class="form-control input-sm" placeholder="请输入车联网商户编号" ignore="ignore"/>
                        </div>
                    </div>
                </div>

                <div class="form-group" id="operator_group2">
                    <label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">运营商标识：</label>
                    <div class="col-sm-7">
                        <div class="input-group" style="width:100%">
                            <input id="operator_id" name="operator_id" type="text" maxlength="50"
                                   class="form-control input-sm" placeholder="请输入运营商标识" ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <%--待调试代码   5.13--%>
                <%--<div class="form-group">--%>
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">外部提供系统TOKEN秘钥：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeyFour" name="workeyFour" value='${workeyFour}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="请输入外部提供系统TOKEN秘钥" ignore="ignore"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                    <%--<label for="operator_id" class="col-sm-6 control-label" style="margin-left: -150px">外部提供系统数据加密秘钥：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeySix" name="workeySix" value='${workeySix}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="请输入外部提供系统数据加密秘钥" ignore="ignore"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">外部提供系统初始化向量：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeyEight" name="workeyEight" value='${workeyEight}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="请输入外部提供系统初始化向量" ignore="ignore"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">外部提供系统签名秘钥：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input  id="workeyTen" name="workeyTen" value='${workeyTen}' type="text" maxlength="50" class="form-control input-sm"--%>
                                    <%--placeholder="请输入外部提供系统签名秘钥" ignore="ignore"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </form>
    </div>
</div>
</body>


<%--<script type="text/javascript" src="${webRoot}/js/jquery-1.11.3.min.js" > </script>--%>

<script type="text/javascript">
    function change() {
        var objS = document.getElementById("appType");
        var p1 = objS.options[objS.selectedIndex].value;
        if (p1 == 1) {
            $('#operator_group00').css('display', 'block')
        } else if (p1 == 0) {
            $('#operator_group00').css('display', 'none')
        }
    }

    /* $(document).ready(function(){
         $('#appType').change(function(){
             var p1=$(this).children('option:selected').val();//这就是selected的值
             if(p1==1){
                 $('#operator_group').css('display','block')
             }else if(p1==0){
                 $('#operator_group').css('display','none')
             }
         })
     })*/

</script>
</html>