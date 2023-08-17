package com.issuetracker.common.config;

import static com.issuetracker.common.config.exception.ErrorType.EXPIRED_TOKEN;
import static com.issuetracker.common.config.exception.ErrorType.FILE_UPLOAD_MAX_SIZE;
import static com.issuetracker.common.config.exception.ErrorType.HTTP_MESSAGE_NOT_READABLE;
import static com.issuetracker.common.config.exception.ErrorType.NO_HANDLER_FOUND;
import static com.issuetracker.common.config.exception.ErrorType.SERVER_ERROR;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodValidHandler(MethodArgumentNotValidException e) {
		LOGGER.error("MethodArgumentNotValidException: ", e);
		String messages = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining(", ", "[", "]"));
		return ResponseEntity.badRequest().body(new ErrorResponse(messages));
	}

	@ExceptionHandler(CustomHttpException.class)
	public ResponseEntity<ErrorResponse> customHandler(CustomHttpException e) {
		LOGGER.error("CustomHttpException: ", e);
		return ResponseEntity.status(e.getHttpStatus())
			.body(new ErrorResponse(e.getErrorType()));
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> maxUploadSizeHandler(MaxUploadSizeExceededException e) {
		LOGGER.error("MaxUploadSizeExceededException: ", e);
		return ResponseEntity.status(FILE_UPLOAD_MAX_SIZE.getStatus())
			.body(new ErrorResponse(FILE_UPLOAD_MAX_SIZE));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException e) {
		LOGGER.error("NoHandlerFoundException: ", e);
		return ResponseEntity.status(NO_HANDLER_FOUND.getStatus())
			.body(new ErrorResponse(NO_HANDLER_FOUND));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		LOGGER.error("HttpMessageNotReadableException: ", e);
		return ResponseEntity.badRequest()
			.body(new ErrorResponse(HTTP_MESSAGE_NOT_READABLE));
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> expiredJwtException(ExpiredJwtException e) {
		LOGGER.error("ExpiredJwtException: ", e);
		return ResponseEntity.status(EXPIRED_TOKEN.getStatus())
			.body(new ErrorResponse(EXPIRED_TOKEN));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
		LOGGER.error("Exception: ", e);
		return ResponseEntity.internalServerError()
			.body(new ErrorResponse(SERVER_ERROR));
	}
}
