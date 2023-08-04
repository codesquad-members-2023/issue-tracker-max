package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum HistoryException implements CustomException {
	NOT_FOUND_HISTORY(HttpStatus.NOT_FOUND, "존재하지 않는 히스토리입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	HistoryException(HttpStatus httpStatus, String message) {
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
