package com.web.study.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class CheckNameAop {
	
	@Pointcut("@annotation(com.web.study.aop.annotation.CheckNameAspect)")
	private void annotationPoint() {};
	
	@Around("annotationPoint()")
	public Object check(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object logic = joinPoint.proceed();
		
		stopWatch.stop();
		
		log.info("[ name ] >>> {}.{}",		// Slf4j 어노테이션을 쓰면 log를 쓸 수 있다.
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName());
		
		return logic;
	}

}
