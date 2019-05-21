package com.kd.op.api.service.impl;
import com.kd.op.api.service.ApiInvokeLogServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.kd.op.api.entity.ApiInvokeLogEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.service.SystemService;

@Service("apiInvokeLogService")
@Transactional
public class ApiInvokeLogServiceImpl extends CommonServiceImpl implements ApiInvokeLogServiceI {
	
	@Autowired
	private SystemService systemService;
 	public void delete(ApiInvokeLogEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public void save(ApiInvokeLogEntity entity) throws Exception{
 		systemService.save(entity);
 	}
 	
 	public void saveOrUpdate(ApiInvokeLogEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
 	private Map<String,Object> populationMap(ApiInvokeLogEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("api_id", t.getApiId());
		map.put("api_name", t.getApiName());
		map.put("app_id", t.getAppId());
		map.put("app_name", t.getAppName());
		map.put("invoke_time", t.getInvokeTime());
		map.put("invoke_ip", t.getInvokeIp());
		map.put("method_type", t.getMethodType());
		map.put("invoke_url", t.getInvokeUrl());
		map.put("response_time_length", t.getResponseTimeLength());
		map.put("response_flow_size", t.getResponseFlowSize());
		map.put("request_flow_size", t.getRequestFlowSize());
		map.put("request_header", t.getRequestHeader());
		map.put("request_param", t.getRequestParam());
		map.put("return_param", t.getReturnParam());
		map.put("return_header", t.getReturnHeader());
		map.put("invoke_openplatform_result", t.getInvokeOpenplatformResult());
		map.put("invoke_serviceprovider_result", t.getInvokeServiceproviderResult());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,ApiInvokeLogEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{api_id}",String.valueOf(t.getApiId()));
		sql  = sql.replace("#{api_name}",String.valueOf(t.getApiName()));
		sql  = sql.replace("#{scene_id}",String.valueOf(t.getSceneId()));
		sql  = sql.replace("#{scene_name}",String.valueOf(t.getSceneName()));
 		sql  = sql.replace("#{app_id}",String.valueOf(t.getAppId()));
 		sql  = sql.replace("#{app_name}",String.valueOf(t.getAppName()));
 		sql  = sql.replace("#{invoke_time}",String.valueOf(t.getInvokeTime()));
 		sql  = sql.replace("#{invoke_ip}",String.valueOf(t.getInvokeIp()));
 		sql  = sql.replace("#{method_type}",String.valueOf(t.getMethodType()));
 		sql  = sql.replace("#{invoke_url}",String.valueOf(t.getInvokeUrl()));
 		sql  = sql.replace("#{response_time_length}",String.valueOf(t.getResponseTimeLength()));
 		sql  = sql.replace("#{response_flow_size}",String.valueOf(t.getResponseFlowSize()));
 		sql  = sql.replace("#{request_flow_size}",String.valueOf(t.getRequestFlowSize()));
 		sql  = sql.replace("#{request_header}",String.valueOf(t.getRequestHeader()));
 		sql  = sql.replace("#{request_param}",String.valueOf(t.getRequestParam()));
 		sql  = sql.replace("#{return_param}",String.valueOf(t.getReturnParam()));
 		sql  = sql.replace("#{return_header}",String.valueOf(t.getReturnHeader()));
 		sql  = sql.replace("#{invoke_openplatform_result}",String.valueOf(t.getInvokeOpenplatformResult()));
 		sql  = sql.replace("#{invoke_serviceprovider_result}",String.valueOf(t.getInvokeServiceproviderResult()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("api_invoke_log",data);
				}
			} catch (Exception e) {
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}