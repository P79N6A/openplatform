$(document).ready(function(){

})
var notify = null;
function quickNotify(msg,type){
	if(type == null || type == undefined){
		type = "warning";
	}
	if(notify != null){
        notify.close();
    }
	notify = $.notify({
		message: msg,
		target: '_blank'
	},{
		type: type,
		placement: {
			from: "bottom",
			align: "right"
		},
		z_index: 9999,
		delay: 200,
        onClose:function(){
            notify = null;
        }
	});
}
function slowNotify(msg,type){
	if(type == null || type == undefined){
		type = "warning";
	}
    if(notify != null){
        notify.close();
    }
    notify = $.notify({
		message: msg,
		target: '_blank'
	},{
		type: type,
		placement: {
			from: "bottom",
			align: "right"
		},
		z_index: 9999,
		delay: 5000,
        onClose:function(){
            notify = null;
        }
	});
}
//将目标文本框的光标放到最后
var focusInput = function(id){
	var value = $("#" + id).val();
	if(value.length > 0){
		$("#" + id).focusEnd();
	}
}
var changePwd = function(obj,targetId,length){
	if(length != null && length != undefined){
		checklengthtext(obj,length);
	}
	//将最后一个值传给真实密码保存位
	var value = obj.value;
	/*//判断是否是删除,如果是删除就相应的删除明文的密码
	var targetValue = $("#" + targetId).val();
	if(targetValue.length > value.length){
		$("#" + targetId).val(targetValue.substring(0,value.length));
		return;
	}
	//如果不是删除就将新添加的密码放到明文存储位
	var trueIndex = 0;
	for(var i = 0;i < value.length;i++){
		if(value[i] != "*"){
			trueIndex = i;
			break;
		}
	}
	//如果trueIndex = 0说明密码没有被更新
	if(trueIndex != 0 || value.indexOf("*") == -1){
		var newPwd = value.substring(trueIndex,value.length);
		if(newPwd != ""){
			var aa = "";
			for(var i = 0;i < value.length;i++){
				aa += "*";
			}
			obj.value = aa;
			$("#" + targetId).val($("#" + targetId).val().substring(0,trueIndex) + newPwd);
		}
	}*/
	//判断是否是删除,如果是删除就相应的删除明文的密码
	var targetValue = aaa[targetId];
	if(targetValue != null && targetValue != undefined && targetValue.length > value.length){
		aaa[targetId] = targetValue.substring(0,value.length);
		return;
	}
	//如果不是删除就将新添加的密码放到明文存储位
	var trueIndex = 0;
	for(var i = 0;i < value.length;i++){
		if(value[i] != "*"){
			trueIndex = i;
			break;
		}
	}
	//如果trueIndex = 0说明密码没有被更新
	if(trueIndex != 0 || value.indexOf("*") == -1){
		var newPwd = value.substring(trueIndex,value.length);
		if(newPwd != ""){
			var aa = "";
			for(var i = 0;i < value.length;i++){
				aa += "*";
			}
			obj.value = aa;
			if(aaa[targetId] != null && aaa[targetId] != undefined){
				aaa[targetId] = aaa[targetId].substring(0,trueIndex) + newPwd;
			}else{
				aaa[targetId] = newPwd;
			}
		}
	}
}

//288个点转化的小时
function toHour1(minute) {
    var hour = parseInt(minute * 5 / 60);
    //var hour = parseInt(minute * 15 / 60);
    if (hour < 10) {
        return "0" + hour;
    }
    return hour;
}
//288个点对应的分钟
function toMinute1(minute) {
    var minute = minute * 5 % 60;
    //var minute = minute * 15 % 60;
    if (minute < 10) {
        return "0" + minute;
    }
    return minute;
}
//96个点对应的小时
function toHour(minute) {
    //var hour = parseInt(minute * 5 / 60);
    var hour = parseInt(minute * 15 / 60);
    if (hour < 10) {
        return "0" + hour;
    }
    return hour;
}
//96个点对应的分钟
function toMinute(minute) {
    //var minute = minute * 5 % 60;
    var minute = minute * 15 % 60;
    if (minute < 10) {
        return "0" + minute;
    }
    return minute;
}
/**
 * 获取当前时间在96个点中的对应点
 * @returns
 */
