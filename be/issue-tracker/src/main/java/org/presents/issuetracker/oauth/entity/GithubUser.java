package org.presents.issuetracker.oauth.entity;

import org.presents.issuetracker.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GithubUser {
	private final String loginId;
	private final String image;

	@Builder
	public GithubUser(String loginId, String image) {
		this.loginId = loginId;
		this.image = image;
	}

	public static GithubUser of(String loginId, String image) {
		return GithubUser.builder().loginId(loginId + "Github").image(image).build();
	}

	public User toEntity() {
		return User.builder()
			.loginId(loginId)
			.password("")
			.image(image)
			.build();
	}
}
