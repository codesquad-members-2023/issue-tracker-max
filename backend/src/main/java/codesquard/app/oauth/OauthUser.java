package codesquard.app.oauth;

public class OauthUser {
	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;

	public OauthUser(String clientId, String clientSecret, String redirectUri) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
	}
}
