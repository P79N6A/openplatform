<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.jeecgframework.core.util.SysThemesUtil,org.jeecgframework.core.enums.SysThemesEnum" %>
<%@page import="org.jeecgframework.web.system.manager.ClientManager,org.jeecgframework.web.system.pojo.base.Client" %>
<%@include file="/context/mytags.jsp" %>
<%
    Client client = ClientManager.getInstance().getClient();
    String keyStr1 = "";
    String randomStr = "";
    if (client != null) {
        keyStr1 = client.getPassKey();
        randomStr = client.getRandomStr();

    }
%>
<%
    session.setAttribute("lang", "zh-cn");
    SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
    String lhgdialogTheme = SysThemesUtil.getLhgdialogTheme(sysTheme);
%>
<!DOCTYPE html>
<html class="">
<head>
    <%@ include file="/webpage/common/resource.jsp" %>
    <%@ include file="/webpage/common/sm.jsp" %>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="kiben" content="no-cache">
    <link rel="shortcut icon" href="favicon.ico">
    <style>
        em.error, label.error {
            font-style: normal;
            margin-top: 3px;
            position: relative;
            top: 6px;
            color: #cc5965;
            font-size: 12px;
            white-space: nowrap;
        }

        .table, .table td, .table th {
            border-collapse: collapse;
        }

        .table > tbody > tr.success > td, .table > tbody > tr.success > th, .table > tbody > tr > td.success, .table > tbody > tr > th.success, .table > tfoot > tr.success > td, .table > tfoot > tr.success > th, .table > tfoot > tr > td.success, .table > tfoot > tr > th.success, .table > thead > tr.success > td, .table > thead > tr.success > th, .table > thead > tr > td.success, .table > thead > tr > th.success {
            /*border:1px solid #aaa !important;*/
            background: rgba(31, 184, 233, .4) !important;
        }

        /* 必填项*号的颜色 */
        .star {
            color: #cc5965
        }

        /*时间选择框样式*/
        .range_inputs .input-mini {
            width: 100% !important;
        }

        .range_inputs .daterangepicker_end_input {
            padding-left: 0 !important;
        }

        body {
            font-family: "Microsoft YaHei" !important;
        }

        div.bootstrap-select > button.btn-default {
            background-color: white !important;
            color: black !important;
            border: 1px solid #e5e6e7 !important;
        }

        .other-login-type {
            text-align: center;
        }

        .other-login-type img {
            width: 40px;
            height: 40px;
            border-radius: 50%
        }

        .other-login-type a {
            cursor: pointer;
        }

        /*新的css*/
        /* .login-input .select-role .login-icon {
            position: absolute;
            left: 0;
            top: 0;
            display: block;
            width: 50px;
            height: 34px;
            z-index: 100;
            background: #ffffff url(../images/login_ico_spirit.png) no-repeat center 10px;
            border-top-left-radius: 8px;
            border-bottom-left-radius: 8px;
        }
        .login-input select {
            width: 100%;
            !* height: 40px; *!
            !* padding: 5px 10px; *!
            !* border: 1px solid #efefef; *!
            background-color: #D0E9F2;
            border: 0;
            padding: 0 10px 0 50px;
            line-height: 40px;
            box-sizing: border-box;
            border-radius: 8px;
        }*/
        .other-login-type img {
            opacity: 0.4;
        }

        .routeCheck img {
            opacity: 0.9;
        }
    </style>


    <title>能力开放平台</title>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/login/css/login-style.css"%>>

    <style type="text/css">.fancybox-margin {
        margin-right: 0px;
    }</style>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/ace/css/bootstrap.css"%>/>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/ace/css/font-awesome.css"%>/>
    <link rel="stylesheet" type="text/css" href=<%= request.getContextPath() + "/plug-in/accordion/css/accordion.css"%>>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/ace/css/ace-fonts.css"%>/>

    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/ace/css/jquery-ui.css"%>/>

    <link rel="stylesheet"
          href=<%= request.getContextPath() + "/plug-in/easyui/themes/default/easyui.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/easyui/themes/icon.css" %> type="text/css">
    <link rel="stylesheet"
          href=<%= request.getContextPath() + "/plug-in/ace/css/ace-part2.css"%> class="ace-main-stylesheet"/>

    <link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/ace/css/ace-ie.css"%>/>
    <script src=<%= request.getContextPath() + "/plug-in/ace/js/ace-extra.js"%>></script>


    <script src=<%= request.getContextPath() + "/plug-in/ace/js/html5shiv.js"%>></script>
    <script src=<%= request.getContextPath() + "/plug-in/ace/js/respond.js"%>></script>
