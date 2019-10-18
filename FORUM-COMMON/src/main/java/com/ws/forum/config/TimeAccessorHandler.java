package com.ws.forum.config;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ws.forum.exception.ServiceException;


public class TimeAccessorHandler implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Calendar c = Calendar.getInstance();
		//设置起始时间
		c.set(Calendar.HOUR_OF_DAY,6);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND,0);
		long start = c.getTimeInMillis();
		//设置结束时间
		c.set(Calendar.HOUR_OF_DAY, 22);
		long end = c.getTimeInMillis();
		//获取当前时间
		long nowTime = System.currentTimeMillis();
		if(nowTime < start || nowTime > end) {
			throw new ServiceException("不在服务时间段内");
		}
		return true;
	}
	
}
