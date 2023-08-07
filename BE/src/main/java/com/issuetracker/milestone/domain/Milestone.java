package com.issuetracker.milestone.domain;

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
	private boolean isOpen;
	private Double progress;

	@Builder
	public Milestone(Long id, String title, String description, LocalDate deadline, boolean isOpen, Double progress) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.isOpen = isOpen;
		this.progress = progress;
	}

	public boolean getIsOpen() {
		return isOpen;
	}
}
