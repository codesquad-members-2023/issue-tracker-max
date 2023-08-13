package com.issuetracker.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String profileImageUrl;

	@Builder
	private Member(Long id, String email, String password, String nickname, String profileImageUrl) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	public boolean equalsId(Long id) {
		return this.id.equals(id);
	}
}
