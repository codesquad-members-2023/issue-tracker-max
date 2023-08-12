package codesquard.app.oauth;

import static codesquard.app.oauth.properties.OauthProperties.*;

import lombok.ToString;

@ToString
public class OauthProvider {
	private final UserProperties userProperties;
	private final ProviderProperties providerProperties;

	public OauthProvider(UserProperties userProperties, ProviderProperties providerProperties) {
		this.userProperties = userProperties;
		this.providerProperties = providerProperties;
	}

	public String getClientId() {
		return userProperties.getClientId();
	}

	public String getClientSecret() {
		return userProperties.getClientSecret();
	}

	public String getRedirectUri() {
		return userProperties.getRedirectUri();
	}

	public String getTokenUri() {
		return providerProperties.getTokenUri();
	}

	public String getUserInfoUri() {
		return providerProperties.getUserInfoUri();
	}

}
