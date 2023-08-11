package codesquard.app.oauth.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.Jwt;

public class OauthLoginResponse {
	@JsonProperty("user")
	private AuthenticateUser user;
	@JsonProperty("jwt")
	private Jwt jwt;
	@JsonProperty("tokenType")
	private String tokenType;

	public OauthLoginResponse(AuthenticateUser user, Jwt jwt, String tokenType) {
		this.user = user;
		this.jwt = jwt;
		this.tokenType = tokenType;
	}
}
