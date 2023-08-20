package codesquard.app.api.errors.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.api.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiResponse<Object> handleBindException(BindException e) {
		logger.debug("BindException handling : {}", e.toString());
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			HttpStatus.BAD_REQUEST.getReasonPhrase(),
			e.getBindingResult().getFieldErrors().stream().map(
				error -> {
					Map<String, String> errors = new HashMap<>();
					errors.put("field", error.getField());
					errors.put("defaultMessage", error.getDefaultMessage());
					return errors;
				})
		);
	}

	@ExceptionHandler(RestApiException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserRestApiException(RestApiException e) {
		logger.error("RestApiException 발생 : {}", e.toString());
		ApiResponse<Object> body = ApiResponse.of(
			e.getErrorCode().getHttpStatus(),
			e.getErrorCode().getMessage(),
			null
		);
		return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(body);
	}

}
