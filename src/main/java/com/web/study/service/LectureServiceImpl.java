package com.web.study.service;

import org.springframework.stereotype.Service;

import com.web.study.domain.entity.Lecture;
import com.web.study.dto.request.lecture.LectureReqDto;
import com.web.study.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@Service	//Component의 종류 중 하나
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {
	
	// final => 상수: 초기화가 무조건 일어나야함.
	private final LectureRepository lectureRepository;

	@Override
	public void registLecture(LectureReqDto lectureReqDto) {
		// Dto -> Entity로 변환되는 과정
		Lecture lecture = lectureReqDto.toEntity();
		System.out.println("변환: " + lecture);
		lectureRepository.regist(lecture);
		
	}
	

}
