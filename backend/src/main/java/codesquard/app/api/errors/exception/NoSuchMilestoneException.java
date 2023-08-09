package codesquard.app.api.errors.exception;

public class NoSuchMilestoneException extends RuntimeException {

	private static final String MESSAGE = "존재하지 않는 마일스톤입니다.";

	public NoSuchMilestoneException() {
		super(MESSAGE);
	}

}
