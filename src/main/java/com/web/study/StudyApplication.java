package com.web.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.web.study.IocAndDi.IocTest;
import com.web.study.IocAndDi.TestA;
import com.web.study.IocAndDi.TestB;
import com.web.study.IocAndDi.TestC;

@SpringBootApplication
public class StudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
//		iocAndDiTest();
	}
	
//	public static void iocAndDiTest() {
//		// Inversion Of Control
//		IocTest iocTest = new IocTest(new TestB());
//		iocTest.run();
//	}

}
