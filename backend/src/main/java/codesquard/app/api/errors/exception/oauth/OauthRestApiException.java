package codesquard.app.api.errors.exception.oauth;

import codesquard.app.api.errors.errorcode.ErrorCode;
import codesquard.app.api.errors.exception.RestApiException;

public class OauthRestApiException extends RestApiException {
	public OauthRestApiException(ErrorCode errorCode) {
		super(errorCode);
	}
}
