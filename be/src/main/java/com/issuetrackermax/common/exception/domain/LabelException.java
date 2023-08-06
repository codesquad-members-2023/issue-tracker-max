package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum LabelException implements CustomException {
	NOT_FOUND_LABEL(HttpStatus.NOT_FOUND, "존재하지 않는 라벨입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	LabelException(HttpStatus httpStatus, String message) {
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
