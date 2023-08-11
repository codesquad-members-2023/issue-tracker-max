package org.presents.issuetracker.user.dto.response;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
	private static final String NO_USER_LOGIN_ID = "none";
	private static final Long NO_USER_ID = 0L;
	private static final String NO_USER_IMAGE = "";

	private Long userId;
	private String loginId;
	private String image;

	private static final UserResponse USER_NOT_ASSIGNED_RESPONSE = UserResponse.builder()
		.userId(NO_USER_ID)
		.loginId(NO_USER_LOGIN_ID)
		.image(NO_USER_IMAGE)
		.build();

	public static UserResponse getUserNotAssignedResponse() {
		return USER_NOT_ASSIGNED_RESPONSE;
	}

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.image(user.getImage())
			.build();
	}
}
