package com.ws.forum.aspect;
import java.lang.reflect.Method;
import java.util.Date;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.forum.annotation.RequiredLog;
import com.ws.forum.pojo.Log;
import com.ws.forum.service.LogService;
import com.ws.forum.util.IPUtils;
import com.ws.forum.util.ShiroUtils;

import lombok.extern.slf4j.Slf4j;
/**
 * @Aspect 用于描述切面类型
 * @Component 用于描述交给spring管理的对象
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {//代理对象调用此对象
	@Autowired
	private LogService logService;

	@Pointcut("@annotation(com.ws.forum.annotation.RequiredLog)")
	public void doPointCut() {}
	
	@Around("doPointCut()")
	public Object around(ProceedingJoinPoint jp)throws Throwable{
		try {
			long t1=System.currentTimeMillis();
			Object result=jp.proceed();
			long t2=System.currentTimeMillis();
			log.info("method execute time :"+(t2-t1));
			//保存用户行为日志
			saveUserLog(jp,(t2-t1));
			return result;
		}catch(Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	/**保存用户行为日志信息*/
	private void saveUserLog(ProceedingJoinPoint jp,long time)throws Exception {
		//1.获取用户行为日志
		//1.1获取目标对象
		Class<?> targetCls=jp.getTarget().getClass();
		//1.2获取目标方法签名信息(包含方法名,参数列表等信息)
		//1.2.1获取方法(类名+方法名)
		MethodSignature ms=(MethodSignature)jp.getSignature();
		Method interfaceMethod=ms.getMethod();
		String methodName=interfaceMethod.getName();
		String clsMethodName=targetCls.getName()+"."+methodName;
		//1.2.2 获取方法参数(实际参数)
		System.out.println("clsMethodName="+clsMethodName);
		ObjectMapper om=new ObjectMapper();//jackson
		String params=om.writeValueAsString(jp.getArgs());//json
		//1.2.3获取注解RequiredLog
		Method targetMethod=
		targetCls.getMethod(methodName,ms.getParameterTypes());
		RequiredLog requiredLog=
		targetMethod.getAnnotation(RequiredLog.class);
		String operation=requiredLog.value();
		//2.封装用户行为日志
		 Log entity=new Log()
		.setUsername(ShiroUtils.getUsername())//登录使用的用户名
		.setOperation(operation)
		.setMethod(clsMethodName)//method=类全名+方法名
		.setParams(params)
		.setTime(time)
		.setIp(IPUtils.getIpAddr())
		.setCreatedTime(new Date());
		System.out.println("log.aspect:"+Thread.currentThread().getName());
		logService.saveObject(entity);
	 
	}
}








