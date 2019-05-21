<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	var table=document.getElementById("targetTopics");  
	 for(var i=0;i<data.length;i++){  
		 var row=table.insertRow(table.rows.length);  
		 var c1=row.insertCell(0);  
		 c1.innerHTML=i+1;
		 var c2=row.insertCell(1);  
		 c2.innerHTML=data[i].src;
		 var c3=row.insertCell(2);  
		 c3.innerHTML=data[i].tgt;
	 }

</script>
</head>
<body>
	<div>
		<div class="modal-body">
			<form id="addForm" name="addForm" method="post" class="form-horizontal read-only" role="form">
		    	<div class="form-group col-sm-6">
		    		<label for="csName" style="padding-left:0;text-align:left;padding-right:0" class="col-sm-3 control-label">配置信息名称</label>
		        	<div class="col-sm-8">
		        		<input type="text" style="padding-left:0" id="csName" name="csName" readonly="readonly" class="form-control" value="${confSync.csName}"/>
		        	</div>
				</div>
				<span class="clearfix"></span>
		    	<div class="form-group ">
		    		<div class="col-sm-6">
			    		<div class="text-center">
			    			<label>源端数据源别名</label>
			    		</div>
						<select class="bootstrap-select form-control" disabled name="srcDsId" style="border:1px solid #ccc;"> 
						<c:if test="${confSync.srcPosition == '01'}">           
                            <option value="${confSync.srcDsId}" selected>${confSync.srcDsAlias}(内网)</option>
                        </c:if>
                      <c:if test="${confSync.srcPosition == '03'}">           
                            <option value="${confSync.srcDsId}" selected>${confSync.srcDsAlias}(外网)</option>
                        </c:if>
			    		</select>
						<div class="text-center">
			    			<label>源端表名称</label>
			    		</div>
						<select class="bootstrap-select form-control" disabled name="srcTblId" style="border:1px solid #ccc;">
			    			<option value="${confSync.srcTblId}">${confSync.srcTblName}</option>
			    		</select>	
		    		</div>
		    		<div class="col-sm-6">
			    		<div class="text-center">
			    			<label>目的端数据源别名</label>
			    		</div>
						<select class="bootstrap-select form-control" disabled name="tgtDsId" style="border:1px solid #ccc;">
						<c:if test="${confSync.tgtPosition == '01'}">  
							<option value="${confSync.tgtDsId}" selected>${confSync.tgtDsAlias}(内网)</option>
						</c:if>
						<c:if test="${confSync.tgtPosition == '03'}">  
							<option value="${confSync.tgtDsId}" selected>${confSync.tgtDsAlias}(外网)</option>
						</c:if>
			    		</select>
						<div class="text-center">
			    			<label>目的端表名称</label>
			    		</div>
						<select class="bootstrap-select form-control" disabled name="tgtTblId" style="border:1px solid #ccc;">
                           	<option value="${confSync.tgtTblId}" selected>${confSync.tgtTblName}</option>
			    		</select>
			    	</div>
			    	 <div class="col-sm-12">
			    	<label class="text-info">列对应关系</label>
			    	</div>
			    	<div class="col-sm-12">
			    	<div style="border:1px #ccc solid;max-height:250px;overflow:auto">
			    		<table id="targetTopics" class="table table-striped table-bordered table-hover table-condensed">
			    			<thead>
			    				<tr>
			    					<th width="10%">序号</th>
			    					<th width="40%">源端列名称</th>
			    					<th width="50%">目的端列名称</th>
			    				</tr>
			    			</thead>
			    		</table>
			    	</div>
			    	</div>
		    	</div>
		    </form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>