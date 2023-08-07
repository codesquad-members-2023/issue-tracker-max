package codesquard.app.user.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquard.app.authenticate_user.entity.AuthenticateUser;
import codesquard.app.authenticate_user.repository.AuthenticateUserRepository;
import codesquard.app.authenticate_user.service.RefreshTokenServiceRequest;
import codesquard.app.jwt.Jwt;
import codesquard.app.jwt.JwtProvider;
import codesquard.app.jwt.filter.VerifyUserFilter;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

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

	@Transactional
	public void updateRefreshToken(AuthenticateUser authenticateUser, Jwt jwt) {
		User findUser = userRepository.findByLoginId(authenticateUser.toEntity());
		if (authenticateUserRepository.isExistRefreshToken(findUser)) {
			authenticateUserRepository.updateRefreshToken(findUser, jwt);
			return;
		}
		saveRefreshToken(findUser, jwt);
	}

	@Transactional
	public void saveRefreshToken(User user, Jwt jwt) {
		authenticateUserRepository.saveRefreshToken(user, jwt);
	}

	@Transactional
	public Jwt refreshToken(RefreshTokenServiceRequest refreshTokenServiceRequest) {
		try {
			// 1. 토큰이 유효한지 확인합니다. 유효하지 않은 경우 예외가 발생합니다.
			jwtProvider.getClaims(refreshTokenServiceRequest.getRefreshToken());
			// 2. refreshToken 값을 가지는 유저를 조회합니다.
			User findUser = userRepository.findByRefreshToken(refreshTokenServiceRequest.getRefreshToken());
			HashMap<String, Object> claims = new HashMap<>();
			AuthenticateUser authenticateUser = findUser.toAuthenticateUser();
			String authenticateUserJson = objectMapper.writeValueAsString(authenticateUser);
			claims.put(VerifyUserFilter.AUTHENTICATE_USER, authenticateUserJson);
			// 3. claims을 입력으로 jwt 토큰을 생성합니다.
			Jwt jwt = jwtProvider.createJwt(claims);
			// 4. refreshToken을 갱신합니다.
			updateRefreshToken(authenticateUser, jwt);
			return jwt;
		} catch (Exception e) {
			logger.error("refreshToken error : {}", e.getMessage());
			return null;
		}
	}
}
