package com.ws.forum.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer{
	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> newFilterRegistrationBean() {
		//构建过滤器对象
		FilterRegistrationBean<DelegatingFilterProxy> fgBean = 
				new FilterRegistrationBean<>();
		//注册过滤器
		DelegatingFilterProxy filter = 
				new DelegatingFilterProxy("shiroFilterFactory");
		//设置过滤器
		fgBean.setFilter(filter);
		//添加过滤器配置
		fgBean.addUrlPatterns("/*");
		return fgBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TimeAccessorHandler())
		.addPathPatterns("/user/doLogin");
	}
	
	
}
