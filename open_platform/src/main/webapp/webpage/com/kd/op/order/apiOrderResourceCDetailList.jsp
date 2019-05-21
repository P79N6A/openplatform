<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>订单中服务选择</title>
</head>
<body>
<div class="col-sm-2">
	<ul id="groups" class="ztree"></ul>
</div>
<div class="panel-body col-sm-10" style="padding-top: 0px; padding-bottom: 0px;">
	<form id="apiInfoForm">
		<div>
			<input type="hidden" id="groupId" name="groupId"/>
		</div>
	</form>
	<div class="table-responsive">
		<table id="apiInfoList1"></table>
	</div>
	<div id="chargeModeIds" style="display:none">

	</div>
</div>
</body>
</html>
<script type="text/javascript">

    $(document).ready(function () {
        $("#btn_sub").click(function () {
            location.href="resourceController.do?insertResource";
        });
    });


    //全局变量
    var orderId;
    var apiInfoListdictsData = {};
    //订单详情列表
    var orderDetails;
    //临时选中的服务map key为apiId value为选中行
    var apiTemps = new Map();
    //保存全部的服务与资源监控的映射
	var apiResourceMap = new Map();
    //保存资源控制
    var resourceParamMap = new Map();
    //保存能力与URL关系
    var urlMap = new Map();
    //保存能力与tag关系
    var tagMap = new Map();

	var webServiceInfoMap = new Map();
	var fromDatabase;
    $(function () {
        $(".modal-dialog").css("width","1200px");
        init();
        orderDetails = ${orderDetails};
        orderId = '${orderId}';
        //缓存计费模型
        $.each(orderDetails, function (i, val) {
            $("#chargeModeIds").append("<input id='" + val.apiId + "'/>")
            $("#" + val.apiId).val(val.chargeId);
            var tempApi = {};
            tempApi.apiId= val.apiId;
            tempApi.apiName = val.apiName;
            apiTemps.set(val.apiId,tempApi);
            // apiResourceMap.set(val.apiId, val.resourceId);
            if(val.methodPath != null && val.methodPath != undefined){
                urlMap.set(val.apiId,val.methodPath);
            }
            if(val.mqTag != null && val.mqTag != undefined){
                tagMap.set(val.apiId,val.mqTag);
            }
            if(val.resourceParam != null && val.resourceParam != undefined){
                resourceParamMap.set(val.apiId,val.resourceParam);
            }
			if(val.paramValue != null && val.paramValue != undefined){
				webServiceInfoMap.set(val.apiId,val.paramValue);
			}
			if(val.fromDatabase != null && val.fromDatabase != undefined){
				fromDatabase = val.fromDatabase;
			}
        });
    });

    //初始化操作
    function init() {
        //处理左侧的分组树
        //构建树  开始
        var setting = {
            view: {
                showLine: true,
                expandSpeed: "fast"
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPid: null
                }
            },
            callback: {
                onClick: onClick
            }
        };

        var zNodes = [];
        $.ajax({
            async: false,
            type: "get",
            dataType: "json",
            url: "apiGroupController.do?loadAll",
            success: function (data) {
                zNodes = data;
            }
        })
        $.fn.zTree.init($("#groups"), setting, zNodes);
    }


    function onClick(event, treeId, treeNode, clickFlag) {
        if (treeNode.id == 0) {
            $("#groupId").val(null);
        } else {
            $("#groupId").val(treeNode.id);
        }
        reloadTable();
    }
    //构建树  结束

    //处理右侧服务信息表格
    // var promiseArr = [];
    // promiseArr.push(new Promise(function (resolve, reject) {
    initDictByCode(apiInfoListdictsData, "apiSts", function(){});
    // }));

    // Promise.all(promiseArr).then(function (results) {
    //
    // }).catch(function (err) {
    // });

    //1.初始化Table
    $('#apiInfoList1').bootstrapTable({
        url: 'order.do?datagridAuditApi&orderId='+'${orderId}', //请求后台的URL（*）
        method: 'get', //请求方式（*）
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        queryParams: function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sort: params.sort, // 排序规则
                order: params.order,
                rows: params.limit, //页面大小
                page: (params.offset / params.limit) + 1, //页码
                pageIndex: params.pageNumber,//请求第几页
                field: 'id,apiName,apiStatus,groupName,resourceCtrl,createDate,apiInvokeType,pubMode,resourceCtrl'
            };
            var params = $("#apiInfoForm").serializeArray();
            for (x in params) {
                temp[params[x].name] = params[x].value;
            }
            return temp;
        },
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 10, //每页的记录行数（*）
        pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
        strictSearch: true,
        showColumns: false, //是否显示所有的列
        showRefresh: true, //是否显示刷新按钮
        minimumCountColumns: 2, //最少允许的列数
        clickToSelect: false, //是否启用点击选中行
        uniqueId: "id", //每一行的唯一标识，一般为主键列
        showToggle: false, //是否显示详细视图和列表视图的切换按钮
        cardView: false, //是否显示详细视图
        detailView: false, //是否显示父子表
        showExport: true, //显示到处按钮
        sortName: 'createDate',
        sortOrder: 'desc',
        columns: [
            {
                field: 'apiName',
                title: '服务名称',
                valign: 'middle',
                width: 120,
                align: 'center',
                switchable: true,
            },
            {
                field: 'apiStatus',
                title: '服务状态',
                valign: 'middle',
                width: 60,
                align: 'center',
                switchable: true,
                formatter: function (value, rec, index) {
                    return listDictFormat(value, apiInfoListdictsData.apiSts);
                }
            },
            {
                field: 'groupName',
                title: '能力中心名称',
                valign: 'middle',
                width: 60,
                align: 'center',
                switchable: true,
            },
            {
                title: "操作",
                field: "action",
                align: 'left',
                valign: 'middle',
                width: 150,
                formatter: function (value, row, index) {
                    if (!row.id) {
                        return '';
                    }
                    var href = '';
                    href += "<button class='btn btn-xs btn-primary' onclick='apiCheck(\"" + row.id + "\")'><span>查看</span></button>&nbsp";
                    href += "<button class='btn btn-xs btn-success' onclick='configCharging(\"" + row.id + "\"," + index + ")'><span>计费方式</span></button>&nbsp";
                   /* if (row.apiInvokeType == 2) {
                        href += "<button class='btn btn-xs btn-warning' data-toggle='popover'" +
                            " data-index='" + index + "' data-url='" +urlMap.get(row.id) +"'><span>查看URL</span></button>";
                    }
                    if (row.pubMode == 2) {
                        href += "<button class='btn btn-xs btn-warning' data-toggle='popover'" +
                            " data-index='" + index + "' data-url='" +tagMap.get(row.id) +"'><span>查看tag</span></button>";
                    }*/
					if ( row.apiInvokeType == 2) {
						href += "<button class='btn btn-xs btn-success' id='configWevservice' onclick='lookConfigWevservice(\"" + row.id + "\")'><span>查看接收信息</span></button>&nbsp";
					}
                    if (row.resourceCtrl == 1) {
                        href += "<button class='btn btn-xs btn-success' onclick='lookResourceCtrol(\"" + row.id + "\")'><span>查看资源控制</span></button>&nbsp";
                    }
                    var rCode = "${rCode}";
                    if (row.resourceCtrl == 1 && rCode == "admin") {
                        href += "<button class='btn btn-xs btn-warning' id='source' onclick='configResource(\"" + row.id + "\")'>" +
                            "<span>配置资源控制</span></button>&nbsp";
                    }
                    return href;
                }
            }
        ],
        onLoadSuccess: function () { //加载成功时执行
            $('[data-toggle="popover"]').each(function () {
                var index = $(this).data("index");
                var url = $(this).data("url");
                var placement = "top";
                if(index == 0){
                    placement = "right";
                }
                $(this).popover({
                    trigger: 'manual',
                    placement: placement,
                    html: 'false',
                    trigger:'hover',
                    content:url
                })
            });
        },
        onLoadError: function () { //加载失败时执行
        },
        onClickCell: function (field, value, row, element) {
        }
    });


    function reloadTable() {
        $('#apiInfoList1').bootstrapTable('refresh');
    }


    function apiCheck(id) {
        bootOpenLook("服务查看", 'apiInfoController.do?goUpdate&id=' + id + "&type=3", "wide");
    }

    function lookResourceCtrol(id) {

        //bootOpenLook("查看资源控制", "resourceController.do?lookResourceCtrol&param=" + resourceParamMap.get(id) , "wide");
        /*bootOpenLook("查看资源控制", "resourceController.do?lookResourceCtrol&apiId=" + id +"&orderId="+orderId, "wide");*/
		bootOpenLook("查看资源控制", "resourceController.do?selectResourceNew&id=" + id+"&orderId="+orderId, "wide");
    }
	function lookConfigWevservice(id) {
		bootOpenLook("查看接收信息", "order.do?appOrderWebserviceConfig&id=" + id +"&fromDatabase="+fromDatabase, "wide");
	}

    //配置计费模型
    function configCharging(id, index) {
        var ss = "";
        if ($("#" + id).length != 0) {
            ss = $("#" + id).val();
        }
        var dia = new BootstrapDialog({
            title: "配置计费方式",
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "size-wide",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "order.do?orderConfigCharging&apiId=" + id + "&selectedId=" + ss+"&orderId="+'${orderId}'
            },
            buttons: [{
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }

    /**
     * 配置资源控制
     * @param id
     */
   /* function configResource(id) {
        var dia = new BootstrapDialog({
            title: "配置资源控制",
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "size-wide",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "resourceController.do?selectResource&id=" + id + "&level=2&type=api"+"&orderId="+'
            },
            buttons: [{
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }*/

    function configResource(id) {
        var dia = new BootstrapDialog({
            title: "配置资源控制",
            type: BootstrapDialog.TYPE_DEFAULT,
            size: "mini",
            message: function (dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': "resourceController.do?selectResourceNew&id=" + id+"&orderId="+orderId ,
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id: "btn_sub",
                action: function (dialogRef) {
                    // var checkedResource = getCheckedReource();
                    var checkedResource = loadAllData();
//                    if (checkedResource.length < formDataLength) {
//                        slowNotify("请完成资源控制配置！", "danger");
//                    }else {
                        $.ajax({
                            url:'resourceController.do?insertResource',
                            type:'post',
                            dataType:"json",
                            data:{
                                info : JSON.stringify({
                                    drArr : checkedResource
                                }),
                                orderId : '${orderId}',
                            },
                            success:function (data) {
                               // alert(data);
                            }
                        })


                        apiResourceMap.set(id, checkedResource);

                        //执行保存动作时选中当前行
                        if (!$("tr[data-uniqueid=" + id + "]").hasClass("success")) {
                            checkedTr($("tr[data-uniqueid=" + id + "]"));
                        }
                        dialogRef.close();
                        quickNotify("资源配置成功", "success");
                    }

//                }
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialogItself) {
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    }

    //选中tr
    function checkedTr($tr) {
        $tr.addClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' checked type='checkbox'/>");
        var tempApi = {};
        tempApi.apiId = $tr.attr("data-uniqueid");
        tempApi.apiName = $tr.find('td').eq(1).text();
        if ($tr.find("[data-toggle='popover']").length > 0) {
            tempApi.apiInvokeType = 2;
        } else {
            tempApi.apiInvokeType = 1;
        }
        if ($tr.find("[data-toggle='popover2']").length > 0) {
            tempApi.pubMode = 2;
        } else {
            tempApi.pubMode = 1;
        }
        if ($tr.find("[id='source']").length > 0) {
            tempApi.resourceCtrl=1;
        } else {
            tempApi.resourceCtrl=0;
        }
        apiTemps.set(tempApi.apiId, tempApi);
    }

    //取消选中tr
    function unCheckedTr($tr) {
        $tr.removeClass('success');//去除之前选中的行的，选中样式
        $tr.find("input[class='td-ck']").remove();
        $tr.find("td:first").append("<input class='td-ck' type='checkbox'/>")
        //从api临时列表删除
        apiTemps.delete($tr.attr("data-uniqueid"));
    }


</script>
