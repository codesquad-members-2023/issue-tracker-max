package codesquard.app.user.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSaveResponse {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("loginId")
	private String loginId;
	@JsonProperty("email")
	private String email;

	public UserSaveResponse() {
	}

	public UserSaveResponse(Long id, String loginId, String email) {
		this.id = id;
		this.loginId = loginId;
		this.email = email;
	}
}
