package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.application.dto.IssuesCountInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssuesSearchResponse {

	private IssuesCountResponse metadata;
	private List<IssueSearchResponse> issues;

	public static IssuesSearchResponse of(IssuesCountInformation issuesCount, List<IssueSearchInformation> issueSearchInformation) {
		return new IssuesSearchResponse(
			IssuesCountResponse.from(issuesCount),
			IssueSearchResponse.from(issueSearchInformation)
		);
	}
}
