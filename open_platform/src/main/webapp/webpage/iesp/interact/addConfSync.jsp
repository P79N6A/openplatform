<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src=<%= request.getContextPath() + "/js/interact/addConfSync.js" %> type="text/javascript"></script>
<title>Insert title here</title>
<style>
	.table-hover > tbody > tr > td.success:hover, .table-hover > tbody > tr > th.success:hover, .table-hover > tbody > tr.success:hover > td, .table-hover > tbody > tr:hover > .success, .table-hover > tbody > tr.success:hover > th {
		background-color:#91f16b
	}
	.table > thead > tr > td.success, .table > tbody > tr > td.success, .table > tfoot > tr > td.success, .table > thead > tr > th.success, .table > tbody > tr > th.success, .table > tfoot > tr > th.success, .table > thead > tr.success > td, .table > tbody > tr.success > td, .table > tfoot > tr.success > td, .table > thead > tr.success > th, .table > tbody > tr.success > th, .table > tfoot > tr.success > th{
		background-color:#91f16b
	}
</style>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<div class="form-group col-sm-6">
		    		<label for="csName" style="padding-left:0;text-align:left;padding-right:0" class="col-sm-3 control-label">配置信息名称</label>
		        	<div class="col-sm-8">
		        		<input type="text" style="padding-left:0px" id="csName" name="csName" class="form-control" placeholder="请输入配置信息名称" />
		        	</div>
		        </div>
		        <span class="clearfix"></span>
		        <div class="form-group ">
		    		<div class="col-sm-6">
			    		<div class="text-center">
			    			<label>源端数据源别名</label>
			    		</div>
			    		<select class="bootstrap-select form-control" id="srcDsAliasSelect" data-size=6 name="srcDsId" 
			    			onChange="selectDs(this)" style="border:1px solid #ccc;" data-tableid="srcTblNameSelect">
			    			<option value="">请选择源端数据源</option>
                            <c:forEach items="${dataSources}" var="ds"  varStatus="status">
                           		<c:if test="${ds.position == '01'}">
                           			<option value="${ds.dsId}">${ds.dsAlias}(内网)</option>
								</c:if>
								<c:if test="${ds.position == '03'}">
									<option value="${ds.dsId}">${ds.dsAlias}(外网)</option>
								</c:if>
                           	</c:forEach>
			    		</select>
			    		
			    		
						<div class="text-center">
			    			<label>源端表名称</label>
			    		</div>
			    		<select class="bootstrap-select form-control" id="srcTblNameSelect" data-size=6 name="srcTblId" onChange="selectCol(this)" style="border:1px solid #ccc;">
			    		</select>			    		
		    		</div>
		    		<div class="col-sm-6">
			    		<div class="text-center">
			    			<label>目的端数据源别名</label>
			    		</div>
			    		<select class="bootstrap-select form-control" id="tgtDsAliasSelect" data-size=6 name="tgtDsId"
			    			 onChange="selectTgtDs(this)" style="border:1px solid #ccc;" data-tableid="tgtTblName">
			    			 <option value="">请选择目的端数据源</option>
                            <c:forEach items="${dataSources}" var="ds"  varStatus="status">
                           		<c:if test="${ds.position == '01'}">
                           			<option value="${ds.dsId}">${ds.dsAlias}(内网)</option>
								</c:if>
								<c:if test="${ds.position == '03'}">
									<option value="${ds.dsId}">${ds.dsAlias}(外网)</option>
								</c:if>
                           	</c:forEach>
			    		</select>
						<div class="text-center">
			    			<label>目的端表名称</label>
			    		</div>
			    		<select class="bootstrap-select form-control" id="tgtTblNameSelect" data-size=6 name="tgtTblId" onChange="selectTgtCol(this)"  style="border:1px solid #ccc;">
                           
			    		</select>			    		
			    	</div>
		    	</div>
		    	
		    	<div class="form-group" >
		    		<div class="col-sm-12">
			    	<label class="text-info">列对应关系</label>
			    	</div>
					<input type="hidden" id="syncInfo"  name="syncInfo"/>
					<div class="col-sm-12" style="max-height:270px; overflow:auto;">
						<table class="table table-bordered" id="dataTable">
						 <thead>
							<tr>
							<th>源端列名称</th>
							<th>目的端列名称</th>
							<th class="text-center">操作</th>
							</tr>
						 </thead>
						 <tbody>
							<tr>
								<td>
									<select class="form-control" id="srcColSelect" name="srcColSelect"></select>
								</td>
								<td>
									<select class="form-control"  id="tgtColSelect" name="tgtColSelect"></select>
								</td>
								<td class="text-center">
									<button type="button" class='btn btn-xs btn-success form-control'  title='添加' onclick="addColumn(this)"><span class='glyphicon glyphicon-plus'></span></button>
									<button type="button" class=' btn btn-xs btn-danger form-control'  title='删除' style='display:none'onclick="deleteColumn(this)"><span class='glyphicon glyphicon-remove'></span></button>
								</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>
		    </form>
		</div>
	</div>
</body>
</html>