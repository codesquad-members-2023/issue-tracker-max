package codesquard.app.api.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.api.errors.exception.IllegalIssueStatusException;
import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.api.response.ApiResponse;

@RestControllerAdvice
public class IssueExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(IssueExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalIssueStatusException.class)
	public ApiResponse<Object> handleIllegalIssueStatusException(IllegalIssueStatusException e) {
		logger.warn("IllegalIssueStatusException handling : {}", e.toString());
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getMessage(),
			null
		);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchIssueException.class)
	public ApiResponse<Object> handleNoSuchIssueException(NoSuchIssueException e) {
		logger.warn("NoSuchIssueException handling : {}", e.toString());
		return ApiResponse.of(
			HttpStatus.NOT_FOUND,
			e.getMessage(),
			null
		);
	}

}
