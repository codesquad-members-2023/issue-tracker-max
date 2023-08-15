package com.issuetracker.account.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Account {

	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String profileImageUrl;

	@Builder
	public Account(Long id, String email, String password, String nickname, String profileImageUrl) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}
}
