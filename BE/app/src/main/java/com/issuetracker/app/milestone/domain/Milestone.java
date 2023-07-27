package com.issuetracker.app.milestone.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Milestone {

	private Long id;
	private String title;
	private String description;
	private LocalDate deadline;
	private Boolean isOpen;

	@Builder(builderClassName = "MilestoneBuilder", access = AccessLevel.PRIVATE)
	private Milestone(Long id, String title, String description, LocalDate deadline, Boolean isOpen) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.isOpen = isOpen;
	}

	@Builder(builderClassName = "MilestoneRequiredBuilder", access = AccessLevel.PRIVATE)
	public static MilestoneBuilder milestoneBuilder(String title, LocalDate deadline, Boolean isOpen) {
		return new MilestoneBuilder()
			.title(title)
			.deadline(deadline)
			.isOpen(isOpen);
	}
}
