package codesquard.app.oauth.client;

import codesquard.app.oauth.OauthProvider;
import codesquard.app.oauth.profile.UserProfile;
import codesquard.app.oauth.service.response.OauthAccessTokenResponse;

public interface OauthClient {
	OauthAccessTokenResponse requestAccessTokenToOauthServer(OauthProvider provider, String code);

	UserProfile requestUserProfile(String provider, OauthAccessTokenResponse oauthAccessTokenResponse,
		OauthProvider oauthProvider);
}
