package codesquard.app.api.errors.exception;

public class NoSuchLabelException extends RuntimeException {

	private static final String MESSAGE = "존재하지 않는 라벨입니다.";

	public NoSuchLabelException() {
		super(MESSAGE);
	}

}
