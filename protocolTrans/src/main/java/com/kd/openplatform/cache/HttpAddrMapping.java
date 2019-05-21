package com.kd.openplatform.cache;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kd.openplatform.bean.ColumnsTypeBean;
import com.kd.openplatform.hsf.consumer.bean.ConsumerBean;


public class HttpAddrMapping {
	
	//http 地址和hsf地址映射
	private ConcurrentHashMap<String, ConsumerBean> httpAddr_HsfAddr = new ConcurrentHashMap<>();
	//hsf地址对应的类
	private ConcurrentHashMap<String, Object> HsfAddr_ObjectClass = new ConcurrentHashMap<>();
	//每个类里的方法
	private ConcurrentHashMap<String, ConcurrentHashMap<String,Method>> ObjectClass_Method = new ConcurrentHashMap<>();
	
	//数据库中方法配置的类型
	private Map<String, List<ColumnsTypeBean>> methodKey_Type = new ConcurrentHashMap<>();

	private Map<String,Map<String,String>> appKeys = new ConcurrentHashMap<>();
	
	public ConcurrentHashMap<String, ConsumerBean> getHttpAddr_HsfAddr() {
		return httpAddr_HsfAddr;
	}
	public ConcurrentHashMap<String, Object> getHsfAddr_ObjectClass() {
		return HsfAddr_ObjectClass;
	}
	public ConcurrentHashMap<String, ConcurrentHashMap<String, Method>> getObjectClass_Method() {
		return ObjectClass_Method;
	}
	public Map<String, List<ColumnsTypeBean>> getMethodKey_Type() {
		return methodKey_Type;
	}

	public Map<String, Map<String, String>> getAppKeys() {
		return appKeys;
	}
}
