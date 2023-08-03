package com.issuetrackermax.controller.member.dto.request;

import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
	private String loginId;
	private String password;
	private String nickName;

	@Builder
	public SignUpRequest(String loginId, String password, String nickName) {
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
	}

	public Member toMember() {
		return Member.builder()
			.loginId(loginId)
			.loginType(LoginType.LOCAL)
			.password(password)
			.nickName(nickName)
			.build();
	}
}
