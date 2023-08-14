package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum LabelException implements CustomException {
	NOT_FOUND_LABEL(HttpStatus.NOT_FOUND, "존재하지 않는 라벨입니다."),
	INVALID_LABEL_TITLE(HttpStatus.BAD_REQUEST, "같은 제목의 라벨이 존재합니다.");

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