</head>
<body>
<div class="body-div">
    <div class="login">
        <div class="row">
            <h3 class="sysname" style="text-align:center;padding:14px 0">能力开放平台</h3>
        </div>
        <div class="row">
            <form id="loinForm" class="login-input form-horizontal" role="form" method="post">
                <div class="alert alert-warning alert-dismissible" role="alert" id="errMsgContiner">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <div id="showErrMsg"></div>
                </div>
                <div class="input-name" id="custInput">
                    <span class="login-icon"></span>
                    <input type="text" name="userName" class="form-control" placeholder="请输入您的用户名" id="userName"/>
                </div>
                <div class="input-password">
                    <span class="login-icon"></span>
                    <input type="password" name="ppp" class="form-control" placeholder="请输入您的密码" id="ppp"
                           autocomplete="off"/>
                </div>
                <%--<div class="input-password">
                    <span class="login-icon"></span>
                    <select class="form-control">
                        <option value="">能力提供者</option>
                        <option value="">能力提供者</option>
                        <option value="">能力提供者</option>
                    </select>
                </div>--%>

                <div class="input-name col-md-12" style="padding:0; margin-bottom: 10px;">
                    <div id="verifyInput" class="col-xs-5" style="padding:0;">
                        <span class="login-icon icon02"></span>
                        <input type="text" name="randCode" class="form-control" placeholder="请输入验证码" id="randCode"/>
                    </div>
                    <div class="col-xs-7 text-center" style="cursor: pointer;">
                        <img src="randCodeImage" id="randCodeImage" onclick="reloadRandCodeImage()" title="刷新验证码"
                             alt="刷新验证码">
                    </div>
                </div>
                <div class="loginbtn">
                    <button type="button"
                            style="width:120px;background-color:#2869FB;border-color:#2869FB;border-radius:8px"
                            id="but_login" onclick="checkUser()" class="btn btn-sm btn-primary">
                        <span style="font-size:16px;line-height:1px">登&nbsp;录</span>
                    </button>
                    <button type="button" style="width:120px;border-radius:8px" id="third-register" onclick="register()"
                            class="btn btn-sm btn-warning">
                        <span style="font-size:16px;line-height:1px">授权</span>
                    </button>
                    <%--<button type="button" style="width:120px;background-color:#2869FB;border-color:#2869FB;border-radius:8px" id="but_register" class="btn btn-sm btn-warning">
                        <span style="font-size:16px;line-height:1px">注&nbsp;册</span>
                    </button>--%>
                </div>
                <div style="color:#3b3939;width:100%;font-size:13px;text-align:center">
                    ----------------------------其他方式------------------------------
                </div>
                <div class="other-login-type">
                    <a data-route="01" data-type="车联网" class="routeCheck">
                        <img title="通过车联网登录" src="images/chelianwang.jpg"></img>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/jquery/jquery-1.8.3.min.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/jquery/jquery.cookie.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/mutiLang/en.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/mutiLang/zh-cn.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/login/js/jquery.tipsy.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/login/js/iphone.check.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
<script type="text/javascript" src=<%= request.getContextPath() + "/js/sm.js"%>></script>
<script src=<%= request.getContextPath() + "/js/jquery.selection.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/js/common.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/js/system/changePwd.js" %> type="text/javascript"></script>
<%--<script type="text/javascript" src=<%= request.getContextPath() + "/plug-in/login/js/login.js"%>></script>--%>

<script src=<%= request.getContextPath() + "/plug-in/easyui/jquery.easyui.min.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/easyui/locale/zh-cn.js" %> type="text/javascript"></script>
<%--<script src=<%= request.getContextPath() + "/plug-in/easyui/easyui_expand.js" %> type="text/javascript"></script>--%>
<script src=<%= request.getContextPath() + "/plug-in/easyui/extends/datagrid-scrollview.js" %> type="text/javascript"></script>


