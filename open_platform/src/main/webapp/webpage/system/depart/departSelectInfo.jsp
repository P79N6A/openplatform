<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/webpage/common/resource.jsp"%> --%>
<!DOCTYPE html >
<html>
<head>
    <title>组织机构集合</title>

    <link rel="stylesheet" href="plug-in/ztree/bootstrap_zTree/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
    <script type="text/javascript" src="plug-in/ztree/bootstrap_zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
    <script type="text/javascript">
        var setting = {
            check: {
                enable: true,
                checkStyle:'checkbox',
//                chkStyle : "radio",
//                radioType: "all",
                chkboxType: { "Y": "", "N": "" }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },callback: {
                onExpand: zTreeOnExpand
            }
        };

        //加载展开方法
        function zTreeOnExpand(event, treeId, treeNode){
            var treeNodeId = treeNode.id;
            $.post(
                'departController.do?getDepartInfo',
                {parentid:treeNodeId,orgIds:$("#orgIds").val()},
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dbDate = eval(d.msg);
                        var tree = $.fn.zTree.getZTreeObj("departSelect");

                        if (!treeNode.zAsync){
                            tree.addNodes(treeNode, dbDate);
                            treeNode.zAsync = true;
                        } else{
                            tree.reAsyncChildNodes(treeNode, "refresh");
                        }
                        //tree.addNodes(treeNode, dbDate);
                    }
                }
            );
        }

        //首次进入加载level为1的
        $(function(){
            $.post(
                'departController.do?getDepartInfo',
                {orgIds:$("#orgIds").val()},
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dbDate = eval(d.msg);
                        $.fn.zTree.init($("#departSelect"), setting, dbDate);
                        SelectNode();
                    }
                }
            );
        });
        function SelectNode() {
            var orgIds= $("#orgIds").val();
            var treeObj = $.fn.zTree.getZTreeObj("departSelect");
            var parentIds = $("#parentIds").val();
            var parentIdArray = parentIds.split(",");
            for(var i = parentIdArray.length - 1;i >= 0;i--){
                var treenode = treeObj.getNodeByParam("id",parentIdArray[i],null);
                if(treenode != null && treenode != undefined){
                    zTreeOnExpand(null, "departSelect", treenode);
                }
                //treeObj.selectNode(treenode);
                //treeObj.expandNode(treenode, true, true, true);
            }
        }
    </script>
</head>
<body>
<div style="min-height:300px;max-height:300px;overflow:auto">
    <input id="orgIds" name="orgIds" type="hidden" value="${orgIds}">
    <input id="parentIds" type="hidden" value="${parentIds}">
    <ul id="departSelect" class="ztree"></ul>
</div>
</body>
</html>
