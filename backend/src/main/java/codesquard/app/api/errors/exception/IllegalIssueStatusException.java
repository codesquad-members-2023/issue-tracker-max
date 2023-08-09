package codesquard.app.api.errors.exception;

import codesquard.app.api.errors.errorcode.ErrorCode;

public class IllegalIssueStatusException extends RuntimeException {

	public IllegalIssueStatusException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}

}
