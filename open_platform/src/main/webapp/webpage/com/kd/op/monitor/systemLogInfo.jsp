<%--
  Created by IntelliJ IDEA.
  User: gongzi_yang
  Date: 2018/11/1
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>系统日志列表-查看</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <link rel="stylesheet" href="${webRoot}/plug-in/select2/css/select2.min.css" type="text/css"/>
    <script type="text/javascript" src="${webRoot}/plug-in/select2/js/select2.full.min.js" > </script>
    <style>
        .control-label{
            padding-right:0px;
        }
    </style>
</head>
<body style="overflow:hidden;overflow-y:auto;">
<div class="container" style="width:100%;">
    <div class="panel-heading"></div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="lookForm"  method="POST">
            <input type="hidden" id="btn_sub" class="btn_sub"/>
            <input type="hidden" id="id" name="id" value="${tsLog.id}"/>
            <div class="form-group">
                <label for="broswer" class="col-sm-3 control-label">用户浏览器类型：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="broswer" name="broswer" readonly="readonly" value='${tsLog.broswer}' type="text" maxlength="100" class="form-control input-sm" ignore="ignore" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="logcontent" class="col-sm-3 control-label">日志内容：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <textarea id="logcontent" name="logcontent" maxlength="200" rows="4" class="form-control input-sm" readonly="readonly"  ignore="ignore" >${tsLog.logcontent}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="loglevel" class="col-sm-3 control-label">业务模块：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="loglevel" name="loglevel" value='${logName}' readonly="readonly" type="text" maxlength="10" class="form-control input-sm"  ignore="ignore" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="note" class="col-sm-3 control-label">操作IP：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="note" name="note" value='${tsLog.note}' readonly="readonly" type="text" maxlength="10" class="form-control input-sm"  ignore="ignore" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="operatetime" class="col-sm-3 control-label">操作时间：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="operatetime" name="operatetime" value='${formatTime}' readonly="readonly" type="text" maxlength="10" class="form-control input-sm"  ignore="ignore" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="operatetype" class="col-sm-3 control-label">日志类型：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="operatetype" name="operatetype" value='${operateName}' readonly="readonly" type="text" maxlength="10" class="form-control input-sm"  ignore="ignore" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="successFlag" class="col-sm-3 control-label">标记：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="successFlag" name="successFlag" value='${flagName}' readonly="readonly" type="text" maxlength="10" class="form-control input-sm"  ignore="ignore" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="aaa" class="col-sm-3 control-label">日志分类：</label>
                <div class="col-sm-7">
                    <div class="input-group" style="width:100%">
                        <input id="aaa" name="aaa" value='${aaaName}' readonly="readonly" type="text" maxlength="10" class="form-control input-sm"  ignore="ignore" />
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var subDlgIndex = '';
    $(document).ready(function() {
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
