package codesquard.app.api.errors.exception.user;

import codesquard.app.api.errors.errorcode.UserErrorCode;
import codesquard.app.api.errors.exception.RestApiException;

public class UserRestApiException extends RestApiException {
	public UserRestApiException(UserErrorCode errorCode) {
		super(errorCode);
	}
}
