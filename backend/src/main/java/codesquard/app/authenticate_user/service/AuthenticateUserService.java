package codesquard.app.authenticate_user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.JwtTokenErrorCode;
import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.service.request.RefreshTokenServiceRequest;
import codesquard.app.authenticate_user.service.response.AuthenticateUserLoginServiceResponse;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;
import io.jsonwebtoken.Claims;

@Transactional
@Service
public class AuthenticateUserService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateUserService.class);

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;

	public AuthenticateUserService(UserRepository userRepository, JwtProvider jwtProvider, ObjectMapper objectMapper,
		RedisTemplate<String, Object> redisTemplate) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
		this.redisTemplate = redisTemplate;
	}

	// redis에 저장된 이메일 키값에 따른 리프레쉬 토큰의 값을 갱신합니다.
	public void updateRefreshToken(AuthenticateUser authenticateUser, Jwt jwt) {
		logger.info("리프레쉬 토큰 갱신 : {}, {}", authenticateUser, jwt);
		redisTemplate.opsForValue().set(authenticateUser.createRedisKey(),
			jwt.getRefreshToken(),
			jwt.getExpireDateRefreshToken(),
			TimeUnit.MILLISECONDS);
	}

	// 입력으로 주어진 리프레쉬 토큰을 이용하여 JWT 객체를 생성하고 갱신합니다.
	public Jwt refreshToken(RefreshTokenServiceRequest refreshTokenServiceRequest) {
		logger.info("리프레쉬 토큰 갱신 서비스 : {}", refreshTokenServiceRequest);
		try {
			HashMap<String, Object> claims = new HashMap<>();

			// 1. 토큰이 유효한지 확인합니다. 유효하지 않은 경우 예외가 발생합니다.
			Claims findClaims = jwtProvider.getClaims(refreshTokenServiceRequest.getRefreshToken());
			logger.debug("claims : {}", findClaims);

			// 2. refreshToken 값을 가지는 유저를 조회합니다.
			String email = findEmailByRefreshToken(refreshTokenServiceRequest.getRefreshToken());
			User findUser = userRepository.findByEmail(email);
			logger.debug("findUser : {},", findUser);

			// 3. User -> AuthenticateUser로 변환
			AuthenticateUser authenticateUser = findUser.toAuthenticateUser();
			logger.debug("authenticateUser : {}", authenticateUser);

			// 4. AuthenticateUser -> json 변환
			String authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
			claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);

			// 5. claims 맵 기반으로 Jwt(액세스 토큰, 리프레쉬 토큰) 생성
			Jwt jwt = jwtProvider.createJwt(claims);
			logger.debug("jwt : {}", jwt);

			// 6. refreshToken을 갱신합니다.
			updateRefreshToken(authenticateUser, jwt);

			return jwt;
		} catch (RestApiException e) {
			logger.error("userRestApiException : {}", e.getMessage());
			throw new RestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN);
		} catch (Exception e) {
			logger.error("refreshToken error : {}", e.getMessage());
			throw new RestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN);
		}
	}

	private String findEmailByRefreshToken(String refreshToken) {
		Set<String> keys = redisTemplate.keys("RT:*");
		if (keys == null) {
			throw new RestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN);
		}
		return keys.stream()
			.filter(key -> Objects.equals(redisTemplate.opsForValue().get(key), refreshToken))
			.findAny()
			.map(email -> email.replace("RT:", ""))
			.orElseThrow(() -> new RestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN));
	}

	public AuthenticateUserLoginServiceResponse login(AuthenticateUser authenticateUser) {
		logger.info("로그아웃 서비스 : {}", authenticateUser);
		Map<String, Object> claims = new HashMap<>();
		// 1. AuthenticateUser 객체를 json으로 변환
		String authenticateUserJson = null;
		try {
			authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
		} catch (JsonProcessingException e) {
			logger.error("AuthenticateUser 직렬화 실패 : {}", e.getMessage());
			throw new RestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		// 2. key="authenticateUser" value=인증 객체의 json 데이터를 claims 맵에 저장
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
		// 3. claims을 기반으로 JWT 객체(액세스 토큰, 갱신 토큰) 생성
		Jwt jwt = jwtProvider.createJwt(claims);
		// 4. redis에 RT:hong1234@gmail.com(key) / 23jijiofj2io3hi32hiongiodsninioda(value) 형태로 리프레시 토큰 저장하기
		redisTemplate.opsForValue().set(
			authenticateUser.createRedisKey(),
			jwt.getRefreshToken(),
			jwt.getExpireDateRefreshToken(),
			TimeUnit.MILLISECONDS);
		return new AuthenticateUserLoginServiceResponse(authenticateUser, jwt);
	}

	public void logout(AuthenticateUser authenticateUser, Jwt jwt) {
		logger.info("로그아웃 서비스 요청 : {}, {}", authenticateUser, jwt);
		// Redis에 유저 email로 저장된 RefreshToken이 있는지 확인
		if (redisTemplate.opsForValue().get(authenticateUser.createRedisKey()) != null) {
			// RefreshToken 삭제
			redisTemplate.delete(authenticateUser.createRedisKey());
		}
		// 해당 액세스 토큰 유효시간을 가지고 와서 블랙리스트에 저장하기
		long expiration = jwt.getExpireDateAccessToken();
		redisTemplate.opsForValue().set(jwt.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
	}
}
