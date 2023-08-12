package codesquard.app.oauth.provider;

import static codesquard.app.oauth.properties.OauthProperties.*;

public class OauthClientRegistration {
	private final UserProperties user;
	private final ProviderProperties provider;

	public OauthClientRegistration(UserProperties user, ProviderProperties provider) {
		this.user = user;
		this.provider = provider;
	}
}
