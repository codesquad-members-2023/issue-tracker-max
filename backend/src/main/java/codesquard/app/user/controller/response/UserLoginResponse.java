package codesquard.app.user.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginResponse {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("loginId")
	private String loginId;
	@JsonProperty("email")
	private String email;
	@JsonProperty("avatarUrl")
	private String avatarUrl;

	public UserLoginResponse() {
	}

	public UserLoginResponse(Long id, String loginId, String email, String avatarUrl) {
		this.id = id;
		this.loginId = loginId;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}
}
