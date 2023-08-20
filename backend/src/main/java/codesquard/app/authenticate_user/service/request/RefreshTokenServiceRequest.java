package codesquard.app.authenticate_user.service.request;

import lombok.ToString;

@ToString
public class RefreshTokenServiceRequest {
	private final String refreshToken;

	public RefreshTokenServiceRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
