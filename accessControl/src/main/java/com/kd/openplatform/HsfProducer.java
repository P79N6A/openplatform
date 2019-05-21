package com.kd.openplatform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.access.entity.ApiParamEntity;
import com.kd.openplatform.common.utils.Constants;
import com.kd.openplatform.util.JsonUtil;
import com.kd.openplatform.util.XmlUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kd.openplatform.control.ControlCenter;
import com.kd.openplatform.control.StrategyContext;
import com.kd.openplatform.control.TestStrategyContext;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import serviceCom.DataCom;

import java.util.*;

import org.dom4j.DocumentException;

import javax.json.Json;
import javax.json.JsonObject;

public class HsfProducer implements DataCom {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private JdbcTemplate jdbcTemplate;

	private static final Log log =  LogFactory.getLog(HsfProducer.class);
	
    private StrategyContext strategyContext;
    private TestStrategyContext testStrategyContext;
    private ControlCenter controlCenter;

    public StrategyContext getStrategyContext() {
        return strategyContext;
    }

    public void setStrategyContext(StrategyContext strategyContext) {
        this.strategyContext = strategyContext;
    }

    public TestStrategyContext getTestStrategyContext() {
        return testStrategyContext;
    }

    public void setTestStrategyContext(TestStrategyContext testStrategyContext) {
        this.testStrategyContext = testStrategyContext;
    }

    public ControlCenter getControlCenter() {
        return controlCenter;
    }

    public void setControlCenter(ControlCenter controlCenter) {
        this.controlCenter = controlCenter;
    }

    //HSF
    public String dataCom(String data) {
    	log.info("-------------------调用者输入的参数是:"+data);
    	String rtn = controlCenter.checkAndReturn(data);
        //String rtn = "{\"status\":\"200\"}";
    	log.info("-------------------返回给调用者的值是:"+rtn);
        return rtn;
    }

    public String sourceControl(String json){
        log.info("对参数进行资源控制");
        //牛延军
        //按appId找到资源控制的字段和参数类型
        JSONObject obj = JSONObject.parseObject(json);
        String apiId = obj.getString("apiId");
        String appId = obj.getString("appId");
        String msg = obj.getString("data");
        String paramType = null;//从数据库中查出来的参数类型
        List<String> delNames = null;//从数据库中查出来的不传的字段

        Jedis jedis = jedisPool.getResource();
        //先从redis里取资源控制信息
        String sourceControlStr = jedis.hget(Constants.REDIS_SOURCE_CONTROL,apiId+"_"+appId);
        JSONObject sourceControlObj = null;
        if(sourceControlStr != null) {
            sourceControlObj = JSONObject.parseObject(sourceControlStr);
        }else {
            sourceControlStr = getSourceByApiAppId(apiId,appId);
            if (sourceControlStr == null) {
                return msg;
            }
            jedis.hset(Constants.REDIS_SOURCE_CONTROL,apiId+"_"+appId,sourceControlStr);
            sourceControlObj = JSONObject.parseObject(sourceControlStr);
        }
        jedis.close();
        log.info(sourceControlStr);
        //先从redis里取参数信息
//        String paramInfoStr = jedis.hget(Constants.REDIS_PARAM_INFO,apiId);
//        log.info(paramInfoStr);
        JSONArray paramInfo = null;
        /*if(paramInfoStr != null) {
            paramInfo = JSONArray.parseArray(paramInfoStr);
        }else {
            Map<String, List<ApiParamEntity>> paramMap = getParamByApiId(apiId);
            List<ApiParamEntity> params = paramMap.get("tail");
            paramInfoStr = JSONObject.toJSONString(params);
            jedis.hset(Constants.REDIS_PARAM_INFO,apiId,paramInfoStr);
            paramInfo = JSONArray.parseArray(sourceControlStr);
        }*/
        //paramType = paramInfo.getString("dataType");
//        delNames = new ArrayList<>(Arrays.asList(sourceControlArr));
        /*if (paramType.equalsIgnoreCase("xml")) {
            XmlUtil xmlUtil = new XmlUtil();
            try {
                msg = xmlUtil.createXml(msg, delNames);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } else if (paramType.equalsIgnoreCase("object")) {
            JsonUtil jsonUtil = new JsonUtil();
            msg = jsonUtil.createJson(msg, delNames);
        }*/
        try{
            //先按json解析如果不行再按xml解析
            JsonUtil jsonUtil = new JsonUtil();
            msg = jsonUtil.createJson(msg, sourceControlObj);
        }catch(Exception e){
            log.info("按json解析有误，尝试用xml解析");
            try{
                XmlUtil xmlUtil = new XmlUtil();
                msg = xmlUtil.createXml(msg, sourceControlObj);
            }catch (Exception e1){
                log.info("msg格式有误");
            }
        }
        return msg;
    }

