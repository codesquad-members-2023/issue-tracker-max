package com.issuetrackermax.controller.member.dto.request;

import com.issuetrackermax.domain.member.Entity.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {
	private final String email;
	private final String password;
	private final String nickName;

	public Member toEntity() {
		return Member.builder()
			.email(email)
			.password(password)
			.nickName(nickName)
			.build();
	}
}
