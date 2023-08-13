package com.issuetracker.label.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelCountMetadata {

	private int totalLabelCount;
	private int totalMilestoneCount;

	@Builder
	public LabelCountMetadata(int totalLabelCount, int totalMilestoneCount) {
		this.totalLabelCount = totalLabelCount;
		this.totalMilestoneCount = totalMilestoneCount;
	}
}
