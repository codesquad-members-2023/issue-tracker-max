package com.issuetracker.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OauthAccountInputData {

	private Long oauthId;
	private String email;
	private String nickname;
	private String profileUrl;

}
