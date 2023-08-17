package com.issuetracker.account.application.dto;

import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.account.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpInputData {

	private String email;
	private String password;
	private String nickname;
	private String profileImageUrl;
	private Long oauthId;
	private MultipartFile multipartFile;

	public Account toAccount() {
		return Account.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.profileImageUrl(profileImageUrl)
			.oauthId(oauthId)
			.build();
	}

	public static SignUpInputData from(Account account) {
		return new SignUpInputData(
			account.getEmail(),
			account.getPassword(),
			account.getNickname(),
			account.getProfileImageUrl(),
			account.getOauthId(),
			null
		);
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
}
