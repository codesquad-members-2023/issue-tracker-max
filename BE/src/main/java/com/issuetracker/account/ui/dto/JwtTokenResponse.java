package com.issuetracker.account.ui.dto;

import com.issuetracker.account.application.dto.JwtTokenInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class JwtTokenResponse {

	private String accessToken;
	private String refreshToken;

	public static JwtTokenResponse from(JwtTokenInformation jwtTokenInformation) {
		return new JwtTokenResponse(
			jwtTokenInformation.getAccessToken(),
			jwtTokenInformation.getRefreshToken()
		);
	}
}
