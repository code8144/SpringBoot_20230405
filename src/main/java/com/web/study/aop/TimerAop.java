package com.web.study.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class TimerAop {

//	private final Logger logger = LogManager.getLogger(TimerAop.class);

	// 접근지정자 public 은 생략이 가능하다
	@Pointcut("execution( * com.web.study..*.*(..))")
	// .. = 매개변수가 비어있음 , * = 전체
	private void pointCut() {
	}

	@Pointcut("@annotation(com.web.study.aop.annotation.TimerAspect)")
	private void annotationPointCut() {
	}

	@Around("annotationPointCut()&&pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		// 데이터 베이스까지 갔다가 돌아오는 시간 기록
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();

		// 전처리
		Object logic = joinPoint.proceed(); // proceed = 메소드 호출

		// 후처리

		stopwatch.stop();
		log.info("[ Time ]>>> {}.{}: {}초", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), stopwatch.getTotalTimeSeconds());

		return logic;

	}

}
