package org.presents.issuetracker.oauth.controller;

import org.presents.issuetracker.oauth.entity.GithubUser;
import org.presents.issuetracker.oauth.service.OauthService;
import org.presents.issuetracker.user.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OauthController {

	private final OauthService oauthService;

	// https://github.com/login/oauth/authorize?client_id=9531d38d8aa9bd9a4602
	@Value("${security.oauth.github.client-id}")
	private String clientId;

	@GetMapping("/login/oauth")
	public ResponseEntity<Void> oauthLogin() {
		String githubUrl = "https://github.com/login/oauth/authorize?client_id=" + clientId;
		return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, githubUrl).build();
	}

	@GetMapping("/login/oauth/callback")
	public ResponseEntity<LoginResponse> getUserInfo(@RequestParam String code) {
		String accessToken = oauthService.getAccessToken(code);
		GithubUser githubUser = oauthService.getGithubUser(accessToken);
		// 현재 로직은 강제 회원가입 진행하는 상태
		LoginResponse loginResponse = oauthService.login(githubUser);
		return ResponseEntity.ok().body(loginResponse);
	}
}
