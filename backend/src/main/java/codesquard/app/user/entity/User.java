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

	public Long getId() {
		return id;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public MapSqlParameterSource createSaveParamSource() {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue("id", id);
		parameterSource.addValue("loginId", loginId);
		parameterSource.addValue("email", email);
		parameterSource.addValue("password", password);
		parameterSource.addValue("avatarUrl", avatarUrl);
		return parameterSource;
	}
}
