package com.issuetrackermax.controller.milestone.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneCloseResponse {
	private Long labelCount;
	private Long openMilestoneCount;
	private List<MilestoneDetailResponse> milestones;

	@Builder
	public MilestoneCloseResponse(Long labelCount, Long openMilestoneCount,
		List<MilestoneDetailResponse> milestones) {
		this.labelCount = labelCount;
		this.openMilestoneCount = openMilestoneCount;
		this.milestones = milestones;
	}
}
