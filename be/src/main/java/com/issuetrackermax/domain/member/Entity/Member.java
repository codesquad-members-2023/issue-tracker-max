package com.issuetrackermax.domain.member.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private Long id;
	private String email;
	private String password;
	private String nickName;

	@Builder
	public Member(String email, String password, String nickName) {
		this.email = email;
		this.password = password;
		this.nickName = nickName;
	}

}
