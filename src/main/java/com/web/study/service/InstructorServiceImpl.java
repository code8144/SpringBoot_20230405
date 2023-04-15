package com.web.study.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.study.domain.entity.Instructor;
import com.web.study.dto.request.instructor.InstructorReqDto;
import com.web.study.dto.request.student.StudentReqDto;
import com.web.study.dto.response.InstructorRespDto;
import com.web.study.repository.InstructorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

	private final InstructorRepository instructorRepository;
	
	@Override
	public void registeInstructor(InstructorReqDto instructorReqDto) {
		instructorRepository.saveInstructor(instructorReqDto.toEntity());
	}
	
	//다른 서버에서 들고온 정보들이라 dto로 변환하는 리스트를 새로 만든 다음 dto로 변환 , 이것이 반복으로 돈다
	@Override
	public List<InstructorRespDto> getInstructorAll() {
		List<InstructorRespDto> dtos = new ArrayList<>();
		
		instructorRepository.getInstructorAll().forEach(entity -> {
			dtos.add(entity.toDto());
		});
		
		return dtos;
	}
	
	@Override
	public InstructorRespDto findInstructorById(int id) {
		return instructorRepository.findInstructorById(id).toDto();
	}
}




