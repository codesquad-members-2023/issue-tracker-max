package codesquard.app.user.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSaveResponse {
	@JsonProperty("success")
	private boolean success;

	private UserSaveResponse(boolean success) {
		this.success = success;
	}

	public static UserSaveResponse success() {
		return new UserSaveResponse(true);
	}

	public static UserSaveResponse fail() {
		return new UserSaveResponse(false);
	}
}
