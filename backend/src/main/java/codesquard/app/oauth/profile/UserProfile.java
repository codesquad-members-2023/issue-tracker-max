package codesquard.app.oauth.profile;

import codesquard.app.user.entity.User;
import lombok.ToString;

@ToString
public class UserProfile {
	private final String loginId;
	private final String email;
	private final String avatarUrl;

	public UserProfile(String loginId, String email, String avatarUrl) {
		this.loginId = loginId;
		this.email = email;
		this.avatarUrl = avatarUrl;
	}

	public User toUserEntity() {
		return new User(null, loginId, email, null, avatarUrl);
	}
}
