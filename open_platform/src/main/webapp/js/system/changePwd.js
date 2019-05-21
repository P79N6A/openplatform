var aaa = {};

function changePassword(dialog) {
    var oldpass = aaa["aaa"];
    var newpass1 = aaa["bbb"];
    var newpass = aaa["ccc"];
    var numasc = 0;
    var charasc = 0;
    var otherasc = 0;
    if (oldpass == null || oldpass == "") {
        quickNotify("旧密码不能为空！", "danger");
        return false;
    }
    if (newpass1 == null || newpass1 == "") {
        quickNotify("新密码不能为空！", "danger");
        return false;
    }
    if (newpass1.toString().length < 8 || newpass1.toString().length > 20) {
        quickNotify("新密码长度必须大于8位并小于20位！", "danger");
        return false;
    }
    if (newpass != newpass1) {
        quickNotify("两次输入的新密码不一致，请重新输入！", "danger");
        return false;
    }
    for (var i = 0; i < newpass1.length; i++) {
        var asciiNumber = newpass1.substr(i, 1).charCodeAt();
        if (asciiNumber >= 48 && asciiNumber <= 57) {
            numasc += 1;
        }
        if ((asciiNumber >= 65 && asciiNumber <= 90) || (asciiNumber >= 97 && asciiNumber <= 122)) {
            charasc += 1;
        }
        if ((asciiNumber >= 33 && asciiNumber <= 47) || (asciiNumber >= 58 && asciiNumber <= 64) || (asciiNumber >= 91 && asciiNumber <= 96) || (asciiNumber >= 123 && asciiNumber <= 126)) {
            otherasc += 1;
        }
    }
    if (0 == numasc) {
        quickNotify("密码必须含有数字!", "danger");
        return false;
    }
    if (0 == charasc) {
        quickNotify("密码必须含有字母!", "danger");
        return false;
    }
    if (otherasc == 0) {
        quickNotify("密码必须包含特殊字符！", "danger");
        return false;
    }
    var v = aaa["bbb"];
    //密码强度通过后，将原始密码和新密码加密
    var oldPassword = aaa["aaa"];
    oldPassword = doCrypt(oldPassword, keyStr1);
    $("#oldPassword1").val(oldPassword);
    var password = $("#oldPassword1").val();
    var newPassword = doCrypt(v, keyStr1);
    $("#newPassword1").val(newPassword);
    // $.ajax({
    //     async: false,
    //     cache: false,
    //     type: 'POST',
    //     url: "dwrMessagePush.do?getRandom&str=" + "",// 请求的action路径
    //     error: function () {// 请求失败处理函数
    //     },
    //     success: function (data) {
    DwrMessagePush.getRandom("", function (data) {
        var random = data.randomStr;
        var options = {
            url: "userController.do?savenewpwd&randomStr=" + random,
            type: "post",
            dataType: "json",
            beforeSubmit: function () {
            },
            success: function (data) {
                if (data.success) {
                    window.location.reload();
                    quickNotify("密码修改成功，下次登录开始将使用新密码", "success");
                } else {
                    slowNotify(data.msg, "danger");
                }
            },
        }
        $("#addForm").ajaxSubmit(options);
    });
}