package codesquard.app.oauth.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
public class OauthAccessTokenResponse {
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

	public OauthAccessTokenResponse() {
	}

	public OauthAccessTokenResponse(String accessToken, String scope, String tokenType) {
		this.accessToken = accessToken;
		this.scope = scope;
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getScope() {
		return scope;
	}

	public String getTokenType() {
		return tokenType;
	}
}
