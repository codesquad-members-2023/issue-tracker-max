package org.presents.issuetracker.global.error.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonApiResponse {
	// ResponseEntity의 body에 담기 위해 사용
	private int statusCode;
	private String message;

	public static CommonApiResponse fail(HttpStatus statusCode, String message) {
		return new CommonApiResponse(statusCode.value(), message);
	}
}
