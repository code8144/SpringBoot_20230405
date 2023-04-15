package com.web.study.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDto2 {
	private final boolean success;
	private final int code;
	private final String message;

	public static ResponseDto2 of(boolean success, int code, String message) {
		return new ResponseDto2(success, code, message);
	}

}
