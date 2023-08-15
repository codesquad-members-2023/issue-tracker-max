package com.issuetracker.account.application.dto;

import com.issuetracker.account.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpInputData {

	private String email;
	private String password;
	private String nickname;
	private String profileImageUrl;

	public Account toAccount() {
		return Account.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.profileImageUrl(profileImageUrl)
			.build();
	}
}
