package com.issuetrackermax.controller.milestone.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestoneOpenResponse {
	private Long labelCount;
	private Long closedMilestoneCount;
	private List<MilestoneDetailResponse> milestones;

	@Builder
	public MilestoneOpenResponse(Long labelCount, Long closedMilestoneCount,
		List<MilestoneDetailResponse> milestones) {
		this.labelCount = labelCount;
		this.closedMilestoneCount = closedMilestoneCount;
		this.milestones = milestones;
	}
}
