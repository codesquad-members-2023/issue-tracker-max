package com.issuetracker.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountInputData {

	private Long id;
	private String email;
	private String nickname;
	private String profileImageUrl;
	private Long oauthId;

	public boolean verifyRequiredValue() {
		return id != null && email != null && nickname != null;
	}

}
