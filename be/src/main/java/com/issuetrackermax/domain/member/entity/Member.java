package com.issuetrackermax.domain.member.entity;

import java.util.Random;

import com.issuetrackermax.domain.member.ProfileImage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Member {
	private Long id;
	private String loginId;
	private String password;
	private String nickName;
	private String imageUrl;
	private LoginType loginType;

	@Builder
	public Member(Long id, String loginId, String password, String nickName, String imageUrl,
		LoginType loginType) {
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
		this.imageUrl = imageUrl;
		this.loginType = loginType;
	}

	public Member(String loginId, String password, String nickName, LoginType loginType) {
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
		imageUrl = setImageUrl();
		this.loginType = loginType;
	}

	private String setImageUrl() {
		int randomNum = new Random().nextInt(11) + 1;
		return ProfileImage.valueOf("DEFAULT_" + randomNum).getImageUrl();
	}
}
