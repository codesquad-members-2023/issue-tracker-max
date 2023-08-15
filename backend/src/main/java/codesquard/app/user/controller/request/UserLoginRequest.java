package codesquard.app.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.user.password.PasswordEncoder;
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
		PasswordEncoder passwordEncoder = new PasswordEncoder();
		String encryptPassword = passwordEncoder.encrypt(password);
		return new UserLoginServiceRequest(loginId, encryptPassword);
	}
}
