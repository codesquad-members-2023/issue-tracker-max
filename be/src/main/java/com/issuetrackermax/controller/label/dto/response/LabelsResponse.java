package com.issuetrackermax.controller.label.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LabelsResponse {
	private Long milestoneCount;
	private List<LabelDetailResponse> labels;

	@Builder
	public LabelsResponse(Long milestoneCount,
		List<LabelDetailResponse> labels) {
		this.milestoneCount = milestoneCount;
		this.labels = labels;
	}

}
