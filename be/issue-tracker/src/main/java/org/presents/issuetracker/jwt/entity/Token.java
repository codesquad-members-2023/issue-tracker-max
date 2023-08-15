package org.presents.issuetracker.jwt.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {
	private final String refreshToken;
	private final String loginId;

	@Builder
	private Token(String refreshToken, String loginId) {
		this.refreshToken = refreshToken;
		this.loginId = loginId;
	}
}
