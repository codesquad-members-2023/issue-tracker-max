package codesquard.app.api.errors.exception;

public class FailedUploadException extends RuntimeException {

	private static final String MESSAGE = "파일 업로드에 실패했습니다.";

	public FailedUploadException() {
		super(MESSAGE);
	}

}
