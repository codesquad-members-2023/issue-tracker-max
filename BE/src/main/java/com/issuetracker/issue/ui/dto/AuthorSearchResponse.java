package com.issuetracker.issue.ui.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.AuthorSearchInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AuthorSearchResponse {
	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static AuthorSearchResponse from(AuthorSearchInformation authorSearchInformation) {
		return new AuthorSearchResponse(
			authorSearchInformation.getId(),
			authorSearchInformation.getNickname(),
			authorSearchInformation.getProfileImageUrl()
		);
	}

	public static List<AuthorSearchResponse> from(List<AuthorSearchInformation> authorSearchInformations) {
		return authorSearchInformations.stream()
			.map(AuthorSearchResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
