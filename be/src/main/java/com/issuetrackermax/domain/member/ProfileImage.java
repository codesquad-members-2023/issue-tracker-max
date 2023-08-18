package com.issuetrackermax.domain.member;

import lombok.Getter;

@Getter
public enum ProfileImage {
	DEFAULT_1("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/red.jpg"),
	DEFAULT_2("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/yellow.jpg"),
	DEFAULT_3("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/green.jpg"),
	DEFAULT_4("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/blue.jpg"),
	DEFAULT_5("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/purple.jpg"),
	DEFAULT_6("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/loopi.jpg"),
	DEFAULT_7("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/fatkitty.jpg"),
	DEFAULT_8("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/june.jpg"),
	DEFAULT_9("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/movie.png"),
	DEFAULT_10("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/earth1.jpg"),
	DEFAULT_11("https://issue-tracker-bucket-04.s3.ap-northeast-2.amazonaws.com/profile/toko.jpg");

	String imageUrl;

	ProfileImage(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
