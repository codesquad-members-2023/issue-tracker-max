package codesquard.app.user.service.request;

import codesquard.app.user.entity.User;
import codesquard.app.user.password.PasswordEncoder;

public class UserLoginServiceRequest {
	private final String loginId;
	private final String password;

	public UserLoginServiceRequest(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}

	public User toEntity() {
		return new User(null, loginId, null, password, null);
	}

	public UserLoginServiceRequest encryptPassword() {
		PasswordEncoder passwordEncoder = new PasswordEncoder();
		String encryptPassword = passwordEncoder.encrypt(password);
		return new UserLoginServiceRequest(loginId, encryptPassword);
	}
}
