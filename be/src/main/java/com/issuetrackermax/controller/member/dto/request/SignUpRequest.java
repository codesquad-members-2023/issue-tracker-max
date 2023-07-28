package com.issuetrackermax.controller.member.dto.request;

import com.issuetrackermax.domain.member.Entity.LoginType;
import com.issuetrackermax.domain.member.Entity.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {
	private final String loginId;
	private final String password;
	private final String nickName;

	public Member toEntity() {
		return Member.builder()
			.loginId(loginId)
			.loginType(LoginType.LOCAL)
			.password(password)
			.nickName(nickName)
			.build();
	}
}
