package com.web.study.service;

import java.util.List;

import com.web.study.dto.request.course.CourseReqDto;
import com.web.study.dto.response.CourseRespDto;

public interface CourseService {
	public void registCourse(CourseReqDto courseReqDto);
	public List<CourseRespDto> getCourseAll();
	public List<CourseRespDto> searchCourse(int type, String searchValue);
}
