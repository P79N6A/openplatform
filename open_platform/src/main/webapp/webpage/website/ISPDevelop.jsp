<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/webpage/common/websiteResources.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>智慧车联网能力开放平台</title>
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/iconfont/iconfont.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/tb.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/api.css" %> type="text/css">
    <link rel="stylesheet" href=<%= request.getContextPath() + "/css/website/style.css" %> type="text/css">
    <style>
        html{
            /*overflow: hidden;*/
        }
        h3{
            margin-left:28px
        }
        h4{
            margin-left:37px
        }
        .img-p{
            margin-left:28px
        }
    </style>
</head>
<body style="background-color:#ffffff;padding-top:0px;">
<div class="api-content" >
    <article>
        <h1 style="text-align: center">
            ISP能力发布及管理说明书
        </h1>
        <h2>
            1.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>平台基本信息
        </h2>
        <h3>
            1.1平台现况说明
        </h3>
        <p style="text-indent:37px">
            能力开放平台暂未将测试环境与生产环境打通，因此，能力的发布需在测试环境和生产环境发布两次。
        </p>
        <p style="text-indent:37px">
            能力开放平台共有信息内网和信息外网2套，<span style=";line-height:150%">账号、能力相互独立</span>。
        </p>
        <h3>
            1.2名词概述
        </h3>
        <p class="MsoListParagraph" style="margin-left:28px">
            1)<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp;&nbsp;&nbsp; </span>能力发布者：简称ISP，在能力开放平台发布自己拥有的能力，供能力订阅者进行订阅。
        </p>
        <p class="MsoListParagraph" style="margin-left:28px">
            2)<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp;&nbsp;&nbsp; </span>能力订阅者：简称ISV，在能力开放平台创建应用，然后为应用订阅能力。
        </p>
        <p class="MsoListParagraph" style="margin-left:28px">
            3)<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp;&nbsp;&nbsp; </span>平台管理员：在能力开放平台维护平台的账号、角色和权限信息。
        </p>
        <p class="MsoListParagraph" style="margin-left:28px">
            4)<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp;&nbsp;&nbsp; </span>ISV主动调用能力：ISV订阅能力后，需要根据能力的URL和参数信息主动调用。
        </p>
        <p class="MsoListParagraph" style="margin-left:28px">
            5)<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp;&nbsp;&nbsp; </span>ISV被动接收能力：ISV订阅能力后，能力开放平台根据ISV提供的URL和参数信息主动推送给ISV，ISV只是被动接收。
        </p>
        <h2>
            2.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>注册及登录
        </h2>
        <h3>
            2.1注册
        </h3>
        <h4>
            测试环境：
        </h4>
        <p style="text-indent:37px">
            能力开放平台在测试环境的账号暂时由平台管理员人工分配，isv需向牛延军提供其在车联网平台的管理员账号。
        </p>
        <h4>
            生产环境：
        </h4>
        <p style="text-indent:37px">
            能力开放平台的ISP暂不开放注册，需首先有车联网平台能力平台的管理员账号，由平台管理员对账号进行授权。
        </p>
        <h3>
            2.2登录
        </h3>
        <h4>
            测试环境：
        </h4>
        <p style="text-indent:37px">
            http://192.168.102.10:8080/openplatform/apiLogin.do?login
        </p>
        <h4>
            生产环境：
        </h4>
        <p style="text-indent:37px">
            信息外网登录地址：
        </p>
        <p style="text-indent:37px">
            https://open.echargenet.com/openplatform/apiLogin.do?login
        </p>
        <p style="text-indent:37px">
            信息内网登录地址：
        </p>
        <p style="text-indent:37px">
            <a href="http://10.0.0.110/"><span style="color: windowtext">http://10.110</span></a>.80.216:8081/openplatform/apiLogin.do?login
        </p>
        <h2>
            3.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>能力发布前的准备工作
        </h2>
        <p style="text-indent:37px">
            根据能力开放平台的运营模式，平台上发布的能力分别有计费策略、流量控制策略、数据权限控制策略三个限定条件，用于对能力进行计量计费、流量控制、数据权限控制。
        </p>
        <p style="text-indent:37px">
            在能力发布时，需分别关联这三种策略，可以不新建策略，选择已有的策略进行关联。
        </p>
        <h3>
            3.1设置计费策略
        </h3>
        <p style="text-indent:37px">
            计费策略有“包年包月”、“按次计费”、“按流量计费”三种模式。可以将计费模型配置给自己发布的能力，由ISV选择适合自己的计费策略。
        </p>
        <p style="text-indent:37px">
            在能力开放平台试运行阶段，所有计费策略都应暂时按照0执行，不收取任何费用。
        </p>
        <h4>
            包年包月：
        </h4>
        <p style="text-indent:37px">
            依次点击“计费管理”-“能力计费策略”-“包年包月”-“录入”，可进行包年包月费用设置。可按周计费，按月计费，按年计费。该设置适用于预付费。
        </p>
        <h4>
            按次收费：
        </h4>
        <p style="text-indent:37px">
            依次点击“计费管理”-“能力计费策略”-“按次收费”-“录入”，可进行按访问次数收费费用设置。在弹出的“收费方式录入”界面中输入次数范围区间和在本区间内的单次价格。该设置适用于后付费。
        </p>
        <h4>
            按流量收费：
        </h4>
        <p style="text-indent:37px">
            依次点击“计费管理”-“能力计费策略”-“按流量收费”-“录入”，可进行按返回值流量收费费用设置。在弹出的“收费方式录入”界面中输入流量范围区间和在本区间内的每KB的价格。该设置适用于后付费。
        </p>
        <h3>
            3.2设置流量控制策略
        </h3>
        <p style="text-indent:37px">
            为了防止高频或大流量的访问造成程序拥堵，当用户访问频率超过设定的值时，访问被拦截。
        </p>
        <p style="text-indent:37px">
            依次点击“流量策略控制”-“录入”，在弹出的界面中设置限制的次数，在“单位”选择中选择“次/秒”、“次/分”、“次/时”、“次/天”。
        </p>
        <h3>
            3.3设置数据权限控制策略
        </h3>
        <p style="text-indent:37px">
            该功能正在开发中。
        </p>
        <h3>
            3.4配置自定义数据类型
        </h3>
        <p style="text-indent:37px">
            该功能正在开发中。
        </p>
        <h2>
            4.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>设置能力中心
        </h2>
        <p style="text-indent:37px">
            所有发布的能力均隶属于某一能力中心。能力中心由平台管理员统一维护，若拟发布的能力的能力中心未录入平台，可联系平台管理员。
        </p>
        <h2>
            5.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>发布能力
        </h2>
        <p style="text-indent:37px">
            当前能力开放平台支持两种能力发布方式：
        </p>
        <p style="text-indent:37px">
            （1）ISV主动调用能力，ISP需提前向平台管理员提供HSF接口的jar包，由能力开放平台自动提供协议转换，ISV订阅了该接口后，可以以HTTP的形式调用该接口。
        </p>
        <p style="text-indent:37px">
            （2）ISV被动接收能力，ISP需首先向平台管理员索要为ISP提供的jar包，由ISP按照规范调用包内接口，由能力开放平台自动提供协议转换，将接口内数据推送至订阅该能力的ISV提供的链接地址，实现ISP主动调用外部接口或主动推送数据的效果（该功能正在开发中）。
        </p>
        <h3>
            5.1录入基本信息
        </h3>
        <p class="MsoListParagraph" style="margin-left:28px">
            1)<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp;&nbsp;&nbsp; </span>ISV主动调用能力
        </p>
        <p style="text-indent:37px">
            依次点击“能力管理”-“能力列表”，在“能力中心”上点击要录入的能力所属的能力中心，点击“录入”，弹出“能力录入”界面。如下图：
        </p>
        <p style="text-align:center">
            <img width="500" height="372" src="../../images/website/docs/5-1.png"/>
        </p>
        <p style="text-indent:37px">
            有红色*标识的是必填项，“HSF分组”是hsf接口的group，“版本号”是hsf分组的version，“类名称”是类的全名包括包名和类名，例如如下类：
        </p>
        <p style="text-align:center">
            <img width="692" height="251" src="../../images/website/docs/5-1-1.png"/>
        </p>
        <p style="text-indent:37px">
            “能力名称”和“能力描述”建议ISP在录入时尽量能描述清楚该能力的功能，否则在审核时可能无法通过。
        </p>
        <p style="text-indent:37px">
            基本信息中的“类名称”就必须填写“sunbox.gateway.api.service.order.OrderSupportApiService”，建议类名称直接复制代码的包名、类名，防止写错。
        </p>
        <p style="text-indent:37px">
            “方法名称”填写方法名，如上面的stakeOrderConsumeList。
        </p>
        <p style="text-indent:37px">
            “发布方式”填写该方法的发布方式，暂时只支持HTTP/HTTPS，其他发布方式正在开发中。
        </p>
        <p class="MsoListParagraph" style="margin-left:28px">
            2)<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp;&nbsp;&nbsp; </span>ISV被动接收能力
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            目前ISV被动接收的能力录入与ISV主动调用能力的录入方式相同，“发布方式”选择“HSF”。
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “HSF分组”填写：openplatformHSF
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “版本号”填写：1.0.0
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “类名称”填写：com.kd.openplatform.OpenplatformAPI
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “方法名称”填写：getOpenplatformData
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “发布方式”选择：HSF
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            点击“下一步”设置“请求参数”。
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “参数名称”填写：json
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “是否必填”选择：是
        </p>
        <p class="MsoListParagraph" style="text-indent:37px">
            “数据类型”选择：string
        </p>
        <h3>
            5.2录入参数信息
        </h3>
        <p style="text-indent:37px">
            点击“下一步”录入能力的“参数信息”设置。
        </p>
        <p style="text-indent:37px">
            依次点击“请求参数”、“添加”可设置参数。
        </p>
        <p style="text-indent:37px">
            “数据类型”是能力开放平台所支持的数据类型，基本数据类型例如int，float，boolean，double等以及他们对应的Integer，Float，Boolean，Double等可选择int，float，boolean，double。Date类型数据选择date。整形数组或字符串数组选择list[int]或list[string]。用户自定义对象选择object，如果选择object，需点击该参数前边“+”，为该对象添加属性，如下图所示：
        </p>
        <p>
            <img width="691" height="358" src="../../images/website/docs/5-2.png" alt="1546914358(1)"/>
        </p>
        <p style="text-indent:37px">
            “请求头参数”如果没有可不录入。
        </p>
        <p style="text-indent:37px">
            “响应参数”为方法返回的结果，ISP需尽可能详细的录入响应参数中的信息，方便ISV的调用。
        </p>
        <p style="text-indent:37px">
            建议在录入参数时只录入必填项，可减少录入的工作量，也可减少ISV给参数赋值的工作量。录入参数名时建议直接从代码中复制，以防止输入错误。
        </p>
        <p style="text-indent:37px">
            ISV主动调用的能力：在录入参数时，请求参数是ISV发起请求时需传入的参数。由于ISV在调用接口时需要给开放平台传appId和authToken，因此，ISP在录入接口的请求参数时，请求参数名不能和appId、authToken重复。
        </p>
        <p style="text-indent:37px">
            ISV被动接收的能力：在录入参数时，请求参数是ISP发起请求时需传入的参数。
        </p>
        <h3>
            5.3关联计费策略
        </h3>
        <p style="text-indent:37px">
            点击“下一步”进行计费模型设置，如下图：
        </p>
        <p style="text-align:center">
            <img width="457" height="258" src="../../images/website/docs/5-3.png"/>
        </p>
        <p style="text-indent:37px">
            为该能力配置计费模型，可多选。当ISV订阅该能力时可选择适合的计费策略。
        </p>
        <h3>
            5.4关联流量控制策略
        </h3>
        <p style="text-indent:37px">
            点击“下一步”进行“流量控制”配置，如下图：
        </p>
        <p style="text-align:center">
            <img width="471" height="224" src="../../images/website/docs/5-4.png"/>
        </p>
        <p style="text-indent:37px">
            流量控制策略为单选。当ISV在该时间段内访问该能力的频率超过限制时将在该时间内被禁止访问，例如最大访问限制是100次/分钟，如果在某一分钟内访问超过100次，则在该分钟内将不允许继续访问，在下一分钟就能正常访问。
        </p>
        <h3>
            5.5关联数据权限控制策略
        </h3>
        <p style="text-indent:37px">
            该功能正在开发中。
        </p>
        <h3>
            5.6发布能力的连通性测试
        </h3>
        <p style="text-indent:37px">
            为验证能力发布是否正确，在测试环境发布能力后，可以进行自测。
        </p>
        <p style="text-indent:37px">
            点击“保存”。此时该能力状态为“暂存”。
        </p>
        <p style="text-indent:37px">
            选中某一能力，点击“测试”。
        </p>
        <p style="text-indent:37px">
            可在如下图所示的界面中对录入的能力进行测试。
        </p>
        <p style="text-align:center">
            <img width="442" height="361" src="../../images/website/docs/5-6.png"/>
        </p>
        <p style="text-indent:37px">
            在“能力测试”页面中的“参数值”输入参数（非必填参数可输入null），点击“测试”。在测试结果中会显示该能力的返回结果。测试结果示例如下图：
        </p>
        <p style="text-align:center">
            <img width="692" height="166" src="../../images/website/docs/5-6-1.png"/>
        </p>
        <p style="text-indent:37px">
            其中status1（右边）是能力开放平台协议转换和能力调用的状态标识，0为成功，非0失败。status2（左边）是能力响应状态标识，0为成功，非0失败。
        </p>
        <p style="text-indent:37px">
            能力平台调用能力成功不一定能力响应成功。如果能力平台调用能力成功但能力响应失败，请自行检查自己的程序。如果测试结果显示“能力平台未找到能力”请检查类名，方法名是否写错，如果测试结果显示“参数不正确”请检查参数名是否拼写错误，参数类型是否错误。详细说明请参看《能力开放平台错误编码》。如果测试结果是“能力平台未找到能力”说明录入有误，可以选中该能力，进行修改。
        </p>
        <p style="text-indent:37px">
            修改后如果测试成功，可提交审核，经审核通过后，可将状态修改为“可见”，“可用”，就能被ISV订阅和调用了。
        </p>
        <p style="text-indent:37px">
            审核通后不可编辑。
        </p>
        <h2>
            6.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>创建业务场景
        </h2>
        <p style="text-indent:37px">
            业务场景是把ISV需要的能力打包，便于订阅。ISV可以选择订阅单个能力，也可以选择订阅一个业务场景。
        </p>
        <p style="text-indent:37px">
            把能完成某个应用的所有或绝大多数能力放在一个场景里，ISV直接订阅这个场景就能满足或基本满足需求。
        </p>
        <p style="text-indent:37px">
            依次点击“能力管理”、“业务场景”、“添加”，弹出如下界面：
        </p>
        <p style="text-align:center">
            <img width="485" height="205" src="../../images/website/docs/6-1.png"/>
        </p>
        <p style="text-indent:37px">
            在“场景添加”、“基本信息”界面中填写“场景名称”。点击“下一步”进行“分配能力”设置，如下图：
        </p>
        <p style="text-align:center">
            <img width="446" height="287" src="../../images/website/docs/6-1-1.png"/>
        </p>
        <p style="text-indent:37px">
            上图右侧的能力列表是已审核通过的能力。点击左侧用户中心、支付中心等可按中心筛选，点击“能力中心”显示所有中心的能力。点击复选框选择能力。点击“下一步”进行“计费模型”设置，在业务场景中，单个能力的计费模型将失效，以业务场景的计费模型为准，如下图：
        </p>
        <p style="text-align:center">
            <img width="460" height="260" src="../../images/website/docs/6-1-2.png"/>
        </p>
        <p style="text-indent:37px">
            为场景选择计费模型，可选择“包年包月”，“按次收费”，“按流量收费”。可多选，将计费模型设置给对应场景，由用户选择适合自己收费方式。点击“保存”完成场景创建。提交审核并审核通过后即可被ISV订阅。
        </p>
        <h2>
            7.发布ISV被动接收能力时的ISP编程实例
        </h2>
        <h3>
            7.1发布能力
        </h3>
        <p style="text-indent:37px">
            依次点击“能力管理”，“能力列表”，在“能力中心”中选择一个中心，点击“录入”。
        </p>
        <p style="text-indent:37px">
            “发布方式”选择：HSF
        </p>
        <p style="text-indent:37px">
            进一步设置计费模型和流量控制。
        </p>
        <p style="text-indent:37px">
            保存并审核后即已发布能力。
        </p>
        <h3>
            7.2获取apiId
        </h3>
        <p style="text-indent:37px">
            选择发布的主动推送的能力，点击“查看”，复制“能力Id”。
        </p>
        <h3>
            7.3导入jar包
        </h3>
        <p style="text-indent:37px">
            将openplatform_hsf_interface-1.0.0.jar复制到webapp /WEB-INF/lib下。
        </p>
        <h3>
            7.4设置pom.xml
        </h3>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;dependency&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;groupId&gt;com.kd&lt;/groupId&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;artifactId&gt;openplatform_hsf_interface&lt;/artifactId&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;version&gt;1.0.0&lt;/version&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;scope&gt;system&lt;/scope&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;systemPath&gt;${project.basedir}/src/main/webapp/WEB-INF/lib/openplatform_hsf_interface-1.0.0.jar&lt;/systemPath&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/dependency&gt;
        </p>
        <h3>
            7.5设置hsf-consumer-beans.xml
        </h3>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;beans xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; xmlns:hsf=&quot;http://www.taobao.com/hsf&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; xmlns=&quot;http://www.springframework.org/schema/beans&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; http://www.taobao.com/hsf
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; http://www.taobao.com/hsf/hsf.xsd&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; default-autowire=&quot;byName&quot;&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;!-- 消费一个能力示例 --&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;hsf:consumer id=&quot;openplatformApi&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; interface=&quot;com.kd.openplatform.OpenplatformAPI&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; version=&quot;1.0.0&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; group=&quot;openplatformHSF&quot;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; clientTimeout=&quot;50000&quot;&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/hsf:consumer&gt;
        </p>
        <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/beans&gt;
        </p>
        <h3>
            7.6设置web.xml
        </h3>
        <p class="img-p">
            <img width="692" height="390" src="../../images/website/docs/6-web.png"/>
        </p>
        <h3>
            7.7编写调用方法
        </h3>
        <pre style="background-color: white;margin:0 28px">
            package com.kd;
            import com.alibaba.fastjson.JSONObject;
            import com.kd.openplatform.OpenplatformAPI;
            import org.springframework.context.ApplicationContext;
            import org.springframework.web.context.support.WebApplicationContextUtils;
            import javax.servlet.ServletContextEvent;
            import javax.servlet.ServletContextListener;

            public class HsfConsumer1 implements ServletContextListener {
                public void contextDestroyed(ServletContextEvent arg0) {
                // TODO Auto-generated method stub
                }
                public void contextInitialized(ServletContextEvent event) {
                    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
                    final OpenplatformAPI server = (OpenplatformAPI)ctx.getBean(&quot;openplatformApi&quot;);
                    final JSONObject json = new JSONObject();
                    json.put(&quot;apiId&quot;, &quot;4028e68a6807bc6d0168212c8dd10606&quot;);
                    //从能力平台上复制下来的能力Id
                    json.put(&quot;opToken&quot;, &quot;xxxxxxxxxxxxxxxxxxxxxxxxxxx&quot;);
                    //先调用能力平台的com.kd.openplatform.OpenplatformAPI的login(String json)方法获取opToken,该接口是hsf接口，入参json数据格式“{“userId”:“xxxx”,“pwd”:“md5加密的密码”}”。login(String json)方法的返回值格式“{“opToken”:“op::xxxxxxxxxxxxxxxxx-xxxxx”}”
                    json.put(&quot;data&quot;, &quot;中台测试数据&quot;);
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                        if (true){
                            try {
                                Thread.sleep(1000);
                                System.err.println(&quot;能力平台返回值：&quot;+server.getOpenplatformData(json.toJSONString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        }
                    });
                    thread.start();
                }
            }
        </pre>
        <p></p>
        <p style="text-indent:37px">
            其中的apiId就是第一步从页面上复制下来的“能力Id”
        </p>
        <h3>
            7.8数据规范
        </h3>
        <p style="text-indent:37px">
            ISP发送的数据必须包含apiId，将业务数据封装入data。ISP数据格式需在响应参数中描述清楚，方便ISV了解数据的含义，能力平台只负责传输。
        </p>
        <p style="text-indent:37px">
            例如：
        </p>
        <p style="text-indent:37px">
            ISP发出的数据：
        </p>
        <p style="text-indent:37px">
            {&quot;apiId&quot;:&quot;4028e68a6807bc6d0168212c8dd10606&quot;,&quot;data&quot;:&quot;中台数据&quot;}。
        </p>
        <p style="text-indent:37px">
            ISV收到的数据：
        </p>
        <p style="text-indent:37px">
            加密后的{中台数据}，即加密后的ISP发出的数据封装在data中的数据。
        </p>
        <p style="text-indent:37px">
            ISV发出的数据：
        </p>
        <p style="text-indent:37px">
            加密后的{调用方返回值}。
        </p>
        <p style="text-indent:37px">
            ISP收到的能力平台返回数据：
        </p>
        <p style="text-indent:37px">
            {&quot;data&quot;:&quot;调用方返回值&quot;,&quot;info&quot;:&quot;调用能力平台能力成功&quot;,&quot;status&quot;:0}
        </p>
        <p>
            &nbsp;
        </p>
        <p>
            &nbsp;
        </p>
        <p>
            <br/>
        </p>
    </article>
</div>
</body>
</html>

