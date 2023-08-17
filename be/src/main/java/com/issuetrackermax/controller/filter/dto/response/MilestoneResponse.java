package com.issuetrackermax.controller.filter.dto.response;

import com.issuetrackermax.domain.milestone.entity.Milestone;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneResponse {
	private final Long id;
	private final String title;

	@Builder
	public MilestoneResponse(Long id, String title) {
		this.id = id;
		this.title = title;
	}

	public static MilestoneResponse from(Milestone milestone) {
		return MilestoneResponse.builder().id(milestone.getId()).title(milestone.getTitle()).build();
	}

}
