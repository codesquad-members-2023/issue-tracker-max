package codesquard.app.oauth.service.response;

import javax.servlet.http.Cookie;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.Jwt;
import codesquard.app.oauth.controller.response.OauthLoginResponse;
import lombok.ToString;

@ToString
public class OauthLoginServiceResponse {
	private final AuthenticateUser user;
	private final Jwt jwt;
	private final String tokenType;

	public OauthLoginServiceResponse(AuthenticateUser user, Jwt jwt, String tokenType) {
		this.user = user;
		this.jwt = jwt;
		this.tokenType = tokenType;
	}

	public OauthLoginResponse toOauthLoginResponse() {
		return new OauthLoginResponse(user, jwt, tokenType);
	}

	public Cookie createRefreshTokenCookie() {
		return jwt.createRefreshTokenCookie();
	}

	public String createAccessTokenHeaderValue() {
		return jwt.createAccessTokenHeaderValue();
	}
}
