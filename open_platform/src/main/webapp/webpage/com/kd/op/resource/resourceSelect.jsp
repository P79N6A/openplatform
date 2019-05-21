<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
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
<form id="paramForm" >
    <%--<input id="level" type="hidden" value="${level}"/>--%>
    <%--<input id="type" type="hidden" value="${type}"/>--%>
    <input id="id" name="id" type="hidden" value="${id}"/>
    <%--<input id="orderId" type="hidden" value="${orderId}"/>--%>
        <table align="center" width="100%">
            <c:forEach items="${paramMap}" var="par">
               <%-- <input type="hidden" id="resourcename" name="resourcename" value="${par.key}">
                <input type="hidden" id="resourcevalue" name="resourcevalue" value="${par.value}">--%>
                <tr>
                    <td align="right" style="font-size: 16px">
                            ${par.key}:
                    </td>
                    <td align="left" >
                        <input class="inputstyle" id="${par.key}" name="${par.key}" value="${par.value}" />
                    </td>
                </tr>
            </c:forEach>
        </table>
</form>
<%--<div id="tree" style="max-height:400px;overflow:auto;margin-bottom:20px;">--%>
<%--<ul class="ztree" id="resources"></ul>--%>
<%--</div>--%>
</body>
<script type="text/javascript">
    var formDataLength;
    function getFormData(){
        var formdata=new Array();
        var paramForm = $("#paramForm").serialize()

        var paramFormArr=paramForm.toString().split("&")
        formDataLength=paramFormArr.length
        for (var j = 0; j < paramFormArr.length; j++){
            var formArr=paramFormArr[j].split("=")
            // && formArr[1]!=""
            if (  formArr[1]!=null &&formArr[1]!=undefined) {
                formdata.push(paramFormArr[j]);
            }
        }
        return formdata;
    }

    $(function () {
        // 回显
        var id =$("#id").val();
        var checkedResource=apiResourceMap.get(id)
        if (checkedResource!=null && checkedResource!=undefined && checkedResource.toString().indexOf("=")!=-1) {
            var checkedResourceArr=checkedResource.toString().split(",");
            for (var i=0;i<checkedResourceArr.length;i++) {
                var chechArr=checkedResourceArr[i].split("=");
                $('#'+chechArr[0]).val(chechArr[1])
            }
        }else if (checkedResource!=null && checkedResource!=undefined && checkedResource.toString().indexOf(":")!=-1) {
            var resourceParams=checkedResource.toString().split(",");
            for (var i = 0; i < resourceParams.length; i++) {
                var paramStr
                if (resourceParams[i].indexOf("\"")!=-1) {
                    paramStr = resourceParams[i].replace(/\"/g,"");
                }
                var paramSplit=paramStr.split(":")
                if (i == 0 && i != resourceParams.length - 1) {
                    var resourceParamKey=paramSplit[0].replace("[","");
                    $('#'+resourceParamKey).val(paramSplit[1])
                } else if(i == 0 && i == resourceParams.length - 1) {
                    var resourceParamKey=paramSplit[0].replace("[","");
                    var resourceParamValue = paramSplit[1].replace("]", "");
                    $('#'+resourceParamKey).val(resourceParamValue)

                }else if (i == resourceParams.length - 1) {
                    var resourceParamValue = paramSplit[1].replace("]", "");
                    $('#'+paramSplit[0]).val(resourceParamValue)
                } else {
                    $('#'+paramSplit[0]).val(paramSplit[1])
                }
            }
        }
    });
</script>
</html>