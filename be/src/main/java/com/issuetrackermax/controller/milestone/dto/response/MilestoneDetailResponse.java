package com.issuetrackermax.controller.milestone.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MilestoneDetailResponse {
	private Long id;
	private String name;
	private String description;
	private LocalDateTime dueDate;
	private Long openIssueCount;
	private Long closedIssueCount;

	@Builder
	public MilestoneDetailResponse(Long id, String name, String description, LocalDateTime dueDate,
		Long openIssueCount, Long closedIssueCount) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.dueDate = dueDate;
		this.openIssueCount = openIssueCount;
		this.closedIssueCount = closedIssueCount;
	}
}
