package codesquard.app.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import codesquard.app.errors.exception.EmptyFileException;

@RestControllerAdvice
public class ImageExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Object> handleMaxUploadSizeExceededException(
		MaxUploadSizeExceededException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(exception.getMessage());
	}

	@ExceptionHandler(EmptyFileException.class)
	public ResponseEntity<Object> handleEmptyFileException(EmptyFileException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getErrorCode().getMessage());
	}

}
