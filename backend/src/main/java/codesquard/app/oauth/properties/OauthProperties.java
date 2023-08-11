package codesquard.app.oauth.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import codesquard.app.oauth.OauthProvider;
import lombok.ToString;

@ToString
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {
	private final Map<String, UserProperties> user;
	private final Map<String, ProviderProperties> provider;

	@ConstructorBinding
	public OauthProperties(Map<String, UserProperties> user, Map<String, ProviderProperties> provider) {
		this.user = user;
		this.provider = provider;
	}

	public Map<String, OauthProvider> createOauthProviderMap() {
		Map<String, OauthProvider> oauthProviderMap = new HashMap<>();
		user.forEach((key, value) -> oauthProviderMap.put(key, new OauthProvider(value, provider.get(key))));
		return oauthProviderMap;
	}

	@ToString
	public static class UserProperties {
		private final String clientId;
		private final String clientSecret;
		private final String redirectUri;

		@ConstructorBinding
		public UserProperties(String clientId, String clientSecret, String redirectUri) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
			this.redirectUri = redirectUri;
		}

		public String getClientId() {
			return clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public String getRedirectUri() {
			return redirectUri;
		}
	}

	@ToString
	public static class ProviderProperties {
		private final String tokenUri;
		private final String userInfoUri;

		@ConstructorBinding
		public ProviderProperties(String tokenUri, String userInfoUri) {
			this.tokenUri = tokenUri;
			this.userInfoUri = userInfoUri;
		}

		public String getTokenUri() {
			return tokenUri;
		}

		public String getUserInfoUri() {
			return userInfoUri;
		}
	}
}
