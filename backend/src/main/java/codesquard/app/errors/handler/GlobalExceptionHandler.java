package codesquard.app.errors.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import codesquard.app.errors.errorcode.CommonErrorCode;
import codesquard.app.errors.errorcode.ErrorCode;
import codesquard.app.errors.exception.IllegalIssueStatusException;
import codesquard.app.errors.exception.NoSuchIssueException;
import codesquard.app.errors.exception.RestApiException;
import codesquard.app.errors.response.ErrorResponse;
import codesquard.app.errors.response.ErrorResultResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(RestApiException.class)
	public ResponseEntity<Object> handleRestApiException(RestApiException e) {
		logger.info("RestApiException handling : {}", e.toString());
		return handleExceptionInternal(e.getErrorCode());
	}

	private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(makeErrorResponse(errorCode));
	}

	private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
		return new ErrorResponse(errorCode, null);
	}

	private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
			.body(makeErrorResponse(e, errorCode));
	}

	private ErrorResultResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
		List<ErrorResponse.ValidationError> validationErrorList =
			e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(ErrorResponse.ValidationError::of)
				.collect(Collectors.toUnmodifiableList());
		return new ErrorResultResponse(new ErrorResponse(errorCode, validationErrorList));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status,
		WebRequest request) {
		logger.warn("handleMethodArgumentNotValid", e);
		CommonErrorCode errorCode = CommonErrorCode.INVALID_INPUT_FORMAT;
		return handleExceptionInternal(e, errorCode);
	}

	@ExceptionHandler(IllegalIssueStatusException.class)
	public ResponseEntity<Object> handleIllegalIssueStatusException(IllegalIssueStatusException e) {
		logger.info("IllegalIssueStatusException handling : {}", e.toString());
		return handleExceptionInternal(e.getErrorCode());
	}

	@ExceptionHandler(NoSuchIssueException.class)
	public ResponseEntity<Object> handleNoSuchIssueException(NoSuchIssueException e) {
		logger.info("NoSuchIssueException handling : {}", e.toString());
		return handleExceptionInternal(e.getErrorCode());
	}
}
