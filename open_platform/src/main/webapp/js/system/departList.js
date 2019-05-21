$(document).ready(function(){
	 //1.初始化Table
	TableInit();
    //2.初始化Button的点击事件
	//新建按钮点击
	// $("#add-btn").bind("click",function(){
	// 	var selections = $('#departs').bootstrapTable("getSelections");
	// 	var id = null;
	// 	if(selections != null && selections.length > 0){
	// 		id = selections[0]["id"];
	// 	}
	// 	var dialog = new BootstrapDialog({
	//         title: '添加组织机构',
	//         type:BootstrapDialog.TYPE_DEFAULT,
	//         message: function(dialog) {
	//             var $message = $('<div></div>');
	//             var pageToLoad = dialog.getData('pageToLoad');
	//             $message.load(pageToLoad);
	//             return $message;
	//         },
	//         closable: false,
	//         data: {
	//             'pageToLoad': 'departController.do?add&id=' + id
	//         },
	//         buttons: [{
	//             label: '保存',
	//             cssClass: 'btn-primary',
	//             action: function(dialogRef){
	//            	 createDepart(dialog);
	//             }
	//         }, {
	//             label: '取消',
	//             cssClass: 'btn-default',
	//             action: function(dialogItself){
	//                 dialogItself.close();
	//             }
	//         }]
	//     });
	//     dialog.open();
	// })
});
function TableInit() {
	$('#departs').bootstrapTable({
		url: "departController.do?getDepartList",
		class: 'table table-hover table-bordered',
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        sidePagination: 'server',
        pagination: false,
        treeView: true,
        treeId: "id",
        treeField: "name",
        rowAttributes: function (row, index) {
            return {
                xx:index
            };
        },
        columns: [
        	// {
	        //     checkbox:true,
	        //     width:"2%"
	        // },
			{
	            field: 'name',
	            title: '名称',
	            width:"30%"
	        },
	        {
	            field: 'code',
	            title: '机构编码',
	            width:"10%"
	        },
	        {
	            field: 'address',
	            title: '地址',
	            width:"20%"
	        },
	        // {
	        //     field: 'action',
	        //     title: '操作',
	        //     width:"5%",
	        //     formatter:function(value,row,index){
        	// 		return "<button class='btn btn-xs btn-success' style='margin-right:5px' title='修改' onclick='editDepart(\"" + row.id + "\")'><span class='glyphicon glyphicon-pencil'></span></button>"+
        	// 		"<button class='btn btn-xs btn-danger' style='margin-right:5px' title='删除' onclick='deleteDepart(\"" + row.id + "\")'><span class='glyphicon glyphicon-trash'></span></button>";
	        // 	},
	        // }
        ],
        onClickRow:function(row,$element,field){
        	$element.parent().find("tr").removeClass("click-tr-bg");
        	$element.parent().find("input[type='checkbox']").prop("checked",false);
			$element.addClass("click-tr-bg");
			$element.find("input[type='checkbox']").prop("checked",true);
			$("#departs").bootstrapTable("uncheckAll");
			$("#departs").bootstrapTable("checkBy", {field:"id", values:[row["id"]]})
        }
	});
}
function refreshTable(){
	$('#departs').bootstrapTable("refresh");
}
function editDepart(id){
	var dialog = new BootstrapDialog({
		title: '编辑组织机构',
		type:BootstrapDialog.TYPE_DEFAULT,
		message: function(dialog) {
			var $message = $('<div></div>');
			var pageToLoad = dialog.getData('pageToLoad');
			$message.load(pageToLoad);
			return $message;
		},
		closable: false,
		data: {
			'pageToLoad': 'departController.do?edit&id='+id
		},
		buttons: [{
			label: '更新',
			cssClass: 'btn-primary',
			action: function(dialogRef){
				updateDepart(dialog);
			}
		}, {
			label: '取消',
			cssClass: 'btn-default',
			action: function(dialogItself){
				dialogItself.close();
			}
		}]
	});
	dialog.open();
}
function deleteDepart(id){
	Confirm.confirm({ message: "确认要将该组织机构删除吗？"}).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "departController.do?del",
            dataType:"json",
            data: {
            	id:id
            },
            success: function (data) {
                if (data.success) {
                	refreshTable();
	           		 quickNotify("组织机构删除成功！","success");
                }else{
                	slowNotify(data.msg,"danger");
                }
            }
        });
    });
}
