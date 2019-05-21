package com.kd.service;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.info.InfoBean;
import com.kd.openplatform.util.Constants;
import com.kd.openplatform.util.ErrorConstants;
import com.kd.openplatform.util.SaveData;
import com.kd.openplatform.ws.client.WSClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//@WebService
@Service
public class OpWebserviceImpl implements OpWebserviceI {

//    @Autowired
//    private SysSupportApiService sysSupportApiService;
//
//    @Autowired
//    private DataCom serviceCom;

    @Autowired
    private SaveData saveData;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WSClient wsClient;

    @Autowired

    private static final Log log = LogFactory.getLog(OpWebserviceI.class);

    @Override
    public String chargeArchives(String param) {
        String s = openService(param);
        log.info("返回值："+s);
        return s;
    }

    @Override
    public String openService(String param) {
        log.info("webservice服务端收到请求，请求参数：" + param);
        InfoBean bean = new InfoBean();
        JSONObject parameObj = null;
        String path = null;
        String paramStr = null;
//        String returnStr = null;
        //todo 鉴权
        //String s = serviceCom.dataCom(param);
        try {
            //尝试用json去解析参数，若不是json格式的数据，则在catch里用xml解析
            parameObj = JSONObject.parseObject(param);
        } catch (Exception e) {
            //尝试用xml格式解析
            String appId = null;
            String apiDomainName = null;
            try {
                Document document = DocumentHelper.parseText(param);
                Element root = document.getRootElement();
                appId = root.attributeValue("appId");
                apiDomainName = root.attributeValue("apiDomainName");
                //如果包含这两个节点
                if(null!=appId  && null!=apiDomainName){
                    path = apiDomainName;
                    //String appid = parameObj.getString("appId");

                }else{
                    //obj拿到了啥???
                    Object obj = redisTemplate.boundHashOps(Constants.REDIS_WSINFO).get("chargeArchivesService");
                    log.info(JSONObject.toJSONString(obj));
                    String replace = JSONObject.toJSONString(obj).replace("}\"", "}");
                    String replace1 = replace.replace("\"{", "{").replace("\\","");
                    log.info(replace1);
                    JSONObject wsObj = JSONObject.parseObject(replace1);
                    if (wsObj != null) {
                        String apiId = wsObj.getString("apiId");
                        String returnStr = wsClient.post(apiId, wsObj, param);
                        log.info("redis方案返回值returnStr:"+returnStr);
                        return returnStr;
                    } else {
                        String sql = "select id apiId,req_addr_http endpoint,api_class_name namespaceURI,api_method_name methodName from api_info where req_addr_hsf=?";
                        List<Map<String, Object>> apiInfoList = jdbcTemplate.queryForList(sql, new Object[]{"chargeArchivesService"});
                        if (apiInfoList != null && apiInfoList.size() > 0) {
                            for (Map<String, Object> map : apiInfoList) {
                                JSONObject wsObject = new JSONObject();
                                String apiId = (String) map.get("apiId");
                                String endpoint = (String) map.get("endpoint");
                                String namespaceURI = (String) map.get("namespaceURI");
                                String methodName = (String) map.get("methodName");
                                wsObject.put("endpoint", endpoint);
                                wsObject.put("namespaceURI", namespaceURI);
                                wsObject.put("methodName", methodName);
                                wsObject.put("apiId", apiId);
                                String returnStr = wsClient.post(apiId, wsObject, param);
                                log.info("数据库方案返回值returnStr:"+returnStr);
                                redisTemplate.boundHashOps(Constants.REDIS_WSINFO).put("chargeArchivesService", wsObject.toJSONString());
                                log.info("redis存储成功");
                                return returnStr;
                            }
                        } else {
                            bean.setInfo(ErrorConstants.ERROR_PATH_DISP);
                            bean.setStatus(ErrorConstants.ERROR_PATH_CODE);
                            return JSONObject.toJSONString(bean);
                        }
                    }
                }
            } catch (DocumentException de) {
                de.printStackTrace();
            }


            //log.error("Exception:"+e);
            //bean.setStatus(ErrorConstants.ERROR_PARSE_CODE);
            //bean.setInfo(ErrorConstants.ERROR_PARSE_DISP);
        }

        //---catch结束----  开始解析json数据
        String returnStr = "";
        if (parameObj != null) {
            if (parameObj.get("apiDomainName") != null) {
                path = parameObj.getString("apiDomainName");
            } else {
                bean.setStatus(ErrorConstants.ERROR_WS_METHOND_CODE);
                bean.setInfo(ErrorConstants.ERROR_WS_METHOND_DISP);
                return JSONObject.toJSONString(bean);
            }
            if (parameObj.get("appId") != null) {
                String appid = parameObj.getString("appId");
            } else {
                bean.setStatus(ErrorConstants.ERROR_GET_APPID_CODE);
                bean.setInfo(ErrorConstants.ERRORT_GET_APPID_DISP);
                return JSONObject.toJSONString(bean);
            }
            if (parameObj.get("data") != null) {
                paramStr = parameObj.getString("data");
            } else {
                bean.setStatus(ErrorConstants.ERROR_WS_PARAM_CODE);
                bean.setInfo(ErrorConstants.ERROR_WS_PARAM_DISP);
                return JSONObject.toJSONString(bean);
            }
        }
        Object o = redisTemplate.boundHashOps(Constants.REDIS_WSINFO).get(path);
        String replace = JSONObject.toJSONString(o).replace("}\"", "}");
        String replace1 = replace.replace("\"{", "{").replace("\\","");
        log.info(replace1);
        JSONObject wsObj = JSONObject.parseObject(replace1);
        if (wsObj != null) {
            String apiId = wsObj.getString("apiId");
            returnStr = wsClient.post(apiId, wsObj, paramStr);
        } else {
            String sql = "select id apiId,req_addr_http endpoint,api_class_name namespaceURI,api_method_name methodName from api_info where req_addr_hsf=?";
            List<Map<String, Object>> apiInfoList = jdbcTemplate.queryForList(sql, new Object[]{path});
            if (apiInfoList != null && apiInfoList.size() > 0) {
                for (Map<String, Object> map : apiInfoList) {
                    JSONObject wsObject = new JSONObject();
                    String apiId = (String) map.get("apiId");
                    String endpoint = (String) map.get("endpoint");
                    String namespaceURI = (String) map.get("namespaceURI");
                    String methodName = (String) map.get("methodName");
                    wsObject.put("endpoint", endpoint);
                    wsObject.put("namespaceURI", namespaceURI);
                    wsObject.put("methodName", methodName);
                    wsObject.put("apiId", apiId);
                    returnStr = wsClient.post(apiId, wsObject, paramStr);
                    redisTemplate.boundHashOps(Constants.REDIS_WSINFO).put(path, wsObject.toJSONString());
                }
            } else {
                bean.setInfo(ErrorConstants.ERROR_PATH_DISP);
                bean.setStatus(ErrorConstants.ERROR_PATH_CODE);
                bean.setData(returnStr);
                return JSONObject.toJSONString(bean);
            }
        }
        bean.setInfo("能力开放平台调用成功");
        bean.setStatus(0);
        bean.setData(returnStr);
        return JSONObject.toJSONString(bean);
    }

//    @Override
//    public String login(String param) {
//        InfoBean bean = new InfoBean();
//        String accesstoken = ((String)redisTemplate.opsForValue().get("ACCESSTOKEN")).replace("\"", "");
//        Integer businessLabel=5;
//        JSONObject paramObj = null;
//        try {
//            paramObj = JSONObject.parseObject(param);
//        } catch (Exception e) {
//            log.error("Exception:"+e);
//            bean.setStatus(ErrorConstants.ERROR_PARSE_CODE);
//            bean.setInfo(ErrorConstants.ERROR_PARSE_DISP);
//            return JSONObject.toJSONString(bean);
//        }
//        OperatorItemModel operatorItemModel = new OperatorItemModel();
//        operatorItemModel.setAccessToken(accesstoken);
//        operatorItemModel.setBusinessLabel(businessLabel);
//        try {
//            operatorItemModel.setUserName(paramObj.getString("userName"));
//            operatorItemModel.setPassword(paramObj.getString("password"));
//        } catch (Exception e) {
//            log.error("Exception:"+e);
//            bean.setStatus(ErrorConstants.ERROR_USERNAME_PASSWORD_CODE);
//            bean.setInfo(ErrorConstants.ERROR_USERNAME_PASSWORD_DISP);
//            return JSONObject.toJSONString(bean);
//        }
//        ResponseVo login = sysSupportApiService.login(operatorItemModel);
//        try {
//            Object data = login.getData();
//            JSONObject obj = (JSONObject) JSONObject.toJSON(data);
//            String token = obj.getString("token");
//            saveData.saveUserTokenSimple(paramObj.getString("userName"),token);
//        } catch (Exception e) {
//            log.error("保存authToken出错："+e);
//        }
//        bean.setData(login);
//        bean.setStatus(0);
//        bean.setInfo("能力开放平台调用成功");
//        return JSONObject.toJSONString(bean);
//    }
}
