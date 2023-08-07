package codesquad.issueTracker.oauth.util;

import codesquad.issueTracker.oauth.domain.OAuthProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthProvider {
	private final String clientId;
	private final String clientSecret;
	private final String redirectUrl;
	private final String tokenUrl;
	private final String userInfoUrl;

	public OauthProvider(OAuthProperties.Client client, OAuthProperties.Provider provider) {
		this(client.getClientId(), client.getClientSecret(), client.getRedirectUrl(), provider.getTokenUrl(),
			provider.getUserInfoUrl());
	}

	@Builder
	public OauthProvider(String clientId, String clientSecret, String redirectUrl, String tokenUrl,
		String userInfoUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;
		this.tokenUrl = tokenUrl;
		this.userInfoUrl = userInfoUrl;
	}
}