    public Map<String,List<ApiParamEntity>> getParamByApiId(String apiId){
        List<ApiParamEntity> ApiParamEntitys = new ArrayList<ApiParamEntity>();
        Map<String,List<ApiParamEntity>> rtn = new HashMap<String,List<ApiParamEntity>>();
        String sql = "select * from api_param A where A.api_id=? ORDER BY param_type,sort";
        List<Map<String, Object>> params = jdbcTemplate.queryForList(sql, new Object[]{apiId});
        List<ApiParamEntity> header = new ArrayList<ApiParamEntity>();
        List<ApiParamEntity> body = new ArrayList<ApiParamEntity>();
        List<ApiParamEntity> tail = new ArrayList<ApiParamEntity>();
        List<ApiParamEntity> headerTmp = new ArrayList<ApiParamEntity>();
        List<ApiParamEntity> bodyTmp = new ArrayList<ApiParamEntity>();
        List<ApiParamEntity> tailTmp = new ArrayList<ApiParamEntity>();
        log.info(sql+"====="+apiId);
        log.info("load params num::::::" + params);
        if (params != null && params.size() > 0) {
            //分出heaer和body的参数
            for(int i=0;i<params.size();i++){
                ApiParamEntity ap = new ApiParamEntity();
                ap.setId(params.get(i).get("id").toString());
                ap.setApiId(params.get(i).get("api_id").toString());
                ap.setParamName(params.get(i).get("param_name").toString());
                ap.setParamType(params.get(i).get("param_type").toString());
                ap.setDataType(params.get(i).get("data_type").toString());
                ap.setParamEncrypt(params.get(i).get("param_encrypt").toString());
                ap.setDefaultValue(params.get(i).get("default_value").toString());
                ApiParamEntitys.add(ap);
                if(params.get(i).get("parent_id") != null) {
                    ap.setParentId(params.get(i).get("parent_id").toString());
                }
                if(ap.getParamType().equals("0")){//请求体
                    bodyTmp.add(ap);
                }
                if(ap.getParamType().equals("1")){//请求体
                    tailTmp.add(ap);
                }
                if(ap.getParamType().equals("2")){//请求头
                    headerTmp.add(ap);
                }
            }
            //先把父亲挑出来
            ApiParamEntity ap = new ApiParamEntity();
            for (int i=bodyTmp.size()-1;i>=0;i--){
                ap = bodyTmp.get(i);
                if(ap.getParentId() == null){
                    body.add(ap);
                    bodyTmp.remove(i);
                }
            }
            for (int i=headerTmp.size()-1;i>=0;i--){
                ap = headerTmp.get(i);
                if(ap.getParentId() == null){
                    header.add(ap);
                    headerTmp.remove(i);
                }
            }
            for (int i=tailTmp.size()-1;i>=0;i--){
                ap = tailTmp.get(i);
                if(ap.getParentId() == null){
                    tail.add(ap);
                    tailTmp.remove(i);
                }
            }
            //给父亲找儿子
            for(ApiParamEntity child : bodyTmp){
                for (ApiParamEntity father : body){
                    if(child.getParentId().equals(father.getId())){
                        father.getChildern().add(child);
                    }
                }
            }
            for(ApiParamEntity child : headerTmp){
                for (ApiParamEntity father : header){
                    if(child.getParentId().equals(father.getId())){
                        father.getChildern().add(child);
                    }
                }
            }

            for(ApiParamEntity child : tailTmp){
                for (ApiParamEntity father : tail){
                    if(child.getParentId().equals(father.getId())){
                        father.getChildern().add(child);
                    }
                }
            }
        }
        rtn.put("header",header);
        rtn.put("body",body);
        rtn.put("tail",tail);
        rtn.put("ApiParamEntitys",ApiParamEntitys);
        return rtn;
    }


    public String getSourceByApiAppId(String apiId,String appId){
        log.info("根据apiId："+apiId+",appId:"+appId+"去数据库查询资源控制字段信息");
        String rtn = null;
        JSONObject jsonObject = new JSONObject();
        String sql = "select resource_param_name paramName,resource_param_value paramValue from api_resource_control  " +
                "where api_id=? and app_id=? ";
//                "and " +
//                "(A.resource_param_value='' or A.resource_param_value='-999' or A.resource_param_value IS NULL or A.resource_param_value = 'NULL')";
        List<Map<String, Object>> params = jdbcTemplate.queryForList(sql, new Object[]{apiId,appId});
        log.info("查询资源控制字段信息结果："+params);
        if (params == null || params.size() == 0) {
            return rtn;
        } else {
            for (Map<String, Object> map : params) {
                if (map.get("paramName")!=null && map.get("paramValue") != null) {
                    jsonObject.put((String) map.get("paramName"),map.get("paramValue"));
                }
                if (map.get("paramName")!=null && map.get("paramValue") == null) {
                    jsonObject.put((String) map.get("paramName"),"");
                }
            }
        }
        rtn = jsonObject.toJSONString();
        log.info(rtn);
        return rtn;
    }
}