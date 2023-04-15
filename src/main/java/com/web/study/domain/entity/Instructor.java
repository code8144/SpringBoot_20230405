package com.web.study.domain.entity;

import java.time.LocalDate;

import com.web.study.dto.response.InstructorRespDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
//요청은 setter, 응답은 getter가 필요함 
public class Instructor {
	private int itm_id;
	private String itm_name;
	private LocalDate itm_birth;
	// 카멜 표기법 문제 해결방법
	// responseDto객체를 만드는 방법과 xml에서 as을 만드는 방법이 있음
	// repository에서 return 이 2번 일어나는 경우가 생김 (return 자료형은 1개밖에 쓰지 못함), response는 재사용
	// 불가 -> dto만드는 것이 더 좋은 이유

	public InstructorRespDto toDto() {
		return InstructorRespDto.builder().id(itm_id).name(itm_name).birthDate(itm_birth).build();
	}
}
