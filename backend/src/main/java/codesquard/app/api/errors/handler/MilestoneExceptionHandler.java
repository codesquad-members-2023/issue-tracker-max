package codesquard.app.api.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.api.errors.exception.DuplicateMilestoneException;
import codesquard.app.api.errors.exception.NoSuchMilestoneException;
import codesquard.app.api.response.ApiResponse;

@RestControllerAdvice
public class MilestoneExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(MilestoneExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateMilestoneException.class)
	public ApiResponse<Object> handleDuplicateMilestoneException(DuplicateMilestoneException e) {
		logger.info("DuplicateMilestoneException handling : {}", e.toString());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoSuchMilestoneException.class)
	public ApiResponse<Object> handleNoSuchMilestoneException(NoSuchMilestoneException e) {
		logger.info("NoSuchMilestoneException handling : {}", e.toString());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

}
