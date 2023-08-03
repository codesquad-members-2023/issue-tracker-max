package com.issuetracker.issue.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssuesCountData {

	private int issueOpenCount;
	private int issueCloseCount;
	private int labelCount;
	private int milestoneCount;

	@Builder
	public IssuesCountData(int issueCloseCount, int issueOpenCount, int labelCount, int milestoneCount) {
		this.issueCloseCount = issueCloseCount;
		this.issueOpenCount = issueOpenCount;
		this.labelCount = labelCount;
		this.milestoneCount = milestoneCount;
	}
}
