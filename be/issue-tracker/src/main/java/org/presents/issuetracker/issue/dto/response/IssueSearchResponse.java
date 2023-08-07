package org.presents.issuetracker.issue.dto.response;

import java.util.List;

import org.presents.issuetracker.issue.entity.vo.IssueSearchCountInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueSearchResponse {
	private Long labelCount;
	private Long milestoneCount;
	private Long openIssueCount;
	private Long closedIssueCount;
	private List<IssueSearch> issues;

	public static IssueSearchResponse of(IssueSearchCountInfo counts, List<IssueSearch> issues) {
		return IssueSearchResponse.builder()
			.labelCount(counts.getLabelCount())
			.milestoneCount(counts.getMilestoneCount())
			.openIssueCount(counts.getOpenIssueCount())
			.closedIssueCount(counts.getClosedIssueCount())
			.issues(issues)
			.build();
	}
}

