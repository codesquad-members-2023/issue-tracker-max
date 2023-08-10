package codesquard.app.authenticate_user.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.api.errors.errorcode.JwtTokenErrorCode;
import codesquard.app.api.errors.errorcode.LoginErrorCode;
import codesquard.app.api.errors.exception.jwt.JwtRestApiException;
import codesquard.app.api.errors.exception.user.LoginRestApiException;
import codesquard.app.api.errors.exception.user.UserRestApiException;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.repository.AuthenticateUserRepository;
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
	private final AuthenticateUserRepository authenticateUserRepository;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	public AuthenticateUserService(UserRepository userRepository, AuthenticateUserRepository authenticateUserRepository,
		JwtProvider jwtProvider, ObjectMapper objectMapper) {
		this.userRepository = userRepository;
		this.authenticateUserRepository = authenticateUserRepository;
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
	}

	public void updateRefreshToken(AuthenticateUser authenticateUser, Jwt jwt) {
		User findUser = userRepository.findByEmail(authenticateUser.toEntity());
		if (authenticateUserRepository.isExistRefreshToken(findUser)) {
			authenticateUserRepository.updateRefreshToken(findUser, jwt);
			return;
		}
		saveRefreshToken(findUser, jwt);
	}

	public void saveRefreshToken(User user, Jwt jwt) {
		authenticateUserRepository.saveRefreshToken(user, jwt);
	}

	public Jwt refreshToken(RefreshTokenServiceRequest refreshTokenServiceRequest) {
		try {
			// 1. 토큰이 유효한지 확인합니다. 유효하지 않은 경우 예외가 발생합니다.
			Claims findClaims = jwtProvider.getClaims(refreshTokenServiceRequest.getRefreshToken());
			logger.info("claims : {}", findClaims);
			// 2. refreshToken 값을 가지는 유저를 조회합니다.
			User findUser = userRepository.findByRefreshToken(refreshTokenServiceRequest.getRefreshToken());
			HashMap<String, Object> claims = new HashMap<>();
			AuthenticateUser authenticateUser = findUser.toAuthenticateUser();
			String authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
			claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
			// 3. claims을 입력으로 jwt 토큰을 생성합니다.
			Jwt jwt = jwtProvider.createJwt(claims);
			logger.info("jwt : {}", jwt);
			// 4. refreshToken을 갱신합니다.
			updateRefreshToken(authenticateUser, jwt);
			return jwt;
		} catch (UserRestApiException e) {
			logger.error("userRestApiException : {}", e.getMessage());
			throw new JwtRestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN);
		} catch (Exception e) {
			logger.error("refreshToken error : {}", e.getMessage());
			throw new JwtRestApiException(JwtTokenErrorCode.NOT_MATCH_REFRESHTOKEN);
		}
	}

	public void updateSocialUserRefreshToken(AuthenticateUser authenticateUser, Jwt jwt) {
		User findUser = userRepository.findByEmail(authenticateUser.toEntity());
		if (authenticateUserRepository.isExistRefreshToken(findUser)) {
			authenticateUserRepository.updateRefreshToken(findUser, jwt);
			return;
		}
		saveRefreshToken(findUser, jwt);
	}

	public AuthenticateUserLoginServiceResponse login(AuthenticateUser authenticateUser) {
		Map<String, Object> claims = new HashMap<>();
		// 1. AuthenticateUser 객체를 json으로 변환
		String authenticateUserJson = null;
		try {
			authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
		} catch (JsonProcessingException e) {
			throw new LoginRestApiException(LoginErrorCode.NOT_MATCH_LOGIN);
		}
		// 2. key="authenticateUser" value=인증 객체의 json 데이터를 claims 맵에 저장
		claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
		// 3. claims을 기반으로 JWT 객체(액세스 토큰, 갱신 토큰) 생성
		Jwt jwt = jwtProvider.createJwt(claims);
		// 4. 갱신 토큰 최신 업데이트
		updateRefreshToken(authenticateUser, jwt);
		return new AuthenticateUserLoginServiceResponse(authenticateUser, jwt);
	}
}
