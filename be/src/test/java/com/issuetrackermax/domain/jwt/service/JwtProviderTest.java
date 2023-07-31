package com.issuetrackermax.domain.jwt.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.issuetrackermax.domain.jwt.entity.Jwt;
import com.issuetrackermax.service.jwt.JwtProvider;

import io.jsonwebtoken.Claims;

@SpringBootTest
@ActiveProfiles("test")
class JwtProviderTest {

	@Autowired
	private JwtProvider jwtProvider;

	@DisplayName("Claim, expireDate를 정한 Jwt Token을 만든 후 올바르게 토큰이 생성되었는지 확인한다.")
	@Test
	void createToken() {
		// given
		Long memberId = 1L;
		Map<String, Object> claims = Map.of("memberId", memberId);
		Date expireDate = jwtProvider.getExpireDateAccessToken();

		// when
		String token = jwtProvider.createToken(claims, expireDate);

		// then
		Claims claim = jwtProvider.getClaims(token);
		assertThat(claim.get("memberId")).isEqualTo(1);
	}

	@DisplayName("accessToken, refreshToken을 담고 있는Jwt 토큰을 생성한다.")
	@Test
	void createJwt() {
		// given
		Long memberId = 1L;
		Map<String, Object> claims = Map.of("memberId", memberId);

		// then
		Jwt jwt = jwtProvider.createJwt(claims);
		Claims accessTokenClaim = jwtProvider.getClaims(jwt.getAccessToken());
		Claims refreshTokenClaim = jwtProvider.getClaims(jwt.getRefreshToken());

		// then
		assertThat(accessTokenClaim.get("memberId")).isEqualTo(1);
		assertThat(refreshTokenClaim.get("memberId")).isNull();
	}

	@Test
	void reissueAccessToken() {
		// given
		String refreshToken = "refreshToken";
		Long memberId = 1L;
		Map<String, Object> claims = Map.of("memberId", memberId);

		// when
		Jwt jwt = jwtProvider.reissueAccessToken(claims, refreshToken);
		Claims accessTokenClaim = jwtProvider.getClaims(jwt.getAccessToken());
		String refreshTokenFromJwt = jwt.getRefreshToken();
		// then
		assertThat(accessTokenClaim.get("memberId")).isEqualTo(1);
		assertThat(refreshTokenFromJwt).isEqualTo(refreshToken);
	}

}