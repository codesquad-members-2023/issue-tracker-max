package codesquard.app.api.errors.exception;

public class DuplicateLabelException extends RuntimeException {

	private static final String MESSAGE = "이미 중복된 이름의 라벨이 있습니다.";

	public DuplicateLabelException() {
		super(MESSAGE);
	}

}
