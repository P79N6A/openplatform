<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>主表</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<t:base type="validform,bootstrap-form"></t:base>
<script src="${webRoot}/plug-in/lhgDialog/lhgdialog.min.js"></script>
<script src="${webRoot}/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
<script src="${webRoot}/plug-in/tools/curdtools.js"></script>
<link rel="stylesheet" href="plug-in/select2/css/select2.min.css" type="text/css"/>
<script type="text/javascript" src="plug-in/select2/js/select2.full.min.js" > </script>
<style>
	.childTr{
		margin-left:20px;
	}
	.btn-div a{
		border-color:#ccc;
		border-radius:3px;
	}
    #step-1,#step-2,#step-3,#step-4,#step-5{
        padding-top:10px;
    }
    .table-div{
        overflow:auto;
        min-height: 200px;
        max-height: 300px;
    }
</style>
</head>
<body>
<div style="index:9999">
<div class="container" style="width:100%;overflow-x:hidden;">
<div class="panel panel-default">
<div class="panel-body">
<form class="form-horizontal" role="form" id="addForm" method="POST">
    <input id="apiId" name="apiInfo.id" value="${apiInfoPage.id}" type="hidden"/>
	<input type="hidden" name="headers" id="headers" value=""/>
	<input type="hidden" name="requests" id="requests" />
	<input type="hidden" name="returns" id="returns" />
    <div id="smartwizard">
        <ul>
            <li><a href="#step-1">基本信息<br /></a></li>
        </ul>
        <div>
            <div id="step-1" class="">
                <fieldset>
                    <div class="main-form">
                        <div class="row">
                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                        应用名称：
                                    </div>
                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                        <input readonly="readonly" name="apiInfo.apiName" value = "${apiInfoPage.appName}"  type="text" class="form-control input-sm" maxlength="100"  ignore="ignore"  />
                                    </div>
                                </div>
                            </div>
                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                        应用描述：
                                    </div>
                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                        <input readonly="readonly" name="apiInfo.apiDesc" value = "${apiInfoPage.appDesc}" type="text" class="form-control input-sm" maxlength="200"  ignore="ignore"  />
                                    </div>
                                </div>
                            </div>
                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                        	发布状态：
                                    </div>
                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    	<t:dictSelect readonly="readonly" field="apiPublishStatus" defaultVal = "${apiInfoPage.publishStatus}" type="select" hasLabel="false" title="发布状态" extendJson="{class:'form-control input-sm'}"  typeGroupCode="pubSts" ></t:dictSelect>
                                    </div>
                                </div>
                            </div>
                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                        	审核状态：
                                    </div>
                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                    	<t:dictSelect readonly="readonly" field="apiAuditStatus" defaultVal = "${apiInfoPage.auditStatus}" type="select" hasLabel="false" title="审核状态" extendJson="{class:'form-control input-sm'}"  typeGroupCode="auditSts" ></t:dictSelect>
                                    </div>
                                </div>
                            </div>
                            <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="row">
                                    <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                        版本号：
                                    </div>
                                    <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                        <input readonly="readonly" id="version" name="apiInfo.version" value = "${apiInfoPage.appVersion}" type="text" class="form-control" maxlength="30"  ignore="ignore"  />
                                    </div>
                                </div>
                            </div>
                            <c:if test="${apiInfoPage.auditStatus == 2 || apiInfoPage.auditStatus == 3}">
                                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="row">
                                        <div class="col-md-3 col-sm-3 col-xs-3 bt-label">
                                            审核意见：
                                        </div>
                                        <div class="col-md-7 col-sm-7 col-xs-7 bt-content">
                                            <textarea rows="4"  name="apiInfo.apiAuditMsg" class="form-control input-sm">${apiInfoPage.auditMsg}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </fieldset>
            </div>
            <div id="step-5" class="" id="resourceInfo">
                <div class="bt-item col-lg-12 col-md-12 col-sm-12 col-xs-12">
                </div>
            </div>
        </div>
    </div>
