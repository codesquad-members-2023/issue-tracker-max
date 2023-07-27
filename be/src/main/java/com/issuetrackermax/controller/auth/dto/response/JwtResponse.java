package com.issuetrackermax.controller.auth.dto.response;

import com.issuetrackermax.domain.jwt.entity.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

	private final String accessToken;
	private final String refreshToken;

	public static JwtResponse from(Jwt jwt) {
		return new JwtResponse(jwt.getAccessToken(), jwt.getRefreshToken());
	}
}