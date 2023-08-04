package com.issuetrackermax.controller.auth.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtRefreshTokenRequest {
	@NotBlank
	private String refreshToken;

	@Builder
	public JwtRefreshTokenRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}

