package codesquard.app.api.errors.exception;

public class CommentAuthorMismatchException extends RuntimeException {

	private static final String MESSAGE = "댓글 작성자와 일치하지 않습니다.";

	public CommentAuthorMismatchException() {
		super(MESSAGE);
	}

}
