package codesquard.app.user.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {

	private Long id;
	private final String loginId;
	private final String email;
	private final String password;
	private final String avatarUrl;

	public String getLoginId() {
		return loginId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}
}
