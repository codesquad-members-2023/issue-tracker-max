package org.presents.issuetracker.issue.dto.response;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {
	private Long userId;
	private String loginId;
	private String image;

	public static UserResponseDto fromEntity(User user) {
		return UserResponseDto.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.image(user.getImage())
			.build();
	}
}
