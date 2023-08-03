package com.issuetrackermax.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ErrorResponse apiExceptionHandler(ApiException e) {
		ErrorCode errorCode = new ErrorCode(e.getHttpStatus().value(), e.getMessage());
		return ErrorResponse.exception(errorCode);
	}
}
