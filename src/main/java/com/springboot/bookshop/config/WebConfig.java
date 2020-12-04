package com.springboot.bookshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot.bookshop.filter.VisitorInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer{

	@Autowired
	VisitorInterceptor visitorInterceptor;


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(visitorInterceptor);
	}
}
