package com.web.study.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.study.dto.DataResponseDto;
import com.web.study.dto.ErrorResponseDto;
import com.web.study.dto.ResponseDto;

				//RestController는 data를 return하는 용도
@RestController	//Controller는 view를 return하는 용도
				/*
				 * Controller는 view만 return가능하기때문에 controller를 쓸때 data를 응답하기 위해서는 
				 * ResponseBody를 항상 써 주어야한다.
				 * */
public class BasicController {
	
	@GetMapping("/view/test")
	public ResponseEntity<? extends ResponseDto> view() {
		
		List<String> strList = new ArrayList<>();
		strList.add("a");
		strList.add("b");
		strList.add("c");
		strList.add("d");
		strList.add("e");
		
		if(strList.contains("e")) {
			try {
				throw new RuntimeException();
			} catch(Exception e) {
				return ResponseEntity.internalServerError().body(ErrorResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, e));
				
			}
		}
		
		return ResponseEntity.ok(DataResponseDto.of(strList));
	}
}
