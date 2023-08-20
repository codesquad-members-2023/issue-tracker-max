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
		// 일반 회원가입 시, (GithubLoginUser) 키워드로는 유저를 생성 및 로그인 할 수 없습니다.
		// loginId 제한 길이 max=16 인데 (GithubLoginUser)는 17글자.
		return GithubUser.builder().loginId(loginId + "(GithubLoginUser)").image(image).build();
	}

	public User toEntity() {
		return User.builder()
			.loginId(loginId)
			.password("") // 빈 문자열로는 일반 회원 로그인을 할 수 없습니다.
			.image(image)
			.build();
	}
}
