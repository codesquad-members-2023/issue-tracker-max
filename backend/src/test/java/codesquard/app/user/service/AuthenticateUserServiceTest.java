package codesquard.app.user.service;

import java.util.HashMap;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.authenticate_user.service.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.user.entity.AuthenticateUser;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;

class AuthenticateUserServiceTest extends IntegrationTestSupport {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticateUserService authenticateUserService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserQueryService userQueryService;

	@Transactional
	@Test
	@DisplayName("refreshToken이 주어지고 토큰 갱신을 요청할때 토큰이 갱신된다")
	public void refreshToken_givenRefreshToken_whenRefreshToken_thenResponseJwt() {
		// given
		// 회원가입
		userService.signUp(new UserSaveServiceRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234", null));

		// 인증 유저 및 jwt 생성
		HashMap<String, Object> claims = new HashMap<>();
		Jwt jwt = jwtProvider.createJwt(claims);
		AuthenticateUser authenticateUser = userQueryService.verifyUser(
			new UserLoginServiceRequest("hong1234", "hong1234"));
		authenticateUserService.updateRefreshToken(authenticateUser, jwt);
		RefreshTokenServiceRequest refreshTokenServiceRequest = new RefreshTokenServiceRequest(
			jwt.createRefreshTokenCookie().getValue());
		// when
		Jwt refreshedJwt = authenticateUserService.refreshToken(refreshTokenServiceRequest);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(refreshedJwt).isNotNull();
			softAssertions.assertAll();
		});
	}
}
