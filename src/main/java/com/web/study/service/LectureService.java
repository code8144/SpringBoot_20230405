package com.web.study.service;

import java.util.List;

import com.web.study.domain.entity.Lecture;
import com.web.study.dto.request.lecture.LectureReqDto;
import com.web.study.dto.response.LectureRespDto;

public interface LectureService {
	
	public void registLecture(LectureReqDto lectureReqDto);
	public List<LectureRespDto> getLectureAll();
	public Lecture findLectureById(int id);

}
