<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp" %>
<%@ include file="/webpage/common/sm.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="images/favicon.ico">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户授权</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <style>
        body {
            background: url(images/register-bg.jpg) no-repeat center center;
            /*background: #297fcc;*/
            background-size: 100% 100%;
        }

        .register-btn {
            width: 150px;
            font-size: 18px;
            color: #000;
            margin-top: 20px;
        }

        .nav-pills > li > a {
            border: 1px solid #ccc;
            margin-right: 0px;
        }
        .nav-pills > li + li {
            margin-left: 1px;
        }

        .tab-pane form {
            margin-top: 20px;
            background-color: #ffffff;
            padding-top: 30px;
        }

        .route {
            text-align: left;
        }

        .route img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            opacity: 0.4;
        }

        .route a {
            cursor: pointer;
        }

        .register-form label {
            color: #ffffff;
        }

        .register-form .form-group {
            margin-top: 15px;
        }

        .register-btn {
            width: 100%;
        }

        .routeCheck img {
            /*background-color: #282ecc;*/
            opacity: 0.9;
        }

        /*.routeUnCheck{*/
        /*background-color: #282ecc;*/
        /*}*/
    </style>
    <script type="text/javascript" src=<%= request.getContextPath() + "/dwr/engine.js"%>></script>
    <script type="text/javascript" src=<%= request.getContextPath() + "/dwr/util.js"%>></script>
    <script type="text/javascript" src=<%= request.getContextPath() + "/dwr/interface/DwrMessagePush.js"%>></script>
    <script type="text/javascript" src=<%= request.getContextPath() + "/js/sm.js"%>></script>
</head>

<body>
<div class="container">
    <div class="rows">
        <div class="col-md-8 col-md-offset-2" style="margin-top:15%;">
            <%--<ul class="nav nav-tabs nav-pills" role="tablist">
                <li role="presentation" data-type="1" style="margin-left:28%;">
                    <a href="#platform" aria-controls="home" role="tab" data-toggle="tab">平台注册</a>
                </li>
                <li id="payPer-li" class="active" role="presentation" data-type="2">
                    <a href="#third" aria-controls="profile" role="tab" data-toggle="tab">第三方授权</a>
                </li>
            </ul>

            <div class="tab-content" style="height:calc(100% - 48px)">
                <div role="tabpanel" class="tab-pane" id="platform">

                </div>
                <div role="tabpanel" class="tab-pane active" id="third">

                </div>
            </div>--%>
            <form class="form form-horizontal register-form">
                <div class="form-group">
                    <div id="title-msg" class="col-md-6 col-md-offset-3"
                         style="text-align:center;color:#c8e012;font-size:25px;">
                        能力开放平台用户授权
                    </div>
                </div>
                <div class="form-group">
                    <label for="zjy" class="col-md-2 col-md-offset-1 control-label">用户名:</label>
                    <div class="col-md-6">
                        <input type="text" class="form-control" id="zjy" name="zjy" placeholder="请输入用户名"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="nyj" class="col-md-2 col-md-offset-1 control-label">密码:</label>
                    <div class="col-md-6">
                        <input type="password" class="form-control" id="nyj" name="nyj" placeholder="请输入密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="role" class="col-md-2 col-md-offset-1 control-label">角色:</label>
                    <div class="col-md-6">
                        <select class="form-control" id="role" name="role">
                            <c:forEach var="role" items="${roles}">
                                <option value="${role.id}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 col-md-offset-1 control-label">授权方:</label>
                    <div class="col-md-6 route">
                        <a data-route="01" data-type="车联网">
                            <img title="授权车联网账号" class="routeCheck" src="images/chelianwang.jpg"></img>
                        </a>
                        <%--<a data-route="02" data-type="智慧能源">
                            <img title="授权智慧能源账号" src="images/sess.jpg"></img>
                        </a>--%>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-4 col-md-offset-4" style="text-align:center">
                        <button type="button" class="btn btn-warning register-btn" id="register-btn">授&nbsp;&nbsp;权
                        </button>
                    </div>
                </div>
                <div class="form-group">
                    <div id="error-msg" class="col-md-4 col-md-offset-4"
                         style="text-align:center;color:#aa0000;font-size:25px;">

                            </div>
                        </div>
                    </form>
            </div>
        </div>
    </div>
<script>
    $(document).ready(function () {
        hideError();
        $("#register-btn").bind("click", function () {
            $("#egister-btn").attr("disabled", true);
            var zjy = $("#zjy").val();
            if(zjy == null || zjy == ""){
                showError("请输入用户名！");
                return;
            }
            var nyj = $("#nyj").val();
            if (nyj == null || nyj == "") {
                showError("请输入密码！");
                return;
            }
            var role = $("#role").val();
            if (role == null || role == "" || role == undefined) {
                showError("请选择角色！");
                return;
            }
            var routeType = $("a.routeCheck");
            if (routeType == null || routeType.length == 0) {
                showError("请选择授权方！");
                return;
            }
            routeType = $(routeType).data("route");

            //通过dwr获取秘钥和随机数
            var passKey = "";
            var randomStr = "";
            DwrMessagePush.prepareLogin(zjy,function(data){
                passKey = data.passKey;
                randomStr = data.randomStr;
                //密码进行加密
                nyj = doCrypt(nyj, passKey);
                zjy = doCrypt(zjy, passKey);
                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "apiLogin.do?registerUser",
                    data: {
                        zjy: zjy,
                        nyj: nyj,
                        role: role,
                        str: randomStr,
                        routeType: routeType
                    },
                    success: function (data) {
                        if (data && data.success) {
                            slowNotify("用户信息无误，待审核通过后可登录能力开放平台", "success");
                        } else {
                            slowNotify(data.msg, "danger");
                        }
                        $("#egister-btn").removeAttr("disabled");
                    }
                });
                // }
            });
        });
        //添加授权方按钮的点击事件
        $(".route a").bind("click", function () {
            $(".route a").removeClass("routeCheck");
            $(this).addClass("routeCheck");
        })
    })

    function showError(msg) {
        $("#error-msg").html(msg);
        $("#error-msg").show();
        window.setTimeout(hideError, 3000);
    }

    function hideError() {
        $("#error-msg").html("");
        $("#error-msg").hide();
    }
</script>
</body>
</html>
