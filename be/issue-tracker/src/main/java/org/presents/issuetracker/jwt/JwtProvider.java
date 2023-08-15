package org.presents.issuetracker.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.statuscode.JwtErrorCode;
import org.presents.issuetracker.global.util.JsonUtil;
import org.presents.issuetracker.jwt.dto.Jwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private static final int ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60;    // 1시간
	private static final int REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 5;    // 5시간

	@Value("${jwt.secret}")
	private String secretKeyPlain;
	private Key key;

	@PostConstruct
	protected void init() {
		String keyBased64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
		key = Keys.hmacShaKeyFor(keyBased64Encoded.getBytes());
	}

	public Jwt generateToken(Map<String, Object> claims) {
		return Jwt.builder()
			.accessToken(createToken(claims, ACCESS_TOKEN_EXPIRATION_TIME))
			.refreshToken(createToken(new HashMap<>(), REFRESH_TOKEN_EXPIRATION_TIME))
			.build();
	}

	public String createToken(Map<String, Object> claims, int seconds) {
		long now = new Date().getTime();
		Date expiration = new Date(now + 1000L * seconds);

		return Jwts.builder()
			.claim("body", JsonUtil.toStr(claims))
			.setExpiration(expiration)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public Jwt reissueToken(Map<String, Object> claims, String refreshToken) {
		if (verify(refreshToken)) {
			// access token만 재발급
			return Jwt.builder()
				.accessToken(createToken(claims, ACCESS_TOKEN_EXPIRATION_TIME))
				.refreshToken(refreshToken)
				.build();
		} else {
			// refresh token 만료 시 다시 로그인 요청을 위한 예외 발생
			throw new CustomException(JwtErrorCode.EXPIRED_JWT_TOKEN);
		}
	}

	public boolean verify(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Map<String, Object> getClaims(String token) {
		String claims = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get("body", String.class);
		return JsonUtil.toMap(claims);
	}
}
