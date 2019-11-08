
package com.reforgedsrc.app.vue2demo.boot.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Pointcut("execution(public * com.wiz.app.vue2.boot.rest.controller..*.*(..))")
	public void webLog() {

	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		log.debug("before CTLR_METHOD : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
	}

	@AfterReturning(pointcut="webLog()", returning="ret")
	public void doAfterReturning(Object ret) throws Throwable {
		
	}
}