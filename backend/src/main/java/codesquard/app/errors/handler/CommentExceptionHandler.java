package codesquard.app.errors.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquard.app.errors.exception.CommentMaxLengthExceededException;

@RestControllerAdvice
public class CommentExceptionHandler {

	@ExceptionHandler(CommentMaxLengthExceededException.class)
	public ResponseEntity<String> handleCommentMaxLengthExceededException(CommentMaxLengthExceededException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());
	}

}
