package codesquard.app.errors.exception;

import codesquard.app.errors.errorcode.ErrorCode;
import codesquard.app.errors.errorcode.ImageErrorCode;

public class EmptyFileException extends RuntimeException {

	private final ErrorCode errorCode = ImageErrorCode.EMPTY_FILE;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
