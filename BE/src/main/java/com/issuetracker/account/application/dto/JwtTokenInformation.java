package com.issuetracker.account.application.dto;

import com.issuetracker.account.domain.JwtToken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtTokenInformation {

	private String accessToken;
	private String refreshToken;

	public static JwtTokenInformation from(JwtToken jwtToken) {
		return new JwtTokenInformation(
			jwtToken.getAccessToken(),
			jwtToken.getRefreshToken()
		);
	}
}
