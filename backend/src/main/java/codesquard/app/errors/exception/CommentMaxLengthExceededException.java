package codesquard.app.errors.exception;

public class CommentMaxLengthExceededException extends RuntimeException {

	public CommentMaxLengthExceededException() {
		super("댓글은 1자 이상 10000자 이하여야 합니다.");
	}

}
