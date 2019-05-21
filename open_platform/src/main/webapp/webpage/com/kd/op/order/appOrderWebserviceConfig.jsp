<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <script src="${webRoot}/plug-in/lhgDialog/lhgdialog.min.js"></script>
    <script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
    <script src="${webRoot}/plug-in/tools/curdtools.js"></script>
    <link rel="stylesheet" href="plug-in/select2/css/select2.min.css" type="text/css"/>
    <script type="text/javascript" src="plug-in/select2/js/select2.full.min.js"></script>
    <%--<script src=<%= request.getContextPath() + "/js/com/kd/op/resource/resourceSelect.js" %> type="text/javascript"></script>--%>
    <script src="${webRoot}/js/common.js" type="text/javascript"></script>
</head>
<style type="text/css">
    .inputstyle {
        border: 1px solid #7d807e;
        height: 33px;
        width: 50%;
        margin-bottom: 2%;
        margin-top: 2%;
        border-radius: 5px;
        margin-left: 2%;
        padding-left:2%;
    }
</style>
<body>
<form id="paramForm">
    <input id="id" type="hidden" value="${id}"/>
    <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding-top:25px">
        <div id="publishMod" class="row">
            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                接收方式：
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                <select id="selectInfo" name="pubmode" type="text" maxlength="100" class="form-control" onchange="refreshInterface()">
                    <option value="0">HTTPS</option>
                    <option value="4">MQ</option>
                    <option value="2">WebService</option>
                </select>
            </div>
        </div>
    </div>
    </div>
    <div id="httpsAddressHead" class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="row">
            <div id="httpsAddressHeadName" class="col-md-3 col-sm-3 col-xs-3 bt-label">
                http(s)地址：
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                <input id="httpsAddress" name="httpsAddress" type="text"
                       maxlength="200"
                       class="form-control"/>
            </div>
        </div>
    </div>
    <div id="topicHead" class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12" hidden="hidden">
        <div class="row">
            <div id="topicHeadName" class="col-md-3 col-sm-3 col-xs-3 bt-label">
                topic：
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                <input id="topic" name="topic" type="text"
                       maxlength="100"
                       class="form-control"/>
            </div>
        </div>
    </div>
    <div id="tagHead" class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12" hidden="hidden">
        <div class="row">
            <div id="tagHeadName" class="col-md-3 col-sm-3 col-xs-3 bt-label">
                tag：
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                <input id="tag" name="tag" type="text"
                       maxlength="100"
                       class="form-control"/>
            </div>
        </div>
    </div>
    <div id="methodNameHead" class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12" hidden="hidden">
        <div class="row">
            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                方法名称：
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                <input id="methodName" name="methodName" type="text"
                       maxlength="100"
                       class="form-control"/>
            </div>
        </div>
    </div>
    <div id="namespaceURIHead" class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12" hidden="hidden">
        <div class="row">
            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                命名空间URL：
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                <input id="namespaceURI" name="namespaceURI" type="text"
                       maxlength="200"
                       class="form-control"/>
            </div>
        </div>
    </div>
    <div id="endpointHead" class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12" hidden="hidden">
        <div class="row">
            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                webservice地址：
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                <input id="endpoint" name="endpoint" type="text"
                       maxlength="200"
                       class="form-control"/>
            </div>
        </div>
    </div>
