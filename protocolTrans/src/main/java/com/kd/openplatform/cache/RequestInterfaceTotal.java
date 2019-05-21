package com.kd.openplatform.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestInterfaceTotal {
	
	//存储APP调用接口的次数《appid，count》  以后采用redis存储
	private Map<String,Map<String,Long>> appRequestTotalMap = new ConcurrentHashMap<>();

	public Map<String,Map<String,Long>> getAppRequestTotalMap() {
		return appRequestTotalMap;
	}
	
	

}
