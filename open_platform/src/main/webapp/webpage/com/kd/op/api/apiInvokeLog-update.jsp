<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>能力调用日志表</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
</head>
<body style="overflow:hidden;overflow-y:auto;">
<div class="container" style="width:100%;">
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="formobj" method="POST">
            <div class="row">
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            能力名称：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="apiName" type="text" class="form-control" maxlength="255"
                                   value="${apiInvokeLog.apiName}" ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            场景名称：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="apiName" type="text" class="form-control" maxlength="255"
                                   value="${apiInvokeLog.sceneName}" ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            调用时间：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input type="text" class="form-control"
                                   value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${apiInvokeLog.invokeTime}'/>"
                                   ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6 <%--col-sm-pull-6 col-md-pull-6--%>">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            调用IP：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="invokeIp" type="text" class="form-control" maxlength="255"
                                   value="${apiInvokeLog.invokeIp}" ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            请求类型：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="methodType" type="text" class="form-control" maxlength="255"
                                   value="${apiInvokeLog.methodType}" ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            调用地址：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="invokeUrl" type="text" class="form-control" maxlength="255"
                                   value="${apiInvokeLog.invokeUrl}" ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <%--<div class="bt-item col-md-6 col-sm-6">--%>
                <%--<div class="row">--%>
                <%--<div class="col-md-2 col-sm-2 col-xs-2 bt-label">--%>
                <%--应用名称：--%>
                <%--</div>--%>
                <%--<div class="col-md-10 col-sm-10 col-xs-10 bt-content">--%>
                <%--<input name="invokeUrl" type="text" class="form-control" maxlength="255" value = "${apiInvokeLog.appName}"  ignore="ignore"  />--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<div class="bt-item col-md-6 col-sm-6">--%>
                <%--</div>--%>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            响应时长：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="responseTimeLength" type="text" class="form-control" maxlength="22"
                                   value="${apiInvokeLog.responseTimeLength}ms" datatype="/^(-?\d+)(\.\d+)?$/"
                                   ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            响应流量：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="responseFlowSize" type="text" class="form-control" maxlength="22"
                                   value="${apiInvokeLog.responseFlowSize}KB" datatype="/^(-?\d+)(\.\d+)?$/"
                                   ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label">
                            请求流量：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <input name="requestFlowSize" type="text" class="form-control" maxlength="22"
                                   value="${apiInvokeLog.requestFlowSize}KB" datatype="/^(-?\d+)(\.\d+)?$/"
                                   ignore="ignore"/>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-12 col-sm-12">
                    <div class="row">
                        <div class="col-md-1 col-sm-1 col-xs-1 bt-label">
                            请求头：
                        </div>
                        <div class="col-md-11 col-sm-11 col-xs-11 bt-content">
                            <textarea class="form-control" rows="4">${apiInvokeLog.requestHeader}</textarea>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-12 col-sm-12">
                    <div class="row">
                        <div class="col-md-1 col-sm-1 col-xs-1 bt-label">
                            请求参数：
                        </div>
                        <div class="col-md-11 col-sm-11 col-xs-11 bt-content">
                            <textarea class="form-control" rows="4">${apiInvokeLog.requestParam}</textarea>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-12 col-sm-12">
                    <div class="row">
                        <div class="col-md-1 col-sm-1 col-xs-1 bt-label">
                            响应参数：
                        </div>
                        <div class="col-md-11 col-sm-11 col-xs-11 bt-content">
                            <textarea class="form-control" rows="4">${apiInvokeLog.returnParam}</textarea>
                        </div>
                    </div>
                </div>
                <%--<div class="bt-item col-md-6 col-sm-6">--%>
                <%--<div class="row">--%>
                <%--<div class="col-md-2 col-sm-2 col-xs-2 bt-label">--%>
                <%--响应头：--%>
                <%--</div>--%>
                <%--<div class="col-md-10 col-sm-10 col-xs-10 bt-content">--%>
                <%--<textarea class="form-control" rows="4">${apiInvokeLog.returnHeader}</textarea>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label" title="能力开放平台调用结果">
                            平台调用：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <c:if test="${apiInvokeLog.invokeOpenplatformResult == 1}">
                                <input value="失败" class="form-control" title="能力开放平台调用结果"/>
                            </c:if>
                            <c:if test="${apiInvokeLog.invokeOpenplatformResult == 0}">
                                <input value="成功" class="form-control" title="能力开放平台调用结果"/>
                            </c:if>
                            <c:if test="${apiInvokeLog.invokeOpenplatformResult == -1 || apiInvokeLog.invokeOpenplatformResult == null}">
                                <input value="未知异常" class="form-control" title="能力开放平台调用结果"/>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="bt-item col-md-6 col-sm-6">
                    <div class="row">
                        <div class="col-md-2 col-sm-2 col-xs-2 bt-label" title="能力提供者调用结果">
                            能力调用：
                        </div>
                        <div class="col-md-10 col-sm-10 col-xs-10 bt-content">
                            <c:if test="${apiInvokeLog.invokeServiceproviderResult == 1}">
                                <input value="失败" class="form-control" title="能力提供者调用结果"/>
                            </c:if>
                            <c:if test="${apiInvokeLog.invokeServiceproviderResult == 0}">
                                <input value="成功" class="form-control" title="能力提供者调用结果"/>
                            </c:if>
                            <c:if test="${apiInvokeLog.invokeServiceproviderResult == -1 || apiInvokeLog.invokeServiceproviderResult == null}">
                                <input value="未知异常" class="form-control" title="能力提供者调用结果"/>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $(".modal-dialog").css("width","1200px");
//        $("form input,textarea").attr("readonly", "readonly")
        $('#formobj input,textarea').attr("readonly","readonly")
    });
</script>
</body>
</html>