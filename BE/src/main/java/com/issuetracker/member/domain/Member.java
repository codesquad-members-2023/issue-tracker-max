package com.issuetracker.member.domain;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String profileImageUrl;

	@Builder
	private Member(Long id, String email, String password, String nickname, String profileImageUrl) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	public boolean equalsId(Long id) {
		return this.id.equals(id);
	}

	public void update(String nickname, String password, String profileImageUrl) {
		if (Objects.nonNull(nickname)) {
			this.nickname = nickname;
		}

		if (Objects.nonNull(password)) {
			this.password = password;
		}

		if (Objects.nonNull(profileImageUrl)) {
			this.profileImageUrl = profileImageUrl;
		}
	}
}
