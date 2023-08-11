package com.issuetrackermax.controller.filter.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterResponse {
	private Long labelCount;
	private Long milestoneCount;
	private Long openIssueCount;
	private Long closedIssueCount;
	private List<IssueResponse> issues;

	@Builder
	public FilterResponse(Long labelCount, Long milestoneCount, Long openIssueCount, Long closedIssueCount,
		List<IssueResponse> issues) {
		this.labelCount = labelCount;
		this.milestoneCount = milestoneCount;
		this.openIssueCount = openIssueCount;
		this.closedIssueCount = closedIssueCount;
		this.issues = issues;
	}
}
