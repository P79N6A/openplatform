<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="${webRoot}/plug-in/tools/curdtoolsnew.js"></script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="apiGroupList" checkbox="false" pagination="false" treegrid="true" treeField="groupName" fitColumns="true" title="" actionUrl="apiGroupController.do?datagrid" idField="id" sortName="createDate" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="能力中心编号"  field="groupCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="能力中心名称"  field="groupName"  query="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="能力中心描述"  field="groupDesc"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="上级分组ID"  field="parentId"  hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="上级分组名称"  field="parentName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="apiGroupController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="apiGroupController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="apiGroupController.do?goUpdate" funname="updatetree" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="apiGroupController.do?goUpdate&type=look" funname="detailtree" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript">
 $(document).ready(function(){
        var selectedId = null;
		$("#apiGroupList").treegrid({
             onExpand : function(row){
                var children = $("#apiGroupList").treegrid('getChildren',row.id);
                 if(children.length<=0){
                    row.leaf=true;
                    $("#apiGroupList").treegrid('refresh', row.id);
                 }
            },
            onClickRow:function(row){
                 if(selectedId == null){
                     selectedId = row.id;
                 }else{
                     if(selectedId == row.id){
                         $("#apiGroupList").treegrid("unselect",row.id);
                         selectedId = null;
                     }else{
                         selectedId = row.id;
                     }
                 }
            }
 		});
 });
 
   
 


/**
 * 获取表格对象
 * @return 表格对象
 */
function getDataGrid(){
	var datagrid = $('#'+gridname);
	return datagrid;
}
 </script>