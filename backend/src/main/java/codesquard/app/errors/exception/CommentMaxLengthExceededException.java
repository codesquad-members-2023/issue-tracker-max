package codesquard.app.errors.exception;

public class CommentMaxLengthExceededException extends RuntimeException {

	private static final String MESSAGE = "댓글은 1자 이상 10000자 이하여야 합니다.";

	public CommentMaxLengthExceededException() {
		super(MESSAGE);
	}

}
