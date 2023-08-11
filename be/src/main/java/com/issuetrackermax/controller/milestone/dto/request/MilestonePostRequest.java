package com.issuetrackermax.controller.milestone.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestonePostRequest {
	private String name;
	private LocalDateTime dueDate;
	private String description;

	@Builder
	public MilestonePostRequest(String name, LocalDateTime dueDate, String description) {
		this.name = name;
		this.dueDate = dueDate;
		this.description = description;
	}
}
