package codesquard.app.oauth;

import java.util.Arrays;
import java.util.Map;

import codesquard.app.oauth.profile.UserProfile;

public enum OauthAttributes {
	GITHUB("github") {
		@Override
		public UserProfile of(Map<String, Object> attributes) {
			String name = (String)attributes.get("name");
			String email = (String)attributes.get("email");
			String avatarUrl = (String)attributes.get("avatar_url");
			return new UserProfile(name, email, avatarUrl);
		}
	};

	private final String providerName;

	OauthAttributes(String providerName) {
		this.providerName = providerName;
	}

	public static UserProfile extract(String providerName, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> providerName.equals(provider.providerName))
			.findAny()
			.orElseThrow(IllegalArgumentException::new)
			.of(attributes);
	}

	public abstract UserProfile of(Map<String, Object> attributes);

}
