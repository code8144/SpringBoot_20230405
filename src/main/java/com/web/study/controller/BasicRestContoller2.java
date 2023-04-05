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
import com.web.study.dto.request.BasicTestDto2;


@RestController
public class BasicRestContoller2 {
	
	@GetMapping("/fuck")
	public ResponseEntity<? extends ResponseDto> fuck(BasicTestDto2 basicTestDto2) {
		String chatInfo = basicTestDto2.getUserId() + "(" + basicTestDto2.getMessage() + ")";
		return ResponseEntity.ok().body(DataResponseDto.of(chatInfo));
	}
	
	@GetMapping("/fuck2/{userId}")
	public ResponseEntity<? extends ResponseDto> fuck2(@PathVariable("userId")int userId) {
		Map<Integer, String> chatInfoMap = new HashMap<>();
		
		chatInfoMap.put(1, "ㅎㅇ");
		chatInfoMap.put(2, "ㅎㅇ2");
		chatInfoMap.put(3, "ㅎㅇ3");
		chatInfoMap.put(4, "ㅎㅇ4");
		chatInfoMap.put(5, "ㅎㅇ5");
		
		return ResponseEntity.ok().body(DataResponseDto.of(chatInfoMap.get(userId)));
	}
	
	@PostMapping("/create2")
	public ResponseEntity<? extends ResponseDto> create2(@RequestBody BasicTestDto2 basicTestDto2) {
		return ResponseEntity.created(null).body(DataResponseDto.of(basicTestDto2));
	}

}
