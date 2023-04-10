package com.web.study.service;

import org.springframework.stereotype.Service;

import com.web.study.domain.entity.Course;
import com.web.study.dto.request.course.CourseReqDto;
import com.web.study.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
	
	private final CourseRepository courseRepository;

	@Override
	public void registCourse(CourseReqDto courseReqDto) {
		Course course = courseReqDto.toEntity();
		courseRepository.registCourse(course);
		
	}

}
