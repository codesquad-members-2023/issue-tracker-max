package com.issuetracker.account.application.dto;

import java.util.UUID;

import com.issuetracker.account.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OauthAccountInputData {

	private Long oauthId;
	private String email;
	private String nickname;
	private String profileUrl;

	public Account toAccount() {
		return Account.builder()
			.email(email)
			.nickname(nickname)
			.password(String.valueOf(UUID.randomUUID()).substring(0, 10))
			.profileImageUrl(profileUrl)
			.build();
	}
}
