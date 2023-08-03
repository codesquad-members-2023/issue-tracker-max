package codesquard.app.user.service.response;

public class UserVerifyResponse {
	private final boolean isValid;

	private UserVerifyResponse(boolean isValid) {
		this.isValid = isValid;
	}

	public static UserVerifyResponse success() {
		return new UserVerifyResponse(true);
	}

	public static UserVerifyResponse fail() {
		return new UserVerifyResponse(false);
	}

	public boolean isValid() {
		return isValid;
	}
}
