package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum IssueException implements CustomException {
	NOT_FOUND_ISSUE(HttpStatus.NOT_FOUND, "존재하지 않는 이슈입니다."),
	INVALID_ISSUE_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	IssueException(HttpStatus httpStatus, String message) {
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
