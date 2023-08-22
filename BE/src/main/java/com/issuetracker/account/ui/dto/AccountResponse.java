package com.issuetracker.account.ui.dto;

import com.issuetracker.account.application.dto.AccountInformation;
import com.issuetracker.account.application.dto.AccountInputData;

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
	private Long oauthId;

	public static AccountResponse from(AccountInformation accountInformation) {
		return new AccountResponse(
			accountInformation.getId(),
			accountInformation.getEmail(),
			accountInformation.getNickname(),
			accountInformation.getProfileImageUrl(),
			accountInformation.getOauthId()
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
