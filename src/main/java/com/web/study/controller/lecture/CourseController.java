package com.web.study.controller.lecture;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.study.aop.annotation.CheckNameAspect;
import com.web.study.aop.annotation.ParamsAspect;
import com.web.study.aop.annotation.TimerAspect;
import com.web.study.aop.annotation.ValidAspect;
import com.web.study.dto.DataResponseDto;
import com.web.study.dto.ResponseDto;
import com.web.study.dto.request.course.CourseReqDto;
import com.web.study.dto.request.course.SearchCourseReqDto;

import com.web.study.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CourseController {

	private final CourseService courseService;

	@PostMapping("/course")
	public ResponseEntity<? extends ResponseDto> registeCourse(@RequestBody CourseReqDto courseReqDto) {
		courseService.registeCourse(courseReqDto);
		return ResponseEntity.ok().body(ResponseDto.ofDefault());
	}

	@CheckNameAspect
	@TimerAspect
	@GetMapping("/courses")
	public ResponseEntity<? extends ResponseDto> getCourseAll() {

		return ResponseEntity.ok().body(DataResponseDto.of(courseService.getCourseAll()));
	}

	@ValidAspect
	@ParamsAspect
	@GetMapping("/search/courses")
	

	
	/*
	 * 	http 통신: 요청과 응답을 해주는 것이 주 목적 , 데이터 베이스에 어떻게 데이터를 입력, 수정, 삭제를 할지가 중요
	프로토콜 = 규약, 약속, tomcat이 데이터를 받음 , 이때 servlet이라는 동적 웹 브라우저라는 것을 사용 -> 요청할때 
	 servlet사용전에는 전처리 후처리든 tomcat을 거쳐서 필터를 사용해서 요청과 응답이 이루어짐
	 요청과 응답에는 각각  header와 body가 있는데 파라미터가 바디임. 
	 컨트롤러는 클래스 형식으로 이루어져있는데 여러가지를 처리할수 있는 메소드로 이루어짐 / 서블릿 하나당 하나의 요청을 처리할 수 있음 (메소드 1개당 url 1개, 하나의 클래스안에서 세분화되어 일처리)
	 메소드는 호출과 리턴(응답)이 있음(입력(요청)과 출력) 
	responseEntity는 응답할 때 사용, 응답되어질때는 응답이 제대로 되었는지, 요청이 제대로 들어왔는지 확인 할수 있는 status(상태코드)가 필요
	 상태코드는 마음대로 바꿀수 없음 , 객체를 수정하면 바꿀수 있지만 헤더를 각각 다 수정해야함으로 responseEntity를 사용(스프링에서만 지원)
	 결과적으로 리스폰스 엔티티는 상태코드와 데이터응답을 담을 수 있음, 어떤걸로 응답할지 모르니 ?로 준다 , 명확한것을 넣어주면 코드를 짤때 일일이 그것에 대해 신경을 써야하기 때문에 제네릭을 사용
	 * 
	 */
	
	public ResponseEntity<? extends ResponseDto> searchCourse(@Valid SearchCourseReqDto searchCourseReqDto,
			BindingResult bindingResult) {
		// 객체와 함께 업캐스팅으로 들고와졌음
		// bindingResult 이것은 interface, interface는 생성을 못하니 다른 객체가 들어옴 그게 나타난 것이
		// BeanPropertyBindingResult

//		if (bindingResult.hasErrors()) {
//			Map<String, String> errorMap = new HashMap<>();
//
//			bindingResult.getFieldErrors().forEach(error -> {
//				errorMap.put(error.getField(), error.getDefaultMessage());
//
//			});
//
//			throw new CustomException("유효성 검사 실패", errorMap);
//		}

		return ResponseEntity.ok().body(DataResponseDto
				.of(courseService.searchCourse(searchCourseReqDto.getType(), searchCourseReqDto.getSearchValue())));
	}
}
