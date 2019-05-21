<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>能力应用表</title>
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
	<form class="form-horizontal" role="form" id="addForm" method="POST">
		<input type="hidden" id="id" name="id" value="${environment.id}"/>
		<div class="form-group">
			<label for="ip" class="col-sm-3 control-label">链接地址：</label>
			<div class="col-sm-9">
				<div class="input-group" style="width:100%">
					<input id="ip" name="ip" type="text" maxlength="100" value="${environment.ip}" class="form-control input-sm"
						   placeholder="IP:port或者域名" />
				</div>
			</div>
		</div>
		<%--<div class="form-group">
			<label for="port" class="col-sm-3 control-label">应用版本：</label>
			<div class="col-sm-9">
				<div class="input-group" style="width:100%">
					<input id="port" name="port" type="number" value="${environment.port}" class="form-control input-sm" placeholder="请输入端口号"  />
				</div>
			</div>
		</div>--%>
	</form>
	</div>
 </div>
<script type="text/javascript">
$(document).ready(function() {
    $("#btn_sub").bind("click",function(){
        var options={
            url:"apiSysEnvironController.do?doUpdate",
            type:"post",
            dataType:"json",
            beforeSubmit:function(){
                /*var ip = $("#ip").val();
                var port = $("#port").val();
                if(ip == null || ip == ""){
                    slowNotify("IP地址不能为空！","danger");
                    return false;
                }
                var ips=ip.split(",");
                var reg ="";
                for(var i=0;i<ips.length;i++){
                    var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                    reg = ips[i].match(exp);
                    if(!reg){
                        slowNotify("IP地址" + ips[i] + "格式有误！","danger");
                        return false;
					}
                }
                if(port == null || port.trim() == ""){
                    slowNotify("端口号不能为空！","danger");
                    return false;
                }
                if(port <= 1024 || port >= 65535){
                    slowNotify("端口号必须大于1024小于65535！","danger");
                    return false;
                }*/
            },
            success:function(data){
                if(data.success){
                    $(".bootstrap-dialog").modal("hide")
                    reloadTable();
                    quickNotify(data.msg,"success");
                }else{
                    slowNotify(data.msg,"danger");
                }
            },
        }
        $("#addForm").ajaxSubmit(options);
    })
});
</script>
</body>
</html>