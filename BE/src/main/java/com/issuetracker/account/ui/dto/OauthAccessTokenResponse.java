package com.issuetracker.account.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthAccessTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;
}
