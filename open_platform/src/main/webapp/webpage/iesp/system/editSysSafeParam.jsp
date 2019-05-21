<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src=<%= request.getContextPath() + "/js/system/editSysSafeParam.js" %> type="text/javascript"></script>
<script type="text/javascript">
	var sysSafeCode = $("#value").val();
	var html = ""
	if(sysSafeCode=="sessionExpiredTime"){
		html+='分钟';
	}else if(sysSafeCode=="lockedTime" || sysSafeCode=="mailCodeTime"){
		html+='分钟';
	}else if(sysSafeCode=="faildTimes"){
		html+='次';
	}else if(sysSafeCode=="passExpiredTime"){
		html+='天';
	}else if(sysSafeCode=="sessionNum"){
		html+='个';
	} else if(sysSafeCode=="logLength"){
		html+='M';
	}
	$("#valueunit").html(html);
</script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
		    	<input type="hidden" id="sysSafeId" name="sysSafeId" value="${sysSafeParam.sysSafeId}" />
		    	<input type="hidden" id="createTime" name="createTime" value="${sysSafeParam.createTime}" />
		    	<input type="hidden" id="value" name="value" value="${sysSafeParam.sysSafeCode}" />
		        <div class="form-group">
		        	<label for="sysSafeCode" class="col-sm-2 control-label">参数类型</label>
		        	<div class="col-sm-8" style="padding-right:0px">
		        		<input type="text" id="sysSafeCode" name="sysSafeCode" readonly="readonly" class="form-control" value="${sysSafeParam.sysSafeCode}" />
		        	</div>
		        </div>
		        <div class="form-group"> 
		        	<label for="sysSafeValue" class="col-sm-2 control-label">参数值</label>
		        	<div class="col-sm-8" style="padding-right:0px">
		        		<input type="text" id="sysSafeValue" name="sysSafeValue" class="form-control" value="${sysSafeParam.sysSafeValue}" />
		        	</div>
		        	<div class="col-sm-2" style="padding-left:10px">
		        		<label id="valueunit" class="control-label" style="text-align:left"></label>
		        	</div>
		        </div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label ">备注</label>
					<div class="col-sm-10">
						<textarea class="form-control" name="remark" rows="3">${sysSafeParam.remark}</textarea>
					</div>
				</div>
		    </form>
		</div>
	</div>
	<div class='notifications'></div>
</body>
</html>