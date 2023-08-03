package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidIssueStatusException extends ApiException {
	public InvalidIssueStatusException() {
		super(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다.");
	}
}
