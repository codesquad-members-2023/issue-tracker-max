package org.presents.issuetracker.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
	private final String accessToken;
	private final String refreshToken;

	@Builder
	private TokenResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
