package com.issuetrackermax.controller.milestone.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestonePostResponse {
	private Long id;

	@Builder
	public MilestonePostResponse(Long id) {
		this.id = id;
	}
}
