package com.issuetracker.milestone.application.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.issuetracker.milestone.domain.Milestone;

import lombok.Getter;

@Getter
public class MilestoneUpdateInputData {

	private Long id;
	private String title;
	private String description;
	private String deadline;

	public MilestoneUpdateInputData(Long id, String title, String description, String color) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = color;
	}

	public Milestone toMilestoneForUpdate() {
		return Milestone.builder()
			.id(id)
			.title(title)
			.description(description)
			.deadline(convertFrom(deadline))
			.build();
	}

	private LocalDate convertFrom(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dateString, formatter);
	}
}
