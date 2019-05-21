<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%--<t:base type="bootstrap,bootstrap-table,layer"></t:base>--%>
<script src=<%= request.getContextPath() + "/js/com/kd/op/app/auditApp.js" %> type="text/javascript"></script>
    <style>
        .control-label{
            padding-right:0px;
        }
		input::-webkit-outer-spin-button,
		input::-webkit-inner-spin-button {
			-webkit-appearance: none;
		}
		/*input[type="number"]{*/
			/*-moz-appearance: textfield;*/
		/*}*/
    </style>
	<script>
		var type = ${type};
	</script>
</head>
<body>
	<div>
		<div class="modal-body">
		    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
				<input name="type" type="hidden" value="${type}" />
				<c:if test="${type == 1}">
					<div class="form-group">
						<label for="num" class="col-sm-2 control-label">时间:</label>
						<div class="col-sm-8" style="padding-right:0px">
							<input type="number" id="num" name="num"  min="0" step="1" style="width:79%" class="form-control col-sm-8" />
							<select name="unit" class="form-control col-sm-8"  style="width:20%">
								<option value="1" selected>周</option>
								<option value="2">月</option>
								<option value="3">年</option>
							</select>
						</div>
					</div>
				</c:if>
				<c:if test="${type == 2}">
					<div class="form-group" id="num-div">
						<label for="startNum" class="col-sm-2 control-label">次数范围:</label>
						<div class="col-sm-8" style="padding-right:0px">
							<div class="input-group">
								<input type="number" id="startNum"  min="0" step="1" name="startNum" class="form-control" />
								<label class="input-group-addon">~</label>
								<input type="number" id="endNum"  min="0" step="1" name="endNum" class="form-control" />
								<label class="input-group-addon" style="">次</label>
								<input name="unit" type="hidden" value="5"/>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${type == 3}">
					<div class="form-group" id="flow-div">
						<label for="startNum" class="col-sm-2 control-label">流量范围:</label>
						<div class="col-sm-8" style="padding-right:0px">
							<div class="input-group">
								<input type="number" id="startNum"  min="0" name="startNum" class="form-control" />
								<label class="input-group-addon">~</label>
								<input type="number" id="endNum"  min="0" name="endNum" class="form-control" />
								<label class="input-group-addon" style="">KB</label>
								<input name="unit" type="hidden" value="4"/>
								<%--<select class="form-control input-group-addon">--%>
									<%--<option selected>KB</option>--%>
									<%--<option>MB</option>--%>
									<%--<option>GB</option>--%>
								<%--</select>--%>
							</div>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label for="price" class="col-sm-2 control-label">价格:</label>
					<div class="col-sm-8" style="padding-right:0px">
						<c:if test="${type == 2}">
							<div class="input-group">
								<input type="number" id="price"  min="0.00" name="price" class="form-control" />
								<label class="input-group-addon" style="">元/次</label>
							</div>
						</c:if>
						<c:if test="${type != 2}">
							<input type="number" id="price" min="0" name="price" class="form-control" />
						</c:if>
					</div>
				</div>
		    </form>
		</div>
	</div>
<script>
	$(document).ready(function(){
        $("#btn_sub").bind("click",function(){
            var validator = $("#addForm").data("bootstrapValidator")
            //手动触发验证
            validator.validate();
            if(validator.isValid()){
                var options={
                    url:"charging.do?create",
                    type:"post",
                    dataType:"json",
                    beforeSubmit:function(){
                        var price = $("#price").val();
                        if(type == 1){
							var num = $("#num").val();
							if(num == null || num == "" || num < 0){
							    slowNotify("时间不能为空和负数！","danger");
							    return false;
							}
                            if (!(/(^[1-9]\d*$)/.test(num))) {
                                slowNotify("时间必须为正整数！","danger");
                                return false;
                            }

						}else if(type == 2){
                            var startNum = $("#startNum").val();
                            var endNum = $("#endNum").val();
                            if(startNum == null || startNum == "" || endNum == null || endNum == "" || startNum < 0 || endNum < 0){
                                slowNotify("次数范围不能为空和负数！","danger");
                                return false;
                            }
                            if (!(/(^[1-9]\d*$)/.test(startNum)) || !(/(^[1-9]\d*$)/.test(endNum))) {
                                slowNotify("次数范围必须为正整数！","danger");
                                return false;
                            }
                            startNum = parseInt(startNum);
                            endNum = parseInt(endNum);
                            if(endNum <= startNum){
                                slowNotify("次数范围的第二个数值必须大于第一个数值！","danger");
                                return false;
							}
						}else if(type == 3){
                            var startNum = $("#startNum").val();
                            var endNum = $("#endNum").val();
                            if(startNum == null || startNum == "" || endNum == null || endNum == "" || startNum < 0 || endNum < 0){
                                slowNotify("流量范围不能为空和负数！","danger");
                                return false;
                            }
                            if (!(/(^[1-9]\d*$)/.test(startNum)) || !(/(^[1-9]\d*$)/.test(endNum))) {
                                slowNotify("流量范围必须为正整数！","danger");
                                return false;
                            }
                            startNum = parseInt(startNum);
                            endNum = parseInt(endNum);
                            if(endNum <= startNum){
                                slowNotify("流量范围的第二个数值必须大于第一个数值！","danger");
                                return false;
                            }
						}
                        if(price == null || price == "" || price < 0){
                            slowNotify("价格不能为空和负数！","danger");
                            return false;
                        }
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
            }
        })
	})
</script>
</body>
</html>