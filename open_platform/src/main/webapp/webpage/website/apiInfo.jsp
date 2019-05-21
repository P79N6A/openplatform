<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/webpage/common/websiteResources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智慧车联网能力开放平台</title>
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/iconfont/iconfont.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/tb.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/api.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/style.css" %> type="text/css">
<link rel="stylesheet" href=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.min.css" %> type="text/css">
<script src=<%= request.getContextPath() + "/js/website/common.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/js/website/api.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/treeview/bootstrap-treeview.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstraptable-treeview.js"%>></script>
<script src=<%= request.getContextPath() + "/plug-in/bootstrap/js/bootstrap-table-treegrid.js" %> type="text/javascript"></script>
<script src=<%= request.getContextPath() + "/plug-in/jquery-plugs/treegrid/jquery.treegrid.js" %> type="text/javascript"></script>
	<style>
		.api-content{
			width:1200px;
		}
		#groupTree li{
			background-color: #ffffff;
			z-index: 1200;
		}
		#hideDiv{
			position: absolute;
			width: 100%;
		}
		.selected-node{
			color:#ffffff;
			background-color:#3954ff;
		}
		#groupTree li:hover{
			background-color: #3954ff;
			color:#ffffff;
		}
	</style>
</head>
<body style="background-color:#F5F5F5;">
	<%--<div class="header">--%>
		<%@ include file="header.jsp" %>
	<%--</div>--%>
	<div class="content api-content">
		<div class="side-menu">
			<div class="menu-content">
				<span class="menu-select1">
					<input type="text" id="knowledgeText" readonly="readonly" name="knowledgeText" class="form-control" value=""
						   onclick="$('#hideDiv').show()" placeholder="选择能力分组"/>
					<div id="hideDiv" style="display: none;max-height:400px;overflow-y: auto">
						<div id="groupTree"></div>
					</div>
				</span>
				<div class="menu-list" style="max-height:624px;overflow: auto">
					<ul class="api-list">
					</ul>
				</div>
			</div>
		</div>
		<div class="main-content">
			<div class="msg-content">
				<div class="msg-tips" id="msg-tips">获取授权用户的基本信息</div>
				<a class="msg-title">API用户授权类型</a>
				<p>需要授权</p>
				<a class="msg-title">请求参数</a>
				<div class="app-table">
					<table id="app-table" class="table">
					</table>
				</div>
				<a class="msg-title">响应参数</a>
				<div class="return-table">
					<table id="return-table" class="table">
					</table>
				</div>
				<div>
					<a class="msg-title">出参示例</a>
					<p class="args-demo" id="args-demo"></p>
				</div>
			</div>
		</div>
	</div>
	<div class="footer" style="background-color:#000000;">
		<%@ include file="footer.jsp" %>
	</div>
</body>
</html>