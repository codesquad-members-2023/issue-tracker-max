package com.issuetracker.issue.application.dto;

import com.issuetracker.issue.domain.IssuesCountData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssuesCountInformation {

	private int issueOpenCount;
	private int issueCloseCount;
	private int labelCount;
	private int milestoneCount;

	public static IssuesCountInformation from(IssuesCountData issuesCountData) {
		return new IssuesCountInformation(
			issuesCountData.getIssueOpenCount(),
			issuesCountData.getIssueCloseCount(),
			issuesCountData.getLabelCount(),
			issuesCountData.getMilestoneCount()
		);
	}
}
