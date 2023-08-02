package com.issuetrackermax.service.oauth;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.oauth.entity.OauthProperties;

public class OauthProviderTest extends IntegrationTestSupport {
	@Autowired
	OauthProvider oauthProvider;

	@Test
	public void testCreateOauthProvider() {
		// given
		String clientId = "testClientId";
		String clientSecret = "testClientSecret";
		String redirectUrl = "http://example.com/redirect";
		String tokenUrl = "http://example.com/token";
		String userInfoUrl = "http://example.com/userinfo";

		OauthProperties.User user = new OauthProperties.User();
		user.setClientId(clientId);
		user.setClientSecret(clientSecret);
		user.setRedirectUri(redirectUrl);
		OauthProperties.Provider provider = new OauthProperties.Provider();
		provider.setTokenUri(tokenUrl);
		provider.setUserInfoUri(userInfoUrl);

		// when
		oauthProvider = OauthProvider.createOauthProvider(user, provider);

		// then
		assertAll(
			() -> assertThat(clientId).isEqualTo(oauthProvider.getClientId()),
			() -> assertThat(clientSecret).isEqualTo(oauthProvider.getClientSecret()),
			() -> assertThat(redirectUrl).isEqualTo(oauthProvider.getRedirectUrl()),
			() -> assertThat(tokenUrl).isEqualTo(oauthProvider.getTokenUrl()),
			() -> assertThat(userInfoUrl).isEqualTo(oauthProvider.getUserInfoUrl()));
	}

}
