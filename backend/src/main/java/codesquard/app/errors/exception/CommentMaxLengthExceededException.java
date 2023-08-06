package codesquard.app.errors.exception;

import codesquard.app.errors.errorcode.ErrorCode;
import codesquard.app.errors.errorcode.ImageErrorCode;

public class CommentMaxLengthExceededException extends RuntimeException {

	private final ErrorCode errorCode = ImageErrorCode.MAX_UPLOAD_SIZE_EXCEED;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}
