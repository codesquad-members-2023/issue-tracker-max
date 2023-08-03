package codesquard.app.errors.exception;

import codesquard.app.errors.errorcode.ErrorCode;

public class NoSuchIssueException extends RuntimeException {

	private final ErrorCode errorCode;

	public NoSuchIssueException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
