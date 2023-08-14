package org.presents.issuetracker.auth.jwt;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
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
		Assertions.assertThat(accessToken).isNotNull();
	}
}
