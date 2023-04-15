package com.web.study.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.web.study.dto.request.BasicTestDto2;

import lombok.Data;

@Data
public class DataResponseDto2<T> extends ResponseDto2 {
	private final T data;

	private DataResponseDto2(T data) {
		super(true, HttpStatus.OK.value(), "Successfully");
		this.data = data;

	}

	private DataResponseDto2(T data, String message) {
		super(true, HttpStatus.OK.value(), message);
		this.data = data;
	}

	public static <T> DataResponseDto2<T> of(T data) {
		return new DataResponseDto2<T>(data);

	}
	public static <T> DataResponseDto2<T> of(T data, String message){
		return new DataResponseDto2<T>(data, message);
	}
	public static <T> DataResponseDto2<T> empty(){
		return new DataResponseDto2<T>(null);
	}

}
