package com.issuetrackermax.common.exception;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.issuetrackermax.common.exception.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ErrorResponse apiExceptionHandler(ApiException e) {
		return new ErrorResponse(e.getCustomException().getHttpStatus().value(), e.getMessage());
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse validationExceptionHandler(MethodArgumentNotValidException e) {
		String message = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("유효하지 않은 값입니다."))
			.findAny()
			.orElseThrow();
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
	}
}
