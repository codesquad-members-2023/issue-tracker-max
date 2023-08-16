package com.issuetrackermax.domain.member;

import lombok.Getter;

@Getter
public enum ProfileImage {
	DEFAULT_1("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/red.jpg"),
	DEFAULT_2("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/yellow.jpg"),
	DEFAULT_3("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/green.jpg"),
	DEFAULT_4("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/blue.jpg"),
	DEFAULT_5("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/purple.jpg");

	String imageUrl;

	ProfileImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
