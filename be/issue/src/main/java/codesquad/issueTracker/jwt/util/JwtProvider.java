package codesquad.issueTracker.jwt.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import codesquad.issueTracker.jwt.domain.Jwt;
import codesquad.issueTracker.jwt.domain.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Getter
public class JwtProvider {

	private final JwtProperties jwtProperties;

	private final Long ACCESS_TOKEN_EXP_TIME = 1000L * 60 * 60;
	private final Long REFRESH_TOKEN_EXP_TIME = 7L * 24 * 60 * 60;

	public String createToken(Map<String, Object> claims, Date expiration) {

		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expiration)
			.signWith(SignatureAlgorithm.HS256,
				Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes()))
			.compact();
	}

	public Long getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("userId", Long.class);
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes()))
			.parseClaimsJws(token)
			.getBody();
	}

	public Jwt createJwt(Map<String, Object> claims) {
		String accessToken = createToken(claims, getExpireDateAccessToken());
		String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToken());

		return Jwt.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public String reissueAccessToken(Map<String, Object> claims) {
		return createToken(claims, getExpireDateAccessToken());
	}

	public Date getExpireDateAccessToken() {
		return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP_TIME);
	}

	public Date getExpireDateRefreshToken() {
		return new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP_TIME);
	}

}
