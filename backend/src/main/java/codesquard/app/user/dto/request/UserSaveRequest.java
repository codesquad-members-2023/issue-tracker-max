package codesquard.app.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import codesquard.app.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest {

	@Size(min = 1, max = 20, message = "영문자, 대소문자를 구분하여 20자 이내여야 합니다.")
	private String loginId;
	@Email(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "이메일은 '사용자명@도메인주소' 형식이어야 합니다.")
	private String email;
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,16}$", message = "비밀번호는 8글자에서 32글자 사이로 영어 소문자 및 숫자 반드시 포함해야 합니다.")
	private String password;
	private String avatarUrl;

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

	public User toEntity() {
		return new User(loginId, email, password, avatarUrl);
	}
}
