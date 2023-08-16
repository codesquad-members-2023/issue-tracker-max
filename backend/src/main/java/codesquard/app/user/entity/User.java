package codesquard.app.user.entity;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import codesquard.app.authenticate_user.entity.AuthenticateUser;

public class User {

	private final Long id;
	private final String loginId;
	private String email;
	private final String password;
	private String avatarUrl;

	public User(Long id, String loginId, String avatarUrl) {
		this(id, loginId, null, null, avatarUrl);
	}

	public User(Long id, String loginId, String email, String password, String avatarUrl) {
		this.id = id;
		this.loginId = loginId;
		this.email = email;
		this.password = password;
		this.avatarUrl = avatarUrl;
	}

	public Long getId() {
		return id;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getEmail() {
		return email;
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

	public User update(User changeUser) {
		return new User(id, loginId, changeUser.email, password, changeUser.avatarUrl);
	}
}
