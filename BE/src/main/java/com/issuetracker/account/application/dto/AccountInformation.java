package com.issuetracker.account.application.dto;

import com.issuetracker.account.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountInformation {

	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String profileImageUrl;

	public static AccountInformation from(Account account) {
		return new AccountInformation(
			account.getId(),
			account.getEmail(),
			account.getPassword(),
			account.getNickname(),
			account.getProfileImageUrl()
		);
	}

}
