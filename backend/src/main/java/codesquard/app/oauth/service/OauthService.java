package codesquard.app.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.errorcode.OauthErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.oauth.InMemoryProviderRepository;
import codesquard.app.oauth.OauthAttributes;
import codesquard.app.oauth.OauthProvider;
import codesquard.app.oauth.profile.UserProfile;
import codesquard.app.oauth.service.response.OauthAccessTokenResponse;
import codesquard.app.oauth.service.response.OauthLoginServiceResponse;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

@Transactional
@Service
public class OauthService {

	private static final Logger logger = LoggerFactory.getLogger(OauthService.class);

	private final InMemoryProviderRepository inMemoryProviderRepository;
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;
	private final AuthenticateUserService authenticateUserService;

	public OauthService(InMemoryProviderRepository inMemoryProviderRepository, UserRepository userRepository,
		JwtProvider jwtProvider, ObjectMapper objectMapper, AuthenticateUserService authenticateUserService) {
		this.inMemoryProviderRepository = inMemoryProviderRepository;
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
		this.authenticateUserService = authenticateUserService;
	}

	public OauthLoginServiceResponse login(String provider, String code) {
		// 1. 클라이언트에서 전달한 provider 이름을 통해 InMemoryProviderRepository에서 OauthProvider를 가져옵니다.
		OauthProvider oauthProvider = inMemoryProviderRepository.findByProviderName(provider);
		logger.info("oauthProvider : {}", oauthProvider);
		// 2. accessToken 가져오기
		OauthAccessTokenResponse accessTokenResponse = requestAccessTokenByAuthorizationCode(oauthProvider, code);
		logger.info("OauthAcessTokenResponse : {}", accessTokenResponse);
		// 3. 유저 정보 가져오기
		UserProfile userProfile = getUserProfile(provider, accessTokenResponse, oauthProvider);
		logger.info("userProfile : {}", userProfile);
		// 4. 유저 정보 DB에 저장
		User user = saveOrUpdate(userProfile);
		// 5. Jwt 객체 생성
		AuthenticateUser authenticateUser = user.toAuthenticateUser();
		logger.info("authenticateUser : {}", authenticateUser);
		Map<String, Object> claims = new HashMap<>();
		try {
			String authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
			claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
		} catch (JsonProcessingException e) {
			logger.error("로그인 오류 : {}", e.getMessage());
			throw new RestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		Jwt jwt = jwtProvider.createJwt(claims);
		authenticateUserService.updateSocialUserRefreshToken(authenticateUser, jwt);
		return new OauthLoginServiceResponse(authenticateUser, jwt, "Bearer");
	}

	private OauthAccessTokenResponse requestAccessTokenByAuthorizationCode(OauthProvider provider, String code) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUri())
			.headers(header -> {
				header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(provider.getRedirectUri(), code))
			.retrieve()// ResponseEntity를 받아 디코딩
			.bodyToMono(OauthAccessTokenResponse.class) // 주어진 타입으로 디코딩
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(String redirectUri, String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", redirectUri);
		return formData;
	}

	private UserProfile getUserProfile(String provider, OauthAccessTokenResponse oauthAccessTokenResponse,
		OauthProvider oauthProvider) {
		Map<String, Object> userAttributes = getUserAttributes(oauthProvider, oauthAccessTokenResponse);
		// 유저 정보(map)를 통해 UserProfile 만들기
		return OauthAttributes.extract(provider, userAttributes);
	}

	// Oauth 서버에서 유저 정보 map으로 가져오기
	private Map<String, Object> getUserAttributes(OauthProvider oauthProvider,
		OauthAccessTokenResponse oauthAccessTokenResponse) {
		try {
			return requestUserAttributes(oauthProvider, oauthAccessTokenResponse);
		} catch (WebClientResponseException.Unauthorized e) {
			logger.error("소셜 사용자의 정보 요청 인가 오류 : {}", e.getMessage());
			throw new RestApiException(OauthErrorCode.BAD_AUTHORIZATION_CODE);
		}
	}

	private Map<String, Object> requestUserAttributes(OauthProvider oauthProvider,
		OauthAccessTokenResponse oauthAccessTokenResponse) {
		return WebClient.create()
			.get()
			.uri(oauthProvider.getUserInfoUri())
			.headers(header -> header.setBearerAuth(oauthAccessTokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	private User saveOrUpdate(UserProfile userProfile) {
		User user = userRepository.findByEmail(userProfile.toUserEntity());

		if (user == null) {
			user = userProfile.toUserEntity();
			Long saveId = userRepository.save(user);
			return userRepository.findById(saveId);
		}

		user = user.update(userProfile.toUserEntity());
		Long modifyId = userRepository.modify(user);
		return userRepository.findById(modifyId);
	}
}
