package codesquard.app.user.controller.request;

import codesquard.app.user.service.request.UserSaveServiceRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {
	private String loginId;
	private String email;
	private String password;
	private String passwordConfirm;

	public UserSaveServiceRequest toUserSaveServiceRequest() {
		return new UserSaveServiceRequest(loginId, email, password, passwordConfirm, null);
	}

	public String getLoginId() {
		return loginId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}
}
