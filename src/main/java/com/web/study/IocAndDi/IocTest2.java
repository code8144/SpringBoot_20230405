package com.web.study.IocAndDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class IocTest2 {
	
	@Qualifier("testC")	//Component Annotation 중복 사용가능하게
	@Autowired	
	private Test test;
	
//	public IocTest(Test test) {
//		this.test = test;
//	}
	
	public void run() {
		test.printTest();
		System.out.println("IoCTest2 출력");
	}

}
