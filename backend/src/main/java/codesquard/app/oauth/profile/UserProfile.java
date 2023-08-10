package codesquard.app.oauth.profile;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import codesquard.app.user.entity.User;
import lombok.ToString;

@ToString
public class UserProfile {
	private final String name;
	private final String email;
	private final String avatarUrl;

	public UserProfile(String name, String email, String avatarUrl) {
		this.name = name;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public User toUserEntity() {
		return new User(null, name, email, null, avatarUrl);
	}

	public MapSqlParameterSource createSaveParamSource() {
		return new MapSqlParameterSource()
			.addValue("name", name)
			.addValue("email", email)
			.addValue("avatarUrl", avatarUrl);
	}
}
