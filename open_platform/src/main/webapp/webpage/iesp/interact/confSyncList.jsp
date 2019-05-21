<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<%@ include file="/webpage/common/resource.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
		margin-bottom:2px;
	}
</style>
<script src=<%= request.getContextPath() + "/js/interact/confSyncList.js" %> type="text/javascript"></script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
				<div class="row">
					<div class="col-xs-6 form-group no-padding">                  	
                   	 	<label class="control-label col-sm-5" for=srcDsAlias>源端数据源</label>
	                    <div class="col-sm-7 no-padding">
	                    	<input type="text" class="form-control " placeholder="源端数据源别名" id="srcDsAlias">
	                    </div>
	                 </div>       
	                 <div class="col-xs-6 form-group no-padding">   
	                 	<label class="control-label col-sm-5" for="srcTblName">源端表名称</label>
	                    <div class="col-sm-7  no-padding">
	                        <input type="text" class="form-control" placeholder="源端表名称" id="srcTblName">
	                    </div>
	                 </div>	
	            </div>	                
	            <div class="row">
                     <div class="col-xs-6 form-group no-padding">   		                        
	                     <label class="control-label col-sm-5" for="tgtDsAlias">目的端数据源</label>
	                     <div class="col-sm-7 no-padding">
	                        <input type="text" class="form-control" placeholder="目的端数据源别名" id="tgtDsAlias">
	                     </div>
	                  </div>
	                  <div class="col-xs-6 form-group no-padding">        
                    		<label class="control-label col-sm-5 pull-left" for="tgtTblName">目的端表名称</label>
	                        <div class="col-sm-7 no-padding">
	                          <input type="text" class="form-control" placeholder="目的端表名称" id="tgtTblName">
	                        </div>
	                   </div>
	            </div> 
				</div>
			</div>
			<div class="col-xs-3 pull-left no-padding">
				<button type="button" class="btn btn-primary" id="search-btn">
					<span class="glyphicon glyphicon-search"></span>查询
				</button>
				<button type="button" class="btn btn-warning" id="reset-btn">
					<span class="glyphicon glyphicon-refresh"></span>重置
				</button>
				<button type="button" class="btn btn-success formnew" id="add-btn">
					<span class="glyphicon glyphicon-plus"></span>新建
				</button>
			</div>
		</form>
	</div>
    <table id="confSyncs"></table>
</body>
</html>