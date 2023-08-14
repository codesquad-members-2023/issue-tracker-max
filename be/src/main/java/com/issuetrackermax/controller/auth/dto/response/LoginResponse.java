package com.issuetrackermax.controller.auth.dto.response;

import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
	private final String accessToken;
	private final String refreshToken;
	private final MemberResponse member;

	@Builder
	public LoginResponse(String accessToken, String refreshToken,
		Member member) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.member = MemberResponse.from(member);
	}
}
