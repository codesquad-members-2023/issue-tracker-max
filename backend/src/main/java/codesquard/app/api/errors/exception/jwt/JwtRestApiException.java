package codesquard.app.api.errors.exception.jwt;

import codesquard.app.api.errors.errorcode.ErrorCode;
import codesquard.app.api.errors.exception.RestApiException;

public class JwtRestApiException extends RestApiException {
	public JwtRestApiException(ErrorCode errorCode) {
		super(errorCode);
	}
}
