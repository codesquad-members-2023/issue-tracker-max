package codesquard.app.api.errors.exception;

public class NoSuchCommentException extends RuntimeException {

	private static final String MESSAGE = "존재하지 않는 댓글입니다.";

	public NoSuchCommentException() {
		super(MESSAGE);
	}

}
