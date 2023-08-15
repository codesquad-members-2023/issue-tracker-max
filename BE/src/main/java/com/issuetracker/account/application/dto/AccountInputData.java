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

}
