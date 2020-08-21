package com.reforgedsrc.app.vue2demo.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class WebLogAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    ThreadLocal<String> calledMethodName = new ThreadLocal<>();

    @Pointcut("execution(public * com.reforgedsrc.app.vue2demo.boot.web.controller..*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        calledMethodName.set(joinPoint.getSignature().getDeclaringTypeName() +
            "." + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
        log.debug("after CTLR_METHOD : {} called by using ({} ms)", calledMethodName.get(), System.currentTimeMillis() - startTime.get());
    }
}