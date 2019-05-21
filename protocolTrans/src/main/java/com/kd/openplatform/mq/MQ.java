package com.kd.openplatform.mq;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.*;
import com.kd.openplatform.activeAPI.OpenplatformAPI;
//import com.kd.openplatform.activeAPI.OpenplatformMqApi;
import com.kd.openplatform.entity.ApiInvokeLogEntity;
import com.kd.openplatform.info.InfoBean;
import com.kd.openplatform.services.OpenPlatformBaseService;
import com.kd.openplatform.util.Constants;
import com.kd.openplatform.util.ErrorConstants;
import com.kd.openplatform.util.XmlUtil;
import com.kd.openplatform.ws.Http;
import com.kd.openplatform.ws.client.WSClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import serviceCom.DataCom;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MQ{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private WSClient wsClient;
    @Autowired
    private OpenPlatformBaseService openPlatformBaseService;
    @Autowired
    private OpenplatformAPI openplatformAPI;
    @Autowired
    private Http http;

    //牛延军
    //这里可以模仿AccessControl里的写法，注入和一个accessControl的对象，爸返回值的资源控制放到里边
    @Autowired
    private DataCom serviceCom;
    private ApiInvokeLogEntity apiInvokeLogEntity;

    private static final ExecutorService service = Executors.newFixedThreadPool(20);

    private static final Log log = LogFactory.getLog(MQ.class);

    Consumer consumer = null;
    //牛延军
    //static String ReceiveTopic = null;
    static String SendTopic = null;
    static String ConsumerId = null;
    static String ProducerId = null;
    /*static String ReceiveAccessKey = null;
    static String SendAccessKey = null;
    static String ReceiveSecretKey = null;
    static String SendSecretKey = null;
    static String ReceiveONSAddr = null;
    static String SendONSAddr = null;*/
    static String AccessKey = null;
    static String SecretKey = null;
    static String ONSAddr = null;


    static {
        InputStream in = null;
        try {
            Properties prop = new Properties();
            in = MQ.class.getClassLoader().getResourceAsStream("mq.properties");
            if (in != null) {
                prop.load(in);
            }

            //牛延军
            //ReceiveTopic = prop.getProperty("RECEIVE_TOPIC");
            SendTopic = prop.getProperty("SEND_TOPIC");
            ConsumerId = prop.getProperty("ConsumerId");
            ProducerId = prop.getProperty("ProducerId");
            /*ReceiveAccessKey = prop.getProperty("RECEIVE_AccessKey");
            SendAccessKey = prop.getProperty("SEND_AccessKey");
            ReceiveSecretKey = prop.getProperty("RECEIVE_SecretKey");
            SendSecretKey = prop.getProperty("SEND_SecretKey");
            ReceiveONSAddr = prop.getProperty("RECEIVE_ONSAddr");
            SendONSAddr = prop.getProperty("SEND_ONSAddr");*/
            AccessKey = prop.getProperty("AccessKey");
            SecretKey = prop.getProperty("SecretKey");
            ONSAddr = prop.getProperty("ONSAddr");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @return void
     * @Author zyz
     * @Description 接收消息
     * @Date 2019/3/17
     * @Param []
     */
    public void receive() {
        //牛延军
        //清楚老的监听
        //当数据库中新录入topic的时候触发这个方法,清除掉老的监听，创建新的监听
        //清除掉老的监听
        clearOldListener();

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, ConsumerId);
        properties.put(PropertyKeyConst.AccessKey, AccessKey);
        properties.put(PropertyKeyConst.SecretKey, SecretKey);
        properties.put(PropertyKeyConst.ONSAddr, ONSAddr);// 此处以公有云生产环境为例
        //牛延军
        //从redis里读取mq发送者的topic
        //如果redis里没有就从数据库里读取并保存到redis里
        List<String> topicList = getTopicFromRedis();//从redis里读取的topic

        log.info("==========----------ConsumerId:"+ConsumerId+"------------AccessKey:"+AccessKey+"------------:SecretKey"+SecretKey+"----------ONSAddr:"+ONSAddr);
        consumer = ONSFactory.createConsumer(properties);

        //创建新的监听
        for (String topic : topicList) {
            log.info("============MQ监听启动==============topic:" + topic);
            log.info("监听的mq topic ==========" + topic);
            consumer.subscribe(topic, "*", new MessageListener() {
                public Action consume(Message message, ConsumeContext context) {
                    log.info("=========开放平台收到MQ消息===========" + "topic---------" + topic);
                    apiInvokeLogEntity = new ApiInvokeLogEntity();
                    apiInvokeLogEntity.setInvokeTime(new Date());
                    String tag = null;
                    String requestParamMq = null;
                    tag = message.getTag();
                    log.info("收到的messageId是----------------------------------------" + message.getMsgID());
                    requestParamMq = new String(message.getBody());
                    log.info("topic---------" + topic + "--------tag----------" + tag);
                    //登录 平台认证
                    //牛延军
                    //因为能力开放平台的mq的topic是写死的，要考虑mq发送者们不同的topic的tag相同的情况
                    //所以建议能力开放推出去的tag再拼上一个topic名
                    if (StringUtils.isNotEmpty(tag) && tag.equals("login_tag")) {
                        String loginRtn = openplatformAPI.login(requestParamMq);
                        //牛延军
                        //因为能力开放平台的mq的topic是写死的，要考虑mq发送者们不同的topic的tag相同的情况
                        //所以建议能力开放推出去的tag再拼上一个topic名
                        //sendMsg("login_tag_return", loginRtn);
                        sendMsg(topic, "login_tag_return", loginRtn);
                        apiInvokeLogEntity.setResponseTimeLength((double) (new Date().getTime() - apiInvokeLogEntity.getInvokeTime().getTime()));
                        saveInvokeLog(requestParamMq, loginRtn);
                        log.info("---------登录接口调用结束------------");
                    } else {
                        log.info("请求参数----------" + requestParamMq);
                        //牛延军
                        //consumeRun(tag, requestParamMq)这个现在没有问题 但是如果有了几个topic有同一个tag的话就有问题了
                        consumeRun(topic, tag, requestParamMq);
                        apiInvokeLogEntity.setResponseTimeLength((double) (new Date().getTime() - apiInvokeLogEntity.getInvokeTime().getTime()));
                        log.info("---------接口调用结束------------");
                    }
                    return Action.CommitMessage;
                }
            });
            consumer.start();
            log.info("Consumer Started");
        }
    }

    /*
     * @Author zyz
     * @Description 根据tag查询webservic信息，调用webservice
     * @Date  2019/3/17
     * @Param [tag, result]
     * @return  java.lang.String
     * e车购mq消息{
     *              "data":
     *                  {"xmlStr":
     *                      "<ORDER>
     *                          <SERVICECODE>0207116</SERVICECODE>
     *                          <source>03</source>
     *                          <target>37101</target>
     *                          <schargerFlag>0</schargerFlag>
     *                          <data>
     *                              <busiType>101</busiType>
     *                              <preAppNo>031012019042928256</preAppNo>
     *                              <channelType>03</channelType>
     *                          </data>
     *                      </ORDER>"
     *                  },
     *              "appId":
     *                  "abcdefghijklmnopq"
     *            }
     * requestParamMq是json字符串
     */
    public String consumeRun(String topic, String tag, String requestParamMq) {
        //牛延军
        //就这次上线来说不要topic也可以用，因为只有一个用户发mq
        //如果将来控制的好的话保证各家的tag不重复也能凑可一段时间，这次可以想不改
        //login_tag重复暂时没问题，因为推出去的时候tag前加了topic
        log.info("i have received:" + tag + "----" + requestParamMq);
        InfoBean infoBean = new InfoBean();
        JSONObject requestParamObj = null;
        boolean needMatch = false;
        String requestId = null;
        try {
            requestParamObj = JSONObject.parseObject(requestParamMq);
            log.info("用户传过来的参数解析为jsonObject为：" + requestParamObj);
        } catch (Exception e) {
            infoBean.setStatus(ErrorConstants.ERROR_PARSE_CODE);
            infoBean.setInfo(ErrorConstants.ERROR_PARSE_DISP);
            log.info("Exeception:" + e);
            sendMsg(topic, tag, JSONObject.toJSONString(infoBean));
            saveInvokeLog(requestParamMq, JSONObject.toJSONString(infoBean));
            return null;
        }
        String requestParam;
        JSONObject data = null;
        requestId = requestParamObj.getString("requestId");
        if(requestId != null){
            needMatch = true;
        }
        if (requestParamObj.get("data") == null) {
            data = requestParamObj;
        } else if (requestParamObj.get("data") != null) {
            //requestParam = requestParamObj.getString("data");
            data = requestParamObj.getJSONObject("data");
//            try {
//                String sql="select app_id from api_app_rela where api_id=?";
//                appId = requestParamObj.getString("appId");
//            } catch (Exception e) {
//                infoBean.setStatus(ErrorConstants.ERROR_GET_APPID_CODE);
//                infoBean.setInfo(ErrorConstants.ERRORT_GET_APPID_DISP);
//                return JSONObject.toJSONString(infoBean);
//            }
            //牛延军
            //从数据库中查出的ISV的入参，该入参其实是ISP和ISV沟通问清楚ISV需要啥，由ISP录入的
            //这里的xmlStr是山东营销的入参的参数名，以后这个应该从数据库里读出来
//          requestParam = data.getString("xmlStr");

        }/* else {
            infoBean.setStatus(ErrorConstants.ERROR_PARSE_CODE);
            infoBean.setInfo(ErrorConstants.ERROR_PARSE_DISP);
            return JSONObject.toJSONString(infoBean);
        }*/
        //用tag从数据库中查找apiId
        requestParam = data.toJSONString();
        log.info("解析到的接口参数为：" + requestParam);
        String returnResult = null;
        //牛延军
        //第一不建议把这个放到api_order_detail表中，第二加上topic防止tag重复
        String sql1 = "select id apiId from api_info where api_class_name=? and api_method_name=?";
        List<Map<String, Object>> apiIdList = jdbcTemplate.queryForList(sql1, new Object[]{topic, tag});
        returnResult = null;
        if (apiIdList != null && apiIdList.size() > 0) {
            for (Map<String, Object> map : apiIdList) {
                String apiId = (String) map.get("apiId");
                apiInvokeLogEntity.setApiId(apiId);
                log.info("apiId===" + apiId);
                //根据apiId去redis中查webservice相关数据
                Object receiveInfoObj = redisTemplate.boundHashOps(Constants.REDIS_RECEIVEINFO).get(apiId);
                if (receiveInfoObj != null && StringUtils.isNotEmpty((String) receiveInfoObj)) {
                    String receiveInfo = (String) receiveInfoObj;
                    //调用webService接口
                    log.info("从redis取到receive信息：receiveInfo=" + receiveInfo);
                    try {
                        JSONArray arr = (JSONArray) JSONArray.parse(receiveInfo);
                        for (Object o : arr) {
                            JSONObject recObj = (JSONObject) o;
                            //资源控制
                            log.info("appId::::::" + recObj.get("appId"));
                            if (recObj != null && recObj.get("appId") != null) {
//                                requestParam = resourceControl(recObj.getString("appId"), apiId, requestParam);
                            }
                            if (recObj != null && recObj.get("receiveType") != null) {
                                if (recObj.getString("receiveType").equals("webService")) {
                                    //调用 webservice
                                    returnResult = wsClient.post(apiId, recObj, requestParam);
                                    apiInvokeLogEntity.setInvokeOpenplatFormResult(0);
                                    if(needMatch){
                                        JSONObject jo = new JSONObject();
                                        jo.put("responseId",requestId);
                                        jo.put("data", XmlUtil.xml2json(returnResult));
                                        returnResult = jo.toJSONString();
                                    }
                                    sendMsg(topic, tag, returnResult);
                                }
                                if (recObj.getString("receiveType").equals("path")) {
                                    // 调用 http
                                    returnResult = http.post(apiId, recObj, requestParam);
                                    apiInvokeLogEntity.setInvokeOpenplatFormResult(0);
                                    sendMsg(topic, tag, returnResult);
                                }
                                if (recObj.getString("receiveType").equals("MQ")) {
                                    //todo 后期有需求完成开发
                                }
                            }
                        }
                        return null;
                    } catch (RuntimeException e) {
                        infoBean.setStatus(ErrorConstants.ERROR_INVOCAION_SDYY_CODE);
                        infoBean.setInfo(ErrorConstants.ERRORT_INVOCAION_SDYY_DISP);
                        log.info("Exeception:" + e);
                        sendMsg(topic, tag, JSONObject.toJSONString(infoBean));
                        return null;
                    }
                } else {
                    //如果redis中没有webservice相关数据，去数据库查找
                    JSONObject receiveObj = null;
                    JSONArray receiveArr = new JSONArray();
                    String sql = "select app_id appId,param_name paramName,param_value paramValue from api_isv_param where api_id=?";
                    List<Map<String, Object>> apiInfoList = jdbcTemplate.queryForList(sql, new Object[]{apiId});
                    for (Map<String, Object> m : apiInfoList) {
                        receiveObj = new JSONObject();
                        String paramName = (String) m.get("paramName");
                        String paramValue = (String) m.get("paramValue");
                        String appId = (String) m.get("appId");
                        //资源控制
//                        requestParam = resourceControl(appId, apiId, requestParam);
                        if (paramName != null && paramName.equals("path")) {
                            receiveObj.put("path", paramValue);
                        } else {
                            receiveObj = JSONObject.parseObject(paramValue);
                        }
                        receiveObj.put("appId", appId);
                        log.info("appId::::::" + appId);
                        receiveObj.put("receiveType", paramName);
                        receiveArr.add(receiveObj);
                        //调用webService接口
                        log.info("从数据库查到的接收信息：wsObj=" + receiveObj);
                        try {
                            if (paramName != null && paramName.equals("webService")) {
                                //调用 webService
                                returnResult = wsClient.post(apiId, receiveObj, requestParam);
                                apiInvokeLogEntity.setInvokeOpenplatFormResult(0);
                                if(needMatch){
                                    JSONObject jo = new JSONObject();
                                    jo.put("responseId",requestId);
                                    jo.put("data", XmlUtil.xml2json(returnResult));
                                    returnResult = jo.toJSONString();
                                }
                                sendMsg(topic, tag, returnResult);
                            } else if (paramName != null && paramName.equals("path")) {
                                // 调用 http
                                returnResult = http.post(apiId, receiveObj, requestParam);
                                apiInvokeLogEntity.setInvokeOpenplatFormResult(0);
                                sendMsg(topic, tag, returnResult);
                            } else if (paramName != null && paramName.equals("MQ")) {
                                //todo 后期有需求完成开发
                            }
                        } catch (RuntimeException e) {
                            infoBean.setStatus(ErrorConstants.ERROR_INVOCAION_SDYY_CODE);
                            infoBean.setInfo(ErrorConstants.ERRORT_INVOCAION_SDYY_DISP);
                            log.info("Exeception:" + e);
                            sendMsg(topic, tag, JSONObject.toJSONString(infoBean));
                            return null;
                        }
                    }
                    //将查到的数据保存redis
                    log.info("存入redis的数据为：" + receiveArr.toJSONString());
                    redisTemplate.boundHashOps(Constants.REDIS_RECEIVEINFO).put(apiId, receiveArr.toJSONString());
                }
            }
            return null;
        } else {
            infoBean.setInfo(ErrorConstants.ERROR_CANNOT_FIND_SERVICE_DISP);
            infoBean.setStatus(ErrorConstants.ERROR_CANNOT_FIND_SERVICE_CODE);
            sendMsg(topic, tag, JSONObject.toJSONString(infoBean));
            return null;
        }
    }

    /**
     * @return void
     * @Author zyz
     * @Description 发送消息
     * @Date 2019/3/17
     * @Param [info]
     */
    private void sendMsg(String topic, String requestTag, String returnParam) {
        //牛延军
        log.info("----------MQ发送消息方法执行------------topic:" + topic + "------requestTag:" + requestTag + "_return");
        String returnTag = topic + "_" + requestTag + "_return";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, ProducerId);
        /*properties.put(PropertyKeyConst.AccessKey, SendAccessKey);
        properties.put(PropertyKeyConst.SecretKey, SendSecretKey);
        properties.put(PropertyKeyConst.ONSAddr, SendONSAddr);// 此处以公有云生产环境为例*/
        properties.put(PropertyKeyConst.AccessKey, AccessKey);
        properties.put(PropertyKeyConst.SecretKey, SecretKey);
        properties.put(PropertyKeyConst.ONSAddr, ONSAddr);// 此处以公有云生产环境为例
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();
        // 发送消息
        log.info("返回给调用者的值：" + returnParam + "------------topic:" + topic + "------requestTag:" + requestTag + "_return");
        Message msg = new Message(SendTopic, returnTag, returnParam.getBytes());
        SendResult sendResult = producer.send(msg);
        log.info("-------- send returnMq ----------- " + sendResult);
        // 在应用退出前，销毁Producer对象
        // 注意：如果不销毁也没有问题
        producer.shutdown();
    }

    //开启多线程保存日志
    public void saveInvokeLog(String requestParamMq, String result) {
        String uuid = UUID.randomUUID().toString();
        String id = uuid.replace("-", "");
        apiInvokeLogEntity.setId(id);
        apiInvokeLogEntity.setMethodType("MQ");
        apiInvokeLogEntity.setRequestParam(requestParamMq);
        apiInvokeLogEntity.setRequestFlowSize(requestParamMq.getBytes().length / 1024d);
        apiInvokeLogEntity.setReturnParam(result);
        apiInvokeLogEntity.setResponseFlowSize(result.getBytes().length / 1024d);
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        String sql = "INSERT INTO api_invoke_log(id,scene_id,scene_name,api_id,api_name,invoke_time," +
                                "invoke_ip,method_type,invoke_url,response_time_length,response_flow_size," +
                                "request_flow_size,request_header,request_param,return_param,return_header," +
                                "invoke_openplatForm_result,app_id,app_name,invoke_serviceProvider_result)" +
                                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        jdbcTemplate.update(sql, apiInvokeLogEntity.getId(),
                                apiInvokeLogEntity.getSceneId(),
                                apiInvokeLogEntity.getSceneName(),
                                apiInvokeLogEntity.getApiId(),
                                apiInvokeLogEntity.getApiName(),
                                apiInvokeLogEntity.getInvokeTime(),
                                apiInvokeLogEntity.getInvokeIp(),
                                apiInvokeLogEntity.getMethodType(),
                                apiInvokeLogEntity.getInvokeUrl(),
                                apiInvokeLogEntity.getResponseTimeLength(),
                                apiInvokeLogEntity.getResponseFlowSize(),
                                apiInvokeLogEntity.getRequestFlowSize(),
                                apiInvokeLogEntity.getRequestHeader(),
                                apiInvokeLogEntity.getRequestParam(),
                                apiInvokeLogEntity.getReturnParam(),
                                apiInvokeLogEntity.getReturnHeader(),
                                apiInvokeLogEntity.getInvokeOpenplatFormResult(),
                                apiInvokeLogEntity.getAppId(),
                                apiInvokeLogEntity.getAppName(),
                                apiInvokeLogEntity.getInvokeServiceProviderResult());
                        log.info("==日志保存成功==");
                    }
                }
            };
            service.submit(runnable);
        } catch (Exception e) {
            log.error("Exception:" + e);
        }
    }

    //从redis里读取mq发送者的topic
    //从redis里读取的topic
    //如果redis里没有就从数据库里读取并保存到redis里
    private List<String> getTopicFromRedis() {
        List<String> topicList = new ArrayList<>();
        //从redis里读取mq发送者的topic
        List<String> listFromRedis = (List<String>) redisTemplate.opsForList().leftPop(Constants.REDIS_TOPIC);
        //如果redis里没有就从数据库里读取并保存到redis里
        if (listFromRedis == null || listFromRedis.size() <= 0) {
            String sql = "select distinct api_class_name topic from api_info where pub_mode=4";
            List<Map<String, Object>> topics = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> topicMap : topics) {
                if (topicMap != null && topicMap.get("topic") != null) {
                    String topic = (String) topicMap.get("topic");
                    topicList.add(topic);
                }
            }
            redisTemplate.opsForList().leftPushAll(Constants.REDIS_TOPIC, topicList);
        } else {
            topicList.addAll(listFromRedis);
        }
        return topicList;
    }

    //清除老的监听
    private void clearOldListener() {
        if (consumer != null) {
            consumer.shutdown();
            consumer = null;
        }
    }

    //这一段放到accessControl中
    private String resourceControl(String appId, String apiId, String msg) {
        //牛延军
        //按appId找到资源控制的字段和参数类型
        JSONObject json = new JSONObject();
        json.put("appId", appId);
        json.put("apiId", apiId);
        json.put("data", msg);
        msg = serviceCom.sourceControl(json.toJSONString());
        return msg;
    }

    public void deleteRedis() {
        log.info("-----------项目重启，清空redis中webservis相关数据-----------");
        redisTemplate.delete(Constants.REDIS_RECEIVEINFO);
        redisTemplate.delete(Constants.REDIS_WSINFO);
        redisTemplate.delete(Constants.REDIS_TOPIC);
    }
}
