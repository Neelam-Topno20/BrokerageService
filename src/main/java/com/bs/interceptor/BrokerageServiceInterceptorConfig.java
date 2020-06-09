package com.bs.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class BrokerageServiceInterceptorConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	BrokerageServiceInterceptor brokerageServiceInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(brokerageServiceInterceptor);
	}


}
