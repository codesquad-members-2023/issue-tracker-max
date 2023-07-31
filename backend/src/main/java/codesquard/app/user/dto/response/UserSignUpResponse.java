package codesquard.app.user.dto.response;

public class UserSignUpResponse {

	private final boolean success;
	private final Long id;

	public UserSignUpResponse(boolean success, Long id) {
		this.success = success;
		this.id = id;
	}

	public boolean isSuccess() {
		return success;
	}

	public Long getId() {
		return id;
	}
}
