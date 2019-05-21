package com.kd.openplatform.services;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.cache.HttpAddrMapping;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

public interface OpenPlatformBaseService {

	public Object hsfInterfaceService(String path, JSONObject args)throws InstantiationException, IllegalAccessException,ParseException,IllegalArgumentException, InvocationTargetException;
	
	public  void loadAddrMapping(String path); 
	
	public HttpAddrMapping getHttpAddrMapping();

	public Map<String,String> getKeysByAppId(String appId);
}
