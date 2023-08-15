package com.issuetracker.account.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OauthAccountInfoResponse {

	@JsonProperty("id")
	private String oauthId;

	@JsonProperty("email")
	private String email;

	@JsonProperty("login")
	private String nickname;

	@JsonProperty("avatar_url")
	private String profileUrl;

}
