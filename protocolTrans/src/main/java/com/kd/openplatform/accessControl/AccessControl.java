package com.kd.openplatform.accessControl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.cache.HttpAddrMapping;
import com.kd.openplatform.entity.ApiInvokeLogEntity;
import com.kd.openplatform.entity.UserToken;
import com.kd.openplatform.info.InfoBean;
import com.kd.openplatform.services.OpenPlatformBaseService;
import com.kd.openplatform.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import serviceCom.DataCom;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
@Component
/**
 * @author zhutianpeng
 * 能力平台http、https提供服务统一入口的AOP切点，这个类用来对accessControl进行hsf的通信
 */
public class AccessControl {
    @Autowired
    private DataCom serviceCom;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SaveData saveData;
    //   @Autowired
    // private HttpServletResponse response;
    @Autowired
    private OpenPlatformBaseService openPlatformBaseService;

    private ApiInvokeLogEntity apiInvokeLogEntity;
    private static final ExecutorService service = Executors.newFixedThreadPool(20);
    private static final Log log = LogFactory.getLog(AccessControl.class);

    @Pointcut("execution(public * com.kd.openplatform.action.OpenPlatformBaseController.baseAction(..))")
    public void accessControl() {
        log.info("accessControl");
    }

    @Around("accessControl()")
    public Object processTx(ProceedingJoinPoint jp) throws Throwable {
        log.info("-----进入切面类，执行目标方法之前执行-----");
        try {
            /**
             * 开始执行之前初始化日志对象,保存相关信息
             */
            apiInvokeLogEntity = new ApiInvokeLogEntity();
            request = (HttpServletRequest) jp.getArgs()[1];
            //response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            String uuid = UUID.randomUUID().toString();
            String id = uuid.replace("-", "");
            apiInvokeLogEntity.setId(id);
            //设置调用时间
            apiInvokeLogEntity.setInvokeTime(new Date());
            //设置调用IP
            apiInvokeLogEntity.setInvokeIp(request.getRemoteAddr());
            //设置请求头
            apiInvokeLogEntity.setRequestHeader(getRequstHeader());
            //设置请求方法类型
            apiInvokeLogEntity.setMethodType("http");
        } catch (Exception e) {
            log.error("Exception:" + e);
        }
        log.info("accessControl");
        /* around方法作为目标方法的前后切点，用来对用户进行：
         * 1. 查询费用余额，查询流量余额；
         * 2. 执行目标方法
         * 3. 计费
         *@param ProceedingJoinPoint 切点的信息
         */
        InfoBean bean = new InfoBean();
        boolean isGetToken = false;
        HttpAddrMapping httpAddrMapping = openPlatformBaseService.getHttpAddrMapping();
        /* around方法作为目标方法的前后切点，用来对用户进行：
         * 1. 查询费用余额，查询流量余额；
         * 2. 执行目标方法
         * 3. 计费
         * @param ProceedingJoinPoint 切点的信息
         */
        log.info("执行目标方法之前，查询费用余额...");
//        String httpAddress = (String) jp.getArgs()[0];
//        HttpServletRequest request = (HttpServletRequest) jp.getArgs()[1];
//        JSONObject requestParam = RequestUtils.getParam(request);
//        requestParam.put("status","1");
//        requestParam.put("path",httpAddress);
        String httpAddress = (String) jp.getArgs()[0];
  /* if(httpAddress.contains("_queryToken")) {
        	isGetToken = true;
        }*/
        //监听能获取到token的方法
        String ignoreCheck = PropertyUtil.getProperty("ignoreCheck");
        String[] strArr = null;
        if (ignoreCheck != null && StringUtils.isNotEmpty(ignoreCheck)) {
            strArr = ignoreCheck.split(",");
        }
        if (strArr != null && strArr.length > 0) {
            for (String str : strArr) {
//                log.info("-------str-----------"+str);
                if (httpAddress.equals(str)) {
                    isGetToken = true;
                    break;
                }
            }
        }


        log.info(String.format("===请求路径httpAddress==:%s", httpAddress));
//        Object[] args = jp.getArgs();
        String argsStr = request.getParameter("args");

//        log.info("用户的入参是：" + argsStr);

        JSONObject requestParam = JSONObject.parseObject(argsStr);

        //为了兼容站控和山东公交小工具没有authToken而是只有token，将authToken塞入JSON
        if (requestParam.getString("authToken") == null && requestParam.getString("token") != null) {
            requestParam.put("authToken", requestParam.getString("token"));
            log.info("封装进authToken后的请求参数------------" + requestParam);
        }
        //兼容token设置在请求头消息中
        if (StringUtils.isNotEmpty(request.getHeader("authToken"))) {
            String authToken = request.getHeader("authToken");
            requestParam.put("authToken", authToken);
        }

        String appId = requestParam.get("appId") + "";
        log.info("====appId为====" + appId);
        if (!httpAddrMapping.getAppKeys().containsKey(appId)) {
            Map<String, String> keys = openPlatformBaseService.getKeysByAppId(appId);
            log.info("httpAddrMapping中的keys为:" + keys);
            if (keys != null) {
                httpAddrMapping.getAppKeys().put(appId, keys);
            }
        }
        JSONObject decryptArgs = new JSONObject();

        String pass_key = "";//加密秘钥
        String iv_str = "";//加密偏移量
        if (httpAddrMapping.getAppKeys().containsKey(appId)) {
            pass_key = httpAddrMapping.getAppKeys().get(appId).get("pass_key");//加密秘钥
            iv_str = httpAddrMapping.getAppKeys().get(appId).get("iv_str");//加密偏移量
            log.info(pass_key + "-----" + iv_str);
            //处理args解密
            try {
                decryptArgs.put("appId", appId);
                for (String key : requestParam.keySet()) {
                    if (!key.equals("appId")) {
                        decryptArgs.put(key, AESUtil.Decrypt(requestParam.get(key) + "", pass_key, iv_str));
                    }
                }
                log.info("解密后的请求参数decrypted::::::" + decryptArgs);
            } catch (Exception e) {
                log.error("args decrypt error:", e);
                bean.setStatus(ErrorConstants.ERROR_PARAM_ERROR_CODE);
                bean.setInfo(ErrorConstants.ERROR_PARAM_ERROR_DISP);
                processAfter(decryptArgs, argsStr, null, null);
                return JSONObject.toJSONString(bean);
            }
        } else {
            log.error("keys load error:");
            bean.setStatus(ErrorConstants.ERROR_KEY_ERROR_CODE);
            bean.setInfo(ErrorConstants.ERROR_KEY_ERROR_DISP);
            processAfter(decryptArgs, argsStr, null, null);
            return JSONObject.toJSONString(bean);
        }
        decryptArgs.put("status", "1");
        decryptArgs.put("path", httpAddress);
        log.info(decryptArgs);
//       1. 查询费用余额，查询流量余额
        log.info("---------------进入访问控制模块--------------");
        String chargeQueryResult = "";
        try {
            if (!isGetToken) {
                log.info(JSON.toJSONString(decryptArgs));
                chargeQueryResult = serviceCom.dataCom(JSON.toJSONString(decryptArgs));

            }
            log.info(String.format("---------------访问控制模块返回值是：%s", chargeQueryResult));
        } catch (Exception e) {
            //记录日志
            setLogValue(decryptArgs, argsStr, chargeQueryResult, null);
            log.error("Exception:", e);
        }
        String status = "";
        if (!chargeQueryResult.isEmpty()) {
            status = JSONObject.parseObject(chargeQueryResult).get("status").toString();
        }
//      2. 执行目标的方法
        Object rvt = null;
        if (status != null && status.equals("500")) {
            //费用超支
            if (decryptArgs.get("appId").equals("WebTest")) {
                chargeQueryResult = packingArgs("1", "jsonpCallback", chargeQueryResult);
                //只是用来屏蔽bug
                String testUsert = packingArgs("2", "jsonpCallback", chargeQueryResult);
                log.info(testUsert);
            }
            JSONObject obj = JSONObject.parseObject(chargeQueryResult);
            String inf = obj.get("info").toString();
            bean.setStatus(1);
            bean.setInfo(inf);
            chargeQueryResult = JSONObject.toJSONString(bean);

            log.info("第一次鉴权出错：" + chargeQueryResult);
            processAfter(decryptArgs, argsStr, chargeQueryResult, rvt);
            return chargeQueryResult;
        } else {
            log.info(String.format("执行目标方法...status: %s", status));
            rvt = jp.proceed(jp.getArgs());

        }
        // ServletWebRequest servletWebRequest=new ServletWebRequest(request);
        //response=servletWebRequest.getResponse();
        //log.info("======response:========"+response);
        //更新用户token对应表
        if (isGetToken) {
            JSONObject json = JSONObject.parseObject(rvt.toString());
            log.info("123123123:::::::" + JSONObject.toJSONString(json));
            //json {"data":{"code":0,"data":"o::0ECCE221F0A74003ADCB0240FAD6CFCC","info":"登录成功","message":"登录成功",
            // "result":"success","status":0},"info":"能力平台调用服务成功","status":0}
            if (json.getString("status").equals("0")) {
                JSONObject data = (JSONObject) json.get("data");
                log.info("第1层data=====" + data);
                //data {"result":"success","code":0,"data":"o::3408A5452C0C4098B1928F3A036A5FC2","message":"登录成功","info":"
                // 登录成功","status":0}

//                {"data":{"data":{"gender":0,"id":49325,"mobile":"","modifier":"超级管理员","" +
//                        "modifierId":49325,"modifyTime":1523254731000,"ouCode":"1000030","realName":
//                    "超级管理员","state":1,"token":"o::B8A6A1A399044D17BCA1F2BF7AB77A85","userName":"ddqc_super"
//                },"result":"success","status":0},"info":"能力平台调用服务成功","status":0}
                if (data.getString("status").equals("0")) {
                    JSONObject data2 = (JSONObject) data.get("data");
                    log.info("第2层data=====" + data2);
                    String authToken = data2.getString("token");
                    String userName = data2.getString("userName");
                    log.info(String.format("----------authToken is : %s-----------", authToken));
                    //decryptArgs.put(Constants.tokenName,authToken);
                    try {
                        saveData.saveUserTokenSimple(userName, authToken);
                    } catch (Exception e) {
                        log.info("保存authToken出错" + e);
                    }
                }
            }
        }

//      3. 计费
        log.info("执行目标方法之后，计费...");
        decryptArgs.put("status", "2");
        log.info(String.format("传入访问控制的参数是：%s", decryptArgs));
        String chargingResult = "";
        try {
            if (!isGetToken) {
                //不是获取token的方法的时候计费
                serviceCom.dataCom(JSON.toJSONString(decryptArgs));
            }
        } catch (Exception e) {
            log.info("访问控制出错", e);
        }
        log.info(String.format("访问控制返回的数据是：%s", chargingResult));

        if (!chargingResult.isEmpty()) {
            if (JSONObject.parseObject(chargingResult).get("status").equals(500)) {
                if (decryptArgs.get("appId").equals("WebTest")) {
                    chargingResult = packingArgs("1", "jsonpCallback", chargingResult.toString());
                }
                JSONObject obj = JSONObject.parseObject(chargingResult);
                String inf = obj.get("info").toString();
                bean.setStatus(1);
                bean.setInfo(inf);
                chargingResult = JSONObject.toJSONString(bean);
                log.info("第2次计费出错：" + chargingResult);
                processAfter(decryptArgs, argsStr, chargeQueryResult, rvt);
                return chargingResult;
            }
        }
        //正确返回
        /*if(decryptArgs.get("appId") != null && decryptArgs.get("appId").equals("WebTest")){
            processAfter(decryptArgs,argsStr,chargeQueryResult,rvt);
            return packingArgs("1","jsonpCallback",rvt.toString());
        }else{*/
        log.info(String.format("-------网关返回值%s", rvt));
        processAfter(decryptArgs, argsStr, chargeQueryResult, rvt);
        //String decode = URLEncoder.encode(rvt.toString(),"utf-8");
        JSONObject json = JSONObject.parseObject(rvt.toString());
        log.info("-------转码result:::::::" + json);
        json.put("data", AESUtil.Encrypt(URLEncoder.encode(JSONObject.toJSONString(json.get("data")), "utf-8"), pass_key, iv_str));
        //log.info("-------加密result:::::::" + json);
        return json.toJSONString();
        /*}*/
    }

