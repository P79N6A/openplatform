<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>能力应用表-编辑</title>
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
            <input type="hidden" id="id" name="id" value="${apiApp.id}"/>
            <input type="hidden" id="iv_str" name="iv_str" value="${ivStr}"/>
            <input type="hidden" id="pass_key" name="pass_key" value="${passKey}"/>

            <div class="form-group">
                <label for="appType" class="col-sm-5 control-label" style="margin-left: -80px">应用类型：</label>
                <div class="col-sm-7">
                    <select class="form-control" id="appType" name="appType" onchange="change()">
                        <option value="-1" disabled selected hidden>请选择应用类型</option>
                        <option
                                <c:if test="${apiApp.appType == 0}">selected</c:if> value="0">普通应用
                        </option>
                        <option
                                <c:if test="${apiApp.appType == 1}">selected</c:if> value="1">互联互通
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="appName" class="col-sm-5 control-label" style="margin-left: -80px">应用名称：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="appName" name="appName" value='${apiApp.appName}' type="text" maxlength="100"
                               class="form-control input-sm" placeholder="请输入应用名称" ignore="ignore"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="appVersion" class="col-sm-5 control-label" style="margin-left: -80px">应用版本：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="appVersion" name="appVersion" value='${apiApp.appVersion}' type="text" maxlength="10"
                               class="form-control input-sm" placeholder="请输入应用版本" ignore="ignore"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="appDesc" class="col-sm-5 control-label" style="margin-left: -80px">应用描述：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <textarea id="appDesc" name="appDesc" maxlength="200" rows="4" class="form-control input-sm"
                                  placeholder="请输入应用描述" ignore="ignore">${apiApp.appDesc}</textarea>
                    </div>
                </div>
            </div>
            <div id="operator_group00" style="display: none">
                <div class="form-group" id="operator_group1">
                    <label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">车联网商户号：</label>
                    <div class="col-sm-7">
                        <div class="input-group" style="width:100%">
                            <input id="innerMerchantno" name="innerMerchantno" value='${apiApp.innerMerchantno}'
                                   type="text" maxlength="100" class="form-control input-sm" placeholder="请输入车联网商户编号"
                                   ignore="ignore"/>
                        </div>
                    </div>
                </div>

                <div class="form-group" id="operator_group2">
                    <label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">运营商标识：</label>
                    <div class="col-sm-7">
                        <div class="input-group" style="width:100%">
                            <input id="operator_id" name="operator_id" value='${apiApp.operator_id}' type="text"
                                   maxlength="50" class="form-control input-sm" placeholder="请输入运营商标识" ignore="ignore" disabled/>
                        </div>
                    </div>
                </div>
                <%--待调试代码  5.13--%>
                <%--<div class="form-group">--%>
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">系统对外提供TOKEN：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeyOne" name="workeyOne" value='${workeyOne}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="系统对外提供TOKEN" ignore="ignore" disabled/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>


                <%--<div class="form-group">--%>
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">外部提供系统TOKEN：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeyTwo" name="workeyTwo" value='${workeyTwo}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="外部提供系统TOKEN" ignore="ignore" disabled/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <%--<div class="form-group">--%>
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">系统对外提供TOKEN秘钥：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeyThree" name="workeyThree" value='${workeyThree}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="系统自动生成" ignore="ignore" disabled/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>


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
                    <%--<label for="operator_id" class="col-sm-6 control-label" style="margin-left: -150px">系统对外提供数据加密秘钥：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeyFive" name="workeyFive" value='${workeyFive}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="系统自动生成" ignore="ignore" disabled/>--%>
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
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">系统对外提供初始化向量：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeySeven" name="workeySeven" value='${workeySeven}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="系统自动生成" ignore="ignore" disabled/>--%>
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
                    <%--<label for="operator_id" class="col-sm-5 control-label" style="margin-left: -80px">系统对外提供签名秘钥：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="workeyNine" name="workeyNine" value='${workeyNine}' type="text" maxlength="50" class="form-control input-sm"--%>
                                   <%--placeholder="系统自动生成" ignore="ignore" disabled/>--%>
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
            <%--</div>--%>
            <%--<div id="operator_group01" style="display: none">--%>
                <%--<div class="form-group">--%>
                    <%--<label for="passKey" class="col-sm-5 control-label" style="margin-left: -80px">passKey：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="passKey" name="passKey" value='${passKey}' type="text" maxlength="16"--%>
                                   <%--class="form-control input-sm" placeholder="请输入16位passKey" ignore="ignore"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label for="ivStr" class="col-sm-5 control-label" style="margin-left: -80px">ivStr：</label>--%>
                    <%--<div class="col-sm-7">--%>
                        <%--<div class="input-group" style="width:100%">--%>
                            <%--<input id="ivStr" name="ivStr" value='${ivStr}' type="text" maxlength="16"--%>
                                   <%--class="form-control input-sm" placeholder="请输入16位ivStr" ignore="ignore"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function change() {
        var objS = document.getElementById("appType");
        var p1 = objS.options[objS.selectedIndex].value;
        if (p1 == 1) {
            $('#operator_group01').css('display', 'none')
            $('#operator_group00').css('display', 'block')
        } else if (p1 == 0) {
            $('#operator_group01').css('display', 'block')
            $('#operator_group00').css('display', 'none')
        }
    }

    var subDlgIndex = '';
    $(document).ready(function () {
        var objS = document.getElementById("appType");
        var p1 = objS.options[objS.selectedIndex].value;
        if (p1 == 1) {
            $('#operator_group01').css('display', 'none')
            $('#operator_group00').css('display', 'block')
        } else if (p1 == 0) {
            $('#operator_group01').css('display', 'block')
            $('#operator_group00').css('display', 'none')
        }
        //单选框/多选框初始化
        // $('.i-checks').iCheck({
        // 	labelHover : false,
        // 	cursor : true,
        // 	checkboxClass : 'icheckbox_square-green',
        // 	radioClass : 'iradio_square-green',
        // 	increaseArea : '20%'
        // });
    });
</script>
</body>
</html>