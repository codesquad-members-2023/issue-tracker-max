package codesquard.app.api.errors.exception;

import codesquard.app.api.errors.errorcode.IssueErrorCode;

public class NoSuchIssueException extends RuntimeException {

	public NoSuchIssueException() {
		super(IssueErrorCode.NOT_FOUND_ISSUE.getMessage());
	}

}
