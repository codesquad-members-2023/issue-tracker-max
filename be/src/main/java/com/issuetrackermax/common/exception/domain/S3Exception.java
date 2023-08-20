package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum S3Exception implements CustomException {
	IMAGE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "이미지 크기가 20MB를 초과하였습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	S3Exception(HttpStatus httpStatus, String message) {
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
