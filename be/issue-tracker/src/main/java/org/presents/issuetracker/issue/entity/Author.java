package org.presents.issuetracker.issue.entity;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Author {
	private Long userId;
	private String loginId;
	private String image;

	public static Author fromEntity(User user) {
		return Author.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.image(user.getImage())
			.build();
	}
}
