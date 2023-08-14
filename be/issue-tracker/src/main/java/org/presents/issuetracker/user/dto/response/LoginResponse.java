package org.presents.issuetracker.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
	private String loginId;
	private String image;
	private String accessToken;
	private String refreshToken;

	@Builder
	private LoginResponse(String loginId, String image, String accessToken, String refreshToken) {
		this.loginId = loginId;
		this.image = image;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
