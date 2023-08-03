package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.AuthorSearchInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AuthorsSearchResponse {

	private List<AuthorSearchResponse> authors;

	public static AuthorsSearchResponse from(List<AuthorSearchInformation> authorSearchInformations) {
		return new AuthorsSearchResponse(AuthorSearchResponse.from(authorSearchInformations));
	}
}
