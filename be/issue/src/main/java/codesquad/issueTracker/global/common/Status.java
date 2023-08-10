package codesquad.issueTracker.global.common;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;

public enum Status {
	OPEN("open", false),
	CLOSED("closed", true);

	private final String type;
	private final boolean value;

	Status(String type, boolean value) {
		this.type = type;
		this.value = value;
	}

	public boolean getStatus() {
		return value;
	}

	public static Status from(String type) {
		for (Status status : Status.values()) {
			if (status.type.equalsIgnoreCase(type)) {
				return status;
			}
		}
		throw new CustomException(ErrorCode.ILLEGAL_STATUS_MILESTONE);
	}

}
