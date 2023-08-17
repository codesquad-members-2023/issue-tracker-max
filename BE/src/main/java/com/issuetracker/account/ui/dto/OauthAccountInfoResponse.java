package com.issuetracker.account.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.issuetracker.account.application.dto.OauthAccountInputData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OauthAccountInfoResponse {

	@JsonProperty("id")
	private Long oauthId;

	@JsonProperty("email")
	private String email;

	@JsonProperty("login")
	private String nickname;

	@JsonProperty("avatar_url")
	private String profileUrl;

	public OauthAccountInputData toOauthAccountInputData() {
		return new OauthAccountInputData(
			oauthId, email, nickname, profileUrl
		);
	}

	public boolean verifyEmail() {
		return email != null && !email.isBlank();
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
