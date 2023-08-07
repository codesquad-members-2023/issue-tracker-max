package codesquard.app.user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.jwt.Jwt;

public class UserLoginResponse {
	@JsonProperty("user")
	private AuthenticateUser authenticateUser;
	@JsonProperty("jwt")
	private Jwt jwt;

	public UserLoginResponse() {
	}

	public UserLoginResponse(AuthenticateUser authenticateUser, Jwt jwt) {
		this.authenticateUser = authenticateUser;
		this.jwt = jwt;
	}
}
