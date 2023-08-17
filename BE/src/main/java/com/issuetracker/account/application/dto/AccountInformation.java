package com.issuetracker.account.application.dto;

import com.issuetracker.account.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountInformation {

	private Long id;
	private String email;
	private String nickname;
	private String profileImageUrl;
	private Long oauthId;

	public static AccountInformation from(Account account) {
		return new AccountInformation(
			account.getId(),
			account.getEmail(),
			account.getNickname(),
			account.getProfileImageUrl(),
			account.getOauthId()
		);
	}

	public AccountInputData toAccountInputData() {
		return new AccountInputData(
			id,
			email,
			nickname,
			profileImageUrl,
			oauthId
		);
	}

}
