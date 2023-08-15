package com.issuetracker.account.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.issuetracker.account.application.AccountService;
import com.issuetracker.account.ui.dto.AccountResponse;
import com.issuetracker.account.ui.dto.JwtTokenResponse;
import com.issuetracker.account.ui.dto.OauthAccessTokenRequest;
import com.issuetracker.account.ui.dto.OauthAccessTokenResponse;
import com.issuetracker.account.ui.dto.OauthAccountInfoResponse;
import com.issuetracker.account.ui.dto.OauthLoginResponse;

@PropertySource("classpath:oauth.properties")
@RestController
@RequestMapping("/api/account")
public class AccountController {

	private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
	private static final String MEMBER_INFO_URL = "https://api.github.com/user";

	private static final RestTemplate restTemplate = new RestTemplate();

	private final AccountService accountService;

	private final String clientId;
	private final String clientSecret;

	public AccountController(
		AccountService accountService,
		@Value("${oauth.github.client-id}") String clientId,
		@Value("${oauth.github.client-secret}") String clientSecret) {

		this.accountService = accountService;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	@GetMapping("/oauth/callback")
	public ResponseEntity<OauthLoginResponse> getMemberInfo(@RequestParam String code) {
		OauthAccessTokenResponse accessTokenResponse = getAccessToken(code);
		OauthAccountInfoResponse accountInfoResponse = getAccountInfo(accessTokenResponse.getAccessToken());

		AccountResponse accountResponse = AccountResponse.from(
			accountService.findByEmail(accountInfoResponse.getEmail())
		);

		if (accountResponse.verify()) {
			return ResponseEntity.ok(
				new OauthLoginResponse(
					true,
					accountInfoResponse,
					JwtTokenResponse.from(accountService.issueJwtToken(accountResponse.getId()))));
		}

		return ResponseEntity.ok(
			new OauthLoginResponse(false, accountInfoResponse, null)
		);
	}

	private OauthAccessTokenResponse getAccessToken(String code) {
		return restTemplate.postForObject(
			ACCESS_TOKEN_URL,
			new OauthAccessTokenRequest(clientId, clientSecret, code),
			OauthAccessTokenResponse.class
		);
	}

	private OauthAccountInfoResponse getAccountInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		return restTemplate.exchange(
			MEMBER_INFO_URL,
			HttpMethod.GET,
			request,
			OauthAccountInfoResponse.class
		).getBody();
	}
}
