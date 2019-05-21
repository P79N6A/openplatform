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
<script src="${pageContext.request.contextPath}/js/system/approvalUserList.js" type="text/javascript"></script>
</head>
<body>
	<div class="search-div">
		<form id="searchForm" class="searchForm" class="form-horizontal">
			<div class="form-horizontal col-xs-9 pull-left" role="form">
				<div class="col-xs-12 pull-left no-padding">
					<div class="col-xs-6 form-group no-padding">                  	
                   	 		<label class="control-label col-sm-5" for="userName">用户名</label>
	                        <div class="col-sm-7 no-padding">
	                            <input type="text" class="form-control " placeholder="用户名" id="userName">
	                        </div>
	                 </div>       
	             </div>
	             <input type="text" value="解决bootstrap与AJAX异步提交表单的冲突" hidden />
	       </div>  
	       <div class="col-xs-3 pull-left no-padding">
				<button type="button" class="btn btn-primary" id="btn_query">
					<span class="glyphicon glyphicon-search"></span>查询
				</button>
				<button type="button" class="btn btn-warning" id="reset-btn">
					<span class="glyphicon glyphicon-refresh"></span>重置
				</button>
			</div>
		 </form>
    </div>
    <table id="approvalUsers"></table>
        <div class="modal" id="approvalUserModal">
	    <div class="modal-dialog" style="">
	        <div class="modal-content">
	          
	        </div>
	    </div>
	</div>
</body>
</html>