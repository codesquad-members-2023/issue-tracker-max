package codesquard.app.api.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.api.errors.exception.NoSuchReactionException;
import codesquard.app.api.errors.exception.NoSuchUserReactionException;
import codesquard.app.api.response.ApiResponse;

@RestControllerAdvice
public class UserReactionExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(UserReactionExceptionHandler.class);

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchReactionException.class)
	public ApiResponse<Object> handleNoSuchReactionException(NoSuchReactionException e) {
		logger.warn("NoSuchReactionException handling : {}", e.toString());
		return ApiResponse.of(
			HttpStatus.NOT_FOUND,
			e.getMessage(),
			null
		);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchUserReactionException.class)
	public ApiResponse<Object> handleNoSuchUserReactionException(NoSuchUserReactionException e) {
		logger.warn("NoSuchUserReactionException handling : {}", e.toString());
		return ApiResponse.of(
			HttpStatus.NOT_FOUND,
			e.getMessage(),
			null
		);
	}
}
