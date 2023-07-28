package com.issuetrackermax.domain.member.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Member {
	private Long id;
	private String loginId;
	private String password;
	private String nickName;
	private LoginType loginType;

	@Builder
	public Member(Long id, String loginId, String password, String nickName, LoginType loginType) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
		this.loginType = loginType;
	}

}
