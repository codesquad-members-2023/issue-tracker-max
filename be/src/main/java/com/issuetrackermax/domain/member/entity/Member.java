package com.issuetrackermax.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Member {
	public static final String DEFAULT_IMAGE = "https://kiosk-coffee.s3.ap-northeast-2.amazonaws.com/cafe_latte1_110424_870.jpeg";
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
		setImage_url(imageUrl);
		this.loginType = loginType;
	}

	private void setImage_url(String image_url) {
		if (image_url == null) {
			this.imageUrl = DEFAULT_IMAGE;
		} else {
			this.imageUrl = image_url;
		}
	}
}
