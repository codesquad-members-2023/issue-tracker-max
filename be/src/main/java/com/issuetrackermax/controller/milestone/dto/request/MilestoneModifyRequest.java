package com.issuetrackermax.controller.milestone.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestoneModifyRequest {
	private String name;
	private String description;
	private LocalDateTime dueDate;

	@Builder
	public MilestoneModifyRequest(String name, String description, LocalDateTime dueDate) {
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
	}
}
