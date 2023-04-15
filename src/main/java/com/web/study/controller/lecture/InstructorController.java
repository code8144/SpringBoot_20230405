package com.web.study.controller.lecture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.study.dto.DataResponseDto;
import com.web.study.dto.ResponseDto;
import com.web.study.dto.request.instructor.InstructorReqDto;
import com.web.study.service.InstructorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InstructorController {
	
	private final InstructorService instructorService;
	
	@PostMapping("/instructor")
	public ResponseEntity<? extends ResponseDto> registerInstructor(@RequestBody InstructorReqDto instructorReqDto) {

		instructorService.registeInstructor(instructorReqDto);
		
		return ResponseEntity.ok().body(ResponseDto.ofDefault());
	}
	
	//강사전체조회
	@GetMapping("/instructors")
	public ResponseEntity<? extends ResponseDto> getInstructors() {
		return ResponseEntity.ok().body(DataResponseDto.of(instructorService.getInstructorAll()));
	}
	
	//강사 아이디로 선택한 아이디만 찾기
	@GetMapping("/instructor/{id}")
	public ResponseEntity<? extends ResponseDto> getInstructorById(@PathVariable int id) {
		return ResponseEntity.ok().body(DataResponseDto.of(instructorService.findInstructorById(id)));
	}
}









