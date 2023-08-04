package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public enum MilestoneException implements CustomException {
	NOT_FOUND_MILESTONE(HttpStatus.NOT_FOUND, "존재하지 않는 마일스톤입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	MilestoneException(HttpStatus httpStatus, String message) {
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
