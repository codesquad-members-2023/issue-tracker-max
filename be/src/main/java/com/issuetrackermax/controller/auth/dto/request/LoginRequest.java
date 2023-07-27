package com.issuetrackermax.controller.auth.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {
	private final String email;
	private final String password;
}
