package codesquard.app.api.errors.exception.user;

import codesquard.app.api.errors.errorcode.ErrorCode;
import codesquard.app.api.errors.exception.RestApiException;

public class LoginRestApiException extends RestApiException {
	public LoginRestApiException(ErrorCode errorCode) {
		super(errorCode);
	}
}
