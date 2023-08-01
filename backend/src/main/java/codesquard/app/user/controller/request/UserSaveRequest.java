package codesquard.app.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {
	private String loginId;
	private String email;
	private String password;

	public String getLoginId() {
		return loginId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
