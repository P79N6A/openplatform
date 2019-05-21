<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
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
    <%--<input id="level" type="hidden" value="${level}"/>--%>
    <%--<input id="type" type="hidden" value="${type}"/>--%>
    <%--<input id="orderId" type="hidden" value="${orderId}"/>--%>
    <table align="center" width="100%">
        <c:forEach items="${paramMap}" var="par">
            <tr>
                <td align="right" style="font-size: 16px">
                        ${par.key}:
                </td>
                <td align="left" >
                    <input class="inputstyle"  value="${par.value}" disabled/>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>
<%--<div id="tree" style="max-height:400px;overflow:auto;margin-bottom:20px;">--%>
<%--<ul class="ztree" id="resources"></ul>--%>
<%--</div>--%>
</body>
</html>