package com.kd.openplatform.hsf.consumer.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.kd.openplatform.cache.RequestInterfaceTotal;

@Component
public class RequestInterfaceTotalFactory {
	
	@Bean
	public RequestInterfaceTotal requestInterfaceTotal() {
		
		return new RequestInterfaceTotal();
	}


}
