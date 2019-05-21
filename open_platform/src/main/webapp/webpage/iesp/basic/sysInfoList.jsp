<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
<script src="${pageContext.request.contextPath}/js/basic/sysInfoList.js" type="text/javascript"></script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
				 <div class="row">
					<div class="col-xs-6 form-group no-padding">                  	
                   	 		<label class="control-label col-sm-5" for="sysname">系统名称</label>
	                        <div class="col-sm-7 no-padding">
	                            <input type="text" class="form-control " placeholder="系统名称" id="sysname">
	                        </div>
	                 </div>       
	                 <div class="col-xs-6 form-group no-padding">   
	                        <label class="control-label col-sm-5" for="syscode">系统编号</label>
	                        <div class="col-sm-7  no-padding">
	                            <input type="text" class="form-control" placeholder="系统编号" id="syscode">
	                        </div>
	                 </div>	
	                </div>
	                
	                <div class="row">
                     <div class="col-xs-6 form-group no-padding">   		                        
	                        <label class="control-label col-sm-5" for="position">所属区域</label>
	                        <div class="col-sm-7 no-padding">
	                        <select class="selectpicker form-control" id="position">
	                            	<option value="">全部</option>
	                            	<option value="01">内网</option>
	                            	<option value="03">外网</option>
	                            </select>
	                        </div>
	                   </div>
	                   <div class="col-xs-6 form-group no-padding">        
                    		<label class="control-label col-sm-5 pull-left" for="state">状态</label>
	                        <div class="col-sm-7 no-padding">
	                            <select class="selectpicker form-control" id="state">
	                            	<option value="">全部</option>
	                            	<option value="0">停用</option>
								    <option value="1">启用</option>
	                            </select>
	                        </div>
	                    </div>
	                   </div> 
	                  </div>
	       </div>  
	       <div class="col-xs-3 pull-left no-padding">
				<button type="button" class="btn btn-primary" id="btn_query">
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
    <table id="sysInfos"></table>
        <div class="modal" id="sysInfoModal">
	    <div class="modal-dialog" style="">
	        <div class="modal-content">
	          
	        </div>
	    </div>
	</div>
</body>
</html>