function getNowPoint(){
    var date = new Date();
    var hour = date.getHours();
    var minute = date.getMinutes();
    //var second =date.getSeconds();
    var nowPoint = parseInt((hour*60+minute)/15);
    return nowPoint;
}

//根据组织单位获取对应的台区信息
function getSelectItem(){
    var orgNo = $('#orgNo').val();
    if(orgNo == ""){
        $.notify({
            message : "请先选择组织单位！"
        }, {
            type : "warning",
            z_index : 9999,
            placement : {
                from : "bottom",
                align : "right"
            },
            delay : 2000
        });

        return false;
    }

    //下拉数据加载
    $.ajax({
        type : 'post',
        url : "monitorController.do?getTgName&orgNo="+orgNo,
        async:false,
        dataType : 'json',
        success : function(datas) {//返回list数据并循环获取
            $("#tgNameSelect").html(""); //清空select下拉框
            var select = $("#tgNameSelect");
            for (var i = 0; i < datas.length; i++) {
                select.append("<option value='"+datas[i].tgId+"'>"
                    + datas[i].tgName + "</option>");
            }
            $('.selectpicker').selectpicker('val', '');
            $('.selectpicker').selectpicker('refresh');
        }
    });
}

function intToTwoStr(num){
    if(num < 10){
        return "0" + num;
    }
    return num;
}
//将时间数据转成yyyy-MM-dd HH:mm:ss格式
function formatDate(time){
    var date = new Date(time);

    var year = date.getFullYear(),
        month = date.getMonth() + 1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    if(sec < 10){
        sec = "0" + sec;
    }
    if(min < 10){
        min = "0" + min;
    }
    if(hour < 10){
        hour = "0" + hour;
    }
    if(day < 10){
        day = "0" + day;
    }
    if(month < 10){
        month = "0" + month;
    }
    var newTime = year + '-' +
        month + '-' +
        day + ' ' +
        hour + ':' +
        min + ':' +
        sec;
    return newTime;
}

function selectOnchang(obj){
    var tgNo = $("#tgNameSelect option:selected").val();
    $('#tgNo').val(tgNo);
}


//转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

//列表数据字典项格式化
function listDictFormat(value,dicts){
    if (!value) return '';
    var valArray = value.split(',');
    var showVal = '';
    if (valArray.length > 1) {
        for (var k = 0; k < valArray.length; k++) {
            if(dicts && dicts.length>0){
                for(var a = 0;a < dicts.length;a++){
                    if(dicts[a].typecode ==valArray[k]){
                        showVal = showVal + dicts[a].typename + ',';
                        break;
                    }
                }
            }
        }
        showVal=showVal.substring(0, showVal.length - 1);
    }else{
        if(dicts && dicts.length>0){
            for(var a = 0;a < dicts.length;a++){
                if(dicts[a].typecode == value){
                    showVal =  dicts[a].typename;
                    break;
                }
            }
        }
    }
    if(showVal==''){
        showVal = value;
    }
    return showVal;
}

