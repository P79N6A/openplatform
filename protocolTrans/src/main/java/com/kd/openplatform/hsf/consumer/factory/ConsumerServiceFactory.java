package com.kd.openplatform.hsf.consumer.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.taobao.hsf.lightapi.ServiceFactory;

@Component
public class ConsumerServiceFactory {
	
	@Bean
	public ServiceFactory consumerService() {
		
		return ServiceFactory.getInstance();
	}

}
