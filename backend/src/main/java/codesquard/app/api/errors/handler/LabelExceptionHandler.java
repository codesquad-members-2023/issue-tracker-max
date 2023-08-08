package codesquard.app.api.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.api.errors.exception.DuplicateLabelException;
import codesquard.app.api.errors.exception.FontColorLabelException;
import codesquard.app.api.errors.exception.NoSuchLabelException;
import codesquard.app.api.response.ApiResponse;

@RestControllerAdvice
public class LabelExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(LabelExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoSuchLabelException.class)
	public ApiResponse<Object> handleNoSuchLabelException(NoSuchLabelException e) {
		logger.info("NoSuchLabelException handling : {}", e.toString());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FontColorLabelException.class)
	public ApiResponse<Object> handleFontColorLabelException(FontColorLabelException e) {
		logger.info("FontColorLabelException handling : {}", e.toString());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateLabelException.class)
	public ApiResponse<Object> handleDuplicateLabelException(DuplicateLabelException e) {
		logger.info("DuplicateLabelException handling : {}", e.toString());
		return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
	}

}
