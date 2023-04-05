package com.web.study.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.study.dto.DataResponseDto;
import com.web.study.dto.ResponseDto;
import com.web.study.dto.request.BasicTestDto;

import lombok.Data;

@Data
class Params {
	private int age;
	private String name;
}

@RestController
public class BasicRestController {
	
	// GET 요청의 param을 처리하는 방법
	@GetMapping("/read")
	public ResponseEntity<? extends ResponseDto> read(BasicTestDto basicTestDto) {
		
		String userInfo = basicTestDto.getName() + "(" + basicTestDto.getAge() + ")";
		
		return ResponseEntity.ok().body(DataResponseDto.of(userInfo));
		//ResponseEntity를 쓰는 이유는 응답하는 데이터(status, header, body)의 상태를 설정하기 위해 사용
	}
	
	@GetMapping("/read2/{id}")
	public ResponseEntity<? extends ResponseDto> read2(@PathVariable("id") int id) {
		Map<Integer, String> userMap = new HashMap<>();
		
		userMap.put(1, "김상현");
		userMap.put(2, "강의진");
		userMap.put(3, "김종환");
		userMap.put(4, "진채희");
		userMap.put(5, "박은빈");
		
		return ResponseEntity.ok().body(DataResponseDto.of(userMap.get(id)));
	}
	
	// Post요청의 데이터 처리
	@PostMapping("/create")
	public ResponseEntity<? extends ResponseDto> create(@RequestBody BasicTestDto basicTestDto) {
		//@RequestBody 를 써야 Json으로 post받을수있음
		System.out.println("데이터 추가");
		
		return ResponseEntity.created(null).body(DataResponseDto.of(basicTestDto));
	}
}
