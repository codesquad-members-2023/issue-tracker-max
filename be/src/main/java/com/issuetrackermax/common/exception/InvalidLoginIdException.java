package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidLoginIdException extends ApiException {
	public InvalidLoginIdException() {
		super(HttpStatus.BAD_REQUEST, "같은 아이디가 존재합니다.");
	}
}
