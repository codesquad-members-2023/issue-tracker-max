package com.issuetracker.milestone.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneCountMetadata {

	private int totalLabelCount;
	private int totalMilestoneCount;
	private int openMilestoneCount;
	private int closeMilestoneCount;

	@Builder
	public MilestoneCountMetadata(int totalLabelCount, int totalMilestoneCount, int openMilestoneCount,
		int closeMilestoneCount) {
		this.totalLabelCount = totalLabelCount;
		this.totalMilestoneCount = totalMilestoneCount;
		this.openMilestoneCount = openMilestoneCount;
		this.closeMilestoneCount = closeMilestoneCount;
	}
}
