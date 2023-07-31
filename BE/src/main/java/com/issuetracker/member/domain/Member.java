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
	private String proFileImageUrl;

	@Builder(builderClassName = "MemberBuilder")
	private Member(Long id, String email, String password, String nickname, String proFileImageUrl) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.proFileImageUrl = proFileImageUrl;
	}

	public static Member createInstanceById(Long id) {
		return Member.builder()
			.id(id)
			.build();
	}
}
