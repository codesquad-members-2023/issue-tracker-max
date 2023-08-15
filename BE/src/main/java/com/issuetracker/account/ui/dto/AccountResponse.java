package com.issuetracker.account.ui.dto;

import com.issuetracker.account.application.dto.AccountInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AccountResponse {

	private Long id;
	private String email;
	private String nickname;
	private String profileImageUrl;

	public static AccountResponse from(AccountInformation accountInformation) {
		return new AccountResponse(
			accountInformation.getId(),
			accountInformation.getEmail(),
			accountInformation.getNickname(),
			accountInformation.getProfileImageUrl()
		);
	}

	public boolean verify() {
		return id != null;
	}
}
