package com.issuetrackermax.controller.auth;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.auth.dto.request.JwtRefreshTokenRequest;
import com.issuetrackermax.controller.auth.dto.request.LoginRequest;
import com.issuetrackermax.controller.auth.dto.request.LogoutRequest;
import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.controller.auth.dto.response.LoginResponse;
import com.issuetrackermax.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
	private final JwtService jwtService;

	@PostMapping("/signin")
	public ApiResponse<LoginResponse> login(
		@RequestBody
		@Valid LoginRequest request) {
		return ApiResponse.success(
			jwtService.login(request.getLoginId(), request.getPassword())
		);
	}

	@PostMapping("/logout")
	public ApiResponse<Void> logout(
		@RequestBody
		@Valid LogoutRequest request) {
		jwtService.logout(request.getRefreshToken());
		return ApiResponse.success();
	}

	@PostMapping("/reissue-access-token")
	public ApiResponse<JwtResponse> reissueAccessToken(
		@RequestBody
		@Valid JwtRefreshTokenRequest request) {
		return ApiResponse.success(
			JwtResponse.from(jwtService.reissueAccessToken(request.getRefreshToken()))
		);
	}

}
