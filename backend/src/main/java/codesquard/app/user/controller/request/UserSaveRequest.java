package codesquard.app.user.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.user.service.request.UserSaveServiceRequest;

public class UserSaveRequest {

	@JsonProperty("loginId")
	@NotNull(message = "아이디는 필수 정보입니다.")
	@Pattern(regexp = "^[a-zA-Z\\d]{5,20}$", message = "영문자 대소문자, 숫자를 사용하여 5~20자 이내여야 합니다.")
	private String loginId;

	@JsonProperty("email")
	@NotNull(message = "이메일은 필수 정보입니다.")
	@Pattern(regexp = "[a-z\\d]+@[a-z]+\\.[a-z]{2,3}", message = "이메일은 '사용자명@도메인주소' 형식이어야 합니다.")
	private String email;

	@JsonProperty("password")
	@NotNull(message = "비밀번호는 필수 정보입니다.")
	@Pattern(regexp = "[A-Za-z\\d!@#$%^&*()_+]{8,16}$", message = "8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")
	private String password;

	@JsonProperty("passwordConfirm")
	@NotNull(message = "비밀번호는 필수 정보입니다.")
	@Pattern(regexp = "[A-Za-z\\d!@#$%^&*()_+]{8,16}$", message = "8~16자 영문 대 소문자, 숫자, 특수문자만 사용 가능합니다.")
	private String passwordConfirm;

	public UserSaveRequest() {
	}

	public UserSaveRequest(String loginId, String email, String password, String passwordConfirm) {
		this.loginId = loginId;
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public UserSaveServiceRequest toUserSaveServiceRequest() {
		return new UserSaveServiceRequest(loginId, email, password, passwordConfirm, null);
	}
}
