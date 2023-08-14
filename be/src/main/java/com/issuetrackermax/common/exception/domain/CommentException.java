package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum CommentException implements CustomException {
	NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "작성자만 수정할 수 있습니다");

	private final HttpStatus httpStatus;
	private final String message;

	CommentException(HttpStatus httpStatus, String message) {
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
