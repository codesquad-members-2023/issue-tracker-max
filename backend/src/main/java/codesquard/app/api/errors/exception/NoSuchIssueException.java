package codesquard.app.api.errors.exception;

public class NoSuchIssueException extends RuntimeException {

	private static final String MESSAGE = "이슈를 찾을 수 없습니다.";

	public NoSuchIssueException() {
		super(MESSAGE);
	}

}
