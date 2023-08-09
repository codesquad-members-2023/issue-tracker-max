package codesquard.app.api.errors.exception;

public class FontColorLabelException extends RuntimeException {

	private static final String MESSAGE = "light, dark 외의 색상은 지원하지 않습니다.";

	public FontColorLabelException() {
		super(MESSAGE);
	}

}
