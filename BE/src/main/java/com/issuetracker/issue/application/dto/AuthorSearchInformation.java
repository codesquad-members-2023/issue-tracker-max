package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.Author;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorSearchInformation {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static AuthorSearchInformation from(Author author) {
		return new AuthorSearchInformation(
			author.getId(),
			author.getNickname(),
			author.getProfileImageUrl()
		);
	}

	public static List<AuthorSearchInformation> from(List<Author> authors) {
		return authors.stream()
			.map(AuthorSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
