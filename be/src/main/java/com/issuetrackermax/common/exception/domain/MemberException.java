package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum MemberException implements CustomException {
	NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	MemberException(HttpStatus httpStatus, String message) {
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
