package codesquard.app.authenticate_user.service;

import java.util.HashMap;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.repository.AuthenticateUserRepository;
import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.user.service.UserQueryService;
import codesquard.app.user.service.UserService;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;

class AuthenticateUserServiceTest extends IntegrationTestSupport {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticateUserService authenticateUserService;

	@Autowired
	private AuthenticateUserRepository authenticateUserRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserQueryService userQueryService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE TABLE comment");
		jdbcTemplate.update("TRUNCATE TABLE issue");
		jdbcTemplate.update("TRUNCATE TABLE milestone");
		jdbcTemplate.update("TRUNCATE TABLE user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

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

	@Test
	@DisplayName("처음 로그인하는 인증 유저와 JWT가 주어지고 RefreshToken 갱신을 요청할때 RefreshToken이 저장된다")
	public void updateRefreshToken_givenAuthenticateUserAndJwt_whenUpdateRefreshToken_thenSaveTheRefreshToken() {
		// given
		// 회원가입
		userService.signUp(new UserSaveServiceRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234", null));
		// 인증 유저 및 jwt 생성
		HashMap<String, Object> claims = new HashMap<>();
		Jwt jwt = jwtProvider.createJwt(claims);
		AuthenticateUser authenticateUser = userQueryService.verifyUser(
			new UserLoginServiceRequest("hong1234", "hong1234"));
		// when
		authenticateUserService.updateRefreshToken(authenticateUser, jwt);
		// then
		boolean actual = authenticateUserRepository.isExistRefreshToken(authenticateUser.toEntity());
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(actual).isTrue();
			softAssertions.assertAll();
		});
	}
}
