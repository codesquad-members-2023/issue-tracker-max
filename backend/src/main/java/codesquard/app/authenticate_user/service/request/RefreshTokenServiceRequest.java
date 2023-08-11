package codesquard.app.authenticate_user.service.request;

public class RefreshTokenServiceRequest {
	private String refreshToken;

	public RefreshTokenServiceRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
