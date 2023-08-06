package com.issuetrackermax.domain.milestone.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Milestone {
	private Long id;
	private String title;
	private String description;
	private LocalDateTime duedate;
	private Boolean isOpen;

	@Builder
	public Milestone(Long id, String title, String description, LocalDateTime duedate, Boolean isOpen) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.duedate = duedate;
		this.isOpen = isOpen;
	}
}
