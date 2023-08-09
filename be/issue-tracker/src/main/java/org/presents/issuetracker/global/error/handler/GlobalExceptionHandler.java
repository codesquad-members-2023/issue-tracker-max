package org.presents.issuetracker.global.error.handler;

import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.response.CommonApiResponse;
import org.presents.issuetracker.global.error.statuscode.ErrorCode;
import org.presents.issuetracker.global.error.statuscode.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// CustomException 으로 발생한 예외 처리
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<CommonApiResponse> handleCustomException(CustomException ex) {
		StatusCode statusCode = ex.getStatusCode();
		log.info("CustomException handling: {}", ex.toString());
		return ResponseEntity.status(statusCode.getHttpStatus()).body(CommonApiResponse.fail(statusCode.getHttpStatus(),
			statusCode.getMessage()));
	}

	// 404 PAGE_NOT_FOUND 예외
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		ErrorCode errorCode = ErrorCode.PAGE_NOT_FOUND;
		log.info("NoHandlerFoundException handling: {}", ex.toString());
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(CommonApiResponse.fail(errorCode.getHttpStatus(), errorCode.getMessage()));
	}

	// 데이터 베이스 오류
	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<CommonApiResponse> handleDataAccessException(DataAccessException ex) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR_DB;
		log.info("DataAccessException handling: {}", ex.toString());
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(CommonApiResponse.fail(errorCode.getHttpStatus(), errorCode.getMessage()));
	}

	// 500 에러
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<CommonApiResponse> handleException(Exception ex) {
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		log.info("Exception handling: {}", ex.toString());
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(CommonApiResponse.fail(errorCode.getHttpStatus(), errorCode.getMessage()));
	}

	// @Valid 예외 처리
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
		log.info("MethodArgumentNotValidException handling: {}", ex.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(CommonApiResponse.fail(errorCode.getHttpStatus(), errorCode.getMessage()));
	}
}
