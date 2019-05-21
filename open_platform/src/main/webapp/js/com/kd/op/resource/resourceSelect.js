// $(document).ready(function(){
//         //构建树
//         var setting = {
//             view: {
//                 showLine:true,
//                 expandSpeed:"fast"
//             },
//             data: {
//                 simpleData: {
//                     enable: true,
//                     idKey:"id",
//                     pIdKey:"pId",
//                     rootPid:0
//                 }
//             },
//             check: {
//                 enable: true,
//                 checkStyle:'checkbox',
//                 chkboxType: { "Y": "ps", "N": "ps" }
//             },
//             callback:{
//                 // onChecked:zTreeOnCheckd
//             }
//         };
//         var id = $("#id").val();
//         var level = $("#level").val();
//         var type = $("#type").val();
//         var orderId = $("#orderId").val();
//         var nodes = null;
//         var checked = [];
//         if(apiResourceMap.get(id)!=null && apiResourceMap.get(id)!=''){
//             checked = apiResourceMap.get(id).split(',');
//         }
//         $.ajax({
//             async : false,   //设置为false。请求为同步请求
//             cache:false,   	//不设置缓存
//             type: 'post',
//             dataType : "json",
//             data:{
//                 'id':id,
//                 'level':level,
//                 'type':type,
//                 'orderId':orderId
//             },
//             url: "resourceController.do?getCorpTree",//后台处理程序
//             error: function () {//请求失败处理函数
//                 return false;
//             },
//             success:function(data){ //请求成功后处理函数。
//                 nodes = data.nodes; //把后台封装好的简单Json格式赋给treeNodes
//                 if(level =='1'){
//                     checked = data.checked;//已经被选中的树节点
//                 }
//                 $.fn.zTree.init($("#resources"), setting,nodes);
//                 //初始化树之后将选中的节点在树结构上选中
//                 var treeObj = $.fn.zTree.getZTreeObj("resources");
//                 if(checked != null && checked.length > 0){
//                     for(var i = 0;i < checked.length;i++){
//                         var node =  treeObj.getNodeByParam('id',checked[i]);
//                         if(node != null){
//                             treeObj.checkNode(node, true);
//                         }
//                     }
//                 }
//             }
//         });
// })
//获取表单数据
// function getCheckedReource() {
//     var zTree = $.fn.zTree.getZTreeObj("resources");
//     var node = zTree.getCheckedNodes(true);
//     //加入实际被选中的节点
//     var cnodes = '';
//     for ( var i = 0; i < node.length; i++) {
//         cnodes += node[i].id + ',';
//     }
//     cnodes = cnodes.substring(0, cnodes.length - 1);
//     return cnodes;
// }