package codesquard.app.jwt;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import codesquard.app.IntegrationTestSupport;
import io.jsonwebtoken.Claims;

class JwtProviderTest extends IntegrationTestSupport {
	@Autowired
	private JwtProvider jwtProvider;

	@Test
	@DisplayName("claims를 암호화합니다.")
	public void createToken() {
		// given
		Map<String, Object> claims = new HashMap<>();
		claims.put("loginId", "hong1234");
		claims.put("password", "hong1234");
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		Date expireDate = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());
		// when
		String token = jwtProvider.createToken(claims, expireDate);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(token).isNotNull();
			softAssertions.assertThat(token).startsWith("eyJhbGciOiJIUzM4NCJ9");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("암호화된 토큰을 파싱합니다.")
	public void getClaims() {
		// given
		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("loginId", "hong1234");
		claimsMap.put("password", "hong1234");
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		Date expireDate = Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String token = jwtProvider.createToken(claimsMap, expireDate);
		// when
		Claims claims = jwtProvider.getClaims(token);
		// then
		String loginId = claims.get("loginId", String.class);
		String password = claims.get("password", String.class);
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(loginId).isEqualTo("hong1234");
			softAssertions.assertThat(password).isEqualTo("hong1234");
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("accessToken과 refreshToken을 가진 Jwt 객체를 생성합니다.")
	public void createJwt() {
		// given
		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("loginId", "hong1234");
		claimsMap.put("password", "hong1234");
		// when
		Jwt jwt = jwtProvider.createJwt(claimsMap);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(jwt).isNotNull();
			softAssertions.assertAll();
		});
	}
}