</form>
<%--<div id="tree" style="max-height:400px;overflow:auto;margin-bottom:20px;">--%>
<%--<ul class="ztree" id="resources"></ul>--%>
<%--</div>--%>
</body>
<script type="text/javascript">
    var fromDatabase=${fromDatabase};
    var formDataLength;
    function getFormData(){
        var formdata=new Array();
        var paramFormStr = $("#paramForm").serialize()
        paramFormStr = paramFormStr+"&fromDatabase=0";
        var paramForm= decodeURIComponent(paramFormStr,true)
        var paramFormArr=paramForm.toString().split("&")
        formDataLength=paramFormArr.length
        for (var j = 0; j < paramFormArr.length; j++){
            var formArr=paramFormArr[j].split("=")
            if (  formArr[1]!=null &&formArr[1]!=undefined && formArr[1]!="") {
                formdata.push(paramFormArr[j]);
            }

        }
        return formdata;
    }
    $(function () {
        // 回显
        var id =$("#id").val();
        var checkedwebServiceInfo=webServiceInfoMap.get(id);
        $("#topic").removeAttr("name")
        $("#tag").removeAttr("name")
        $("#methodName").removeAttr("name")
        $("#namespaceURI").removeAttr("name")
        $("#endpoint").removeAttr("name")

        if (checkedwebServiceInfo!=null && checkedwebServiceInfo!=undefined ) {
            if(JSON.stringify(checkedwebServiceInfo).indexOf("fromDatabase")!=-1 && JSON.stringify(checkedwebServiceInfo).indexOf("pubmode")!=-1){
                fromDatabase=0;
            }

            //判断回显数据是否来自数据库(1不可编辑)
            if(fromDatabase==1||fromDatabase==2){
                if(fromDatabase==1){
                    $("#selectInfo").attr("disabled", "disabled")
                }
                var resourceParams = JSON.stringify(checkedwebServiceInfo).split(",");
                if (resourceParams.length == 2) {
                    $($("#selectInfo option")[1]).attr("selected", true)

                    $("#httpsAddressHead").attr("hidden", "hidden")
                    $("#topic").attr("name", "topic")
                    $("#tag").attr("name", "tag")
                    $("#methodNameHead").attr("hidden", "hidden")
                    $("#namespaceURIHead").attr("hidden", "hidden")
                    $("#endpointHead").attr("hidden", "hidden")

                    $("#httpsAddress").removeAttr("name")
                    $("#topicHead").removeAttr("hidden")
                    $("#tagHead").removeAttr("hidden")
                    $("#methodName").removeAttr("name")
                    $("#namespaceURI").removeAttr("name")
                    $("#endpoint").removeAttr("name")

                    $("#topic").val(checkedwebServiceInfo.topic)
                    $("#tag").val(checkedwebServiceInfo.tag)
                }
                if (resourceParams.length == 3) {
                    $($("#selectInfo option")[2]).attr("selected", true)

                    $("#httpsAddressHead").attr("hidden", "hidden")
                    $("#topicHead").attr("hidden", "hidden")
                    $("#tagHead").attr("hidden", "hidden")
                    $("#methodName").attr("name", "methodName")
                    $("#namespaceURI").attr("name", "namespaceURI")
                    $("#endpoint").attr("name", "endpoint")

                    $("#httpsAddress").removeAttr("name")
                    $("#topic").removeAttr("name")
                    $("#tag").removeAttr("name")
                    $("#methodNameHead").removeAttr("hidden")
                    $("#namespaceURIHead").removeAttr("hidden")
                    $("#endpointHead").removeAttr("hidden")

                    $("#methodName").val(checkedwebServiceInfo.methodName)
                    $("#namespaceURI").val(checkedwebServiceInfo.namespaceURI)
                    $("#endpoint").val(checkedwebServiceInfo.endpoint)
                }
                if (resourceParams.length == 1) {
                    $($("#selectInfo option")[0]).attr("selected", true)

                    $("#httpsAddress").attr("name", "httpsAddress")
                    $("#topicHead").attr("hidden", "hidden")
                    $("#tagHead").attr("hidden", "hidden")
                    $("#methodNameHead").attr("hidden", "hidden")
                    $("#namespaceURIHead").attr("hidden", "hidden")
                    $("#endpointHead").attr("hidden", "hidden")

                    $("#httpsAddressHead").removeAttr("hidden")
                    $("#topic").removeAttr("name")
                    $("#tag").removeAttr("name")
                    $("#methodName").removeAttr("name")
                    $("#namespaceURI").removeAttr("name")
                    $("#endpoint").removeAttr("name")

                    $("#httpsAddress").val(checkedwebServiceInfo)
                }
            }else{
                var checkedResourceArr = checkedwebServiceInfo.toString().split(",");
                if (checkedResourceArr.length == 3) {
                    $($("#selectInfo option")[0]).attr("selected", true)

                    $("#httpsAddress").attr("name", "httpsAddress")
                    $("#topicHead").attr("hidden", "hidden")
                    $("#tagHead").attr("hidden", "hidden")
                    $("#methodNameHead").attr("hidden", "hidden")
                    $("#namespaceURIHead").attr("hidden", "hidden")
                    $("#endpointHead").attr("hidden", "hidden")

                    $("#httpsAddressHead").removeAttr("hidden")
                    $("#topic").removeAttr("name")
                    $("#tag").removeAttr("name")
                    $("#methodName").removeAttr("name")
                    $("#namespaceURI").removeAttr("name")
                    $("#endpoint").removeAttr("name")
                }
                if (checkedResourceArr.length == 5) {
                    $($("#selectInfo option")[2]).attr("selected", true)

                    $("#httpsAddressHead").attr("hidden", "hidden")
                    $("#topicHead").attr("hidden", "hidden")
                    $("#tagHead").attr("hidden", "hidden")
                    $("#methodName").attr("name", "methodName")
                    $("#namespaceURI").attr("name", "namespaceURI")
                    $("#endpoint").attr("name", "endpoint")

                    $("#httpsAddress").removeAttr("name")
                    $("#topic").removeAttr("name")
                    $("#tag").removeAttr("name")
                    $("#methodNameHead").removeAttr("hidden")
                    $("#namespaceURIHead").removeAttr("hidden")
                    $("#endpointHead").removeAttr("hidden")
                }
                if (checkedResourceArr.length == 4) {
                    $($("#selectInfo option")[1]).attr("selected", true)

                    $("#httpsAddressHead").attr("hidden", "hidden")
                    $("#topic").attr("name", "topic")
                    $("#tag").attr("name", "tag")
                    $("#methodNameHead").attr("hidden", "hidden")
                    $("#namespaceURIHead").attr("hidden", "hidden")
                    $("#endpointHead").attr("hidden", "hidden")

                    $("#httpsAddress").removeAttr("name")
                    $("#topicHead").removeAttr("hidden")
                    $("#tagHead").removeAttr("hidden")
                    $("#methodName").removeAttr("name")
                    $("#namespaceURI").removeAttr("name")
                    $("#endpoint").removeAttr("name")

                }
                for (var i = 1; i < checkedResourceArr.length; i++) {
                    var paramN = checkedResourceArr[i].substring(0,checkedResourceArr[i].indexOf("="));
                    var paramV = checkedResourceArr[i].substring(checkedResourceArr[i].indexOf("=")+1);
                    $('#' + paramN).val(paramV)
                }
            }

        }
    });
    function refreshInterface() {
        var selec = $('select option:selected').val();

        if(selec == 0){
            $("#httpsAddress").attr("name","httpsAddress")
            $("#topicHead").attr("hidden","hidden")
            $("#tagHead").attr("hidden","hidden")
            $("#methodNameHead").attr("hidden","hidden")
            $("#namespaceURIHead").attr("hidden","hidden")
            $("#endpointHead").attr("hidden","hidden")

            $("#httpsAddressHead").removeAttr("hidden")
            $("#topic").removeAttr("name")
            $("#tag").removeAttr("name")
            $("#methodName").removeAttr("name")
            $("#namespaceURI").removeAttr("name")
            $("#endpoint").removeAttr("name")
        }
        if(selec == 4){
            $("#httpsAddressHead").attr("hidden","hidden")
            $("#topic").attr("name","topic")
            $("#tag").attr("name","tag")
            $("#methodNameHead").attr("hidden","hidden")
            $("#namespaceURIHead").attr("hidden","hidden")
            $("#endpointHead").attr("hidden","hidden")

            $("#httpsAddress").removeAttr("name")
            $("#topicHead").removeAttr("hidden")
            $("#tagHead").removeAttr("hidden")
            $("#methodName").removeAttr("name")
            $("#namespaceURI").removeAttr("name")
            $("#endpoint").removeAttr("name")
        }
        if(selec == 2){
            $("#httpsAddressHead").attr("hidden","hidden")
            $("#topicHead").attr("hidden","hidden")
            $("#tagHead").attr("hidden","hidden")
            $("#methodName").attr("name","methodName")
            $("#namespaceURI").attr("name","namespaceURI")
            $("#endpoint").attr("name","endpoint")

            $("#httpsAddress").removeAttr("name")
            $("#topic").removeAttr("name")
            $("#tag").removeAttr("name")
            $("#methodNameHead").removeAttr("hidden")
            $("#namespaceURIHead").removeAttr("hidden")
            $("#endpointHead").removeAttr("hidden")
        }
    }
   /* $(function () {
        // 回显
        var id =$("#id").val();
        var checkedwebServiceInfo=webServiceInfoMap.get(id)
        if (checkedwebServiceInfo!=null && checkedwebServiceInfo!=undefined && JSON.stringify(checkedwebServiceInfo).indexOf("=")!=-1) {
            var checkedResourceArr=checkedwebServiceInfo.toString().split(",");
            for (var i=0;i<checkedResourceArr.length;i++) {
                var chechArr=checkedResourceArr[i].split("=");
                $('#'+chechArr[0]).val(chechArr[1])
            }
        }else if (checkedwebServiceInfo!=null && checkedwebServiceInfo!=undefined && JSON.stringify(checkedwebServiceInfo).indexOf(":")!=-1 && JSON.stringify(checkedwebServiceInfo).indexOf("=") ==-1) {
            $("#methodName").val(checkedwebServiceInfo.methodName)
            $("#namespaceURI").val(checkedwebServiceInfo.namespaceURI)
            $("#endpoint").val(checkedwebServiceInfo.endpoint)

        }
    });*/
</script>
</html>