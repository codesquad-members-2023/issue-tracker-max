package com.issuetrackermax.common.exception.domain;

import org.springframework.http.HttpStatus;

public interface CustomException {
	HttpStatus getHttpStatus();

	String getMessage();
}
