package codesquard.app.authenticate_user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.Jwt;

public class AuthenticateUserLoginResponse {
	@JsonProperty("user")
	private AuthenticateUser authenticateUser;
	@JsonProperty("jwt")
	private Jwt jwt;

	public AuthenticateUserLoginResponse() {
	}

	public AuthenticateUserLoginResponse(AuthenticateUser authenticateUser, Jwt jwt) {
		this.authenticateUser = authenticateUser;
		this.jwt = jwt;
	}
}
