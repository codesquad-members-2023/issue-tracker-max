package org.presents.issuetracker.issue.dto.response;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearch {
	private Long userId;
	private String loginId;
	private String image;

	public static UserSearch fromEntity(User user) {
		return UserSearch.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.image(user.getImage())
			.build();
	}
}
