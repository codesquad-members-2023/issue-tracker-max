package codesquard.app.errors.exception;

import codesquard.app.errors.errorcode.ErrorCode;

public class IllegalIssueStatusException extends RuntimeException {

	private final ErrorCode errorCode;

	public IllegalIssueStatusException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
