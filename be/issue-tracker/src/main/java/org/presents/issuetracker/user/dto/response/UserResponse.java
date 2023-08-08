package org.presents.issuetracker.user.dto.response;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
	private Long userId;
	private String loginId;
	private String image;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.image(user.getImage())
			.build();
	}
}
