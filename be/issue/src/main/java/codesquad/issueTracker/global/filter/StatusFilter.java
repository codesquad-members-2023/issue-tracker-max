package codesquad.issueTracker.global.filter;

import codesquad.issueTracker.global.exception.CustomException;
import codesquad.issueTracker.global.exception.ErrorCode;

public enum StatusFilter {
	OPEN("open", false),
	CLOSED("closed", true);

	private final String type;
	private final boolean value;

	StatusFilter(String type, boolean value) {
		this.type = type;
		this.value = value;
	}

	public boolean getStatus() {
		return value;
	}

	public static StatusFilter from(String type) {
		for (StatusFilter status : StatusFilter.values()) {
			if (status.type.equalsIgnoreCase(type)) {
				return status;
			}
		}
		throw new CustomException(ErrorCode.ILLEGAL_STATUS_MILESTONE);
	}

}
