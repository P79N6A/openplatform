package com.kd.openplatform.hsf.consumer.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.kd.openplatform.cache.HttpAddrMapping;

@Component
public class HttpAddrMappingFactory {
	
	@Bean
	public HttpAddrMapping httpAddrMapping() {
		
		return new HttpAddrMapping();
	}

}
