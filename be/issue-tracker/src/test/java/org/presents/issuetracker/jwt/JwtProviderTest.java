package org.presents.issuetracker.jwt;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtProviderTest {

	@Autowired
	private JwtProvider jwtProvider;

	@Test
	@DisplayName("jwt 토큰을 발급한다.")
	public void createToken() {
		// given
		Map<String, Object> claims = new HashMap<>();
		claims.put("loginId", "ayaan");
		claims.put("password", "1234");

		// when
		String accessToken = jwtProvider.createToken(claims, 3600);

		//then
		System.out.println(accessToken);
		assertThat(accessToken).isNotNull();
	}

	@Test
	@DisplayName("토큰의 유효기간을 검증한다.")
	public void verify() {
		//given
		Map<String, Object> claims = new HashMap<>();
		claims.put("loginId", "ayaan");
		claims.put("password", "1234");

		//when
		String accessToken2 = jwtProvider.createToken(claims, 3600);
		String accessToken = jwtProvider.createToken(claims, -1);

		//then
		assertThat(jwtProvider.verify(accessToken2)).isTrue();
		assertThat(jwtProvider.verify(accessToken)).isFalse();
	}

	@Test
	@DisplayName("토큰의 claim 정보를 구할 수 있다.")
	public void getClaims() {
		//given
		Map<String, Object> givenClaims = new HashMap<>();
		givenClaims.put("loginId", "ayaan");
		givenClaims.put("password", "1234");

		//when
		String accessToken = jwtProvider.createToken(givenClaims, 3600);
		Map<String, Object> claims = jwtProvider.getClaims(accessToken);

		//then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(claims.get("loginId")).isEqualTo(givenClaims.get("loginId"));
			softAssertions.assertThat(claims.get("password")).isEqualTo(givenClaims.get("password"));
			softAssertions.assertAll();
		});
	}
}
