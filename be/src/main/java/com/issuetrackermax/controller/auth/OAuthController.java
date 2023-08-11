package com.issuetrackermax.controller.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.service.oauth.OauthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OAuthController {
	private final OauthService oauthService;

	@GetMapping("/oauth/{provider}")
	public ApiResponse<JwtResponse> login(@PathVariable String provider, @RequestParam String code) {
		JwtResponse jwtResponse = oauthService.login(provider, code);
		return ApiResponse.success(jwtResponse);
	}
}
