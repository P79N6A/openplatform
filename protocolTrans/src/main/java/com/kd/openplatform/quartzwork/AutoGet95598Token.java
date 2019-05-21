package com.kd.openplatform.quartzwork;

import com.kd.openplatform.util.AESUtil;
import com.kd.openplatform.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestClientException;
import sunbox.gateway.api.model.system.AccessTokenModel;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;

public class AutoGet95598Token {
    private static final Log log =  LogFactory.getLog(AutoGet95598Token.class);
    String url = Constants.url95598;
    String appCode = Constants.appCode95598;
    String appSecret = Constants.appSecret95598;
    String tjAccount = Constants.tjAccount;
    String tjPasswd = Constants.tjPasswd;
    String tjTokenUrl = Constants.tjTokenUrl;
    String tjSsrc = Constants.tjSsrc;
    String tjSkey = Constants.tjSkey;



    public void init() {
        log.info("--------项目启动,执行获取95598Token方法---------");
        getToken();
    }
    @Scheduled(cron="0 0/9 * * * ?")
    public void getToken(){
        log.info("------定时任务开始,执行获取95598Token方法-------");
        Constants.TOKEN_95598 = get95598Token(url, appCode, appSecret);
        log.info("------定时任务开始,执行获取天津客服Token方法-------");
        try {
            Constants.TOKEN_TJ=getTjToken(tjTokenUrl, tjAccount, tjPasswd,tjSsrc,tjSkey);
        } catch (Exception e) {
            log.info("调用天津业务出错");
        }
    }

    public static String get95598Token(String url, String appCode, String appSecret) {
        HttpHeaders header = new HttpHeaders();
        header.set("Accept-Charset", "UTF-8");
        header.set("Content-Type", "application/json; charset=utf-8");
        String token = null;
        JSONObject object = new JSONObject();
        object.put("appCode", appCode);
        object.put("appSecret", appSecret);
        log.info(object.toJSONString());  //{"appCode":"1001","appSecret":"0ebce0f1-fc81-4b51-a8fe-60a5bf926623"}
        HttpEntity<String> entity = new HttpEntity<String>(object.toJSONString(), header);
        RestTemplate restTemplate = new RestTemplate();
        String res = null;
        try {
            res = restTemplate.postForObject(url, entity, String.class);
            //res = URLDecoder.decode(res);
        log.info(res); //{"statusCode":"","token":"","currTime":"","expire":""}

        JSONObject obj = JSON.parseObject(res);
        Integer statusCode = Integer.parseInt(obj.get("statusCode").toString());
        if(statusCode != null && statusCode == 1) {
            token = obj.get("token").toString();
            log.info("95598 token is :----=================="+token);
        }
        } catch (RestClientException e) {
            log.info("访问95598系统出错："+e);
        }
        return token;
    }

    public static String getTjToken(String tjTokenUrl, String account, String passwd,String tjSsrc,String tjSkey) {
        HttpHeaders header = new HttpHeaders();
        header.set("Accept-Charset", "UTF-8");
        header.set("Content-Type", "application/json; charset=utf-8");
        String token = null;
        JSONObject object = new JSONObject();
        object.put("account", account);
        try {
            object.put("passwd", AESUtil.Encrypt(passwd,tjSsrc, tjSkey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject login = new JSONObject();
        login.put("login",object);
        log.info(login.toJSONString());  //{"appCode":"1001","appSecret":"0ebce0f1-fc81-4b51-a8fe-60a5bf926623"}
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<String>(login.toJSONString(), header);
        } catch (Exception e) {
            log.error("获取天津业务token加密出错"+e);
        }
        RestTemplate restTemplate = new RestTemplate();
        String res = null;
        try {
            res = restTemplate.postForObject(tjTokenUrl, entity, String.class);
            log.info(res); //{"statusCode":"","token":"","currTime":"","expire":""}

            JSONObject obj = JSON.parseObject(res);
            JSONObject payload=null;
            if (obj.get("payload") != null) {
                payload = (JSONObject) obj.get("payload");
                log.info(payload.toJSONString());
            }
            token = payload.get("token").toString();
            log.info("天津业务 token is :----=================="+token);
        } catch (RestClientException e) {
            log.info("访问天津业务系统出错："+e);
        }
        return token;
    }
}
