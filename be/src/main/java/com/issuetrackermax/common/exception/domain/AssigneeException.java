package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum AssigneeException implements CustomException {
	NOT_FOUND_ASSIGNEE(HttpStatus.NOT_FOUND, "존재하지 않는 담당자입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	AssigneeException(HttpStatus httpStatus, String message) {
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
