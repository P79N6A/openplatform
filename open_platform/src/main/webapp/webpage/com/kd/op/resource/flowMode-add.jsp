<%--
  Created by IntelliJ IDEA.
  User: yangbendong
  Date: 2018/11/7
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
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
    </style>
</head>
<body>
<div>
    <div class="modal-body">
        <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
            <input id="type" name="type" type="hidden" value="${type}" />
            <%--<div class="form-group">--%>
                <%--<label for="modeName" class="col-sm-2 control-label">流量策略:</label>--%>
                <%--<div class="col-sm-8" style="padding-right:0px">--%>
                    <%--<input type="text" id="modeName" name="modeName" class="form-control" />--%>
                <%--</div>--%>
            <%--</div>--%>
            <c:if test="${type == 1}">
                <div class="form-group">
                    <label for="maxNum" class="col-sm-2 control-label">次数限制:</label>
                    <div class="col-sm-8" style="padding-right:0px">
                        <input type="number" id="maxNum" min="0" step="1" name="maxNum"  class="form-control col-sm-8" />

                    </div>
                </div>
                <div class="form-group">
                    <label for="unit1" class="col-sm-2 control-label">单位:</label>
                    <div class="col-sm-8" style="padding-right:0px">
                            <select id="unit1" name="unit" class="form-control col-sm-8"  style="width:50%">
                            <option value="1" selected>次/秒</option>
                            <option value="2">次/分</option>
                            <option value="3">次/时</option>
                            <option value="4">次/天</option>
                            </select>
                    </div>
                </div>

            </c:if>
            <c:if test="${type == 2}">
                <div class="form-group" id="num-div">
                    <label for="maxNum" class="col-sm-2 control-label">流量限制:</label>
                    <div class="col-sm-8" style="padding-right:0px">
                            <input type="number"  name="maxNum" min="0" style="width:70%" class="form-control col-sm-8" />
                    </div>
                </div>
                <div class="form-group" id="num-div">
                    <label for="unit" class="col-sm-2 control-label">时间:</label>
                    <div class="col-sm-8" style="padding-right:0px">
                        <%--<input type="number" id="maxTime" name="maxTime" style="width:70%" class="form-control col-sm-8" />--%>
                        <select id="unit" name="unit" class="form-control col-sm-8"  style="width:50%">
                            <option value="5" selected>MB/秒</option>
                            <option value="6">MB/分</option>
                            <option value="7">MB/时</option>
                            <option value="8">MB/天</option>
                        </select>
                    </div>
                </div>
            </c:if>

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
                    url:"resourceController.do?flowDoAdd",
                    type:"post",
                    dataType:"json",
                    beforeSubmit:function(){
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

    function reloadTable() {
        location.href = "resourceController.do?flowModeList&type=" + ${type};
    }
</script>
</body>
</html>
