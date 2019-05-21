$(document).ready(function(){
	var roleId = $("#roleId").val();
	//构建树
	var setting = {
		view: {
			showLine:true,
			expandSpeed:"fast"
		},
		data: {
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"pId",
				rootPid:0
			}
		},
		check: {
			enable: true,
			checkStyle:'checkbox',
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		callback:{
			//onClick:zTreeOnClick
		}
	};
	
	var nodes = null;
	var approvaled = null;
	$.ajax({   
		  async : false,   //设置为false。请求为同步请求
		  cache:false,   	//不设置缓存
		  type: 'post',   
		  dataType : "json",  
		  url: "check.do?approvalAuthority&roleId=" + roleId,//后台处理程序    
		  error: function () {//请求失败处理函数   
			  return false;
		  },   
		  success:function(data){ //请求成功后处理函数。 
			  nodes = data.data; //把后台封装好的简单Json格式赋给treeNodes
			  approvaled = data.approval;//已经被选中的树节点
			  $.fn.zTree.init($("#functions"), setting,nodes);
			  //初始化树之后将选中的节点在树结构上选中
			  var treeObj = $.fn.zTree.getZTreeObj("functions");
			  for(var i = 0;i < approvaled.length;i++){
				  var node =  treeObj.getNodeByParam('id',approvaled[i]);
				  if(node != null){
					  treeObj.checkNode(node, true);
				  }
			  }
		  }
	 });
})