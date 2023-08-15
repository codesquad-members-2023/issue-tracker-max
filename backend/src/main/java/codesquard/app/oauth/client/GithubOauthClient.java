package codesquard.app.oauth.client;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import codesquard.app.api.errors.errorcode.OauthErrorCode;
import codesquard.app.api.errors.exception.RestApiException;
import codesquard.app.oauth.OauthAttributes;
import codesquard.app.oauth.OauthProvider;
import codesquard.app.oauth.profile.UserProfile;
import codesquard.app.oauth.service.response.OauthAccessTokenResponse;

@Component
public class GithubOauthClient {

	private static final Logger logger = LoggerFactory.getLogger(GithubOauthClient.class);

	private final String clientId;
	private final String clientSecret;
	private final String tokenUri;
	private final String userInfoUri;

	public GithubOauthClient(
		@Value("${oauth2.user.github.client-id}") String clientId,
		@Value("${oauth2.user.github.client-secret}") String clientSecret,
		@Value("${oauth2.provider.github.token-uri}") String tokenUri,
		@Value("${oauth2.provider.github.user-info-uri}") String userInfoUri) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.tokenUri = tokenUri;
		this.userInfoUri = userInfoUri;
	}

	public OauthAccessTokenResponse getAccessToken(OauthProvider provider, String code) {
		return WebClient.create()
			.post()
			.uri(provider.getTokenUri())
			.headers(header -> {
				header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(provider.getRedirectUri(), code))
			.retrieve()// ResponseEntity를 받아 디코딩
			.bodyToMono(OauthAccessTokenResponse.class) // 주어진 타입으로 디코딩
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(String redirectUri, String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", redirectUri);
		return formData;
	}

	public UserProfile getUserProfile(String provider, OauthAccessTokenResponse oauthAccessTokenResponse,
		OauthProvider oauthProvider) {
		Map<String, Object> userAttributes = getUserAttributes(oauthProvider, oauthAccessTokenResponse);
		// 유저 정보(map)를 통해 UserProfile 만들기
		return OauthAttributes.extract(provider, userAttributes);
	}

	// Oauth 서버에서 유저 정보 map으로 가져오기
	private Map<String, Object> getUserAttributes(OauthProvider oauthProvider,
		OauthAccessTokenResponse oauthAccessTokenResponse) {
		try {
			return requestUserAttributes(oauthProvider, oauthAccessTokenResponse);
		} catch (WebClientResponseException.Unauthorized e) {
			logger.error("소셜 사용자의 정보 요청 인가 오류 : {}", e.getMessage());
			throw new RestApiException(OauthErrorCode.BAD_AUTHORIZATION_CODE);
		}
	}

	private Map<String, Object> requestUserAttributes(OauthProvider oauthProvider,
		OauthAccessTokenResponse oauthAccessTokenResponse) {
		return WebClient.create()
			.get()
			.uri(oauthProvider.getUserInfoUri())
			.headers(header -> header.setBearerAuth(oauthAccessTokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

}
