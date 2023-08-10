package codesquard.app.authenticate_user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
import lombok.ToString;

@ToString
public class RefreshTokenRequest {
	@JsonProperty("refreshToken")
	private String refreshToken;

	public RefreshTokenRequest() {
	}

	public RefreshTokenRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public RefreshTokenServiceRequest toRefreshTokenServiceRequest() {
		return new RefreshTokenServiceRequest(refreshToken);
	}
}
