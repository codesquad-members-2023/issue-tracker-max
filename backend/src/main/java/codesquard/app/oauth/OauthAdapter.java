package codesquard.app.oauth;

import java.util.Map;

import codesquard.app.oauth.properties.OauthProperties;

public class OauthAdapter {
	private OauthAdapter() {
	}

	// OauthProperties를 OauthProvider로 변환해준다.
	public static Map<String, OauthProvider> getOauthProviders(OauthProperties properties) {
		return properties.createOauthProviderMap();
	}
}
