package org.presents.issuetracker.issue.entity.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueSearchCountInfo {
	private Long labelCount;
	private Long milestoneCount;
	private Long openIssueCount;
	private Long closedIssueCount;

	@Builder
	public IssueSearchCountInfo(Long labelCount, Long milestoneCount, Long openIssueCount, Long closedIssueCount) {
		this.labelCount = labelCount;
		this.milestoneCount = milestoneCount;
		this.openIssueCount = openIssueCount;
		this.closedIssueCount = closedIssueCount;
	}
}
