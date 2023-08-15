package org.presents.issuetracker.user.dto.response;

import org.presents.issuetracker.jwt.dto.Jwt;
import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
	private final String loginId;
	private final String image;
	private final String accessToken;
	private final String refreshToken;

	@Builder
	private LoginResponse(String loginId, String image, String accessToken, String refreshToken) {
		this.loginId = loginId;
		this.image = image;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static LoginResponse of(User user, Jwt jwt) {
		return LoginResponse.builder()
			.loginId(user.getLoginId())
			.image(user.getImage())
			.accessToken(jwt.getAccessToken())
			.refreshToken(jwt.getRefreshToken())
			.build();
	}
}