//加载字典数据
function initDictByCode(dictObj,code,callback){
    if(!dictObj[code]){
        jQuery.ajax({
            url: "systemController.do?typeListJson&typeGroupName="+code,
            type:"post",
            dataType:"JSON",
            async:false,
            success: function (back) {
                if(back.success){
                    dictObj[code]= back.obj;

                }
                callback();
            }
        });
    }
}
//加载form查询数据字典项
function loadSearchFormDicts(obj,arr,type,name){
    var html = "";
    for(var a = 0;a < arr.length;a++){
        if("select"== type){
            html+="<option value = '"+arr[a].typecode+"'>"+arr[a].typename+"</option>";
        }else{
            if(!arr[a].typecode){
            }else{
                html+="<input name = '"+name+"' type='"+type+"' value = '"+arr[a].typecode+"'>"+arr[a].typename +"&nbsp;&nbsp;";
            }

        }
    }
    obj.html(html);
}
//bootstrap dialog样式
function bootOpenNormal(title,url,size){
    var dialog = new BootDialog();
    return dialog.openNormal(title,url,formateSizeType(size));
}function bootOpenLook(title,url,size){
    var dialog = new BootDialog();
    dialog.openLook(title,url,formateSizeType(size));
}function bootOpenTest(title,url,size){
    var dialog = new BootDialog();
    dialog.openTest(title,url,formateSizeType(size));
}
function formateSizeType(size){
    switch (size){
        case "small":
            size = BootstrapDialog.SIZE_SMALL;
            break;
        case "large":
            size = BootstrapDialog.SIZE_LARGE;
            break;
        case "wide":
            size = BootstrapDialog.SIZE_WIDE;
            break;
        default:
            size = BootstrapDialog.SIZE_NORMAL;
    }
    return size;
}
var BootDialog = function() {
    var dialog = new Object();
    //初始化Table
    dialog.openNormal = function (title,url,size) {
        var dia = new BootstrapDialog({
            title: title,
            type:BootstrapDialog.TYPE_DEFAULT,
            size:size,
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': url
            },
            buttons: [{
                label: '保存',
                cssClass: 'btn-primary',
                id:"btn_sub",
                // action: saveAction
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
        dia.open();
        return dia;
    };
    dialog.openTest = function (title,url,size) {
        var dia = new BootstrapDialog({
            title: title,
            type:BootstrapDialog.TYPE_DEFAULT,
            size:size,
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: false,
            data: {
                'pageToLoad': url
            },
            buttons: [{
                label: '测试',
                cssClass: 'btn-primary',
                id:"btn_sub",
                // action: saveAction
            }, {
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
        dia.open();
        return dia;
    };
    dialog.openLook = function (title,url,size) {
        var dia = new BootstrapDialog({
            title: title,
            type:BootstrapDialog.TYPE_DEFAULT,
            size:size,
            message: function(dialog) {
                var $message = $('<div></div>');
                var pageToLoad = dialog.getData('pageToLoad');
                $message.load(pageToLoad);
                return $message;
            },
            closable: true,
            data: {
                'pageToLoad': url
            },
            buttons: [{
                label: '取消',
                cssClass: 'btn-default',
                action: function(dialogItself){
                    dialogItself.close();
                }
            }]
        });
        dia.open();
    };
    return dialog;
}
//创建时间戳id
function timeId(){
    return dateFtt("hhmmssS",new Date());
}
//时间格式处理
function dateFtt(fmt,date)
{ //author: meizz
    var o = {
        "M+" : date.getMonth()+1,                 //月份
        "d+" : date.getDate(),                    //日
        "h+" : date.getHours(),                   //小时
        "m+" : date.getMinutes(),                 //分
        "s+" : date.getSeconds(),                 //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S"  : date.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}
//单选框/多选框初始化
function formControlInit(){
    $('.i-checks').iCheck({
        labelHover : false,
        cursor : true,
        checkboxClass : 'icheckbox_square-green',
        radioClass : 'iradio_square-green',
        increaseArea : '20%'
    });
}
//判断value中是否包含特殊字符和空格(下划线除外)
function checkSpecialCharAndSpace(value){
    var pattern = new RegExp("[`~!@#$^&*=|{}':;',\\[\\]<>《》/?~！@#￥……&*|{}【】‘；：”“'。，、？' ']");
    return pattern.test(value);
}
function loadNowData(tbody) {
    var treeData = $("#departs").bootstrapTable("loadTreeData");
    var nowData = new Array();
    $(tbody).find("tr").each(function(){
        var row = {};
        $(this).find("input").each(function(){
            var name = $(this).attr("name");
            if(name != null && name != undefined){
                row[name] = $(this).val();
            }
        });
        $(this).find("select").each(function(){
            var name = $(this).attr("name");
            if(name != null && name != undefined){
                row[name] = $(this).val();
            }
        });
        if(row["parentId"] == ""){
            row["parentId"] = null;
        }
        row.hidden = false;
        nowData.push(row);
    });
    //将tree需要的参数补充上
    if(treeData != null && treeData != undefined){
        $.each(nowData,function(i,row){
            var id = row.id;
            $.each(treeData,function(j,treeRow){
                if(treeRow.id == id){
                    row.level = treeRow.level;
                    row.isLeaf = treeRow.isLeaf;
                    row.hidden = treeRow.hidden;
                }
            })
        })
    }
    return nowData;
}
function alertMessage(msg){
    BootstrapDialog.alert({
        type: BootstrapDialog.TYPE_WARNING,
        title: '操作提示',
        message: msg,
        closeable: true,
        buttonLabel: "确定"
    });
}