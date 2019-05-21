<%--
  Created by IntelliJ IDEA.
  User: yangbendong
  Date: 2018/11/5
  Time: 19:57
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
            <input name="id" value="${chargeMode.id}" type="hidden"/>
            <input name="type" type="hidden" />
            <div class="form-group">
                <label for="resourceName" class="col-sm-2 control-label">组织机构:</label>
                <div class="col-sm-8" style="padding-right:0px">
                    <div class="input-group">
                        <input type="text" id="resourceName" name="resourceName"  value="${chargeMode.resourceName}" class="form-control" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="resourcePlace" class="col-sm-2 control-label">地点:</label>
                <div class="col-sm-8" style="padding-right:0px">
                    <div class="input-group">
                        <input type="text" id="resourcePlace" name="resourcePlace" value="${chargeMode.resourcePlace}" class="form-control" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="resourceSort" class="col-sm-2 control-label">类别:</label>
                <div class="col-sm-8" style="padding-right:0px">
                    <div class="input-group">
                        <input type="text" id="resourceSort" name="resourceSort" value="${chargeMode.resourceSort}" class="form-control" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="resourceStatus" class="col-sm-2 control-label">状态:</label>
                <div class="col-sm-8" style="padding-right:0px">
                    <div class="input-group">
                        <input type="text" id="resourceStatus" name="resourceStatus" value="${chargeMode.resourceStatus}" class="form-control" />
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="apiSort" class="col-sm-2 control-label">资源访问权限:</label>
                <div class="col-sm-8" style="padding-right:0px">
                    <div class="input-group">
                        <input type="text" id="apiSort" name="apiSort" value="${chargeMode.apiSort}" class="form-control" />
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#btn_sub").bind("click",function(){
            // var validator = $("#addForm").data("bootstrapValidator")
            // //手动触发验证
            // validator.validate();
            // if(validator.isValid()){
            var options={
                url:"resourceController.do?dataResourceupdate",
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
            var resourceName = $("#resourceName").val();
            var resourcePlace = $("#resourcePlace").val();
            var resourceSort = $("#resourceSort").val();
            var resourceStatus = $("#resourceStatus").val();
            var resourceStatus = $("#apiSort").val();

            if( isnull(resourceName) && isnull(resourcePlace) && isnull(resourceSort) && isnull(resourceStatus) && isnull(apiSort)) {
                $("#addForm").ajaxSubmit(options);
            }
            else {
                slowNotify("输入不能为空","success");
            }

            // }
        })
    })

    function reloadTable() {
        location.href = "resourceController.do?dataResourceList";
    }

    function isnull(val) {

        var str = val.toString().replace(/(^\s*)|(\s*$)/g, '');//去除空格;

        if (str == '' || str == undefined || str == null) {
            return false;
        } else {
            return true;
        }
    }
</script>
</body>
</html>
