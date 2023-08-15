package codesquard.app.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final Key key;

	public JwtProvider(@Value("${jwt_secret}") String jwt_secret) {
		byte[] secret = jwt_secret.getBytes();
		key = Keys.hmacShaKeyFor(secret);
	}

	public String createToken(Map<String, Object> claims, Date expireDate) {
		// claims를 비밀키로 이용하여 암호화
		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expireDate)
			.signWith(key)
			.compact();
	}

	/**
	 *
	 * @param token
	 * @UnsupportedJwtException – claimsJws 인수가 Claims JWS를 나타내지 않는 경우
	 * @MalformedJwtException – claimsJws 문자열이 유효한 JWS가 아닌 경우
	 * @SignatureException – claimsJws의 JWS 서명이 유효성 검사가 실패하는 경우
	 * @ExpiredJwtException – 만약 명세된 JWT가 Claims JWT이고 Claims이 만료된 경우
	 * @IllegalArgumentException – ClaimsJws 문자열이 null, empty, 공백인 경우
	 * @return token을 비밀키로 복호화한 Claims
	 */
	public Claims getClaims(String token) {
		// token을 비밀키로 복호화하여 Claims 추출
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Jwt createJwt(Map<String, Object> claims) {
		// 1. accessToken 생성
		String accessToken = createToken(claims, getExpireDateAccessToken());

		// 2. refreshToken 생성
		String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToken());

		// 3. JWT 생성
		return new Jwt(accessToken, refreshToken);
	}

	public Date getExpireDateAccessToken() {
		final long DEFAULT_ACCESSTOKEN_EXPIRE_MILLISECOND = 1000 * 60 * 5; // 5분
		return new Date(System.currentTimeMillis() + DEFAULT_ACCESSTOKEN_EXPIRE_MILLISECOND);
	}

	public Date getExpireDateRefreshToken() {
		final long DEFAULT_REFRESHTOKEN_EXPIRE_MILLISECOND = 1000L * 60 * 60 * 24 * 14; // 14일
		return new Date(System.currentTimeMillis() + DEFAULT_REFRESHTOKEN_EXPIRE_MILLISECOND);
	}
}
