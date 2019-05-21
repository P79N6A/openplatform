<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src=<%= request.getContextPath() + "/js/interact/editConfSync.js" %> type="text/javascript"></script>
<title>Insert title here</title>
<style>
	.table-hover > tbody > tr > td.success:hover, .table-hover > tbody > tr > th.success:hover, .table-hover > tbody > tr.success:hover > td, .table-hover > tbody > tr:hover > .success, .table-hover > tbody > tr.success:hover > th {
		background-color:#91f16b
	}
	.table > thead > tr > td.success, .table > tbody > tr > td.success, .table > tfoot > tr > td.success, .table > thead > tr > th.success, .table > tbody > tr > th.success, .table > tfoot > tr > th.success, .table > thead > tr.success > td, .table > tbody > tr.success > td, .table > tfoot > tr.success > td, .table > thead > tr.success > th, .table > tbody > tr.success > th, .table > tfoot > tr.success > th{
		background-color:#91f16b
	}
</style>
<script type="text/javascript">
	var data = ${confSync.syncInfo};
	var tr=$("#button").parent().parent();
	var html = "";
	for(var i=0;i<data.length;i++){ 
		 var scrCol = data[i].src;
		 var tgtCol = data[i].tgt;
		 if(i == data.length - 1){
			 html += '<tr>'+
				'<td>' +
					'<select class="form-control" id="srcColSelect' + i + '" name="srcColSelect">' +
					'<option value=' +scrCol + '>' + scrCol + '</option>' +
					'</select>'+
				'</td>'+
				'<td>'+
					'<select class="form-control" id="tgtColSelect' + i + '"name="tgtColSelect">'+
					'<option value=' +tgtCol + '>' + tgtCol + '</option>' +
					'</select>'+
				'</td>'+
				'<td class="text-center">'+
					'<button type="button" id="button" class="btn btn-xs btn-success form-control"  title="添加" style="display:block" onclick="editColumn(this)"><span class="glyphicon glyphicon-plus"></span></button>'+
					'<button type="button"  id="button" class="btn btn-xs btn-danger form-control"  title="删除" style="display:none" onclick="deleteColumn(this)"><span class="glyphicon glyphicon-remove"></span></button>'+
				'</td>'+
				'</tr>';
		 }else{
			 html += '<tr>'+
				'<td>' +
					'<select class="form-control" readonly="readonly" id="srcColSelect' + i + '" name="srcColSelect">' +
					'<option value=' +scrCol + '>' + scrCol + '</option>' +
					'</select>'+
				'</td>'+
				'<td>'+
					'<select class="form-control" readonly="readonly" id="tgtColSelect' + i + '"name="tgtColSelect">'+
					'<option value=' +tgtCol + '>' + tgtCol + '</option>' +
					'</select>'+
				'</td>'+
				'<td class="text-center">'+
					/* '<button type="button" id="button" class="btn btn-xs btn-success form-control"  title="添加" onclick="editColumn(this)"><span class="glyphicon glyphicon-plus"></span></button>'+ */
					'<button type="button"  id="button" class="btn btn-xs btn-danger form-control"  title="删除" style="display:block" onclick="deleteColumn(this)"><span class="glyphicon glyphicon-remove"></span></button>'+
				'</td>'+
				'</tr>';
		 }
		 
		/* tr.before(html);
		$("#button").next().attr("style","display:block"); */
	 }
	$("#columnBody").html(html);
</script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
				<input type="hidden" id="csId" name="csId" value="${confSync.csId}" />
				<input type="hidden" id="createTime" name="createTime" value="${confSync.createTime}" />
				<input type="hidden" id="srcDsId" name="srcDsId" value="${confSync.srcDsId}" />
				<input type="hidden" id="srcTblId" name="srcTblId" value="${confSync.srcTblId}" />
				<input type="hidden" id="tgtDsId" name="tgtDsId" value="${confSync.tgtDsId}" />
				<input type="hidden" id="tgtTblId" name="tgtTblId" value="${confSync.tgtTblId}" />
		    	<div class="form-group col-sm-6">
		    		<label for="csName" style="padding-left:0;text-align:left;padding-right:0" class="col-sm-3 control-label">配置信息名称</label>
		        	<div class="col-sm-8">
		        		<input type="text" style="padding-left:0" id="csName" readonly="readonly" name="csName" class="form-control" value="${confSync.csName}"  />
		        	</div>
		        </div>
		        <span class="clearfix"></span>				
		    	<div class="form-group ">
		    		<div class="col-sm-6">
			    		<div class="text-center">
			    			<label>源端数据源别名</label>
			    		</div>
			    		<c:if test="${confSync.srcPosition == '01'}">
			    			<input type="text" readonly="readonly" name="srcDsAlias" class="form-control" value="${confSync.srcDsAlias}(内网)"  />
			    		</c:if>
			    		<c:if test="${confSync.srcPosition == '03'}">
			    			<input type="text" readonly="readonly" name="srcDsAlias" class="form-control" value="${confSync.srcDsAlias}(外网)"  />
			    		</c:if>
						<div class="text-center">
			    			<label>源端表名称</label>
			    		</div>
			    		<input type="text" readonly="readonly" name="srcTblName" class="form-control" value="${confSync.srcTblName}"  />		    		
		    		</div>
		    		<div class="col-sm-6">
			    		<div class="text-center">
			    			<label>目的端数据源别名</label>
			    		</div>
			    		<c:if test="${confSync.tgtPosition == '01'}">
			    			<input type="text" readonly="readonly" name="tgtDsAlias" class="form-control" value="${confSync.tgtDsAlias}(内网)"  />
			    		</c:if>
			    		<c:if test="${confSync.tgtPosition == '03'}">
			    			<input type="text" readonly="readonly" name="tgtDsAlias" class="form-control" value="${confSync.tgtDsAlias}(外网)"  />
			    		</c:if>	
						<div class="text-center">
			    			<label>目的端表名称</label>
			    		</div>
			    		<input type="text" readonly="readonly" name="tgtTblName" class="form-control" value="${confSync.tgtTblName}"  />		    		
			    	</div>
		    	</div>
		    	
		    	<div class="form-group" >
		    		<div class="col-sm-12">
			    	<label class="text-info">列对应关系</label>
			    	</div>
					<input type="hidden" id="syncInfo"  name="syncInfo"></input>
					<div class="col-sm-12" style="max-height:270px; overflow:auto;">
						<table class="table table-bordered" id="dataTable">
						 <thead id ="thead">
							<tr>
							<th>源端列名称</th>
							<th>目的端列名称</th>
							<th class="text-center">操作</th>
							</tr>
						 </thead>
						 <tbody id="columnBody">
						</tbody>
						</table>
					</div>
				</div>
		    </form>
		</div>
	</div>
</body>
</html>