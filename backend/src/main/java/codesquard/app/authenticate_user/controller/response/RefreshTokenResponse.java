package codesquard.app.authenticate_user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.jwt.Jwt;

public class RefreshTokenResponse {
	@JsonProperty("jwt")
	private Jwt jwt;

	public RefreshTokenResponse() {
	}

	public RefreshTokenResponse(Jwt jwt) {
		this.jwt = jwt;
	}
}
