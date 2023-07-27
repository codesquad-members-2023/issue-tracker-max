package com.issuetracker.app.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String proFileImageUrl;

	@Builder(builderClassName = "MemberBuilder", access = AccessLevel.PRIVATE)
	private Member(Long id, String email, String password, String nickname, String proFileImageUrl) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.proFileImageUrl = proFileImageUrl;
	}

	@Builder(builderClassName = "MemberRequiredBuilder", access = AccessLevel.PRIVATE)
	public static MemberBuilder memberBuilder(String email, String password, String nickname) {
		return new MemberBuilder()
			.email(email)
			.password(password)
			.nickname(nickname);
	}
}
