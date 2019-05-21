<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="/webpage/common/resource.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.fixed-table-toolbar .bs-bars,.fixed-table-toolbar .search,.fixed-table-toolbar .columns
	{
	margin-bottom: 2px;
}
html,body{
   		height:100%;
   		width:100%;
   	}
	.row div{
		border:1px solid black;
	}
	.tab-pane{
		height:100%
	}
</style>
<script>
	$(document).ready(function(){
	})
</script>
</head>
<body>
<div style="height:100%">

  <ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#dataBase" aria-controls="home" role="tab" data-toggle="tab">数据库详情</a></li>
    <li role="presentation"><a href="#message" aria-controls="profile" role="tab" data-toggle="tab">图标详情</a></li>
  </ul>

  <div class="tab-content" style="height:calc(100% - 45px)">
    <div role="tabpanel" class="tab-pane active" id="dataBase">
    	<iframe height="100%" width="100%" src="dataBase.do?dataBaseList"></iframe>
    </div>
    <div role="tabpanel" class="tab-pane" id="message">
    <iframe height="100%" width="100%" src=""></iframe>
    </div>
  </div>

</div>
</body>
</html>