package com.issuetracker.account.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtRefreshToken {

	private Long memberId;
	private String refreshToken;
	private LocalDateTime expirationDateTime;

	public static JwtRefreshToken from(Long memberId, String refreshToken, LocalDateTime expirationDateTime) {
		return new JwtRefreshToken(
			memberId,
			refreshToken,
			expirationDateTime
		);
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expirationDateTime);
	}
}
