package com.issuetrackermax.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.issuetrackermax.controller.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ApiResponse<ErrorResponse> apiExceptionHandler(ApiException e) {
		ErrorCode errorCode = new ErrorCode(e.getHttpStatus().value(), e.getMessage());
		return ApiResponse.exception(ErrorResponse.exception(errorCode));
	}
}
