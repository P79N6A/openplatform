package com.kd.openplatform.action;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.cache.HttpAddrMapping;
import com.kd.openplatform.cache.RequestInterfaceTotal;
import com.kd.openplatform.hsf.consumer.bean.ConsumerBean;
import com.kd.openplatform.info.InfoBean;
import com.kd.openplatform.services.OpenPlatformBaseService;
import com.kd.openplatform.util.AESUtil;
import com.kd.openplatform.util.ErrorConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;


/**
 * @author zhangsg
 * 能力平台http、https提供服务统一入口
 */
@RequestMapping("/openapi")
@Controller
public class OpenPlatformBaseController {
	
	private static final Log log =  LogFactory.getLog(OpenPlatformBaseController.class);

	@Autowired
	private OpenPlatformBaseService openPlatformBaseService;
	
	//存储调用 次数
	@Autowired
	private RequestInterfaceTotal requestInterfaceTotal; 
	
	
	@RequestMapping(value="/{path}",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String baseAction(@PathVariable("path") String path,HttpServletRequest request)
    {
		if(path.indexOf("&")!=-1) {
			path = path.substring(0, path.indexOf("&"));
		}
		String argsStr =  request.getParameter("args");
	  	log.info("目标方法传过来的参数--OpenPlatformBaseController --argsStr==== "+argsStr);
		String json = "";
		InfoBean bean = new InfoBean();
		bean.setStatus(0);
		bean.setInfo("能力平台调用服务成功");
		if(argsStr==null||"null".equalsIgnoreCase(argsStr)) {
			bean.setStatus(ErrorConstants.ERROR_DATA_ISNULL_CODE);
			bean.setInfo(ErrorConstants.ERROR_DATAA_ISNULL_DISP);
			json = JSONObject.toJSONString(bean);
			log.info(json);
			return json;
		}

		JSONObject args = JSONObject.parseObject(argsStr);

		log.info("目标方法中解析的访问路径path为："+path);
		
		HttpAddrMapping httpAddrMapping = openPlatformBaseService.getHttpAddrMapping();
		
		if(!httpAddrMapping.getHttpAddr_HsfAddr().containsKey(path)) {
			synchronized (this) {
				try {
					openPlatformBaseService.loadAddrMapping(path);
				}catch(RuntimeException e){
					bean.setStatus(ErrorConstants.ERROR_CONSUMERSERVICE_SUBSCRIBE_CODE);
					bean.setInfo(ErrorConstants.ERROR_CONSUMERSERVICE_SUBSCRIBE_DISP);
					json = JSONObject.toJSONString(bean);
					log.info(json);
					return json;
				}
			}
		}
		log.info("======第一次取出的apiId====="+httpAddrMapping.getHttpAddr_HsfAddr().get(path).getConsumerId());
		request.setAttribute("apiId",httpAddrMapping.getHttpAddr_HsfAddr().get(path).getConsumerId());
		ConsumerBean consumerBean = httpAddrMapping.getHttpAddr_HsfAddr().get(path);
		
		if(consumerBean == null) {
			 bean.setStatus(ErrorConstants.ERROR_CANNOT_FIND_SERVICE_CODE);
			 bean.setInfo(ErrorConstants.ERROR_CANNOT_FIND_SERVICE_DISP);
			 json = JSONObject.toJSONString(bean);
			 log.info(json);
			 return json;
		}

		try {
			JSONObject jsonObject = JSONObject.parseObject(args.toString());
			String appId = jsonObject.get("appId") + "";
			log.info("appId::::::" + appId);
			if(!httpAddrMapping.getAppKeys().containsKey(appId)){
				Map<String,String> keys = openPlatformBaseService.getKeysByAppId(appId);
				log.info("keys:::::" + keys);
				if(keys != null){
					httpAddrMapping.getAppKeys().put(appId,keys);
				}
			}
			if(httpAddrMapping.getAppKeys().containsKey(appId)){
				String pass_key = httpAddrMapping.getAppKeys().get(appId).get("pass_key");//加密秘钥
				String iv_str = httpAddrMapping.getAppKeys().get(appId).get("iv_str");//加密偏移量
				log.info(pass_key + "-----" + iv_str);
				//处理args解密
				boolean decryptFlag = false;
				JSONObject decryptArgs = new JSONObject();
				try {
					decryptArgs.put("appId",appId);
					for(String key:jsonObject.keySet()){
						if(!key.equals("appId")){
							String decrypt = null;
							try {
								decrypt = AESUtil.Decrypt(jsonObject.get(key) + "", pass_key, iv_str);
							}catch (Exception e1){
								log.error("args decrypt error:",e1);
								bean.setStatus(ErrorConstants.ERROR_DENCRYPTION_ENCRYPTION_CODE);
								bean.setInfo(ErrorConstants.ERROR_DENCRYPTION_ENCRYPTION_DISP);
								json = JSONObject.toJSONString(bean);
								log.info(json);
								return json;
							}
							try {
								decryptArgs.put(key, JSONObject.parseObject(decrypt));
							} catch (Exception e) {
								decryptArgs.put(key, decrypt.replace("\"",""));
							}
						}
					}
					decryptFlag = true;
					log.info("decrypted::::::"+decryptArgs);
				}catch (Exception e){
					decryptFlag = false;
					log.error("args decrypt error:",e);
					bean.setStatus(ErrorConstants.ERROR_PARAM_ENCRYPTION_CODE);
					bean.setInfo(ErrorConstants.ERROR_PARAM_ENCRYPTION_DISP);
					json = JSONObject.toJSONString(bean);
					log.info(json);
					return json;
				}
				//参数解密成功之后获取数据
				if(decryptFlag){
					//decryptArgs.put("accessToken",request.getAttribute("accessToken"));
					Object res = openPlatformBaseService.hsfInterfaceService(path, decryptArgs);
                    bean.setData(res);
					//	        log.info("接口服务耗时（ms）："+responseTimeLength+"=="+path);
				}
			}else{
				log.error("keys load error:");
				bean.setStatus(ErrorConstants.ERROR_CANNOT_FIND_KEY_CODE);
				bean.setInfo(ErrorConstants.ERROR_CANNOT_FIND_KEY_DISP);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			log.error("InstantiationException:",e);
			bean.setStatus(ErrorConstants.ERROR_INSTANTIATION_CODE);
			bean.setInfo(ErrorConstants.ERROR_INSTANTIATION_DISP);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			log.error("IllegalAccessException:",e);
			bean.setStatus(ErrorConstants.ERROR_ILLEGAL_ACCESS_CODE);
			bean.setInfo(ErrorConstants.ERROR_ILLEGAL_ACCESS_DISP);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			log.error("IllegalArgumentException:",e);
			bean.setStatus(ErrorConstants.ERROR_ILLEGAL_ARGUMENT_CODE);
			bean.setInfo(ErrorConstants.ERROR_ILLEGAL_ARGUMENT_DISP);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			log.error("InvocationTargetException:",e);
			 bean.setStatus(ErrorConstants.ERROR_INVOCATION_TARGET_CODE);
			 bean.setInfo(ErrorConstants.ERROR_INVOCATION_TARGET_DISP);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("ParseException:",e);
			bean.setStatus(ErrorConstants.ERROR_PARSE_CODE);
			bean.setInfo(ErrorConstants.ERROR_PARSE_DISP);
		}finally {
			json = JSONObject.toJSONString(bean);
		}
		log.info("返回值  json："+json); 
        return json;
    }


}
