package com.web.study.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.study.dto.DataResponseDto;
import com.web.study.dto.ResponseDto;
import com.web.study.dto.request.BasicTestDto2;


@RestController
public class BasicRestContoller2 {
	
	@GetMapping("/lol/get1")	//param 1개
	public ResponseEntity<? extends ResponseDto> lolGet(String lolInfo) {
		return ResponseEntity.ok().body(DataResponseDto.of(lolInfo));
	}
	
	@GetMapping("/lol/get2") //dto 만들어서
	public ResponseEntity<? extends ResponseDto> lolGet2(BasicTestDto2 basicTestDto2) {
		String lolInfo = basicTestDto2.getNickName() + "(" + basicTestDto2.getLevel() + ")" + " : " + basicTestDto2.getTier();
		return ResponseEntity.ok().body(DataResponseDto.of(lolInfo));
	}
	
	@GetMapping("/lol/get3") //dto 없이
	public ResponseEntity<? extends ResponseDto> lolGet3(int level, String tier, String nickName) {
		String lolInfo = nickName + "(" + level + ")" + " : " + tier;
		return ResponseEntity.ok().body(DataResponseDto.of(lolInfo));
	}
	
	// ===============================================================================================
	
	@PostMapping("/lol/post1") //formData로
	public ResponseEntity<? extends ResponseDto> lolPostForm(BasicTestDto2 basicTestDto2) {
		return ResponseEntity.created(null).body(DataResponseDto.of(basicTestDto2));
	}
	
	@PostMapping("/lol/post2") //Json으로
	public ResponseEntity<? extends ResponseDto> lolPostJson(@RequestBody BasicTestDto2 basicTestDto2) {
		return ResponseEntity.created(null).body(DataResponseDto.of(basicTestDto2));
	}
}
