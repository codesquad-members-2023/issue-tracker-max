package org.presents.issuetracker.oauth.service;

import java.util.Map;

import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.statuscode.OauthErrorCode;
import org.presents.issuetracker.jwt.JwtProvider;
import org.presents.issuetracker.jwt.dto.Jwt;
import org.presents.issuetracker.jwt.service.JwtService;
import org.presents.issuetracker.oauth.entity.GithubUser;
import org.presents.issuetracker.user.dto.response.LoginResponse;
import org.presents.issuetracker.user.entity.User;
import org.presents.issuetracker.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OauthService {
	private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
	private static final String MEMBER_INFO_URL = "https://api.github.com/user";
	private final RestTemplate restTemplate = new RestTemplate();
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final JwtProvider jwtProvider;

	@Value("${security.oauth.github.client-id}")
	private String clientId;

	@Value("${security.oauth.github.client-secret}")
	private String clientSecret;

	public String getAccessToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);

		UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(ACCESS_TOKEN_URL)
			.queryParam("client_id", clientId)
			.queryParam("client_secret", clientSecret)
			.queryParam("code", code)
			.build(true);

		try {
			ResponseEntity<Object> response = restTemplate.exchange(uriBuilder.toString(), HttpMethod.POST, entity,
				Object.class);
			Map<String, Object> body = (Map<String, Object>)response.getBody();
			String accessToken = body.get("access_token").toString();

			return accessToken;
		} catch (Exception e) {
			throw new CustomException(OauthErrorCode.GITHUB_ACCESS_DENIED);
		}
	}

	public GithubUser getGithubUser(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<Void> request = new HttpEntity<>(headers);

		Map<String, Object> response = (Map<String, Object>)restTemplate.exchange(
			MEMBER_INFO_URL,
			HttpMethod.GET,
			request,
			Object.class
		).getBody();

		return GithubUser.of(response.get("login").toString(), response.get("avatar_url").toString());
	}

	public User createUser(GithubUser githubUser) {
		User user = githubUser.toEntity();
		Long userId = userRepository.save(user);
		return User.of(user, userId);
	}

	public LoginResponse login(GithubUser githubUser) {
		User user = userRepository.findByLoginId(githubUser.getLoginId())
			.orElseGet(() -> createUser(githubUser));

		Jwt jwt = jwtProvider.generateToken(Map.of("userId", user.getUserId()));
		jwtService.saveRefreshToken(jwt.getRefreshToken(), user.getLoginId());
		return LoginResponse.of(user, jwt);
	}
}
