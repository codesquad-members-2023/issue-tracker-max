package org.presents.issuetracker.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenInfo {
	private final String accessToken;
	private final String refreshToken;

	@Builder
	private TokenInfo(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
