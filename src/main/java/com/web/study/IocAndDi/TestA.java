package com.web.study.IocAndDi;

import org.springframework.stereotype.Component;

@Component("t1")
public class TestA implements Test {
	
	public void printTest() {
		System.out.println("TestA 클래스");
	}

}
