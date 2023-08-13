package com.issuetrackermax.controller.auth.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutRequest {
	@NotBlank
	private String refreshToken;

}