    /**
     * 此程序正常运行结束,保存日志
     */
    public void processAfter(JSONObject requestParam, String argsStr, String chargeQueryResult, Object rvt) {
        setLogValue(requestParam, argsStr, chargeQueryResult, rvt);
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        excutSql();
                    }
                }
            };
            service.submit(runnable);
        } catch (Exception e) {
            log.error("Exception:" + e);
        }
    }

    /**
     * 抛异常时接口调用结果设置为-1，保存日志
     */
    @AfterThrowing("accessControl()")
    public void processAfterThrowing() {
        try {
            log.info("============运行程序异常");
            //设置响应时长
            apiInvokeLogEntity.setInvokeOpenplatFormResult(-1);
            apiInvokeLogEntity.setInvokeServiceProviderResult(-1);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        excutSql();
                    }
                }
            };
            service.submit(runnable);
        } catch (Exception e) {
            log.error("Exception:" + e);
        }
    }

    public String packingArgs(String testUser, String callBackName, String args) {
        switch (testUser) {
            case "1":
                args = callBackName + "(" + args + ")";
                break;
            case "2":
                args = callBackName;
                break;
            default:
                log.info(String.format("-------网关返回值%s", testUser));
                break;
        }
//        if(testUser.equals("1")){
//            args= callBackName +"("+args+")";
//        }
        log.info(String.format("-------回调函数值%s", args));
        return args;
    }

    /*
     * 为日志设置参数
     * */
    private void setLogValue(JSONObject requestParam, String argsStr, String chargeQueryResult, Object rvt) {
        try {
            log.info("为日志设置参数");
            log.info("requestParam====" + requestParam);
//            log.info("argsStr===="+argsStr);
            log.info("chargeQueryResult====" + chargeQueryResult);
            //log.info("rvt===="+rvt);
            log.info("request====" + request);
            //设置响应头
            //apiInvokeLogEntity.setReturnHeader(getResponsHeader());
            //设置请求方法类型
            apiInvokeLogEntity.setMethodType("http");
            if (requestParam != null && !requestParam.toString().isEmpty()) {
                //设置调用路径
                apiInvokeLogEntity.setInvokeUrl(requestParam.getString("path"));
            }
            if (chargeQueryResult != null && !chargeQueryResult.isEmpty()) {
                JSONObject chargeQueryResultrJson = JSONObject.parseObject(chargeQueryResult);
                if (chargeQueryResultrJson != null) {
                    JSONObject data = chargeQueryResultrJson.getJSONObject("data");
                    if (data != null) {
                        apiInvokeLogEntity.setSceneId(data.getString("sceneId"));
                    }
//                    apiInvokeLogEntity.setApiId(data.getString("apiId"));
                }
            }
            //设置请求参数
            apiInvokeLogEntity.setRequestParam(argsStr);
            //设置appId
            if (requestParam.get("appId") != null && !(requestParam.get("appId").toString().isEmpty())) {
                apiInvokeLogEntity.setAppId(requestParam.getString("appId"));
            }
            log.info("apiId===========" + request.getAttribute("apiId"));
            if (request.getAttribute("apiId") != null) {
                apiInvokeLogEntity.setApiId((String) request.getAttribute("apiId"));
            }
            //设置应用名称
//            if (requestParam.get("appName") != null && !requestParam.get("appName").toString().isEmpty()) {
//                apiInvokeLogEntity.setAppName(String.valueOf(requestParam.get("appName")));
//            }
            //设置场景Id
//            if (requestParam.get("sceneId") != null && !requestParam.get("sceneId").toString().isEmpty()) {
//                apiInvokeLogEntity.setSceneId(String.valueOf(requestParam.get("sceneId")));
//            }
            //设置场景名称
//            if (requestParam.get("sceneName") != null && !requestParam.get("sceneName").toString().isEmpty()) {
//                apiInvokeLogEntity.setSceneName(String.valueOf(requestParam.get("sceneName")));
//            }
            //设置请求流量
            if (argsStr != null && !argsStr.isEmpty()) {
                double requestFlowSize = requestParam.toJSONString().getBytes().length / 1024d;
                apiInvokeLogEntity.setRequestFlowSize(requestFlowSize);
            }
            if (rvt != null) {
                //设置响应流量
                double responseFlowSize = rvt.toString().getBytes().length / 1024d;
                apiInvokeLogEntity.setResponseFlowSize(responseFlowSize);
                //设置响应参数
                apiInvokeLogEntity.setReturnParam(rvt.toString());
                //设置接口调用结果,解析rvt
                JSONObject rvtJson = JSONObject.parseObject(rvt.toString());
                log.info("-------------" + rvtJson);
                //解析调用能力开发平台状态,设置结果
                if (rvtJson.get("status") != null) {
                    Integer invokeOpenplatFormResult = Integer.parseInt(rvtJson.get("status").toString());
                    apiInvokeLogEntity.setInvokeOpenplatFormResult(invokeOpenplatFormResult);
                }
                //解析服务提供者调用结果
                if (rvtJson.get("data") != null) {
                    String rvtData = rvtJson.get("data").toString();
                    JSONObject rvtDataJson = JSONObject.parseObject(rvtData);
                    if (rvtDataJson.get("status") != null) {
                        Integer invokeServiceProviderResult = Integer.parseInt(rvtDataJson.get("status").toString());
                        apiInvokeLogEntity.setInvokeServiceProviderResult(invokeServiceProviderResult);
                    }
                }
            } else if (chargeQueryResult != null) {
                double responseFlowSize = chargeQueryResult.toString().getBytes().length / 1024d;
                apiInvokeLogEntity.setResponseFlowSize(responseFlowSize);
                //设置响应参数
                apiInvokeLogEntity.setReturnParam(chargeQueryResult.toString());
                //设置接口调用结果,鉴权未通过
                apiInvokeLogEntity.setInvokeServiceProviderResult(-1);
                apiInvokeLogEntity.setInvokeOpenplatFormResult(-1);

            }
            //设置响应时长
            apiInvokeLogEntity.setResponseTimeLength((double) (new Date().getTime() - apiInvokeLogEntity.getInvokeTime().getTime()));
            log.info("日志参数设置成功");
        } catch (Exception e) {
            log.error("Exception:" + e);
        }
    }

    /**
     * 保存日志,执行sql
     *
     * @return
     */
    private void excutSql() {
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

    /**
     * 获取请求头
     *
     * @return
     */
    private String getRequstHeader() {
        Enumeration<String> enumeration = request.getHeaderNames();
        StringBuffer headers = new StringBuffer();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            headers.append(name + ":" + value).append(",");
        }
        return headers.toString();
    }
    /**
     * 获取响应头
     * @return
     */
