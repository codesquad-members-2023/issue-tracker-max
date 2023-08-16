package codesquard.app.oauth.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.service.AuthenticateUserService;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.oauth.InMemoryProviderRepository;
import codesquard.app.oauth.OauthProvider;
import codesquard.app.oauth.client.GithubOauthClient;
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
	private final GithubOauthClient oauthClient;

	public OauthService(InMemoryProviderRepository inMemoryProviderRepository, UserRepository userRepository,
		JwtProvider jwtProvider, ObjectMapper objectMapper, AuthenticateUserService authenticateUserService,
		GithubOauthClient oauthClient) {
		this.inMemoryProviderRepository = inMemoryProviderRepository;
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
		this.authenticateUserService = authenticateUserService;
		this.oauthClient = oauthClient;
	}

	public OauthLoginServiceResponse login(String provider, String code) {
		Map<String, Object> claims = new HashMap<>();

		// 1. 클라이언트에서 전달한 provider 이름을 통해 InMemoryProviderRepository에서 OauthProvider를 가져옵니다.
		OauthProvider oauthProvider = inMemoryProviderRepository.findByProviderName(provider);
		logger.debug("oauthProvider : {}", oauthProvider);

		// 2. accessToken 가져오기
		OauthAccessTokenResponse accessTokenResponse = oauthClient.requestAccessTokenToOauthServer(oauthProvider, code);
		logger.debug("OauthAcessTokenResponse : {}", accessTokenResponse);

		// 3. 유저 정보 가져오기
		UserProfile userProfile = oauthClient.requestUserProfile(provider, accessTokenResponse, oauthProvider);
		logger.debug("userProfile : {}", userProfile);

		// 4. 유저 정보 DB에 저장
		User user = saveOrUpdate(userProfile);
		logger.debug("user : {}", user);

		// 5. Jwt 객체 생성
		AuthenticateUser authenticateUser = user.toAuthenticateUser();
		logger.debug("authenticateUser : {}", authenticateUser);

		try {
			// 6. claims 맵에 인증 유저 json 정보 추가
			String authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
			claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
		} catch (JsonProcessingException e) {
			logger.error("AuthenticateUser json 변환 에러 : {}", e.getMessage());
			throw new RestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		// 7. Jwt(액세스 토큰, 리프레쉬 토큰) 객체 생성
		Jwt jwt = jwtProvider.createJwt(claims);
		logger.debug("jwt : {}", jwt);

		// 8. 리프레쉬 토큰 갱신
		authenticateUserService.updateRefreshToken(authenticateUser, jwt);
		return new OauthLoginServiceResponse(authenticateUser, jwt, "Bearer");
	}

	private User saveOrUpdate(UserProfile userProfile) {
		User user = userRepository.findByEmail(userProfile.toUserEntity().getEmail());

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
