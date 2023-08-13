package codesquad.issueTracker.oauth.util;

import java.util.HashMap;
import java.util.Map;

import codesquad.issueTracker.oauth.domain.OAuthProperties;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OauthAdapter {

	public static Map<String, OauthProvider> getOauthProviders(OAuthProperties properties) {
		Map<String, OauthProvider> oauthProvider = new HashMap<>();

		properties.getClient().forEach(
			(key, value) -> oauthProvider.put(
				key, new OauthProvider(value, properties.getProvider().get(key))));
		return oauthProvider;
	}
}
