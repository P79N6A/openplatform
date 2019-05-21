<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>能力信息表</title>
    <t:base type="bootstrap,bootstrap-table,layer"></t:base>
    <link rel="stylesheet" type="text/css" href="test.css">
    <script>

    </script>
</head>
<body>
<div class="maincontent">
    <table style="border-collapse:collapse; border-spacing:0;width:1000px;">
        <tbody><tr style="height:40px;">
            <td colspan="2">
					<span style="float: left;">
						<a href="/docs/api_list.htm" target="_blank" style="color:blue">API文档(API Document)</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="/apitools/apiPropTools.htm" target="_blank" style="color:blue;">API属性工具(Item Property Tools)</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="/apitools/sessionPage.htm" target="_blank" style="color:blue;">Session获取工具(Authorize Tools)</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="/apitools/errorCodeSearch.htm" target="_blank" style="color:blue;">错误码自查工具(ErrorCode Tools)</a>
					</span>
            </td>
        </tr>
        <tr style="height:40px;">
            <td colspan="2">
					<span style="float: left;">
						<span style="color:red">系统分配AppKey只能调用基础API，增值API需要填入自己申请的AppKey
						<br>Default AppKey can only request base APIs , Please use your Appkey while request other APIs</span>
					</span>
            </td>
        </tr>
        <tr>
            <td valign="top" data-spm-anchor-id="0.0.0.i7.7511762bQV3KjK">
                <table border="0" cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <td>
                            <input type="hidden" name="appkey" id="appkey" value="12129701">
                            <input type="hidden" name="api_soure" id="api_soure" value="0">
                            <input type="hidden" name="c_SessionId" id="c_SessionId" value="k0WDUmi7iD9YK9hjXoIHGZ4L0gX8FNZsOo">
                            <table class="parameters" width="500" border="0" cellpadding="4" cellspacing="0">
                                <tbody>
                                <tr>
                                    <td width="160" align="right" data-spm-anchor-id="0.0.0.i0.7511762bQV3KjK">返回格式(Format)：</td>
                                    <td width="340">
                                        <select id="format" name="format" style="width: 195px; background-color: rgb(204, 232, 207);" data-spm-anchor-id="0.0.0.i8.7511762bQV3KjK">
                                            <option value="xml">XML</option>
                                            <option value="json">JSON</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">API类目(API Category)：</td>
                                    <td data-spm-anchor-id="0.0.0.i9.7511762bQV3KjK">
                                        <select name="apiCategoryId" id="apiCategoryId" style="width: 195px; background-color: rgb(204, 232, 207);" onchange="getApiListByCategoryId(this);" data-spm-anchor-id="0.0.0.i6.7511762bQV3KjK">
                                            <option value="">--请选择API类目--</option>
                                            <option value="1">用户API</option>
                                            <option value="3">类目API</option>
                                            <option value="4">商品API</option>
                                            <option value="5">交易API</option>
                                            <option value="6">评价API</option>
                                            <option value="7">物流API</option>
                                            <option value="9">店铺API</option>
                                            <option value="15">分销API</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">API名称(API Name)：</td>
                                    <td>
                                        <span id="SipApinameDiv"><select name="sip_apiname" id="sip_apiname" style="width: 195px; background-color: rgb(204, 232, 207);" data-spm-anchor-id="0.0.0.i11.7511762bQV3KjK"><option value="">--请选择API--</option><option value="285">taobao.appstore.subscribe.get</option><option value="10873">taobao.user.teluser.search</option><option value="10972">taobao.user.shipping.addresses.get</option><option value="21348">taobao.user.buyer.get</option><option value="21349">taobao.user.seller.get</option><option value="21350">taobao.user.extend.get</option><option value="21428">taobao.mixnick.get</option><option value="22188">taobao.video.add</option><option value="24109">taobao.user.identity.get</option><option value="24410">taobao.mixnick.change</option><option value="24672">taobao.opensecurity.uid.get</option><option value="24673">taobao.opensecurity.isv.uid.get</option><option value="24819">taobao.open.account.delete</option><option value="24820">taobao.open.account.update</option><option value="24821">taobao.open.account.create</option><option value="24832">taobao.pamirsathena.solution.upgrade</option><option value="24833">taobao.pamirsathena.solution.getbyrelation</option><option value="24835">taobao.pamirsathena.solution.remove</option><option value="24836">taobao.pamirsathena.solution.modify</option><option value="24838">taobao.pamirsathena.solution.get</option><option value="24839">taobao.pamirsathena.solution.add</option><option value="24848">taobao.open.account.list</option><option value="24938">account.aliyuncs.com.GetPubKey.2013-07-01</option><option value="25015">tmall.crm.member.get</option><option value="25157">taobao.open.account.search</option><option value="25270">taobao.open.account.token.validate</option><option value="25271">taobao.open.account.token.apply</option><option value="25558">taobao.user.weibo.payment.binduser.get</option><option value="25594">taobao.user.weibo.payment.get</option><option value="25596">taobao.open.sms.sendvercode</option><option value="25597">taobao.open.sms.checkvercode</option><option value="25598">taobao.open.sms.sendmsg</option><option value="25725">taobao.item.barcode.feedback.query</option><option value="25816">taobao.baichuan.credit.authpageurl</option><option value="25817">taobao.baichuan.credit.queryusercredit</option><option value="25823">taobao.item.barcode.gs.update</option><option value="25824">taobao.item.barcode.subscribe.query</option><option value="25869">taobao.open.account.index.find</option><option value="25870">taobao.item.barcode.company.update</option><option value="25940">taobao.data.wifidevice.list</option><option value="25957">taobao.tasp.chengxian.test</option><option value="25966">taobao.open.sms.rmdelaymsg</option><option value="25969">taobao.data.wifi.put</option><option value="25978">alibaba.aliqin.flow.wallet.check.balance</option><option value="26018">taobao.user.mobile.cancelled.notice</option><option value="26027">taobao.open.sms.batchsendmsg</option><option value="26212">taobao.item.barcode.gs.addwhitelist</option><option value="26303">taobao.user.avatar.get</option><option value="26716">taobao.xxxx.xiaoxuan.test</option><option value="26900">taobao.accountlink.token.apply</option><option value="26933">taobao.accountlink.bind.create</option><option value="26934">taobao.accountlink.bind.sync</option><option value="26935">taobao.accountlink.bind.delete</option><option value="26941">taobao.accountlink.account.get</option><option value="27005">alibaba.mj.xlife.member.add</option><option value="27006">alibaba.mj.xlife.member.modify</option><option value="27203">alibaba.mj.xlife.intime.order.add</option><option value="27436">tmall.service.settleadjustment.modify</option><option value="27964">alibaba.mj.xlife.intimeright.callback</option><option value="31159">taobao.rdc.aligenius.account.validate</option><option value="31237">tmall.fantasy.kindle.crowd</option><option value="31570">alibaba.interact.ui.video</option><option value="31647">taobao.accountlink.bind.query.eid</option><option value="31654">taobao.accountlink.bind.query.hid</option><option value="31908">taobao.messageaccount.messsage.mass.send</option><option value="31931">taobao.messageaccount.messsage.reply</option><option value="32099">yunos.tv.tao.uic.getuserinfo</option><option value="32271">alibaba.passport.authcode.getbypwd</option><option value="32274">alibaba.passport.update.account</option><option value="32275">alibaba.passport.send.phonecode</option><option value="32276">alibaba.passport.change.password</option><option value="32277">alibaba.passport.reset.passport</option><option value="32278">alibaba.passport.set.password</option><option value="32279">alibaba.passport.pre.createaccount</option><option value="32280">alibaba.passport.validate.ssoticket</option><option value="32281">alibaba.passport.accesstoken.get.ssoticket</option><option value="32282">alibaba.passport.time.get</option><option value="32283">alibaba.passport.login.phonecode</option><option value="32284">alibaba.passport.disable.accesstoken</option><option value="32285">alibaba.passport.refresh.accesstoken</option><option value="32286">alibaba.passport.authcode.get.accesstoken</option><option value="32287">alibaba.passport.validate.accesstoken</option><option value="32548">taobao.accountlink.accesstoken.refresh</option><option value="32655">taobao.video.query</option><option value="32729">taobao.video.authorized</option><option value="32833">taobao.ww.video.appointment.consult.worktime.get</option><option value="33095">yunos.dm.byod.bind</option><option value="33096">yunos.dm.byod.unbind</option><option value="33102">yunos.dm.byod.bindcheck</option><option value="33103">yunos.dm.byod.logincheck</option><option value="33819">alibaba.home.kp.get</option><option value="35382">taobao.messageaccount.messsage.normal.send</option><option value="35583">alibaba.alihealth.alidoctor.gst.diseaserecord.update</option><option value="35804">alibaba.tbdx.crm.order.update</option><option value="35949">alibaba.tbdx.crm.order.updatestatus</option><option value="35955">alibaba.tbdx.crm.order.add</option><option value="35956">alibaba.tbdx.crm.customerlog.add</option><option value="36006">alibaba.tbdx.crm.customer.add</option><option value="37216">qimen.taobao.xxx.xiaoxuan</option><option value="37652">alibaba.tbdx.crm.customer.batchadd</option><option value="37653">alibaba.tbdx.crm.customer.pickin</option><option value="37654">alibaba.tbdx.crm.customer.pickout</option><option value="37676">alibaba.tbdx.crm.customer.query</option><option value="38087">alibaba.tbdx.crm.customer.pickoutcallback</option><option value="38088">alibaba.tbdx.crm.customer.pickoutquery</option><option value="38221">taobao.lift.user.get</option><option value="38386">taobao.accountlink.bind.checkunbind</option><option value="38414">taobao.miniapp.messsage.normal.send</option><option value="38415">taobao.miniapp.messsage.reply</option><option value="38525">alibaba.wdk.portal.user.entryevent.add</option><option value="38612">taobao.rhino.gateway.test</option><option value="39044">yunos.dm.mobile.init</option><option value="39049">yunos.dm.mobile.device.parent.unbind</option><option value="39050">yunos.dm.mobile.device.parent.bind</option><option value="39051">yunos.dm.mobile.device.school.bind</option><option value="39052">yunos.dm.mobile.device.school.unbind</option><option value="39053">yunos.dm.mobile.device.school.change</option><option value="39055">yunos.dm.mobile.workflow.send</option><option value="39056">yunos.dm.mobile.parent.webblacklist.set</option><option value="39057">yunos.dm.mobile.device.info.set</option><option value="39058">yunos.dm.mobile.device.apps.set</option><option value="39061">yunos.dm.mobile.parent.controlmode.set</option><option value="39092">yunos.dm.mobile.device.status</option><option value="39094">yunos.dm.mobile.device.apps.get</option><option value="39111">yunos.dm.mobile.parent.webblacklist.get</option><option value="39112">yunos.dm.mobile.device.get.teachermode</option><option value="39113">yunos.dm.mobile.report.get</option><option value="39124">yunos.dm.mobile.parent.controlmode.get</option><option value="39136">yunos.dm.mobile.device.parent</option><option value="39190">taobao.tbmpc.youku.bind.revise</option><option value="39191">taobao.tbmpc.youku.revise.youkuid.query</option><option value="39313">yunos.dm.mobile.device.recode</option><option value="40389">wdk.hema.orderlist.get</option><option value="40526">yunos.tv.tao.subscribe</option><option value="40613">yunos.tv.tao.sendmessage</option><option value="40783">taobao.nimitz.unicorn.user.report</option><option value="40784">taobao.nimitz.unicorn.code.get</option><option value="40785">taobao.nimitz.unicorn.award.query</option></select></span>
                                        &nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">数据环境(environment)：</td>
                                    <td data-spm-anchor-id="0.0.0.i10.7511762bQV3KjK"><input id="restId" type="radio" name="restId" value="1">沙箱(Sandbox) <input id="restId" type="radio" name="restId" checked="" value="2"> 正式(Online)</td>
                                </tr>
                                <tr>
                                    <td align="right">提交方式(Method)：</td>
                                    <td><input type="radio" name="sip_http_method" value="2" checked=""> POST　<input type="radio" name="sip_http_method" value="1"> GET</td>
                                </tr>
                                <tr>
                                    <td align="right">SDK类型(SDK Language)：</td>
                                    <td><input type="radio" name="codeType" value="JAVA" checked=""> JAVA　<input type="radio" name="codeType" value="PHP"> PHP　<input type="radio" name="codeType" value=".NET"> .NET <input type="radio" name="codeType" value="PYTHON"> PYTHON</td>
                                </tr>
                                <tr>
                                    <td align="right">AppKey：</td>
                                    <td><input type="text" id="app_key" name="app_key" value="系统分配(Default)" style="width: 190px; background-color: rgb(204, 232, 207);" readonly="true" data-spm-anchor-id="0.0.0.i12.7511762bQV3KjK">&nbsp;<a href="javascript:void(0)" onclick="javascript:changeAppInfo();this.blur();"><span id="automaticSpan">自定义(Custom Settings)</span></a></td>
                                </tr>
                                <tr>
                                    <td align="right">AppSecret：</td>
                                    <td><input type="text" id="app_secret" name="app_secret" value="系统分配(Default)" style="width: 190px; background-color: rgb(204, 232, 207);" readonly="true" data-spm-anchor-id="0.0.0.i13.7511762bQV3KjK"></td>
                                </tr>
                                <tr id="sessionSapn" style="display:none">
                                    <td align="right">SessionKey：</td>
                                    <td><input type="text" id="session" name="session" value="" style="width: 190px; background-color: rgb(204, 232, 207);">&nbsp;<a href="javascript:void(0)" onclick="alert('当API的访问级别为‘公开’时，SessionKey不需要填写；\r\n当API的访问级别为‘须用户登录’时，SessionKey必须填写；\r\n当API的访问级别为‘隐私数据须用户登录’时，SessionKey可填可不填；\r\n如何获取SessionKey，请搜索‘用户授权介绍’或点击上面的‘Session获取工具’');">说明</a></td>
                                </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td><form id="apiForm" method="post" enctype="multipart/form-data"><div id="ParamDiv"><table width="500" border="0" cellpadding="4" cellspacing="0"><tbody><tr style="height:30px;"><td colspan="2">将鼠标移至说明上，查看参数介绍；<font color="red">*</font> 表示必填，<font color="blue">*</font> 表示几个参数中必填一个；查看<a href="/apidoc/api.htm?path=categoryId:1-apiId:21349" target="_blank" style="color:blue">API详情</a><br>Mouse over the caption line to know Parameter Reference , View <a href="/apidoc/api.htm?path=categoryId:1-apiId:21349" target="_blank" style="color:blue">API Detail</a></td></tr><tr style="height:30px;"><td align="right" width="140">fields：</td><td width="360"><span class="l"><input type="text" id="apiParam_fields" name="fields" value="" style="width: 220px; background-color: rgb(204, 232, 207);" data-spm-anchor-id="0.0.0.i14.7511762bQV3KjK"></span><span class="point-red">*</span></td></tr></tbody></table></div></form></td>
                    </tr>
                    <tr>
                        <td>
                            <table width="500" cellspacing="0" cellpadding="4" border="0">
                                <tbody>
                                <tr style="height:30px;">
                                    <td width="140" align="right">验证码：</td>
                                    <td width="360" data-spm-anchor-id="0.0.0.i5.7511762bQV3KjK">

                                        <input type="text" style="width: 120px; background-color: rgb(204, 232, 207);" value="" name="c_code" id="c_code" data-spm-anchor-id="0.0.0.i15.7511762bQV3KjK">
                                        <img src="//pin.aliyun.com//get_img?sessionid=k0WDUmi7iD9YK9hjXoIHGZ4L0gX8FNZsOo&amp;identity=isvportal" id="checkCodeImg">
                                        <a id="code_change_image" href="#" onclick="javascript:flashCheckCode();this.focus();">看不清楚？</a>

                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table width="500" border="0" cellpadding="4" cellspacing="0">
                                <tbody>
                                <tr>
                                    <td width="160">&nbsp;</td>
                                    <td width="340" align="left">
                                        <input id="apiTestButton" type="button" value="提交测试(Execute)" onclick="checkForm();this.blur();" style="width:120px;height:24px;*padding-top:3px;border:#666666 1px solid;cursor:pointer">
                                        <span id="bindUrlSpan" style=""><input type="button" value="绑定用户(Authorize)" onclick="bindUser();" onfocus="blur();" style="width:120px;height:24px;*padding-top:3px;border:#666666 1px solid;cursor:pointer;"></span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
            <form id="errorCodeSearchForm" name="errorCodeSearchForm" action="/apitools/errorCodeSearch.htm" target="_blank" method="post"></form>
            <input name="apiCategoryId" type="hidden" id="apiCategoryIdForCodeSearch">
            <input name="apiName" type="hidden" id="apiNameForCodeSearch">
            <input name="error" type="hidden" id="error">

            <td valign="top">
                API请求参数(API Request)：
                <br>
                <textarea name="param" id="param" cols="90" rows="5" readonly="" style="background-color: rgb(204, 232, 207);" data-spm-anchor-id="0.0.0.i4.7511762bQV3KjK"></textarea>
                <br>
                <br>
                API返回结果(API Response)：
                <span style="display:none" id="codeSearchButton">

					 	<a id="errorReason" data-placement="right" data-toggle="tooltip" data-original-title="" href="javascript:void(0);" class="tag">查看错误原因(Error Reason)</a>
					 </span>
                <br>
                <textarea name="resultShow" id="resultShow" cols="90" rows="10" readonly="" style="background-color: rgb(204, 232, 207);" data-spm-anchor-id="0.0.0.i3.7511762bQV3KjK"></textarea>
                <br>
                <br>
                SDK调用示例代码(Sample Code)：
                <br>
                <textarea id="sampleCode" name="sampleCode" cols="90" rows="8" readonly="" style="background-color: rgb(204, 232, 207);" data-spm-anchor-id="0.0.0.i2.7511762bQV3KjK"></textarea>
            </td>
        </tr>
        <tr></tr>
        </tbody></table>
</div>
</body>
</html>