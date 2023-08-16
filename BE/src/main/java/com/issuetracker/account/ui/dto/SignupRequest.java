package com.issuetracker.account.ui.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.issuetracker.account.application.dto.SignUpInputData;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignupRequest {

	@Email(message = "이메일 형식이 아닙니다.")
	@NotBlank(message = "이메일은 필수 입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입니다.")
	@Length(min = 6, max = 12, message = "비밀번호는 6자에서 12자 사이 입니다.")
	private String password;

	@NotBlank(message = "닉네임은 필수 입니다.")
	private String nickname;

	private String profileImageUrl;
	private Long oauthId;

	public SignUpInputData toSignUpInputData() {
		return new SignUpInputData(
			email,
			password,
			nickname,
			profileImageUrl,
			oauthId
		);
	}
}
