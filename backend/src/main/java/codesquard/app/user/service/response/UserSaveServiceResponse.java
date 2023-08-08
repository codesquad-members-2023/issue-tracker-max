package codesquard.app.user.service.response;

import codesquard.app.user.entity.User;

public class UserSaveServiceResponse {
	private final Long id;
	private final String loginId;
	private final String email;

	public UserSaveServiceResponse(Long id, String loginId, String email) {
		this.id = id;
		this.loginId = loginId;
		this.email = email;
	}

	public static UserSaveServiceResponse from(User user) {
		return new UserSaveServiceResponse(user.getId(), user.getLoginId(), user.getEmail());
	}

	public UserSaveResponse toUserSaveResponse() {
		return new UserSaveResponse(id, loginId, email);
	}
}
