$(document).ready(function(){
	var roleId = $("#roleId").val();
	/*var setting = {
		check: {
			chkStyle: "checkbox",
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}, 
		async: {
			enable: true,
			url:"roleController.do?setAuthority&roleId=" + roleId,
			dataFilter: filter				
		},
		callback: {
			onClick: function (event, treeId, treeNode){
				if (null == treeNode.children) {
					var roleId = $("#rid").val();
				}
			}
		}
	};*/
	
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
	var checked = null;
	$.ajax({
		  async : false,   //设置为false。请求为同步请求
		  cache:false,   	//不设置缓存
		  type: 'post',
		  dataType : "json",
		  url: "roleController.do?getFunctionsByRole&roleId=" + roleId,//后台处理程序
		  error: function () {//请求失败处理函数
			  return false;
		  },
		  success:function(data){
                nodes = data.data; //把后台封装好的简单Json格式赋给treeNodes
                checked = data.checked;//已经被选中的树节点
                $.fn.zTree.init($("#functions"), setting,nodes);
                //初始化树之后将选中的节点在树结构上选中
                var treeObj = $.fn.zTree.getZTreeObj("functions");
                for(var i = 0;i < checked.length;i++){
                    var node =  treeObj.getNodeByParam('id',checked[i]);
                    if(node != null){
                        treeObj.checkNode(node, true);
                    }
                }
		  }
	 });
})
function updateFunctions(dialog){
	var roleId = $("#roleId").val();
	var s = GetNode();
	$.ajax({
		type:"post",
		dataType:"json",
		url:"roleController.do?updateAuthority&rolefunctions=" + s + "&roleId=" + roleId,
		success:function(data){
			if(data && data.success){
				quickNotify("权限更新成功,等待审核通过！","success");
				dialog.close();
			}else{
				slowNotify("权限更新失败！","danger");
			}
		}
	})
}
/*function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i=0, l=childNodes.length; i<l; i++) {
		childNodes[i].name = childNodes[i].text;
		if (childNodes[i].children != null) {					
			childNodes[i].nodes = childNodes[i].children;
			filter(null, childNodes[i], childNodes[i].nodes);//递归设置子节点
		}
	}		
	return childNodes;
}*/
function GetNode() {
	var zTree = $.fn.zTree.getZTreeObj("functions");				
	var node = zTree.getCheckedNodes(true);
	//加入实际被选中的节点
	var cnodes = '';
	for ( var i = 0; i < node.length; i++) {
		cnodes += node[i].id + ',';		
	}
	cnodes = cnodes.substring(0, cnodes.length - 1);
	return cnodes;
}