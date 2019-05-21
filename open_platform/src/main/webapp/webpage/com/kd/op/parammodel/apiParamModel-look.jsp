<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>参数模型查看</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <t:base type="validform,bootstrap-form"></t:base>
    <script src="${webRoot}/plug-in/lhgDialog/lhgdialog.min.js"></script>
    <script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
    <script src="${webRoot}/plug-in/tools/curdtools.js"></script>
    <style>
        .btn-div a{
            border-color:#ccc;
            border-radius:3px;
        }
        #step-1,#step-2{
            padding-top:10px;
        }
        .table input, select{
            width: 100% !important;
        }
        .table th{
            white-space: nowrap
        }
        body{
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="container" style="width:100%;overflow-x:hidden">
    <div class="panel panel-default">
        <div class="panel-body">
            <form class="form-horizontal" role="form" id="lookForm" method="POST" >
                <div id="smartwizard">
                    <ul>
                        <li><a href="#step-1">基本信息<br /></a></li>
                        <li><a href="#step-2">参数信息<br /></a></li>
                    </ul>
                    <div>
                        <div id="step-1" class="" style="width: 800px;height:400px;">
                            <div class="main-form">
                                <div class="row">
                                    <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="row">
                                            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                模型名称：
                                            </div>
                                            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                <input  name="modelName" type="text" class="form-control input-sm" maxlength="100"  ignore="ignore" value="${apiParamModel.modelName}" />
                                            </div>
                                            <span class="col-lg-1 col-md-1 col-sm-1  Range"><span style="color:red">*</span></span>
                                        </div>
                                    </div>
                                    <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                        <div class="row">
                                            <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                                模型描述：
                                            </div>
                                            <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                                <input name="modelDesc"  type="text" class="form-control input-sm" maxlength="200"  ignore="ignore" value="${apiParamModel.modelDesc}" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="step-2" class="" style="height:400px;">
                            <div class="params_content" id="params" >
                                <div class="params_content_table" style="margin-top: 10px;overflow-y: scroll;height: 300px" style="width: 800px">
                                    <table class="table" id="paramsTable">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>参数名称</th>
                                            <th>参数描述</th>
                                            <th>数据类型</th>
                                            <th>参数默认值</th>
                                        </thead>
                                        <tbody>
                                        <c:if test="${apiParamModel.paramModelDetails.size()>0}">
                                            <c:forEach items="${apiParamModel.paramModelDetails}" var="detail" varStatus="sts">
                                                <tr class="tempTr">
                                                    <td style="padding: 10px 15px">
                                                        <span style="text-align: center">${sts.index+1}</span>
                                                    </td>
                                                    <td>
                                                        <input name="paramModelDetails[sts.index].paramName"  type="text" class="form-control input-sm"   maxlength="50"  ignore="ignore"  value="${detail.paramName}"/>
                                                    </td>
                                                    <td>
                                                        <input name="paramModelDetails[sts.index].paramDesc"  type="text" class="form-control input-sm" maxlength="200"   ignore="ignore" value="${detail.paramDesc}" />
                                                    </td>
                                                    <td>
                                                        <t:dictSelect field="paramModelDetails[sts.index].dataType"  type="select" hasLabel="false" title="数据类型" extendJson="{class:'form-control input-sm'}"  typeGroupCode="parDType" defaultVal="${detail.dataType}"></t:dictSelect>
                                                    </td>
                                                    <td>
                                                        <input name="paramModelDetails[sts.index].defaultValue"  type="text" class="form-control input-sm"  maxlength="50"  ignore="ignore"  value="${detail.defaultValue}"/>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        </tbody>
                                    </table>
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
    $(document).ready(function() {
        $(".modal-dialog").css("width","1000px");
        $('#smartwizard').smartWizard({
            keyNavigation: false,   //禁用键盘左右键激活上一步、下一步
            cycleSteps: true, // Allows to cycle the navigation of steps
            lang: {  // Language variables
                next: '下一步',
                previous: '上一步'
            },
            anchorSettings: {
                anchorClickable: true, // Enable/Disable anchor navigation
                enableAllAnchors: true, // Activates all anchors clickable all times
                markDoneStep: true, // Add done css
                markAllPreviousStepsAsDone: true, // When a step selected by url hash, all previous steps are marked done
                removeDoneStepOnNavigateBack: false, // While navigate back done step after active step will be cleared
                enableAnchorOnDoneStep: true // Enable/Disable the done steps navigation
            },
            theme: 'dots',
            transitionEffect: 'fade', // Effect on navigation, none/slide/fade
            transitionSpeed: '400'
        });
        $("#smartwizard").find("li:first").find("a").click();

        //按钮input不可点击
        $("#lookForm :input[type=text] , select").attr("disabled","disabled");
    });
</script>

</body>
</html>