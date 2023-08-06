package com.issuetrackermax.controller.auth.dto.response;

import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberProfileResponse {
	private final String loginId;

	@Builder
	public MemberProfileResponse(String loginId) {
		this.loginId = loginId;
	}

	public Member toMember() {
		return Member.builder()
			.password(null)
			.nickName(loginId)
			.loginId(loginId)
			.loginType(LoginType.GITHUB)
			.build();
	}
}
