package com.issuetracker.milestone.application.dto;

import com.issuetracker.milestone.domain.Milestone;

public class MilestoneSearchByOpenStatusInputData {

	private boolean isOpen;

	public MilestoneSearchByOpenStatusInputData(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Milestone toMilestoneForSearchByOpenStatus() {
		return Milestone.builder()
			.isOpen(isOpen)
			.build();
	}
}

