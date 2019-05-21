<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>订单详情页面</title>
</head>
<body style="overflow:hidden;overflow-y:auto;">
<div class="modal-body">
    <form id="addForm" name="addForm" method="post" class="form-horizontal" role="form">
        <div class="form-group"  >
            <label class="col-sm-2 control-label">订单编号：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input value='${apiOrder.id}' type="text" maxlength="32" readonly="readonly"
                       class="form-control input-sm"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">应用名称：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input value='${apiOrder.appName}' type="text" readonly="readonly"
                       class="form-control input-sm"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">订单金额：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input value='${apiOrder.money}' type="number" maxlength="32" readonly="readonly"
                       class="form-control input-sm"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">下单时间：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input id="createDate" value='${apiOrder.createDate}' type="text" maxlength="32" readonly="readonly"
                       class="form-control input-sm laydate-datetime"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">创建人：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input value='${apiOrder.createName}' type="text" maxlength="32" readonly="readonly"
                       class="form-control input-sm"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">支付状态：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input id="payStatus" value='' type="text" readonly="readonly"
                       class="form-control input-sm"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">支付时间：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input id="payTime" value='${apiOrder.payTime}' type="text" maxlength="32" readonly="readonly"
                       class="form-control input-sm laydate-datetime"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">审核状态：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <input id="auditStatus" value='' type="text" maxlength="32" readonly="readonly"
                       class="form-control input-sm"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">审核意见：</label>
            <div class="col-sm-8" style="padding-right:0px">
                <textarea class="form-control" name="auditMsg" rows="3"
                          readonly="readonly">${apiOrder.auditMsg}</textarea>
            </div>
        </div>

    </form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        var payTime = '${apiOrder.payTime}';
        if (payTime != null && payTime != '') {
            $("#payTime").val(payTime.substring(0,payTime.indexOf('.')));
        }
        var createDate = '${apiOrder.createDate}';
        if (createDate != null && createDate != '') {
            $("#createDate").val(createDate.substring(0,createDate.indexOf('.')));
        }

        //状态字段替换
        var auditStatus = '${apiOrder.payStatus}';
        var auditStatusValue = '';
        if (auditStatus == 0) {
            auditStatusValue = '暂存';
        } else if (auditStatus == 1) {
            auditStatusValue = '待审核';
        } else if (auditStatus == 2) {
            auditStatusValue = '通过';
        } else if (auditStatus == 3) {
            auditStatusValue = '审核失败';
        }
        $("#auditStatus").val(auditStatusValue)
        var payStatus = '${apiOrder.payStatus}';
        var payStatusValue = '';
        if (payStatus == 0) {
            payStatusValue = '未支付';
        } else if (payStatus == 1) {
            payStatusValue = '已支付';
        }
        $("#payStatus").val(payStatusValue);
    });
</script>
</body>
</html>