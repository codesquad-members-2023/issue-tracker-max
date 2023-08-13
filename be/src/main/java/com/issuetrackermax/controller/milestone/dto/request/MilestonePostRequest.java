package com.issuetrackermax.controller.milestone.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestonePostRequest {
	private String title;
	private LocalDateTime dueDate;
	private String description;

	@Builder
	public MilestonePostRequest(String title, LocalDateTime dueDate, String description) {
		this.title = title;
		this.dueDate = dueDate;
		this.description = description;
	}
}
