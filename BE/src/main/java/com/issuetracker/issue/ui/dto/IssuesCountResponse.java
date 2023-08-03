package com.issuetracker.issue.ui.dto;

import com.issuetracker.issue.application.dto.IssuesCountInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssuesCountResponse {

	private int issueOpenCount;
	private int issueCloseCount;
	private int labelCount;
	private int milestoneCount;

	public static IssuesCountResponse from(IssuesCountInformation issuesCountInformation) {
		return new IssuesCountResponse(
			issuesCountInformation.getIssueOpenCount(),
			issuesCountInformation.getIssueCloseCount(),
			issuesCountInformation.getLabelCount(),
			issuesCountInformation.getMilestoneCount()
		);
	}
}
