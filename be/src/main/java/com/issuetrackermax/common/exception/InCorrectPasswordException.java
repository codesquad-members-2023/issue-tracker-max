package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class InCorrectPasswordException extends ApiException {
	public InCorrectPasswordException() {
		super(HttpStatus.BAD_REQUEST, "올바르지 않은 패스워드 입니다.");
	}
}
