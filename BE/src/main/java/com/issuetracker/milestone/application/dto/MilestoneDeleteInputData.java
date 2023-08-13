package com.issuetracker.milestone.application.dto;

import com.issuetracker.milestone.domain.Milestone;

import lombok.Getter;

@Getter
public class MilestoneDeleteInputData {

	private Long id;

	public MilestoneDeleteInputData(Long id) {
		this.id = id;
	}

	public Milestone toMilestoneForDelete() {
		return Milestone.builder()
			.id(id)
			.build();
	}
}

