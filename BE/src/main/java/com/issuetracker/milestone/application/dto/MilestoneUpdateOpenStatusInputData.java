package com.issuetracker.milestone.application.dto;

import com.issuetracker.milestone.domain.Milestone;

public class MilestoneUpdateOpenStatusInputData {

	private Long id;
	private boolean isOpen;

	public MilestoneUpdateOpenStatusInputData(Long id, boolean isOpen) {
		this.id = id;
		this.isOpen = isOpen;
	}

	public Milestone toMilestoneForUpdateOpenStatus() {
		return Milestone.builder()
			.id(id)
			.isOpen(isOpen)
			.build();
	}
}
