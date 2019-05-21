
package com.kd.openplatform.util;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.accessControl.AccessControl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by AndrewKing on 11/22/2018.
 */
public class RequestUtils {
    /*
    * 这个方法是由request，返回真实requestParam参数的方法,为了应对前端的jsonp跨域传值方式
    * input: HttpServletRequest request
    * output: JSONObject requestParam
    * */
    private static final Log log =  LogFactory.getLog(AccessControl.class);
    public static JSONObject getParam(HttpServletRequest request){
        JSONObject requestParam = new JSONObject();
        Map<String, String[]> reqParams = request.getParameterMap();
        if(reqParams!=null){
            for(String key : reqParams.keySet()) {
                if(!(key.equals("callback") || key.equals("_"))){
                    requestParam = JSONObject.parseObject(key);
                }
            }
        }
        return requestParam;
    }

    public static String mapPost(String url, Map<String,Object> map, String encoding){
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),String.valueOf(elem.getValue())));
            }
            if(list.size() > 0){
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
        }catch(Exception e){
            log.error("Exception:"+e);
        }finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("Exception:"+e);
                }
            }
        }
        return result;
    }
}
