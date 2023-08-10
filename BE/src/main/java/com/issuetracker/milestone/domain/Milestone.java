package com.issuetracker.milestone.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	private Integer progress;

	@Builder
	public Milestone(Long id, String title, String description, LocalDate deadline, boolean isOpen, Integer progress) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.isOpen = isOpen;
		this.progress = progress;
	}

	@Builder
	public Milestone(Long id, String title, String description, String deadline, boolean isOpen, Integer progress) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = convertFrom(deadline);
		this.isOpen = isOpen;
		this.progress = progress;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	private LocalDate convertFrom(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		return LocalDate.parse(dateString, formatter);
	}
}
