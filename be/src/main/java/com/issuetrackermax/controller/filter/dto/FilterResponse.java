package com.issuetrackermax.controller.filter.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterResponse {
	private Long labelCount;
	private Long mileStoneCount;
	private Long openIssueCount;
	private Long closedIssueCount;
	private List<IssueResponse> issues;

	@Builder
	public FilterResponse(Long labelCount, Long mileStoneCount, Long openIssueCount, Long closedIssueCount,
		List<IssueResponse> issues) {
		this.labelCount = labelCount;
		this.mileStoneCount = mileStoneCount;
		this.openIssueCount = openIssueCount;
		this.closedIssueCount = closedIssueCount;
		this.issues = issues;
	}
}
