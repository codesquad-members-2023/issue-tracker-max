package org.presents.issuetracker.jwt.service;

import org.presents.issuetracker.jwt.entity.Jwt;
import org.presents.issuetracker.jwt.repository.JwtRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	private final JwtRepository jwtRepository;

	public void saveRefreshToken(String refreshToken, String loginId) {
		jwtRepository.save(Jwt.builder()
			.refreshToken(refreshToken)
			.loginId(loginId)
			.build());
	}
}
