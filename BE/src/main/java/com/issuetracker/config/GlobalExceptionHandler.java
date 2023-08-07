package com.issuetracker.config;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.issuetracker.config.exception.CustomHttpException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodValidHandler(MethodArgumentNotValidException e) {
		String messages = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining(", ", "[", "]"));
		return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, messages));
	}

	@ExceptionHandler(CustomHttpException.class)
	public ResponseEntity<ErrorResponse> apiHandler(CustomHttpException e) {
		return ResponseEntity.status(e.getHttpStatus())
			.body(new ErrorResponse(e.getHttpStatus(), e.getMessage()));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ErrorResponse(HttpStatus.NOT_FOUND, "해당 API 경로로는 요청할 수 없습니다."));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError()
			.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다"));
	}
}
