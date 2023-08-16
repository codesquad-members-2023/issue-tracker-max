package com.issuetrackermax.controller.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
	@NotBlank(message = "아이디를 입력해주세요.")
	@Email(message = "아이디는 이메일 형식으로 입력해주세요.")
	@Size(min = 6, max = 25, message = "아이디는 6자 이상, 25자 이하로 입력해주세요.")
	private String loginId;
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(min = 6, max = 16, message = "비밀번호는 6자 이상, 16자 이하로 입력해주세요.")
	private String password;
	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 1, max = 8, message = "닉네임은 1자 이상, 8자 이하로 입력해주세요.")
	private String nickName;

	@Builder
	public SignUpRequest(String loginId, String password, String nickName) {
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
	}

	public Member toMember() {
		return new Member(loginId, password, nickName, LoginType.LOCAL);
	}
}
