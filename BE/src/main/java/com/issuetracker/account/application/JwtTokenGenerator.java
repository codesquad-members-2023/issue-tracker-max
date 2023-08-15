package com.issuetracker.account.application;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.issuetracker.account.domain.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenGenerator {

	private static final long EXPIRED_TIME_OF_ACCESS_TOKEN_IN_MILLISECOND = 1000L * 60 * 60;
	private static final long EXPIRED_TIME_OF_REFRESH_TOKEN_IN_MILLISECOND = 1000L * 60 * 60 * 24 * 60;

	private final Key key;

	public JwtTokenGenerator(@Value("${jwt.secret}") String secret) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	private String createToken(Map<String, Object> claims, Date expireDate) {
		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expireDate)
			.signWith(key)
			.compact();
	}

	/**
	 * 파라미터로 입력받은 token에서 Claims을 추출한다. 추출 하면서 토큰 검증도 같이 한다. 토큰 검증에 실패한 경우 JwtException 을 발생시킨다.
	 * @param token Claims를 추출할 토큰 문자열
	 * @return 토큰의 Claims
	 */
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public JwtToken createJwt(Map<String, Object> claims) {
		String accessToken = createToken(claims, getExpireDateAccessToken());
		String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToken());
		return new JwtToken(accessToken, refreshToken);
	}

	public JwtToken reissueAccessToken(Map<String, Object> claims, String refreshToken) {
		String accessToken = createToken(claims, getExpireDateAccessToken());
		return new JwtToken(accessToken, refreshToken);
	}

	private Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + EXPIRED_TIME_OF_ACCESS_TOKEN_IN_MILLISECOND);
	}

	private Date getExpireDateRefreshToken() {
		return new Date(System.currentTimeMillis() + EXPIRED_TIME_OF_REFRESH_TOKEN_IN_MILLISECOND);
	}

}