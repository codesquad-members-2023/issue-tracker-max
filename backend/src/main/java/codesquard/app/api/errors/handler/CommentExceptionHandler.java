package codesquard.app.api.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.api.errors.exception.CommentMaxLengthExceededException;
import codesquard.app.api.response.ApiResponse;

@RestControllerAdvice
public class CommentExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(CommentExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CommentMaxLengthExceededException.class)
	public ApiResponse<Object> handleCommentMaxLengthExceededException(CommentMaxLengthExceededException e) {
		logger.info("CommentMaxLengthExceededException handling : {}", e.toString());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

}
