package codesquard.app.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import lombok.ToString;

@ToString
public class UserLoginRequest {
	@JsonProperty("loginId")
	private String loginId;

	@JsonProperty("password")
	private String password;

	public UserLoginRequest() {
	}

	public UserLoginRequest(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}

	public UserLoginServiceRequest toUserLoginServiceRequest() {
		return new UserLoginServiceRequest(loginId, password);
	}

	public AuthenticateUser toAuthenticateUserEntity() {
		return new AuthenticateUser(null, loginId, null, null);
	}
}
