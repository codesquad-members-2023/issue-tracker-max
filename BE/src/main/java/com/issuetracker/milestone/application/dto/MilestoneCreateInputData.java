package com.issuetracker.milestone.application.dto;

import java.time.LocalDate;

import com.issuetracker.milestone.domain.Milestone;

import lombok.Getter;

@Getter
public class MilestoneCreateInputData {

	private String title;
	private String description;
	private LocalDate deadline;

	public MilestoneCreateInputData(String title, String description, LocalDate deadline) {
		this.title = title;
		this.description = description;
		this.deadline = deadline;
	}

	public Milestone toMilestoneForCreate() {
		return Milestone.builder()
			.title(title)
			.description(description)
			.deadline(deadline)
			.build();
	}
}

