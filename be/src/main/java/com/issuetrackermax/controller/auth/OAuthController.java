package com.issuetrackermax.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.auth.dto.response.JwtResponse;
import com.issuetrackermax.service.oauth.OauthService;

@RestController
public class OAuthController {

	private final OauthService oauthService;

	public OAuthController(OauthService oauthService) {
		this.oauthService = oauthService;
	}

	@GetMapping("/oauth/{provider}")
	public ResponseEntity<JwtResponse> login(@PathVariable String provider, @RequestParam String code) {
		JwtResponse jwtResponse = oauthService.login(provider, code);
		return ResponseEntity.ok().body(jwtResponse);
	}
}
