<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/webpage/common/resource.jsp"%> --%>
<!DOCTYPE html >
<html>
<head>
    <title>能力中心选择</title>

    <link rel="stylesheet" href="plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
    <script type="text/javascript" src="plug-in/ztree/bootstrap_zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
<div class="col-sm-12">
<ul id="groups" class="ztree"></ul>
<input type="hidden"  id="groupName" />
</div>
<script>
    $(function() {
        //处理左侧的分组树
        //构建树  开始
        var setting = {
            view : {
                showLine : true,
                expandSpeed : "fast"
            },
            data : {
                simpleData : {
                    enable : true,
                    idKey : "id",
                    pIdKey : "pId",
                    rootPid : null
                }
            },
            callback : {
                onClick:onClick,
            }
        };

        var zNodes =[];

        $.ajax({
            async: false,
            type:"post",
            dataType:"json",
            url:"apiGroupController.do?loadAll",
            success:function(data){
                zNodes = data;
            }
        })


        $.fn.zTree.init($("#groups"), setting, zNodes);
    })
</script>
</body>
</html>