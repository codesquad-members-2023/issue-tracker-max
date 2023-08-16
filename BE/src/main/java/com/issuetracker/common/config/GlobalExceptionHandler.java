package com.issuetracker.common.config;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;

import io.jsonwebtoken.ExpiredJwtException;

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
	public ResponseEntity<ErrorResponse> customHandler(CustomHttpException e) {
		return ResponseEntity.status(e.getHttpStatus())
			.body(new ErrorResponse(e.getHttpStatus(), e.getMessage()));
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> maxUploadSizeHandler(MaxUploadSizeExceededException e) {
		ErrorType errorType = ErrorType.FILE_UPLOAD_MAX_SIZE;
		return ResponseEntity.status(errorType.getStatus())
			.body(new ErrorResponse(errorType.getStatus(), errorType.getMessage()));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ErrorResponse(HttpStatus.NOT_FOUND, "해당 API 경로로는 요청할 수 없습니다."));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return ResponseEntity.badRequest().body(new ErrorResponse(
			HttpStatus.BAD_REQUEST,
			"JSON 필드 값은 String, Number, Array, Object 객체 또는 'null', 'true', 'false'만 입력 가능합니다.")
		);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> expiredJwtException(ExpiredJwtException e) {
		return new ResponseEntity<ErrorResponse>(
			new ErrorResponse(HttpStatus.UNAUTHORIZED, "기한이 만료된 토큰입니다."),
			HttpStatus.UNAUTHORIZED
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
		e.printStackTrace();
		return ResponseEntity.internalServerError()
			.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다"));
	}
}
