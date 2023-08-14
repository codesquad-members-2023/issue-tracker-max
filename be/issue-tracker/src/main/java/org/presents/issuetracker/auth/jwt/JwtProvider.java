package org.presents.issuetracker.auth.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.presents.issuetracker.global.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

		return Jwts.builder()
			.claim("body", JsonUtil.toStr(claims))
			.setExpiration(expiration)
			.signWith(getSecretKey(), SignatureAlgorithm.HS512)
			.compact();
	}

	public boolean verify(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.build()
				.parseClaimsJws(token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map<String, Object> getClaims(String token) {
		String claims = Jwts.parserBuilder()
			.setSigningKey(getSecretKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.get("body", String.class);
		return JsonUtil.toMap(claims);
	}
}
