package com.kd.openplatform.hsf.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.accessControl.AccessControl;
import com.kd.openplatform.activeAPI.OpenplatformAPI;
import com.kd.openplatform.bean.ApiParam;
import com.kd.openplatform.info.ActiveInfoBean;
import com.kd.openplatform.util.*;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class OpenplatformAPIImpl implements OpenplatformAPI {
    private static final Log log = LogFactory.getLog(OpenplatformAPIImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SaveData saveData;

    /*
     * @Author zyz
     * @Description 上海自建平台
     * @Date  2019/4/1
     * @Param [json]
     * @return  java.lang.String
     */
    @Override
    public String getOpenplatformData(String json) {
        log.info("+++++++++++++++"+json);
        String appId = null;
        List<String> appIds = new ArrayList<String>();
        String sql = null;
        String rtn = null;
        List<String> path = null;
        //解析数据
        ActiveInfoBean bean = new ActiveInfoBean();
        bean.setStatus(0);
        bean.setInfo("能力平台调用服务成功");
        if (null == json || "null".equalsIgnoreCase(json)) {
            bean.setStatus(1);
            bean.setInfo("参数不正确");
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }

        JSONObject args = JSONObject.parseObject(json);
        String apiId = args.getString("apiId");
        String dataStr = args.getString("data");
        String opToken = args.getString("opToken");
        if (apiId == null) {
            bean.setStatus(ErrorConstants.ERROR_NO_API_ID_CODE);
            bean.setInfo(ErrorConstants.ERROR_NO_API_ID_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }
        //按apiId查找参数

        /*if(opToken == null){
            bean.setStatus(ErrorConstants.ERROR_NO_OPTOKEN_CODE);
            bean.setInfo(ErrorConstants.ERROR_NO_OPTOKEN_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }*/
        if (dataStr == null) {
            bean.setStatus(ErrorConstants.ERROR_ISP_DATA_ERROR_CODE);
            bean.setInfo(ErrorConstants.ERROR_ISP_DATA_ERROR_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }
        //根据服务Id查找应用
        sql = "SELECT api.app_id FROM api_app_rela api LEFT JOIN " +
                "api_scene_rela scene ON api.scene_id = scene.scene_id " +
                "WHERE (scene.api_id = ? OR api.api_id = ?)";
        List<Map<String, Object>> apps = jdbcTemplate.queryForList(sql, new Object[]{apiId, apiId});

        log.info(sql+"--------------"+apiId);
        if (apps == null || apps.size() == 0) {
            bean.setStatus(ErrorConstants.ERROR_SERVICE_NOT_ORDER_CODE);
            bean.setInfo(ErrorConstants.ERROR_SERVICE_NOT_ORDER_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        } else {
            for (Map<String, Object> map : apps) {
                appIds.add(String.valueOf(map.get("app_id")));
                log.info("应用"+String.valueOf(map.get("app_id"))+"订阅了"+apiId);
            }
        }

        //根据服务Id和应用Id查找path,为了容错这里用了循环但实际上是不允许一个主动能力有多个场景订阅的
        //如果有需要时将推送和返回值用多线程改造
        log.info(JSONObject.toJSONString(appIds)+"---------------");
        for(int i=0;i<appIds.size();i++) {
            log.info(i+"--------"+appIds.size());
            path = new ArrayList<String>();
            appId = appIds.get(i);
            log.info(i+"--------"+appId);
            bean.setInfo(bean.getInfo()+"---appId:"+appId);
            //sql = "SELECT method_path FROM api_order_detail WHERE api_id=? and order_id in (select id from api_order where app_id = ?)";
            //List<Map<String, Object>> methodPaths = jdbcTemplate.queryForList(sql, new Object[]{apiId, appId});
            //根据服务Id和应用Id查找path
            sql = "SELECT param_value FROM api_isv_param WHERE api_id=? AND app_id=? AND param_name=?";
            List<Map<String, Object>> methodPaths = jdbcTemplate.queryForList(sql, new Object[]{apiId, appId, "path"});

            log.info(sql + "-----apiId----" + apiId + "-----appId------" + appId + "-------apis----------"+JSONObject.toJSONString(methodPaths));

            String pathStr = null;
            if (methodPaths == null || methodPaths.size() == 0) {
                bean.setStatus(ErrorConstants.ERROR_NO_PATH_CODE);
                bean.setInfo(ErrorConstants.ERROR_ERROR_NO_PATH_DISP);
                bean.setAppId(appId);
                bean.setApiId(apiId);
                rtn = JSONObject.toJSONString(bean);
                log.info(rtn);
                return rtn;
            } else if (methodPaths.size() > 1) {
                bean.setStatus(ErrorConstants.ERROR_REPEATED_ORDER_CODE);
                bean.setInfo(ErrorConstants.ERROR_REPEATED_ORDER_DISP);
                bean.setAppId(appId);
                bean.setApiId(apiId);
                rtn = JSONObject.toJSONString(bean);
                log.info(rtn);
                return rtn;
            } else {
                for (Map<String, Object> map : methodPaths) {
                    pathStr  = String.valueOf(map.get("param_value"));
                    //pathStr = String.valueOf(map.get("method_path"));
                    log.info("----------路径是----------------"+pathStr);
                    path.add(pathStr);
                }
            }
            //按appId查找解密密码
            Map<String, String> keyMap = getKeysByAppId(appId);
            if (keyMap == null) {
                bean.setStatus(ErrorConstants.ERROR_API_ERROR_OR_NO_PWD_CODE);
                bean.setInfo(ErrorConstants.ERROR_API_ERROR_OR_NO_PWD_DISP);
                bean.setAppId(appId);
                bean.setApiId(apiId);
                rtn = JSONObject.toJSONString(bean);
                log.info(rtn);
                return rtn;
            }
            for (String p : path) {
                if(p.isEmpty() || "null".equalsIgnoreCase(p)){
                    log.info("--------------------------"+p);
                    bean.setApiId(apiId);
                    bean.setAppId(appId);
                    rtn = JSONObject.toJSONString(bean);
                    log.info(rtn);
                    return rtn;
                }else{
                    log.info("++++++++++++"+p);
                }
                try {
                    Map<String, List<ApiParam>> paramMap = getParamByApiId(apiId);
                    HttpEntity<String> entity = initPostData(dataStr, paramMap, keyMap);
                    RestTemplate restTemplate = new RestTemplate();
                    log.info("开始推送" + p);
                    String returnData = restTemplate.postForObject(p, entity, String.class);
                    try {
                        JSONObject rtnObj = JSONObject.parseObject(returnData);
                        log.info(rtnObj);
                        bean.setData(rtnObj);
                    }catch(Exception e){
                        log.info("返回值转化成JSON出错以字符串形式返回");
                        log.info(returnData);
                        bean.setData(returnData);
                    }
                } catch (Exception e1) {
                    bean.setStatus(ErrorConstants.ERROR_DATA_POST_ERROR_CODE);
                    bean.setInfo(ErrorConstants.ERROR_DATA_POST_ERROR_DISP);
                    bean.setAppId(appId);
                    bean.setApiId(apiId);
                    log.info("数据发送有误：", e1);
                }
                log.info("调用参数：：" + json);
                log.info("调用返回值：：" + JSONObject.toJSONString(bean));
                return JSONObject.toJSONString(bean);
            }
        }
        return null;
    }

    //获取95598 accessToken
    public static String getAccessToken(String url, String appCode, String appSecret) {
        HttpHeaders header = new HttpHeaders();
        header.set("Accept-Charset", "UTF-8");
        header.set("Content-Type", "application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        object.put("appCode", appCode);
        object.put("appSecret", appSecret);
        log.info(object.toJSONString());  //{"appCode":"1001","appSecret":"0ebce0f1-fc81-4b51-a8fe-60a5bf926623"}
        HttpEntity<String> entity = new HttpEntity<String>(object.toJSONString(), header);
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, entity, String.class);
        log.info("=====res========"+res); //{"statusCode":"","token":"","currTime":"","expire":""}

        JSONObject obj = JSON.parseObject(res);
        Integer statusCode = (Integer) obj.get("statusCode");
        String token = null;
        if(statusCode != null && statusCode == 1) {
            token = obj.get("token").toString();
            log.info("=========token========"+token);
        }
        return token;
    }

    @Override
    public String login(String json) {
        //解析出用户名密码，密码是MD5加密后的
        JSONObject args = JSONObject.parseObject(json);
        String userId = args.getString("userId");
        String pwd = args.getString("pwd");
       //验证登录


        log.info("================"+json);
        // 去数据库中查找该用户
        //生成token;
        Calendar cal = Calendar.getInstance();
        JSONObject rtnObj = new JSONObject();
        String opToken = "op::"+ UUID.randomUUID().toString().replace("-","")+"-"+cal.getTimeInMillis();
        rtnObj.put("opToken",opToken);
        rtnObj.put("authToken",opToken);
        rtnObj.put("userId",userId);
        log.info(rtnObj.toJSONString());
        //将token存入redis
        saveData.saveUserTokenSimple(userId,opToken);
        return rtnObj.toJSONString();
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

    public Map<String,List<ApiParam>> getParamByApiId(String apiId){
        List<ApiParam> apiParams = new ArrayList<ApiParam>();
        Map<String,List<ApiParam>> rtn = new HashMap<String,List<ApiParam>>();
        String sql = "select * from api_param A where A.api_id=? ORDER BY param_type,sort";
        List<Map<String, Object>> params = jdbcTemplate.queryForList(sql, new Object[]{apiId});
        List<ApiParam> header = new ArrayList<ApiParam>();
        List<ApiParam> body = new ArrayList<ApiParam>();
        List<ApiParam> tail = new ArrayList<ApiParam>();
        List<ApiParam> headerTmp = new ArrayList<ApiParam>();
        List<ApiParam> bodyTmp = new ArrayList<ApiParam>();
        List<ApiParam> tailTmp = new ArrayList<ApiParam>();
        log.info(sql+"====="+apiId);
        log.info("load params num::::::" + params);
        if (params != null && params.size() > 0) {
           //分出heaer和body的参数
           for(int i=0;i<params.size();i++){
               ApiParam ap = new ApiParam();
               ap.setId(params.get(i).get("id").toString());
               ap.setApiId(params.get(i).get("api_id").toString());
               ap.setParamName(params.get(i).get("param_name").toString());
               ap.setParamType(params.get(i).get("param_type").toString());
               ap.setDataType(params.get(i).get("data_type").toString());
               ap.setParamEncrypt(params.get(i).get("param_encrypt").toString());
               ap.setDefaultValue(params.get(i).get("default_value").toString());
               apiParams.add(ap);
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
            ApiParam ap = new ApiParam();
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
            for(ApiParam child : bodyTmp){
                for (ApiParam father : body){
                    if(child.getParentId().equals(father.getId())){
                        father.getChildern().add(child);
                    }
                }
            }
            for(ApiParam child : headerTmp){
                for (ApiParam father : header){
                    if(child.getParentId().equals(father.getId())){
                        father.getChildern().add(child);
                    }
                }
            }

            for(ApiParam child : tailTmp){
                for (ApiParam father : tail){
                    if(child.getParentId().equals(father.getId())){
                        father.getChildern().add(child);
                    }
                }
            }
        }
        rtn.put("header",header);
        rtn.put("body",body);
        rtn.put("tail",tail);
        rtn.put("apiParams",apiParams);
        return rtn;
    }

    private  HttpEntity<String> initPostData(String data,Map<String,List<ApiParam>> map,Map<String, String> keyMap){
        JSONObject jsonObject = JSONObject.parseObject(data);
        List<ApiParam> head = map.get("header");
        List<ApiParam> body = map.get("body");
        HttpHeaders header = new HttpHeaders();
        header.set("Accept-Charset", "UTF-8");
        header.set("Content-Type", "application/json; charset=utf-8");
        header.set("Accept","application/json");
        if(head.size()>0) {
            for (ApiParam ap : head) {
                try {
                    if (ap.getDataType().equals("3")) {//object
                        JSONObject jObj = jsonObject.getJSONObject(ap.getParamName());
                        log.info(ap.getParamName()+" param is object and value is  "+jObj.toJSONString());
                        if(ap.getParamEncrypt().equals("1")) {//需要加密
                            header.set(ap.getParamName(), AESUtil.Encrypt(jObj.toJSONString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                        }else{
                            //header.set(ap.getParamName(), jObj.toJSONString());
                            header.set(ap.getParamName(), jObj.toJSONString());
                        }
                    } else if (ap.getDataType().equals("10") || ap.getDataType().equals("11")) {//list[string],list[int]
                        JSONArray jArr = jsonObject.getJSONArray(ap.getParamName());
                        log.info(ap.getParamName()+" param is array and value is  "+jArr.toJSONString());
                        if(ap.getParamEncrypt().equals("1")) {//需要加密
                            header.set(ap.getParamName(), AESUtil.Encrypt(jArr.toJSONString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                        }else{
                            header.set(ap.getParamName(), jArr.toJSONString());
                        }
                    } else {
                        log.info(ap.getParamName());
                        if(ap.getParamEncrypt().equals("1")) {//需要加密
                            header.set(ap.getParamName(), AESUtil.Encrypt(jsonObject.getString(ap.getParamName()), keyMap.get("pass_key"), keyMap.get("iv_str")));
                        }else{
                            header.set(ap.getParamName(), jsonObject.getString(ap.getParamName()));
                        }
                    }
                } catch (Exception e) {
                    log.error("error:"+e);
                }
            }
        }

        JSONObject object = new JSONObject();
        if(body.size() >0) {
            for (ApiParam ap : body) {
                if (ap.getDataType().equals("3")) {//object
                    try {
                        JSONObject jObj = jsonObject.getJSONObject(ap.getParamName());
                        try {
                            if (ap.getParamEncrypt().equals("1")) {//需要加密
                                object.put(ap.getParamName(), AESUtil.Encrypt(jObj.toJSONString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                            } else {
                                //object.put(ap.getParamName(), jObj.toJSONString());
                                object.put(ap.getParamName(), jObj);
                            }
                        } catch (Exception e) {
                            log.info(e);
                        }
                    }catch(Exception e){
                        log.info("解析JsonObject出错，尝试以JsonString方式解析");

                        try {
                            if (ap.getParamEncrypt().equals("1")) {//需要加密
                                object.put(ap.getParamName(), AESUtil.Encrypt(jsonObject.getString(ap.getParamName()), keyMap.get("pass_key"), keyMap.get("iv_str")));
                            } else {
                                //object.put(ap.getParamName(), jObj.toJSONString());
                                object.put(ap.getParamName(), jsonObject.getString(ap.getParamName()));
                            }
                            log.info(ap.getParamName()+"--======--"+jsonObject.getString(ap.getParamName()));
                        } catch (Exception e1) {
                            log.info(e1);
                        }
                    }
                } else if (ap.getDataType().equals("10") || ap.getDataType().equals("11")) {//list[string],list[int]
                    JSONArray jArr = jsonObject.getJSONArray(ap.getParamName());
                    try {
                        if(ap.getParamEncrypt().equals("1")) {//需要加密
                            object.put(ap.getParamName(), AESUtil.Encrypt(jArr.toJSONString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                        }else{
                            object.put(ap.getParamName(), jArr.toJSONString());
                        }
                    } catch (Exception e) {
                        log.info(e);
                    }
                } else {
                    try {
                        if(ap.getParamEncrypt().equals("1")) {//需要加密
                            object.put(ap.getParamName(), AESUtil.Encrypt(jsonObject.getString(ap.getParamName()), keyMap.get("pass_key"), keyMap.get("iv_str")));
                        }else{
                            object.put(ap.getParamName(), jsonObject.getString(ap.getParamName()));
                        }

                        log.info(ap.getParamName()+"--======--"+jsonObject.getString(ap.getParamName()));
                    } catch (Exception e) {
                        log.info(e);
                    }
                }
            }
        }else{
            object = JSONObject.parseObject(data);
        }

        log.info(object.toJSONString()+"-------"+header);
        HttpEntity<String> entity = new HttpEntity<String>(object.toJSONString(), header);

        log.info(entity);
        return  entity;
    }

    /*
     * @Author zyz
     * @Description 互联互通
     * @Date  2019/4/1
     * @Param [json]
     * @return  java.lang.String
     */
    @Override
    public String linkToHLHT(String json){
//       json: {"appID":"","data":{"RequestData":
// "{\"Data\":\"70HwXjIrwH2cWdcJKDHkQ0bvN30Sr2bBdIUB6FEnHPU=\",\"OperatorID\":\"MA002TMQX\",\"Seq\":\"0001\",
// \"Sig\":\"511C064E7CC900634DB57E536CFA881A\",\"TimeStamp\":\"20190315153200\"}",
// "RequestUrl":"http://106.14.50.58:8000/html/v1.0.0/query_stations_info","Token":"Bearer d01a9d12d59f982759e6f1a14ebfd240"}}
        log.info("+++++++互联互通++++++++");
        String path = null;
        String rtn = null;
        String token = null;
        String returnData = null;

        HttpHeaders header = new HttpHeaders();
        header.set("Accept-Charset", "UTF-8");
        header.set("Content-Type", "application/json; charset=utf-8");
        header.set("Accept","application/json");

        //解析数据
        ActiveInfoBean bean = new ActiveInfoBean();
        bean.setStatus(0);
        bean.setInfo("能力平台调用服务成功");
        if (json == null || "null".equalsIgnoreCase(json)) {
            bean.setStatus(1);
            bean.setInfo("参数不正确");
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }

        JSONObject args = JSONObject.parseObject(json);
//        log.info("args========"+args.toJSONString());
//        args:{"data":{"RequestData":"{\"Data\":\"70HwXjIrwH2cWdcJKDHkQ0bvN30Sr2bBdIUB6FEnHPU=\",
//          \"OperatorID\":\"MA002TMQX\",\"Seq\":\"0001\",\"Sig\":\"511C064E7CC900634DB57E536CFA881A\",
//             \"TimeStamp\":\"20190315153200\"}",
//          "RequestUrl":"http://106.14.50.58:8000/html/v1.0.0/query_stations_info",
//          "Token":"Bearer d01a9d12d59f982759e6f1a14ebfd240"},"appID":""}
        JSONObject data = null;
        String dataStr = null;
        String appId=null;
        if (args.get("appID") != null) {
             appId = args.getString("appID");
        }
        log.info("appId========"+appId);
        JSONObject dataObj = args.getJSONObject("data");
//        String opToken = args.getString("Token");
        if (dataObj!=null) {
            path = dataObj.getString("RequestUrl");
            token  = dataObj.getString("Token");
            dataStr = dataObj.getString("RequestData");
        }



        if (dataObj == null) {
            bean.setStatus(ErrorConstants.ERROR_ISP_DATA_ERROR_CODE);
            bean.setInfo(ErrorConstants.ERROR_ISP_DATA_ERROR_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }
        //推送
        try{
            if(path.toLowerCase(Locale.ENGLISH).startsWith("https")){
                returnData = HttpRequestUtil.doPostSSL(path, dataStr,token);
            } else {
                returnData = HttpRequestUtil.sendPostTokenRequestUrl(path, dataStr, "utf-8", token);
            }
//            log.info("推送数据" + dataStr + "=====" + token);
//            log.info("开始推送" + path);
            log.info(returnData);
            bean.setData(returnData);
        } catch (Exception e1) {
            bean.setStatus(ErrorConstants.ERROR_DATA_POST_ERROR_CODE);
            bean.setInfo(ErrorConstants.ERROR_DATA_POST_ERROR_DISP);
            bean.setAppId(appId);
            log.info("数据发送有误：", e1);
            return JSONObject.toJSONString(bean);
        }
        return  returnData;
    }
    /*
     * @Author zyz
     * @Description 95598
     * @Date  2019/4/1
     * @Param [json]
     * @return  java.lang.String
     */
    @Override
    public String for95598(String json) {
        log.info("+++++++95598++++++++");
        String appId = null;
        List<String> appIds = new ArrayList<String>();
        String sql = null;
        String rtn = null;
        List<String> path = null;
        //解析数据
        ActiveInfoBean bean = new ActiveInfoBean();
        bean.setStatus(0);
        bean.setInfo("能力平台调用服务成功");
        if (null == json || "null".equalsIgnoreCase(json)) {
            bean.setStatus(1);
            bean.setInfo("参数不正确");
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }

        JSONObject args = JSONObject.parseObject(json);
        String apiId = args.getString("apiId");
        String dataStr = args.getString("data");
        String opToken = args.getString("opToken");
        String serviceCode = args.getString("serviceCode");
        String target = args.getString("target");
        String source = args.getString("source");
        if (apiId == null) {
            bean.setStatus(ErrorConstants.ERROR_NO_API_ID_CODE);
            bean.setInfo(ErrorConstants.ERROR_NO_API_ID_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }
        //按apiId查找参数

        /*if(opToken == null){
            bean.setStatus(ErrorConstants.ERROR_NO_OPTOKEN_CODE);
            bean.setInfo(ErrorConstants.ERROR_NO_OPTOKEN_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }*/
        if (dataStr == null) {
            bean.setStatus(ErrorConstants.ERROR_ISP_DATA_ERROR_CODE);
            bean.setInfo(ErrorConstants.ERROR_ISP_DATA_ERROR_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        }
        //根据服务Id查找应用
        sql = "SELECT api.app_id FROM api_app_rela api LEFT JOIN " +
                "api_scene_rela scene ON api.scene_id = scene.scene_id " +
                "WHERE (scene.api_id = ? OR api.api_id = ?)";
        List<Map<String, Object>> apps = jdbcTemplate.queryForList(sql, new Object[]{apiId, apiId});

        log.info(sql+"--------------"+apiId);
        if (apps == null || apps.size() == 0) {
            bean.setStatus(ErrorConstants.ERROR_SERVICE_NOT_ORDER_CODE);
            bean.setInfo(ErrorConstants.ERROR_SERVICE_NOT_ORDER_DISP);
            rtn = JSONObject.toJSONString(bean);
            log.info(rtn);
            return rtn;
        } else {
            for (Map<String, Object> map : apps) {
                appIds.add(String.valueOf(map.get("app_id")));
                log.info("应用"+String.valueOf(map.get("app_id"))+"订阅了"+apiId);
            }
        }

        //根据服务Id和应用Id查找path,为了容错这里用了循环但实际上是不允许一个主动能力有多个场景订阅的
        //如果有需要时将推送和返回值用多线程改造
        log.info(JSONObject.toJSONString(appIds)+"---------------");
        for(int i=0;i<appIds.size();i++) {
            log.info(i+"--------"+appIds.size());
            path = new ArrayList<String>();
            appId = appIds.get(i);
            log.info(i+"--------"+appId);
            bean.setInfo(bean.getInfo()+"---appId:"+appId);
            //sql = "SELECT method_path FROM api_order_detail WHERE api_id=? and order_id in (select id from api_order where app_id = ?)";
            //List<Map<String, Object>> methodPaths = jdbcTemplate.queryForList(sql, new Object[]{apiId, appId});
            //根据服务Id和应用Id查找path
            sql = "SELECT param_value FROM api_isv_param WHERE api_id=? AND app_id=? AND param_name=?";
            List<Map<String, Object>> methodPaths = jdbcTemplate.queryForList(sql, new Object[]{apiId, appId, "path"});


            log.info(sql + "-----apiId----" + apiId + "-----appId------" + appId + "-------apis----------"+JSONObject.toJSONString(methodPaths));

            String pathStr = null;
            if (methodPaths == null || methodPaths.size() == 0) {
                bean.setStatus(ErrorConstants.ERROR_NO_PATH_CODE);
                bean.setInfo(ErrorConstants.ERROR_ERROR_NO_PATH_DISP);
                bean.setAppId(appId);
                bean.setApiId(apiId);
                rtn = JSONObject.toJSONString(bean);
                log.info(rtn);
                return rtn;
            } else if (methodPaths.size() > 1) {
                bean.setStatus(ErrorConstants.ERROR_REPEATED_ORDER_CODE);
                bean.setInfo(ErrorConstants.ERROR_REPEATED_ORDER_DISP);
                bean.setAppId(appId);
                bean.setApiId(apiId);
                rtn = JSONObject.toJSONString(bean);
                log.info(rtn);
                return rtn;
            } else {
                for (Map<String, Object> map : methodPaths) {
                    pathStr  = String.valueOf(map.get("param_value"));
                    //pathStr = String.valueOf(map.get("method_path"));
                    log.info("----------路径是----------------"+pathStr);
                    path.add(pathStr);
                }
            }
            //按appId查找解密密码
            Map<String, String> keyMap = getKeysByAppId(appId);
            if (keyMap == null) {
                bean.setStatus(ErrorConstants.ERROR_API_ERROR_OR_NO_PWD_CODE);
                bean.setInfo(ErrorConstants.ERROR_API_ERROR_OR_NO_PWD_DISP);
                bean.setAppId(appId);
                bean.setApiId(apiId);
                rtn = JSONObject.toJSONString(bean);
                log.info(rtn);
                return rtn;
            }
            for (String p : path) {
                if(p.isEmpty() || "null".equalsIgnoreCase(p)){
                    log.info("--------------------------"+p);
                    bean.setApiId(apiId);
                    bean.setAppId(appId);
                    rtn = JSONObject.toJSONString(bean);
                    log.info(rtn);
                    return rtn;
                }else{
                    log.info("++++++++++++"+p);
                }
                try {
                    //Map<String, List<ApiParam>> paramMap = getParamByApiId(apiId);
                    HttpHeaders header = new HttpHeaders();
                    header.set("Accept-Charset", "UTF-8");
                    header.set("Content-Type", "application/json; charset=utf-8");
                    header.set("token", Constants.TOKEN_95598);
                    log.info("bodyStr----------------------"+dataStr);
                    JSONObject bodyObj = new JSONObject();//JSONObject.parseObject(bodyStr);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    Calendar cal = Calendar.getInstance();
                    String timestamp = sdf.format(cal.getTimeInMillis());
                    bodyObj.put("serviceCode",serviceCode);
                    bodyObj.put("target",target);
                    bodyObj.put("source",source);
                    dataStr = URLEncoder.encode(dataStr,"utf-8");
                    bodyObj.put("data",dataStr);
                    bodyObj.put("timestamp",timestamp);
                    bodyObj.put("sign",AESUtil.Encrypt(timestamp.toString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                    //bodyObj.put("sign",AES.Encrypt(timestamp.toString(), "ta36cFzDMqkeFzd2"));

                    //log.info("-----------------------------");
                    //log.info(AESUtil.Encrypt(timestamp.toString(), "ta36cFzDMqkeFzd2", "ta36cFzDMqkeFzd2"));
                    //log.info("-----------------------------");
                    //log.info(AES.Encrypt(timestamp.toString(), "ta36cFzDMqkeFzd2"));
                    //log.info("-----------------------------");
                    JSONObject tmp = new JSONObject();
                    log.info("bodyObj========================="+bodyObj);
                    tmp.put("encodeData",AESUtil.Encrypt(bodyObj.toJSONString(), keyMap.get("pass_key"), keyMap.get("iv_str")));
                    //tmp.put("encodeData",AES.Encrypt(bodyObj.toJSONString(), "ta36cFzDMqkeFzd2"));
                    HttpEntity<String> putEntity = new HttpEntity<String>(tmp.toJSONString(), header);
                    log.info("推送95598" + putEntity);
                    RestTemplate restTemplate = new RestTemplate();
                    log.info("开始推送" + p);
                    String returnData = restTemplate.postForObject(p, putEntity, String.class);
                    try {
                        JSONObject rtnObj = JSONObject.parseObject(returnData);
                        log.info(rtnObj);
                        bean.setData(rtnObj);
                    }catch(Exception e){
                        log.info("返回值转化成JSON出错以字符串形式返回");
                        log.info(returnData);
                        returnData = URLDecoder.decode(returnData,"utf-8");
                        bean.setData(returnData);
                    }
                } catch (Exception e1) {
                    bean.setStatus(ErrorConstants.ERROR_DATA_POST_ERROR_CODE);
                    bean.setInfo(ErrorConstants.ERROR_DATA_POST_ERROR_DISP);
                    bean.setAppId(appId);
                    bean.setApiId(apiId);
                    log.info("数据发送有误：", e1);
                }
                log.info("调用参数：：" + json);
                log.info("调用返回值：：" + JSONObject.toJSONString(bean));
                return JSONObject.toJSONString(bean);
            }
        }
        return null;
    }

 /*   public static void main(String args[]){
        *//*String appCode = "32";
        String appSecret = "admin";
        String url = "http://210.77.179.14:7016/95598/authzserverauthent/authentoken";
        String accessToken = getAccessToken(url, appCode, appSecret);
        log.info(accessToken);*//*
        //OpenplatformAPIImpl o = new OpenplatformAPIImpl();
        //o.getParamByApiId("4028e68a6801f5d501680250aac7043e");//4028e68a6807bc6d0168212c8dd10606
        String str = "{\"apiId\":\"4028e68a6807bc6d0168212c8dd10606\",\"opToken\":\"op::d0d9d03860ec4b7384687f6e96f5f49e-1548820647533\",\"data\":{\"msgId\":\"C0A8649D273E51917D9B955415910046\",\"appNo\":\"1548322552098\",\"busitypeCode\":null,\"busiSubType\":null,\"orgNo\":null,\"handleOrg\":null,\"handleDept\":\"国网商户\",\"sendDept\":null,\"handleStatus\":1,\"arriceTime\":1548820515000,\"handleStatusMode\":\"办结成功-----半截飒飒大苏打实打实大苏打实打实的\",\"handleOpinion\":\"办结成功-----半截飒飒大苏打实打实大苏打实打实的\",\"consOpinion\":null,\"handleStaff\":\"超级管理员\",\"handleFinishTime\":1548820515000,\"handleResult\":\"01\",\"trueFlag\":\"1\",\"respFlag\":\"0\",\"arriveDept\":null,\"acceptDept\":\"国网商户\",\"acceptEmp\":\"超级管理员\",\"acceptTime\":1548820515000}}";
        OpenplatformAPI oa = new OpenplatformAPIImpl();
        str = null;
        oa.getOpenplatformData(str);
        *//*List<ApiParam> lst = new ArrayList<ApiParam>();
        for(int i=0;i<3;i++) {
            ApiParam ap = new ApiParam();
            ap.setId(i+"");
            lst.add(ap);
        }
//        lst.get(0).
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(lst));
        System.err.println(array);
        JSONObject obj = JSONObject.parseObject(str);
        String ss = obj.getString("data");
        JSONObject so = obj.getJSONObject("data");
        JSONObject token = obj.getJSONObject("opToken");
        JSONObject jj = new JSONObject();
        jj.put("data",ss);
        System.err.println(ss);
        System.err.println(jj.toJSONString());

        JSONObject j = new JSONObject();
        j.put("data",so);
        System.err.println(j.toJSONString());*//*
    }*/
}