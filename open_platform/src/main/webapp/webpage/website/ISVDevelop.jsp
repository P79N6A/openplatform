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
    </style>
</head>
<body style="background-color:#ffffff;padding-top:0px;">
<div class="api-content" >
    <article>
        <h1 style="text-align: center">
            ISV能力订阅使用说明书
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
            能力开放平台共有信息内网和信息外网2套，账号、能力相互独立。
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
            能力开放平台在测试环境的账号暂时由平台管理员人工分配。
        </p>
        <h4>
            生产环境：
        </h4>
        <p style="text-indent:37px">
            能力开放平台的ISV分为个人ISV和企业ISV，个人ISV采用手机号登录，企业ISV采用企业管理员账号登录。ISV暂不开放注册，需首先有车联网平台的操作员账号，由平台管理员对账号进行授权。
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
        <p style="text-indent:37px">
            登录测试环境前需首先申请开通白名单。
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
            3.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>创建应用
        </h2>
        <p style="text-indent:37px">
            订阅能力前首先需创建应用，即要订阅的能力要在哪个应用中使用。
        </p>
        <p style="text-indent: 37px">
            依次点击“应用管理”，“应用配置”，“录入”，在“应用录入”界面中输入“应用名称”，点击“保存”完成应用录入。
        </p>
        <p style="text-indent: 37px">
            点击“订阅能力”或“订阅场景”可订阅所需的能力或场景。
        </p>
        <h2>
            4.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>订阅能力
        </h2>
        <p style="text-indent:37px">
            当前能力开放平台支持两种能力订阅方式：
        </p>
        <p style="text-indent:37px">
            （1）ISV订阅能力后获取能力的调用接口和使用说明，由ISV调用该接口，获取数据。
        </p>
        <p style="text-indent:37px">
            （2）ISV订阅能力时需提供一个可调用的链接地址，由开放平台主动调用该地址，将数据推送至ISV。当前，该链接地址需线下提供给平台管理员。
        </p>
        <h3>
            4.1订阅单个能力
        </h3>
        <p style="text-indent: 37px">
            在应用列表中点击“订阅能力”，选择需要的能力，并为该能力选择计费方式，点击左侧的用户中心、支付中心等可按中心筛选右侧的能力，点击“能力中心”则显示全部能力。点击保存完成能力选择。
        </p>
        <h3>
            4.2订阅业务场景
        </h3>
        <p style="text-indent: 37px">
            点击“订阅场景”弹出“订阅场景”页面，选择需要的场景及相应的计费方式。点击“保存”可完成场景订购。如果想了解该场景有哪些能力，可点击“查看能力”。
        </p>
        <h2>
            5.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>提交订单
        </h2>
        <p style="text-indent:37px">
            每次订阅均会生成一笔订单，用于计量计费。
        </p>
        <p style="text-indent:37px">
            依次点击“交易管理”、“订单管理”，点击“提交审核”，审核通过后即可使用订阅的场景或能力。
        </p>
        <h2>
            6.<span style="font-variant-numeric: normal;font-variant-east-asian: normal;font-weight: normal;font-stretch: normal;font-size: 9px;line-height: normal;font-family: &#39;Times New Roman&#39;">&nbsp; </span>调用能力
        </h2>
        <h3>
            6.1开通白名单
        </h3>
        <p style="text-indent:37px">
            向平台管理员提供测试服务器和生产服务器的IP地址和端口号，能力开放平台线下开通白名单。
        </p>
        <h3>
            6.2编码实例
        </h3>
        <p style="text-indent:37px">
            此编码实例仅针对于ISV主动调用能力的使用。对于ISV被动接收能力，由开放平台主动调用接口，传入数据。
        </p>
        <p style="text-indent:37px">
            参考编码实例，由ISV调用能力开放平台接口或由能力开放平台调用ISV接口。首先在测试环境测试通过，再申请生产环境白名单，执行上线操作。
        </p>
        <pre style="margin:0 28px;background-color: white">
            public static void test() throws Exception{
                //内网环境是http+ip+port
                //外网环境是https+域名
                //methodPath可以从能力开放平台获取
                String url =&quot;http://ip:port/protocolTrans/openapi/&quot;+methodPath;
                JSONObject model = new JSONObject();
                JSONObject body = new JSONObject();
                //将能力开放平台需要的参数封装入body
                body.put(&quot;appId&quot;,&quot;myAppId&quot;);
                //appId必填
                body.put(&quot;authToken&quot;, encrypt(&quot;myAuthToken&quot;));
                //authToken必填
                //将自己的业务数据封装进body中
                //如果参数是object，先封装进一个json对象中，再把这个json对象封装进body
                model.put(&quot;password&quot;, &quot;myPassword&quot;);
                model.put(&quot;userName&quot;, &quot;myUserName&quot;);
                int[] seq = {1,2,3};
                model.put(&quot;startChargeSeq&quot;, seq );
                //list[int]型
                model.put(&quot;businessLabel&quot;,5 );
                //如果参数是java本身的对象就直接封装入body
                body.put(&quot;stakeNo&quot;, encrypt(&quot;0&quot;) );
                //int型
                body.put(&quot;orderDate&quot;, encrypt(&quot;2018-01-01&quot;));
                //date型数据
                body.put(&quot;model&quot;, encrypt(model.toJSONString()));
                //加密
                Map&lt;String, Object&gt; map= new HashMap&lt;String, Object&gt;();
                map.put(&quot;args&quot;, body);
                //这里必须是args
                String jsonStr =  mapPost(url, map, &quot;utf-8&quot;);
                JSONObject jsonObj = JSONObject.parseObject(jsonStr);
                jsonObj.put(&quot;data&quot;,
                URLDecoder.decode(dencrypt(jsonObj.getString(&quot;data&quot;))));
                //为防止中文乱码
                if(jsonObj.getString(&quot;status&quot;).equals(&quot;0&quot;)) {
                    JSONObject data1 = jsonObj.getJSONObject(&quot;data&quot;);
                    if(data1.getString(&quot;status&quot;).equals(&quot;0&quot;)) {
                        //data2即调用的能力返回的数据
                        JSONObject data2 = data1. getJSONObject(&quot;data&quot;);
                    }
                }
            }
            public static String encrypt(String data) throws Exception {
                String pass_key = &quot;234dfaiga5getrga &quot;;
                String iv_str = &quot;1232abfgetsafjdh &quot;;
                return AESUtil.Encrypt(data, pass_key,iv_str); }
                public static String dencrypt(String data) throws Exception{
                String pass_key = &quot;234dfaiga5getrga &quot;;
                String iv_str = &quot;1232abfgetsafjdh &quot;;
                return AESUtil.Decrypt(data, pass_key, iv_str);
            }
            public static String mapPost(String url, Map&lt;String,Object&gt; map, String encoding){
                CloseableHttpClient httpClient = null;
                HttpPost httpPost = null;
                String result = null;
                try{
                    httpClient = HttpClients.createDefault();
                    httpPost = new HttpPost(url);
                    List&lt;NameValuePair&gt;
                    list = new ArrayList&lt;NameValuePair&gt;();
                    Iterator iterator = map.entrySet().iterator();
                    while(iterator.hasNext()){
                        Map.Entry&lt;String,String&gt; elem = (Map.Entry&lt;String, String&gt;)
                        iterator.next();
                        list.add(new BasicNameValuePair(elem.getKey(),String.valueOf(elem.getValue())));
                    }
                    if(list.size() &gt; 0){
                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,encoding);
                        httpPost.setEntity(entity);
                    }
                    HttpResponse response = httpClient.execute(httpPost);
                    if(response != null){
                        HttpEntity resEntity = response.getEntity();
                    if(resEntity != null){
                        result = EntityUtils.toString(resEntity,encoding);
                    }
                }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            return result;
        }
        </pre>
        <p></p>
        <h3>
            6.3参数说明
        </h3>
        <h4>
            输入参数
        </h4>
        <p style="text-indent:37px">
            appId、methodPath：
        </p>
        <p style="padding:0 37px">
            依次点击“交易管理”，“订单管理”，其中“订单编号”即编程实例中的“appId”。点击“查看场景”或“查看能力”，弹出页面中的“http请求地址”即编程实例中的“methodPath”。
        </p>
        <p style="text-indent:37px">
            <strong>对于ISV主动调用的能力</strong>，ISV发出的数据：标黄的appId、authToken是开放平台需要的参数，其他是ISP接收到的数据。
        </p>
        <p style="text-indent:37px">
            {&quot;<span style="background:yellow;background:yellow">appId</span>&quot;:&quot;<span style="background:yellow;background:yellow">myAppId</span>&quot;,&quot;authToken&quot;:&quot;myAuthToken&quot;,&quot;user&quot;:&quot;myName&quot;,&quot;password&quot;:&quot;myPassword&quot;}
        </p>
        <p style="text-indent:37px">
            <strong>对于ISV被动接收的能力</strong>，ISV收到的数据即是ISP拟传给ISV的加密后的数据，可直接解析。如：
        </p>
        <p style="text-indent:37px">
            ISP发出的数据：apiId是开放平台需要的参数，data是ISP拟传给ISV的数据。
        </p>
        <p style="text-indent:37px">
            {&quot;<span style="background:yellow;background:yellow">apiId</span>&quot;:&quot;4028e68a6807bc6d0168212c8dd10606&quot;,&quot;<span style="background:yellow;background:yellow">data</span>&quot;:&quot;{&quot;user&quot;:&quot;myName&quot;,&quot;password&quot;:&quot;myPassword&quot;}&quot;}。
        </p>
        <p style="text-indent:37px">
            ISV收到的数据：
        </p>
        <p style="text-indent:37px">
            加密后的{&quot;user&quot;:&quot;myName&quot;,&quot;password&quot;:&quot;myPassword&quot;}，即加密后的ISP发出的数据封装在data中的数据。
        </p>
        <h4>
            返回参数
        </h4>
        <p style="text-indent:37px">
            <strong>对于ISV主动调用的能力</strong>，ISV收到的数据：标黄的status、info是开放平台返回的参数，data是ISP接口返回的数据。
        </p>
        <p style="padding:0 37px">
            {&quot;<span style="background:yellow;background:yellow">status</span>&quot;:0,&quot;<span style="background:yellow;background:yellow">data</span>&quot;:&quot;{&quot;result&quot;:&quot;success&quot;,&quot;code&quot;:0,&quot;data&quot;:{&quot;creator&quot;:&quot;系统管理员&quot;,&quot;gender&quot;:0,&quot;creatorId&quot;:1,&quot;mobile&quot;:&quot;18765976668&quot;, &quot;ouCode&quot;:&quot;1000030128&quot;,&quot;userName&quot;:&quot;WB_SGSDFW_SYH&quot;,&quot;ouName&quot;:&quot;国网电动汽车服务（山东）有限公司&quot;,&quot;token&quot;:&quot;o::8239B37DF06B4 27E948A1852F561CE69&quot;,&quot;realName&quot;:&quot;史永辉&quot;,&quot;createTime&quot;: 1540189700000,&quot;sysRoleType&quot;:&quot;4&quot;,&quot;id&quot;:100003654,&quot;state&quot;:1},&quot;message&quot;:&quot;登录成功&quot;,&quot;info&quot;:&quot;登录成功&quot;,&quot;status&quot;:0}&quot;,&quot;<span style="background:yellow;background:yellow">info</span>&quot;:&quot;能力平台调用服务成功&quot;}
        </p>
        <p style="text-indent:37px">
            <strong>对于ISV被动接收的能力</strong>，ISV发出的数据即是ISV拟传给ISP的加密后的数据。如：
        </p>
        <p style="text-indent:37px">
            ISV发出的数据：
        </p>
        <p style="text-indent:37px">
            加密后的{调用方返回值}。
        </p>
        <h3>
            6.4其他说明
        </h3>
        <p style="text-indent:37px">
            订阅能力或场景的账号必须和调用能力接口的账号一致，否则无法调用。
        </p>
        <p style="text-indent:37px">
            调用能力开放平台接口必须传appId，authToken，否则无法调用。
        </p>
        <p style="text-indent:37px">
            将参数封装成json对象，key必须是“args”，能力参数可从能力开放平台查看或参看接口调用文档。
        </p>
        <p style="text-indent:37px">
            秘钥和偏移量由平台管理员线下方式提供。
        </p>
        <p style="text-indent:37px">
            加解密jar包从能力开放平台自行下载。
        </p>
        <p style="text-indent:37px">
            除了appId其余参数均需加密。
        </p>
    </article>
</div>
</body>
</html>