//    private String getResponsHeader() {
//        Collection<String> headerName = response.getHeaderNames();
//        StringBuffer headers = new StringBuffer();
//        for(String name : headerName) {
//
//            String value = response.getHeader(name);
//            headers.append(name + ":" + value).append(",");
//        }
//        return headers.toString();
//    }

    /**
     * 保存userToken
     *
     * @param requestParam
     * @param authToken
     */
    public void saveUserToken(JSONObject requestParam, String authToken) {
        try {
            log.info("保存authToken--------requestParam--------:" + requestParam);
            UserToken userToken = new UserToken();
            Object paramName = request.getAttribute("paramName");
            if (paramName != null) {
                log.info("================");
                String paramNameStr = (String) paramName;
                log.info("-------paramNameStr-------" + paramNameStr);
                Object model = requestParam.get(paramNameStr);
                if (model != null) {
                    String modelStr = String.valueOf(model);
                    log.info("-------modelStr-------" + modelStr);
                    if (JSONObject.parseObject(modelStr).get("userName") != null) {
                        String userName = JSONObject.parseObject(modelStr).getString("userName");
                        if (userName == null) {
                            userName = JSONObject.parseObject(modelStr).getString("var2");
                        }
                        log.info("---------userName-----------------" + userName);
                        userToken.setUserId(String.valueOf(userName));
                    }
                }
            }
            log.info("token---------" + authToken);
            userToken.setToken(authToken);
            if (userToken.getUserId() != null && !userToken.getUserId().isEmpty()) {
                String redisToken = (String) redisTemplate.boundHashOps(Constants.REDIS_USERTOKEN).get(userToken.getUserId());
                if (redisToken != null && !redisToken.isEmpty()) {
                    redisTemplate.boundHashOps(Constants.REDIS_TOKENUSER).delete(redisToken);
                }
            }
            redisTemplate.boundHashOps(Constants.REDIS_USERTOKEN).put(userToken.getUserId(), userToken.getToken());
            redisTemplate.boundHashOps(Constants.REDIS_TOKENUSER).put(userToken.getToken(), userToken.getUserId());
            log.info("------token save success-------");
        } catch (DataAccessException e) {
            log.info("token save err", e);
        }
    }


}



