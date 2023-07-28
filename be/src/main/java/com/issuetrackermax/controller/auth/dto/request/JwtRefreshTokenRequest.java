package com.issuetrackermax.controller.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtRefreshTokenRequest {
	private String refreshToken;
}

