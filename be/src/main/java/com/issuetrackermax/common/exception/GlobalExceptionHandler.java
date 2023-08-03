package com.issuetrackermax.common.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ErrorResponse apiExceptionHandler(ApiException e) {
		ErrorCode errorCode = new ErrorCode(e.getHttpStatus().value(), e.getMessage());
		return ErrorResponse.exception(errorCode);
	}

	public ErrorResponse validationExceptionHandler(MethodArgumentNotValidException e) {
		String message = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("유효하지 않은 값입니다."))
			.findAny()
			.orElseThrow();
		ErrorCode errorCode = new ErrorCode(HttpStatus.BAD_REQUEST.value(), message);
		return ErrorResponse.exception(errorCode);
	}
}
