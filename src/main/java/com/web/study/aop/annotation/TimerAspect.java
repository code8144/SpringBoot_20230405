package com.web.study.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)		//실행되면 이 어노테이션을 적용시켜라.
@Target({ElementType.METHOD})	//메소드에다 달수있도록 만들어주는 어노테이션
public @interface TimerAspect {

}
