package codesquard.app.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.user.controller.response.UserLoginResponse;
import lombok.ToString;

@ToString
public class AuthenticateUser {
	@JsonProperty("id")
	private Long id;
	@JsonProperty("loginId")
	private String loginId;
	@JsonProperty("email")
	private String email;
	@JsonProperty("avatarUrl")
	private String avatarUrl;

	public AuthenticateUser() {
	}

	public AuthenticateUser(Long id, String loginId, String email, String avatarUrl) {
		this.id = id;
		this.loginId = loginId;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public User toEntity() {
		return new User(id, loginId, email, null, avatarUrl);
	}

	public UserLoginResponse toUserLoginResponse() {
		return new UserLoginResponse(id, loginId, email, avatarUrl);
	}
}
