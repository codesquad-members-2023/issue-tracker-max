package org.presents.issuetracker.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
	private Long userId;
	private String loginId;
	private String password;
	private String image;

	@Builder
	private User(Long userId, String loginId, String password, String image) {
		this.userId = userId;
		this.loginId = loginId;
		this.password = password;
		this.image = image;
	}
}

