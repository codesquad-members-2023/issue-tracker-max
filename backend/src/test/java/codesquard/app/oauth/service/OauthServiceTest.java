package codesquard.app.oauth.service;

import static org.mockito.Mockito.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.oauth.InMemoryProviderRepository;
import codesquard.app.oauth.client.GithubOauthClient;
import codesquard.app.oauth.fixture.FixedAuthenticateUserFactory;
import codesquard.app.oauth.profile.UserProfile;
import codesquard.app.oauth.service.response.OauthAccessTokenResponse;
import codesquard.app.oauth.service.response.OauthLoginServiceResponse;
import codesquard.app.user.repository.UserRepository;

@Transactional
class OauthServiceTest extends IntegrationTestSupport {

	private OauthService oauthService;

	@Autowired
	private InMemoryProviderRepository inMemoryProviderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthenticateUserService authenticateUserService;

	@Mock
	private GithubOauthClient oauthClient;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@BeforeEach
	public void beforeEach() {
		oauthService = new OauthService(inMemoryProviderRepository, userRepository, jwtProvider, objectMapper,
			authenticateUserService, oauthClient);
	}

	@Test
	@DisplayName("깃허브와 같은 provider 이름과 인가코드가 주어지고 소셜 로그인을 요청 할때 Jwt를 담은 객체를 응답한다")
	public void givenProviderAndAuthorizationCode_whenRequestLogIn_thenResponseJwt() {
		// given
		String provider = "github";
		String code = "authorizationcode";
		OauthAccessTokenResponse response = FixedAuthenticateUserFactory.oauthAccessTokenResponse();
		UserProfile userProfile = FixedAuthenticateUserFactory.userProfile();
		// mocking
		when(oauthClient.getAccessToken(any(), any())).thenReturn(response);
		when(oauthClient.getUserProfile(any(), any(), any())).thenReturn(userProfile);
		// when
		OauthLoginServiceResponse serviceResponse = oauthService.login(provider, code);
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(serviceResponse).extracting("user").isNotNull();
			softAssertions.assertThat(serviceResponse).extracting("jwt").isNotNull();
			softAssertions.assertThat(serviceResponse).extracting("tokenType").isEqualTo("Bearer");
			softAssertions.assertThat(redisTemplate.opsForValue().get("RT:hong1234@gmail.com")).isNotNull();
			softAssertions.assertAll();
		});
	}
}
