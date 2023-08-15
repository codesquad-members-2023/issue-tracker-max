package org.presents.issuetracker.jwt.service;

import java.util.Map;

import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.statuscode.JwtErrorCode;
import org.presents.issuetracker.jwt.JwtProvider;
import org.presents.issuetracker.jwt.dto.Jwt;
import org.presents.issuetracker.jwt.entity.Token;
import org.presents.issuetracker.jwt.repository.JwtRepository;
import org.presents.issuetracker.user.entity.User;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	private final UserRepository userRepository;
	private final JwtRepository jwtRepository;
	private final JwtProvider jwtProvider;

	public void saveRefreshToken(String refreshToken, String loginId) {
		jwtRepository.save(Token.builder()
			.refreshToken(refreshToken)
			.loginId(loginId)
			.build());
	}

	public Jwt reissueAccessToken(String refreshToken) {
		String loginId = jwtRepository.findByRefreshToken(refreshToken);
		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> {
				throw new CustomException(JwtErrorCode.NOT_VALID_LOGIN_INFO);
			});

		return jwtProvider.reissueToken(Map.of("userId", user.getUserId()), refreshToken);
	}
}
