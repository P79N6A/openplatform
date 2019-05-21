<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>附件上传</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <script src="${webRoot}/plug-in/lhgDialog/lhgdialog.min.js"></script>
    <script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
    <script src="${webRoot}/plug-in/tools/curdtools.js"></script>
    <style>
        .childTr {
            margin-left: 20px;
        }

        .btn-div a {
            border-color: #ccc;
            border-radius: 3px;
        }

        #step-1, #step-2, #step-3, #step-4 {
            padding-top: 10px;
        }

        .table-div {
            overflow: auto;
            min-height: 200px;
            max-height: 400px;
        }

        .table input, select {
            width: 100% !important;
        }

        .table th {
            white-space: nowrap
        }

        body {
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="container" style="width:100%;overflow-x:hidden">
    <div class="panel panel-default">
        <div class="panel-body">
            <form class="form-horizontal" role="form" id="lookAnnexForm" method="POST">
                <div class="main-form">
                    <div class="row">
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    附件名称：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <input  name="annexName" type="text" value="${apiAnnex.annexName}"
                                           class="form-control input-sm" maxlength="500" ignore="ignore"/>
                                </div>
                            </div>
                        </div>
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    附件描述：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <input  name="annexDesc" type="text" value="${apiAnnex.annexDesc}"
                                           class="form-control input-sm" maxlength="500" ignore="ignore"/>
                                </div>
                            </div>
                        </div>
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    附件状态：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <t:dictSelect readonly="readonly" field="apiAuditStatus" defaultVal = "${apiAnnex.annexStatus}" type="select" hasLabel="false" title="审核状态" extendJson="{class:'form-control input-sm'}"  typeGroupCode="apiSts" ></t:dictSelect>
                                </div>
                            </div>
                        </div>
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    审核状态：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <t:dictSelect readonly="readonly" field="apiAuditStatus" defaultVal = "${apiAnnex.auditStatus}" type="select" hasLabel="false" title="审核状态" extendJson="{class:'form-control input-sm'}"  typeGroupCode="auditSts" ></t:dictSelect>
                                </div>
                            </div>
                        </div>
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    附件大小(KB)：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <input   type="text" value="${apiAnnex.annexSize}"
                                           class="form-control input-sm" maxlength="500" ignore="ignore"/>
                                </div>
                            </div>
                        </div>
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    附件格式：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <input   type="text" value="${apiAnnex.annexSuffix}"
                                             class="form-control input-sm" maxlength="500" ignore="ignore"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </form>
    </div>
</div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        //按钮input不可点击
        $("#lookAnnexForm :input[type=text]").attr("disabled","disabled");
        $("#lookAnnexForm select").attr("disabled","disabled");
    })

</script>

</body>
</html>