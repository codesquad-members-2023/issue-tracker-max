package codesquard.app.api.errors.exception;

public class DuplicateMilestoneException extends RuntimeException {

	private static final String MESSAGE = "이미 동일한 이름의 마일스톤이 있습니다.";

	public DuplicateMilestoneException() {
		super(MESSAGE);
	}

}
