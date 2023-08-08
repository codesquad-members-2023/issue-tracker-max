package codesquard.app.api.errors.exception;

import codesquard.app.api.errors.errorcode.ErrorCode;

public class RestApiException extends RuntimeException {

	private final ErrorCode errorCode;

	public RestApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
