package com.issuetrackermax.controller.auth.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "아이디를 입력해주세요.")
	@Email(message = "아이디는 이메일 형식으로 입력해주세요.")
	@Size(min = 6, max = 25, message = "아이디는 6자 이상, 25자 이하로 입력해주세요.")
	private String loginId;
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(min = 6, max = 16, message = "비밀번호는 6자 이상, 16자 이하로 입력해주세요.")
	private String password;

	@Builder
	public LoginRequest(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}
}
