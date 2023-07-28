package com.issuetrackermax.controller.member.dto.request;

import com.issuetrackermax.domain.member.Entity.LoginType;
import com.issuetrackermax.domain.member.Entity.Member;

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
	private SignUpRequest(String loginId, String password, String nickName) {
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
	}

	public Member toEntity() {
		return Member.builder()
			.loginId(loginId)
			.loginType(LoginType.LOCAL)
			.password(password)
			.nickName(nickName)
			.build();
	}
}
