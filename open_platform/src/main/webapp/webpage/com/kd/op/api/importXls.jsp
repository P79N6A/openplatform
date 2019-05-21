<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<body style="overflow:hidden;overflow-y:auto;">
<div class="container" style="width:100%;">
    <div class="panel-body">
        <form id="addForm" action="apiInfoController.do?doImportXls"  method="post"  enctype="multipart/form-data" onsubmit="return tovalid();">
            <p><input type="file" name="file" id="file" accept=".xlsx,.xls"></p>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("#btn_sub").bind("click",function(){
            if(tovalid()){
                var options={
                    url:"apiInfoController.do?doImportXls",
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
    function tovalid(){
        if($('#file').val() !=null && $('#file').val()!=''){
            return true
        };
        alert('请选择一个文件')
        return false;
    }

</script>
</body>
</html>
