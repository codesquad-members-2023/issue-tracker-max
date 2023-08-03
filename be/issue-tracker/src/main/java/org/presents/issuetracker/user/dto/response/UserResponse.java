package org.presents.issuetracker.user.dto.response;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
	private Long userId;
	private String loginId;
	private String image;

	@Builder
	private UserResponse(Long userId, String loginId, String image) {
		this.userId = userId;
		this.loginId = loginId;
		this.image = image;
	}

	public static UserResponse fromEntity(User user) {
		return UserResponse.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.image(user.getImage())
			.build();
	}
}
