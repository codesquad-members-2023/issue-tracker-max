package com.issuetracker.app.milestone.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Milestone {

	private Long id;
	private String title;
	private String description;
	private LocalDate deadline;
	private Boolean isOpen;

	@Builder(builderClassName = "MilestoneBuilder")
	private Milestone(Long id, String title, String description, LocalDate deadline, Boolean isOpen) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.isOpen = isOpen;
	}

	public static Milestone createInstanceById(Long id) {
		return Milestone.builder()
			.id(id)
			.build();
	}
}
