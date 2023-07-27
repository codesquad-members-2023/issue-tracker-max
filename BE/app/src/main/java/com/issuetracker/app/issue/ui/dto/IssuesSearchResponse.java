package com.issuetracker.app.issue.ui.dto;

import java.util.List;

import com.issuetracker.app.issue.application.dto.IssueSearchInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssuesSearchResponse {

	private List<IssueSearchResponse> issues;

	public static IssuesSearchResponse from(List<IssueSearchInformation> issueSearchInformation) {
		return new IssuesSearchResponse(IssueSearchResponse.from(issueSearchInformation));
	}
}
