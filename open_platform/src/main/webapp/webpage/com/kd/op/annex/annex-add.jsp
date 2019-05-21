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
            <form class="form-horizontal" role="form" id="addAnnexForm" method="POST">
                <div class="main-form">
                    <div class="row">
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    附件描述：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <input id="annexDesc" name="annexDesc" type="text"
                                           class="form-control input-sm" maxlength="500" ignore="ignore"/>
                                </div>
                                <span class="col-lg-1 col-md-1 col-sm-1  Range"><span style="color:red">*</span></span>
                            </div>
                        </div>
                        <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="row">
                                <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                    附件选择：
                                </div>
                                <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    <p><input type="file" name="file" id="file" accept="*" multiple="multiple"></p>
                                </div>
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
        </form>
    </div>
</div>
</div>

<script type="text/javascript">
   /* $(document).ready(function () {
        $("#btn_sub").bind("click", function () {
            if (tovalid()) {
                var options = {
                    url: "apiAnnexController.do?doUpload",
                    type: "post",
                    dataType: "json",
                    beforeSubmit: function () {
                    },
                    success: function (data) {
                        if (data.success) {
                            $(".bootstrap-dialog").modal("hide")
                            reloadTable();
                            quickNotify(data.msg, "success");
                        } else {
                            slowNotify(data.msg, "danger");
                        }
                    },
                }
                $("#addAnnexForm").ajaxSubmit(options);
            }
        })
    })
    function tovalid() {
        if ($('#file').val() != null && $('#file').val() != '') {
            return true
        }
        ;
        alert('请选择一个文件')
        return false;
    }*/
</script>

</body>
</html>