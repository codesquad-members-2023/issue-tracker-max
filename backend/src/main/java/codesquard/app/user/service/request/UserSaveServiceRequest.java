package codesquard.app.user.service.request;

import codesquard.app.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSaveServiceRequest {
	private final String loginId;
	private final String email;
	private final String password;
	private final String passwordConfirm;
	private final String avatarUrl;

	public User toEntity() {
		return new User(null, loginId, email, password, avatarUrl);
	}
}
