package codesquard.app.errors.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import codesquard.app.errors.exception.EmptyFileException;
import codesquard.app.errors.response.ApiResponse;

@RestControllerAdvice
public class ImageExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ImageExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ApiResponse<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getMessage(),
			null
		);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmptyFileException.class)
	public ApiResponse<Object> handleEmptyFileException(EmptyFileException e) {
		return ApiResponse.of(
			HttpStatus.BAD_REQUEST,
			e.getMessage(),
			null
		);
	}

}
