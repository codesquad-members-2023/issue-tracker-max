package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum LoginException implements CustomException {
	INVALID_LOGIN_ID(HttpStatus.BAD_REQUEST, "같은 아이디가 존재합니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호는 8글자 이상 입니다."),
	INCORRECT_PASSWORD(HttpStatus.BAD_REQUEST, "올바르지 않은 패스워드 입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	LoginException(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
