package kr.codesquad.issuetracker.infrastructure.config.jwt;

import lombok.Getter;

@Getter
public class Jwt {

	private final String secretKey;
	private final long expirationMilliseconds;

	public Jwt(String secretKey, long expirationMilliseconds) {
		this.secretKey = secretKey;
		this.expirationMilliseconds = expirationMilliseconds;
	}
}
