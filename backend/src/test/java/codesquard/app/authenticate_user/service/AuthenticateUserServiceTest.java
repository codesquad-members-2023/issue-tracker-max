package codesquard.app.authenticate_user.service;

import static codesquard.app.user.fixture.FixedUserFactory.*;

import java.util.HashMap;

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
	public void refreshToken_givenRefreshToken_whenRefreshToken_thenResponseJwt() throws JsonProcessingException {
		// sample
		signUp();
		// given
		AuthenticateUser user = authenticateUser();
		Jwt jwt = jwtWithAuthenticateUser(user);
		authenticateUserService.updateRefreshToken(user, jwt);
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
		// sample
		signUp();
		// given
		AuthenticateUser user = authenticateUser();
		Jwt jwt = jwtWithAuthenticateUser(user);
		// when
		authenticateUserService.updateRefreshToken(user, jwt);
		// then
		String refreshToken = (String)redisTemplate.opsForValue().get(user.createRedisKey());
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(refreshToken).isEqualTo(jwt.getRefreshToken());
			softAssertions.assertAll();
		});
	}

	@Test
	@DisplayName("인증 유저 객체와 JWT 객체가 주어지고 로그아웃을 요청할때 로그아웃 된다")
	public void givenAuthenticateUserAndJwt_whenRequestLogout_thenLogout() throws JsonProcessingException {
		// given
		signUp();
		AuthenticateUser user = authenticateUser();
		Jwt jwt = jwtWithAuthenticateUser(user);
		login(user);
		// when
		authenticateUserService.logout(user, jwt);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(redisTemplate.opsForValue().get(jwt.getAccessToken())).isEqualTo("logout");
			softAssertions.assertThat(redisTemplate.opsForValue().get(user.createRedisKey())).isNull();
		});
	}

	private void signUp() {
		userService.signUp(userSaveServiceRequest());
	}

	private void login(AuthenticateUser user) {
		authenticateUserService.login(user);
	}

	private AuthenticateUser authenticateUser() {
		UserLoginServiceRequest request = userLoginServiceRequest();
		return userQueryService.verifyUser(request);
	}

	private Jwt jwtWithAuthenticateUser(AuthenticateUser user) throws JsonProcessingException {
		// 인증 유저 및 jwt 생성
		HashMap<String, Object> claims = new HashMap<>();
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, objectMapper.writeValueAsString(user));
		return jwtProvider.createJwt(claims);
	}
}
