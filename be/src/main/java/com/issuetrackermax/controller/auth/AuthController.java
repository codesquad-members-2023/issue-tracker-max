package com.issuetrackermax.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.auth.dto.request.JwtRefreshTokenRequest;
import com.issuetrackermax.controller.auth.dto.request.LoginRequest;
import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.service.Jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {
	private final JwtService jwtService;

	@PostMapping("/login")
	public ApiResponse<JwtResponse> login(@RequestBody LoginRequest request) throws Exception {
		return ApiResponse.success(
			JwtResponse.from(jwtService.login(request.getEmail(), request.getPassword()))
		);
	}

	@PostMapping("/reissue-access-token")
	public ApiResponse<JwtResponse> reissueAccessToken(@RequestBody JwtRefreshTokenRequest request) {
		return ApiResponse.success(
			JwtResponse.from(jwtService.reissueAccessToken(request.getRefreshToken()))
		);
	}
}
