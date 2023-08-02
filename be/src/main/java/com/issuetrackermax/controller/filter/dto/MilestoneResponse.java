package com.issuetrackermax.controller.filter.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneResponse {
	private Long id;
	private String title;

	@Builder
	public MilestoneResponse(Long id, String title) {
		this.id = id;
		this.title = title;
	}
}
