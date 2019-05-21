package com.kd.openplatform.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.bean.ApiParam;
import com.kd.openplatform.hsf.provider.OpenplatformAPIImpl;
import com.kd.openplatform.info.InfoBean;
//import com.kd.openplatform.mq.MQ;
import com.kd.openplatform.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Http {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Log log = LogFactory.getLog(Http.class);
    public String post(String apiId, JSONObject obj, String requestParam){
        log.info("http post方法执行");
        InfoBean infoBean = new InfoBean();
        String appId = obj.getString("appId");
        String path = obj.getString("path");
//        String token = obj.getString("token");
        log.info("http post方法执行"+appId+path);
        String returnData = null;
        String dataStr = null;
        InfoBean bean = new InfoBean();
        //JSONObject jsonObject = JSONObject.parseObject(requestParam);
//        OpenplatformAPIImpl openplatformAPI = new OpenplatformAPIImpl();
        Map<String, String> keyMap = getKeysByAppId(appId);
//        log.info("单独对某个字段加密::::"+keyMap.get("pass_key")+ keyMap.get("iv_str"));
        //单独对某个字段加密
        /*Map<String,List<ApiParam>> paramMap = openplatformAPI.getParamByApiId(apiId);
        List<ApiParam> params = paramMap.get("tail");
        JSONObject json = new JSONObject();

        for (ApiParam ap : params) {
            try {
                if (ap.getDataType().equals("3")) {//object
                    JSONObject jObj = jsonObject.getJSONObject(ap.getParamName());
                    log.info(ap.getParamName()+" param is object and value is  "+jObj.toJSONString());
                    if(ap.getParamEncrypt().equals("1")) {//需要加密
                        json.put(ap.getParamName(), AESUtil.Encrypt(jObj.toJSONString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                    }else{
                        //header.set(ap.getParamName(), jObj.toJSONString());
                        json.put(ap.getParamName(), jObj.toJSONString());
                    }
                } else if (ap.getDataType().equals("10") || ap.getDataType().equals("11")) {//list[string],list[int]
                    JSONArray jArr = jsonObject.getJSONArray(ap.getParamName());
                    log.info(ap.getParamName()+" param is array and value is  "+jArr.toJSONString());
                    if(ap.getParamEncrypt().equals("1")) {//需要加密
                        json.put(ap.getParamName(), AESUtil.Encrypt(jArr.toJSONString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                    }else{
                        json.put(ap.getParamName(), jArr.toJSONString());
                    }
                } else {
                    log.info(ap.getParamName());
                    if(ap.getParamEncrypt().equals("1")) {//需要加密
                        json.put(ap.getParamName(), AESUtil.Encrypt(jsonObject.getString(ap.getParamName()), keyMap.get("pass_key"), keyMap.get("iv_str")));
                    }else{
                        json.put(ap.getParamName(), jsonObject.getString(ap.getParamName()));
                    }
                }
            } catch (Exception e) {
                log.error("error:"+e);
            }
        }

        dataStr = json.toString();*/
        if(keyMap == null){
            infoBean.setStatus(ErrorConstants.ERROR_CANNOT_FIND_KEY_CODE);
            infoBean.setInfo(ErrorConstants.ERROR_CANNOT_FIND_KEY_DISP);
            log.info(JSONObject.toJSONString(infoBean));
        }else {
            try {
                dataStr = AESUtil.Encrypt(requestParam, keyMap.get("pass_key"), keyMap.get("iv_str"));
                infoBean.setData(dataStr);
                infoBean.setStatus(0);
                infoBean.setInfo("能力开放平台调用成功");
                log.info(infoBean);
            } catch (Exception e) {
                log.info("Encrypt err:::::" + e.getMessage());
            }
        }
        dataStr = JSONObject.toJSONString(infoBean);
        //推送
        try{
            if(path.toLowerCase(Locale.ENGLISH).startsWith("https")){
                returnData = HttpRequestUtil.doPostSSL(path, dataStr, Constants.TOKEN_TJ);
            } else {
                log.info(path);
                returnData = HttpRequestUtil.sendPostTokenRequestUrl(path, dataStr, "utf-8", Constants.TOKEN_TJ);
                log.info(returnData);
            }
//            log.info("推送数据" + dataStr + "=====" + token);
//            log.info("开始推送" + path);
            log.info("push data is :::::"+returnData);
            bean.setData(returnData);
            bean.setInfo("能力开放平台调用成功");
            bean.setStatus(0);
        } catch (Exception e1) {
            bean.setStatus(ErrorConstants.ERROR_DATA_POST_ERROR_CODE);
            bean.setInfo(ErrorConstants .ERROR_DATA_POST_ERROR_DISP);
            log.info("数据发送有误：", e1);
            return JSONObject.toJSONString(bean);
        }
log.info("return data:::::"+JSONObject.toJSONString(bean));
        return JSONObject.toJSONString(bean);
    }

    public Map<String, String> getKeysByAppId(String appId) {
        log.info("appId:::::"+appId);
        String sql = "select pass_key,iv_str from api_app_keys A where A.app_id=?";
        log.info("jdbcTemplate:"+jdbcTemplate);
        List<Map<String, Object>> keys = jdbcTemplate.queryForList(sql, new Object[]{appId});
        log.info(sql+"====="+appId);
        log.info("load keys num::::::" + keys);
        if (keys != null && keys.size() > 0) {
            Map<String, String> key = new ConcurrentHashMap<>();
            key.put("pass_key", keys.get(0).get("pass_key") + "");
            key.put("iv_str", keys.get(0).get("iv_str") + "");
            return key;
        }
        return null;
    }
}
