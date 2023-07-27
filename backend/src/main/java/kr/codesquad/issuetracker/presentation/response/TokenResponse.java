package kr.codesquad.issuetracker.presentation.response;

import lombok.Getter;

@Getter
public class TokenResponse {

	private final String tokenType;
	private final String accessToken;

	public TokenResponse(String accessToken) {
		this.tokenType = "Bearer";
		this.accessToken = accessToken;
	}
}
