package org.presents.issuetracker.auth.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private SecretKey cachedSecretKey;

	@Value("${jwt.secret}")
	private String secretKeyPlain;

	private SecretKey encodeSecretKey() {
		String keyBased64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
		return Keys.hmacShaKeyFor(keyBased64Encoded.getBytes());
	}

	public SecretKey getSecretKey() {
		if (cachedSecretKey == null) {
			cachedSecretKey = encodeSecretKey();
		}
		return cachedSecretKey;
	}

	public String createToken(Map<String, Object> claims, int seconds) {
		long now = new Date().getTime();
		Date expiration = new Date(now + 1000L * seconds);

		try {
			return Jwts.builder()
				.claim("body", new ObjectMapper().writeValueAsString(claims))
				.setExpiration(expiration)
				.signWith(getSecretKey(), SignatureAlgorithm.HS512)
				.compact();
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
