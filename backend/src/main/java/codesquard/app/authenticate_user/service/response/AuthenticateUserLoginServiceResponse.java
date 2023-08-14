package codesquard.app.authenticate_user.service.response;

import javax.servlet.http.Cookie;

import codesquard.app.authenticate_user.controller.response.AuthenticateUserLoginResponse;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.Jwt;

public class AuthenticateUserLoginServiceResponse {
	private final AuthenticateUser authenticateUser;
	private final Jwt jwt;

	public AuthenticateUserLoginServiceResponse(AuthenticateUser authenticateUser, Jwt jwt) {
		this.authenticateUser = authenticateUser;
		this.jwt = jwt;
	}

	public AuthenticateUserLoginResponse toAuthenticateUserLoginResponse() {
		return new AuthenticateUserLoginResponse(authenticateUser, jwt);
	}

	public Cookie createRefreshTokenCookie() {
		return jwt.createRefreshTokenCookie();
	}

	public String createAccessTokenHeaderValue() {
		return jwt.createAccessTokenHeaderValue();
	}

	public String getRefreshToken() {
		return jwt.getRefreshToken();
	}

}
