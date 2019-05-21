<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<body style="overflow-y: hidden" scroll="no">
  <t:datagrid name="roleList" checkbox="true" pagination="false" fitColumns="true" title="角色选择" 
  			  actionUrl="roleController.do?datagridSelect" onLoadSuccess="initCheck"
  			  idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  queryMode="single"  width="120" hidden="true"></t:dgCol>
   <t:dgCol title="角色名称"  field="rolename"  queryMode="single"  width="120" ></t:dgCol>
  </t:datagrid>
</body>
</html>
 <script type="text/javascript">
//  function initCheck(data){
// 		var ids = "${roleid}";
// 		var idArr = ids.split(",");
// 		for(var i=0;i<idArr.length;i++){
// 			if(idArr[i]!=""){
// 				$("#roleList").datagrid("selectRecord",idArr[i]);
// 			}
// 		}
// 	}
 </script>