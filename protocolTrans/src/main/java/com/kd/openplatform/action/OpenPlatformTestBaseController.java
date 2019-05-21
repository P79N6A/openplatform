package com.kd.openplatform.action;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.cache.HttpAddrMapping;
import com.kd.openplatform.cache.RequestInterfaceTotal;
import com.kd.openplatform.hsf.consumer.bean.ConsumerBean;
import com.kd.openplatform.info.InfoBean;
import com.kd.openplatform.quartzwork.InitServlet;
import com.kd.openplatform.services.OpenPlatformBaseService;
import com.kd.openplatform.util.AESUtil;
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
@RequestMapping("/testopenapi")
@Controller
public class OpenPlatformTestBaseController {
	
	private static final Log log =  LogFactory.getLog(OpenPlatformTestBaseController.class);

	@Autowired
	private OpenPlatformBaseService openPlatformBaseService;

	@RequestMapping(value="/{path}",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String baseAction(@PathVariable("path") String path,HttpServletRequest request)
    {
    	log.info("=======================");
		if(path.indexOf("&")!=-1) {
			path = path.substring(0, path.indexOf("&"));
		}
		String argsStr =  request.getParameter("args");
//		log.info("用户传过来的参数--OpenPlatformBaseController --argsStr==== "+argsStr);
		String json = "";
		InfoBean bean = new InfoBean();
		bean.setStatus(0);
		bean.setInfo("能力平台调用服务成功");
		if(argsStr==null||"null".equalsIgnoreCase(argsStr)) {
			bean.setStatus(1);
			bean.setInfo("参数不正确");
			json = JSONObject.toJSONString(bean);
			log.info(json);
			return json;
		}

		JSONObject args = JSONObject.parseObject(argsStr);
		log.info("访问路径：："+path);
		
		HttpAddrMapping httpAddrMapping = openPlatformBaseService.getHttpAddrMapping();
		
		if(!httpAddrMapping.getHttpAddr_HsfAddr().containsKey(path)) {
			synchronized (this) {
				openPlatformBaseService.loadAddrMapping(path);
			}
		}
		ConsumerBean consumerBean = httpAddrMapping.getHttpAddr_HsfAddr().get(path);
		
		if(consumerBean == null) {
			 bean.setStatus(1);
			 bean.setInfo("能力平台未找到服务");
			 json = JSONObject.toJSONString(bean);
			 log.info(json);
			 return json;
		}

		try {
			JSONObject jsonObject = JSONObject.parseObject(args.toString());
			Object res = openPlatformBaseService.hsfInterfaceService(path, jsonObject);
			bean.setData(res);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			log.error("InstantiationException:",e);
			bean.setStatus(1);
				bean.setInfo("能力平台未找到服务"+e.getMessage());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				log.error("IllegalAccessException:",e);
				bean.setStatus(1);
				bean.setInfo("能力平台未找到服务"+e.getMessage());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				log.error("IllegalArgumentException:",e);
				bean.setStatus(1);
				bean.setInfo("能力平台未找到服务"+e.getMessage());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				log.error("InvocationTargetException:",e);
				 bean.setStatus(1);
				 bean.setInfo("能力平台未找到服务"+e.getMessage());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error("ParseException:",e);
				bean.setStatus(1);
				bean.setInfo("能力平台未找到服务"+e.getMessage());
			}finally {
				json = JSONObject.toJSONString(bean);
			}
			log.info("返回值  json："+json);
			return json;
		}


	}
