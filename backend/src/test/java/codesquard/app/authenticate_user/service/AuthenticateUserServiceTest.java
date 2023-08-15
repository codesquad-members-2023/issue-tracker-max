package codesquard.app.authenticate_user.service;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.repository.AuthenticateUserRepository;
import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.service.UserQueryService;
import codesquard.app.user.service.UserService;
import codesquard.app.user.service.request.UserLoginServiceRequest;
import codesquard.app.user.service.request.UserSaveServiceRequest;

@Transactional
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

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0");
		jdbcTemplate.update("TRUNCATE TABLE comment");
		jdbcTemplate.update("TRUNCATE TABLE issue");
		jdbcTemplate.update("TRUNCATE TABLE milestone");
		jdbcTemplate.update("TRUNCATE TABLE user");
		jdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 1");
	}

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
		RefreshTokenServiceRequest refreshTokenServiceRequest = new RefreshTokenServiceRequest(jwt.getRefreshToken());
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
	public void updateRefreshToken_givenAuthenticateUserAndJwt_whenUpdateRefreshToken_thenSaveTheRefreshToken() throws
		JsonProcessingException {
		// given
		HashMap<String, Object> claims = new HashMap<>();

		// 회원가입
		userService.signUp(new UserSaveServiceRequest("hong1234", "hong1234@gmail.com", "hong1234", "hong1234", null));

		// 인증 유저 및 jwt 생성
		AuthenticateUser authenticateUser = userQueryService.verifyUser(
			new UserLoginServiceRequest("hong1234", "hong1234"));
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, objectMapper.writeValueAsString(authenticateUser));
		Jwt jwt = jwtProvider.createJwt(claims);

		// when
		authenticateUserService.updateRefreshToken(authenticateUser, jwt);
		// then
		String refreshToken = (String)redisTemplate.opsForValue().get(authenticateUser.createRedisKey());
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(refreshToken).isEqualTo(jwt.getRefreshToken());
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("인증 유저 객체와 JWT 객체가 주어지고 로그아웃을 요청할때 로그아웃 된다")
	public void givenAuthenticateUserAndJwt_whenRequestLogout_thenLogout() throws JsonProcessingException {
		// given
		AuthenticateUser user = new AuthenticateUser(1L, "hong1234", "hong1234@gmail.com", null);
		Map<String, Object> claims = new HashMap<>();
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, objectMapper.writeValueAsString(user));
		Jwt jwt = jwtProvider.createJwt(claims);
		// 로그인
		authenticateUserService.login(user);
		// when
		authenticateUserService.logout(user, jwt);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(redisTemplate.opsForValue().get(jwt.getAccessToken())).isEqualTo("logout");
			softAssertions.assertThat(redisTemplate.opsForValue().get(user.createRedisKey())).isNull();
		});
	}
}
