package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends ApiException {
	public InvalidPasswordException() {
		super(HttpStatus.BAD_REQUEST, "비밀번호는 8글자 이상 입니다.");
	}

}