<script type="text/javascript">
    $(function () {
        //延迟3秒再加载一次验证码，防止IE浏览器上出现验证码在后台为空的情况
        reloadRandCodeImage();
        optErrMsg();
        darkStyle();
        var passUpdateFlag = <%=request.getAttribute("passUpdateFlag")%>;//判断初次登陆和密码过期，暂时先删掉
    });
    $(function () {
        optErrMsg();
        //绑定其他登陆方式的点击事件
        $(".other-login-type a").bind("click", function () {
            if ($(this).hasClass("routeCheck")) {
                $(this).removeClass("routeCheck");
                // $(".login").removeAttr("style");
            } else {
                $(".other-login-type a").removeClass("routeCheck");
                $(this).addClass("routeCheck");
                // $(".login").css("background","url(images/clw_info_bg.png) no-repeat center 10px");
            }
        })
    });
    $("#errMsgContiner").hide();

    function optErrMsg() {
        $("#showErrMsg").html('');
        $("#errMsgContiner").hide();
    }

    //输入验证码，回车登录
    $(document).keydown(function (e) {
        if (e.keyCode == 13) {
            $("#but_login").click();
        }
    });

    //验证用户信息
    function checkUser() {
        if (!validForm()) {
            return false;
        }
        newLogin();
    }

    //表单验证
    function validForm() {
        if ($.trim($("#userName").val()).length == 0) {
            showErrorMsg("请输入用户名");
            return false;
        }

        if ($.trim($("#ppp").val()).length == 0) {
            showErrorMsg("请输入密码");
            return false;
        }

        if ($.trim($("#randCode").val()).length == 0) {
            showErrorMsg("请输入验证码");
            return false;
        }
        return true;
    }

    //登录处理函数
    function newLogin(orgId) {
        setCookie();
        //获取密钥和随机数
        var passKey = "";
        var randomStr = "";
        var userName = $.trim($("#userName").val());
        // $.ajax({
        //     async: false,
        //     cache: false,
        //     type: 'POST',
        //     // data:userName,
        //     url: "dwrMessagePush.do?prepareLogin&userName=" + userName,// 请求的action路径
        //     error: function () {// 请求失败处理函数
        //     },
        //     success: function (data) {
        //         passKey = data.passKey;
        //         randomStr = data.randomStr;
        //         var actionurl = "apiLogin.do?login";//提交路径
        //         var checkurl = "apiLogin.do?checkuser";//验证路径
        //         var formData = new Object();
        //         var data = $(":input").each(function () {
        //             formData[this.name] = $("#" + this.name).val();
        //         });
        //         formData['orgId'] = orgId ? orgId : "";
        //         //语言
        //         formData['langCode'] = $("#langCode").val();
        //         formData['langCode'] = $("#langCode option:selected").val();
        //         var ppp = formData['ppp'];
        //         var userName = formData['userName'];
        //         //密码进行加密
        //         ppp = doCrypt(ppp, passKey);
        //         userName = doCrypt(userName, passKey);
        //         formData['ppp'] = ppp;
        //         formData['userName'] = userName
        //         //formData['al'] = passKey;
        //         formData['str'] = randomStr;
        //         //处理登录方式
        //         var routeType = $("a.routeCheck");
        //         if (routeType != null) {
        //             routeType = $(routeType).data("route");
        //             formData['routeType'] = routeType;
        //         }
        //         $.ajax({
        //             async: false,
        //             cache: false,
        //             type: 'POST',
        //             url: checkurl,// 请求的action路径
        //             data: formData,
        //             error: function () {// 请求失败处理函数
        //             },
        //             success: function (data) {
        //                 var d = $.parseJSON(data);
        //                 if (d.success) {
        //                     //原来的没有邮箱认证码的功能
        //                     window.location.href = actionurl;
        //                 } else {
        //                     showErrorMsg(d.msg);
        //                     //登陆失败，重新加载验证码
        //                     reloadRandCodeImage();
        //                 }
        //             }
        //         });
        //     }
        // });
        DwrMessagePush.prepareLogin(userName, function (data) {
            passKey = data.passKey;
            randomStr = data.randomStr;
            var actionurl = "apiLogin.do?login";//提交路径
            var checkurl = "apiLogin.do?checkuser";//验证路径
            var formData = new Object();
            var data = $(":input").each(function () {
                formData[this.name] = $("#" + this.name).val();
            });
            formData['orgId'] = orgId ? orgId : "";
            //语言
            formData['langCode'] = $("#langCode").val();
            formData['langCode'] = $("#langCode option:selected").val();
            var ppp = formData['ppp'];
            var userName = formData['userName'];
            //密码进行加密
            ppp = doCrypt(ppp, passKey);
            userName = doCrypt(userName, passKey);
            formData['ppp'] = ppp;
            formData['userName'] = userName
            //formData['al'] = passKey;
            formData['str'] = randomStr;
            //处理登录方式
            var routeType = $("a.routeCheck");
            if (routeType != null) {
                routeType = $(routeType).data("route");
                formData['routeType'] = routeType;
            }
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                url: checkurl,// 请求的action路径
                data: formData,
                error: function () {// 请求失败处理函数
                },
                success: function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        //原来的没有邮箱认证码的功能
                        window.location.href = actionurl;
                    } else {
                        showErrorMsg(d.msg);
                        //登陆失败，重新加载验证码
                        reloadRandCodeImage();
                    }
                }
            });
        });
    }

    //登录提示消息显示
    function showErrorMsg(msg) {
        $("#errMsgContiner").show();
        $("#showErrMsg").html(msg);
        window.setTimeout(optErrMsg, 3000);
    }

    /**
     * 刷新验证码
     */
    $('#randCodeImage').click(function () {
        reloadRandCodeImage();
    });

    function reloadRandCodeImage() {
        var date = new Date();
        var img = document.getElementById("randCodeImage");
        img.src = 'randCodeImage?a=' + date.getTime();
    }

    function darkStyle() {
        $('body').attr('class', 'login-layout');
        $('#id-text2').attr('class', 'red');
        $('#id-company-text').attr('class', 'blue');
        /*  e.preventDefault(); */
    }

    function lightStyle() {
        $('body').attr('class', 'login-layout light-login');
        $('#id-text2').attr('class', 'grey');
        $('#id-company-text').attr('class', 'blue');

        /*  e.preventDefault(); */
    }

    function blurStyle() {
        $('body').attr('class', 'login-layout blur-login');
        $('#id-text2').attr('class', 'white');
        $('#id-company-text').attr('class', 'light-blue');

        /* e.preventDefault(); */
    }

    //设置cookie
    function setCookie() {
        if ($('#on_off').val() == '1') {
            $("input[iscookie='true']").each(function () {
                $.cookie(this.name, $("#" + this.name).val(), "/", 24);
                $.cookie("COOKIE_NAME", "true", "/", 24);
            });
        } else {
            $("input[iscookie='true']").each(function () {
                $.cookie(this.name, null);
                $.cookie("COOKIE_NAME", null);
            });
        }
    }

    //读取cookie
    function getCookie() {
        var COOKIE_NAME = $.cookie("COOKIE_NAME");
        if (COOKIE_NAME != null) {
            $("input[iscookie='true']").each(function () {
                $($("#" + this.name).val($.cookie(this.name)));
                if ("admin" == $.cookie(this.name)) {
                    $("#randCode").focus();
                } else {
                    $("#password").val("");
                    $("#password").focus();
                }
            });
            $("#on_off").attr("checked", true);
            $("#on_off").val("1");
        }
        else {
            $("#on_off").attr("checked", false);
            $("#on_off").val("0");
            $("#randCode").focus();
        }
    }

    //注册页面跳转
    function register() {
        window.open("apiLogin.do?register", "_blank");
    }
</script>
<%=lhgdialogTheme %>

</body>
<script type="text/javascript">
    var keyStr1 = "<%=keyStr1%>";
    var randomStr = "<%=randomStr%>";
</script>
</html>