</form>
</div>
</div>
</div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function() {
    $(".modal-dialog").css("width","1200px");
    $('#smartwizard').smartWizard({
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

	formControlInit();
    $(".firstTr").data("key",dateFtt("hhmmssS",new Date()));

	$("#groupSelect").on("select2:select",function(e){
		var groupName = e.params.data.text;
		$("#groupName").val(groupName);
	});

	function collectParams(tableId){
        var returns = new Array();
        var index = 1;
        $("#" +tableId + " tbody").find("tr").each(function(){
            var key = $(this).data("key");
            if(key && key != undefined){
                var ret = new Object();
                ret["paramName"] = $(this).find("input[name$='paramName']").val();
                ret["paramDesc"] = $(this).find("input[name$='paramDesc']").val();
                ret["paramVisible"] = $(this).find("select[name$='paramVisible']").val();
                ret["dataType"] = $(this).find("select[name$='dataType']").val();
                ret["defaultValue"] = $(this).find("input[name$='defaultValue']").val();
                //处理子节点
                var children = new Array();
                var sort = 1;
                $("#" +tableId + " tbody").find("tr").each(function(){
                    if($(this).data("parent-key") == key){
                        var child = new Object();
                        child["paramName"] = $(this).find("input[name$='paramName']").val();
                        child["paramDesc"] = $(this).find("input[name$='paramDesc']").val();
                        child["paramVisible"] = $(this).find("select[name$='paramVisible']").val();
                        child["dataType"] = $(this).find("select[name$='dataType']").val();
                        child["defaultValue"] = $(this).find("input[name$='defaultValue']").val();
                        child["sort"] = sort;
                        sort++;
                        children.push(child);
                    }
                })
                ret["children"] = children;
                ret["sort"] = index;
                index++;
                returns.push(ret);
            }
        })
		return returns;
	}

	initChargeMode();
    initFlowCtrl();
    //初始化资源控制判断开关
    if($("#resourceCtrl").val() == 1){
        initResourceCtrl();
    }
    $("#resourceCtrlBtn").bootstrapSwitch({
        size : "mini",
        onText : "启用",
        offText : "禁用",
        onColor : "success",
        offColor : "danger",
        // labelWidth: "8",//设置label宽度
        readonly:true,
    })
	
	$("#addForm :input").attr("readonly","true");
	$("#addForm :checkbox").attr("disabled","true");
	$("#addForm :radio").attr("disabled","true");
	
	$('#smartwizard').attr("isDone",1);  
	
});


function initChargeMode(){
    var tableNames = ["monthlyPaymentList","payPerList","payByFlowList"];
    var chargeModeIds = $("#chargeModeIds").val();
    var apiId = '${apiInfoPage.id}';
    for(var i = 0;i < tableNames.length;i++){
        var tableName = tableNames[i];
        var type = $("#" + tableName).data("type");
        $('#' +tableName).bootstrapTable({
            url : 'charging.do?datagrid&apiId='+apiId, //请求后台的URL（*）
            method : 'post', //请求方式（*）
            contentType : "application/x-www-form-urlencoded",
            striped : false, //是否显示行间隔色
            cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination : true, //是否显示分页（*）
            queryParams : function(params) {
                var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    pageSize : params.limit, // 每页要显示的数据条数
                    offset : params.offset, // 每页显示数据的开始行号
                    sort : params.sort, // 排序规则
                    order : params.order,
                    rows : params.limit, //页面大小
                    page : (params.offset / params.limit) + 1, //页码
                    pageIndex : params.pageNumber,//请求第几页
                    field : 'id,type,num,startNum,endNum,unit,price'
                };

                // var params = $("#chargeModeForm").serializeArray();
                // for (x in params) {
                //     temp[params[x].name] = params[x].value;
                // }
                temp["type"] = type;
                return temp;
            },
            sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 10, //每页的记录行数（*）
            pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
            clickToSelect : true, //是否启用点击选中行
            // height : $(window).height() - 35, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId : "id", //每一行的唯一标识，一般为主键列
            sortName:'price',
            sortOrder:'asc',
            tableId:tableName,
            columns : [
                {
                    title : '序号',
                    width : 45,
                    align : 'center',
                    switchable : false,
                    formatter : function(value,row, index) {
                        //return index+1; //序号正序排序从1开始
                        var pageSize = 10
                        var pageNumber = 1;
                        return pageSize* (pageNumber - 1) + index + 1;
                    }
                },
                {
                    field : 'id',
                    title : 'id',
                    valign : 'middle',
                    width : 120,
                    visible:false,
                    align : 'center',
                    switchable : true,
                },
                {
                    field : 'num',
                    title : '时间',
                    valign : 'middle',
                    width : 120,
                    align : 'center',
                    switchable : true,
                },{
                    field : 'startNum',
                    title : '起始值',
                    valign : 'middle',
                    width : 120,
                    align : 'center',
                    switchable : true,
                },{
                    field : 'endNum',
                    title : '截止值',
                    valign : 'middle',
                    width : 120,
                    align : 'center',
                    switchable : true,
                },{
                    field : 'unit',
                    title : '单位',
                    valign : 'middle',
                    width : 120,
                    align : 'center',
                    switchable : true,
                    formatter : function(value,row, index) {
                        if(value == 1){
                            return "周";
                        }else if(value == 2){
                            return "月";
                        }else if(value == 3){
                            return "年";
                        }else if(value == 4){
                            return "KB";
                        }else if(value == 5){
                            return "次";
                        }else {
                            return value
                        }
                    }
                },
                {
                    field : 'price',
                    title : '价格(元)',
                    valign : 'middle',
                    width : 120,
                    align : 'center',
                    switchable : true,
                }],
            onLoadSuccess : function(data) { //加载成功时执行
                //将已经选中的计费方式再次选中
                if(chargeModeIds != null && chargeModeIds != ""){
                    var ids = chargeModeIds.split(",");
                    $("#" + $(this)[0]["tableId"]).bootstrapTable("checkBy", {field:"id", values:ids})
                }
            },
            onLoadError : function() { //加载失败时执行
            }
        });
        //处理需要隐藏的列
        if(type == 1){
            $("#" + tableName).bootstrapTable("hideColumn","startNum");
            $("#" + tableName).bootstrapTable("hideColumn","endNum");
        }else if(type == 2){
            $("#" + tableName).bootstrapTable("hideColumn","num");
        }else if(type == 3){
            $("#" + tableName).bootstrapTable("hideColumn","num");
        }
    }
}



</script>
