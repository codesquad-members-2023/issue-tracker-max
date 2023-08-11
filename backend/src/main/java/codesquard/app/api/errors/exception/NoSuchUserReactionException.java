package codesquard.app.api.errors.exception;

public class NoSuchUserReactionException extends RuntimeException {

	private static final String MESSAGE = "존재하지 않는 사용자 반응입니다.";

	public NoSuchUserReactionException() {
		super(MESSAGE);
	}
}
