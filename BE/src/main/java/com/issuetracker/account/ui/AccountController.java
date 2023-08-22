package com.issuetracker.account.ui;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.account.application.AccountService;
import com.issuetracker.account.ui.dto.JwtTokenResponse;
import com.issuetracker.account.ui.dto.LoginRequest;
import com.issuetracker.account.ui.dto.OauthAccessTokenRequest;
import com.issuetracker.account.ui.dto.OauthAccessTokenResponse;
import com.issuetracker.account.ui.dto.OauthAccountInfoResponse;
import com.issuetracker.account.ui.dto.OauthEmailResponse;
import com.issuetracker.account.ui.dto.RefreshTokenRequest;
import com.issuetracker.account.ui.dto.SignupRequest;
import com.issuetracker.common.util.MemberIdExtractor;

@PropertySource("classpath:oauth.properties")
@RestController
@RequestMapping("/api/account")
public class AccountController {

	private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
	private static final String MEMBER_INFO_URL = "https://api.github.com/user";
	private static final String GET_EMAIL_URL = "https://api.github.com/user/emails";

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

	@PostMapping("/login")
	public ResponseEntity<JwtTokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
		return ResponseEntity.ok(
			JwtTokenResponse.from(
				accountService.issueJwtToken(loginRequest.toLoginInputData())
			)
		);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		accountService.logout(MemberIdExtractor.extractMemberId(request));

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> signUp(@RequestPart SignupRequest request,
		@RequestPart(required = false) MultipartFile file) {

		accountService.signUp(request.toSignUpInputData(file));
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/reissue-access-token")
	public ResponseEntity<JwtTokenResponse> reissueAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return ResponseEntity.ok(
			JwtTokenResponse.from(accountService.reissueJwtToken(refreshTokenRequest.getRefreshToken())));
	}

	@GetMapping("/oauth/callback")
	public ResponseEntity<JwtTokenResponse> getMemberInfo(@RequestParam String code) {
		OauthAccessTokenResponse accessTokenResponse = getAccessToken(code);
		OauthAccountInfoResponse accountInfoResponse = getAccountInfo(accessTokenResponse.getAccessToken());
		
		return ResponseEntity.ok(
			JwtTokenResponse.from(
				accountService.proceedToOauthLogin(
					accountInfoResponse.toOauthAccountInputData())));
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

		OauthAccountInfoResponse oauthAccountInfoResponse
			= restTemplate.exchange(
			MEMBER_INFO_URL,
			HttpMethod.GET,
			request,
			OauthAccountInfoResponse.class
		).getBody();

		assert oauthAccountInfoResponse != null;
		if (!oauthAccountInfoResponse.verifyEmail()) {

			OauthEmailResponse[] emailResponse = restTemplate.exchange(
				GET_EMAIL_URL,
				HttpMethod.GET,
				request,
				OauthEmailResponse[].class
			).getBody();

			assert emailResponse != null;
			oauthAccountInfoResponse.setEmail(emailResponse[0].getEmail());
		}

		return oauthAccountInfoResponse;
	}
}
