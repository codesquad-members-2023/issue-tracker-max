package com.issuetracker.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.issuetracker.config.exception.ApiException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodValidHandler(MethodArgumentNotValidException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponse> bindHandler(BindException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage())); // TODO: 메세지 새로 작성해주기
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> apiHandler(ApiException e) {
		return ResponseEntity.status(e.getStatus()).body(new ErrorResponse(e.getStatus(), e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다"));
	}

}
