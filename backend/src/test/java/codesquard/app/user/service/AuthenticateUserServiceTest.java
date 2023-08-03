package codesquard.app.user.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.authenticate_user.service.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;

class AuthenticateUserServiceTest extends IntegrationTestSupport {

	@Autowired
	private AuthenticateUserService authenticateUserService;

	@Transactional
	@Test
	@DisplayName("refreshToken이 주어지고 토큰 갱신을 요청할때 토큰이 갱신된다")
	public void refreshToken_givenRefreshToken_whenRefreshToken_thenResponseJwt() {
		// given
		String refreshToken = "eyJhbGciOiJIUzM4NCJ9.eyJleHAiOjE2OTM2Mzk0Njl9.VTsTjBvNnS_D2xAj2uNwYlqQ9v5Re8tEQNuPuojTrud7Ybf7b1_wm9s1ypmhLbzS";
		RefreshTokenServiceRequest refreshTokenServiceRequest = new RefreshTokenServiceRequest(refreshToken);
		// when
		Jwt jwt = authenticateUserService.refreshToken(refreshTokenServiceRequest);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(jwt).isNotNull();
			softAssertions.assertAll();
		});
	}
}
