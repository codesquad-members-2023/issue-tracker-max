package codesquard.app.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.errors.exception.IllegalIssueStatusException;
import codesquard.app.errors.exception.NoSuchIssueException;
import codesquard.app.errors.response.ApiResponse;

@RestControllerAdvice
public class IssueExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(IssueExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalIssueStatusException.class)
	public ApiResponse<Object> handleIllegalIssueStatusException(IllegalIssueStatusException e) {
		logger.info("IllegalIssueStatusException handling : {}", e.toString());
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getMessage(),
			null
		);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoSuchIssueException.class)
	public ApiResponse<Object> handleNoSuchIssueException(NoSuchIssueException e) {
		logger.info("NoSuchIssueException handling : {}", e.toString());
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getMessage(),
			null
		);
	}

}
