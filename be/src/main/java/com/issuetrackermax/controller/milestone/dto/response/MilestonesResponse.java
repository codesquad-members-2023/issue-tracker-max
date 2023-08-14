package com.issuetrackermax.controller.milestone.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestonesResponse {
	private Long labelCount;
	private Long oppositeCount;
	private List<MilestoneDetailResponse> milestones;

	@Builder
	public MilestonesResponse(Long labelCount, Long oppositeCount,
		List<MilestoneDetailResponse> milestones) {
		this.labelCount = labelCount;
		this.oppositeCount = oppositeCount;
		this.milestones = milestones;
	}
}
