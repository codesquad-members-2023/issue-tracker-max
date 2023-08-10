package codesquard.app.api.errors.exception;

public class NoSuchReactionException extends RuntimeException {

	private static final String MESSAGE = "존재하지 않는 반응입니다.";

	public NoSuchReactionException() {
		super(MESSAGE);
	}
}
