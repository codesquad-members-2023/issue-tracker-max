package codesquard.app.issue.entity;

import codesquard.app.api.errors.errorcode.IssueErrorCode;
import codesquard.app.api.errors.exception.IllegalIssueStatusException;

public enum IssueStatus {

	OPENED, CLOSED;

	public static IssueStatus validateIssueStatus(String status) {
		try {
			return IssueStatus.valueOf(status);
		} catch (IllegalArgumentException e) {
			throw new IllegalIssueStatusException(IssueErrorCode.INVALID_ISSUE_STATUS);
		}
	}
}
