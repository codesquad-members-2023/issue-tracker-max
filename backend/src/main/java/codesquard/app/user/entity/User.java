package codesquard.app.user.entity;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class User {

	private final Long id;
	private final String loginId;
	private final String email;
	private final String password;
	private final String avatarUrl;

	public User(Long id, String loginId, String avatarUrl) {
		this.id = id;
		this.loginId = loginId;
		this.avatarUrl = avatarUrl;
		this.email = null;
		this.password = null;
	}

	public User(Long id, String loginId, String email, String password, String avatarUrl) {
		this.id = id;
		this.loginId = loginId;
		this.email = email;
		this.password = password;
		this.avatarUrl = avatarUrl;
	}

	public String getLoginId() {
		return loginId;
  }

	public MapSqlParameterSource createSaveParamSource() {
		return new MapSqlParameterSource()
			.addValue("id", id)
			.addValue("loginId", loginId)
			.addValue("email", email)
			.addValue("password", password)
			.addValue("avatarUrl", avatarUrl);
	}

	public AuthenticateUser toAuthenticateUser() {
		return new AuthenticateUser(id, loginId, email, avatarUrl);
	}
}
