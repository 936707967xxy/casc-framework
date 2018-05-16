/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.hoomsun.core.util.LoggerUtil;

/**
 * 作者：liusong <br>
 * 创建时间：2017年10月10日 <br>
 * 描述：spring aop 拦截所有controller
 */

@Aspect
public class LoggerAspect {

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private MongoTemplate mongoTemplate;

	public LoggerAspect() {
	}

	// 声明一个切入点
	@Pointcut("execution(* com.hoomsun.hsfs.controller.*.*.*(..))")
	private void anyMethod() {
	}

	// 声明前置通知
	@Before("anyMethod()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		LoggerUtil push = new LoggerUtil(joinPoint, mongoTemplate);
		threadPoolTaskExecutor.execute(push);
	}

}
