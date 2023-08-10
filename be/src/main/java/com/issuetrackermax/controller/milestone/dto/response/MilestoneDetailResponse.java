package com.issuetrackermax.controller.milestone.dto.response;

import java.time.LocalDateTime;

import com.issuetrackermax.domain.milestone.entity.Milestone;

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
	public MilestoneDetailResponse(Milestone milestone,
		Long openIssueCount, Long closedIssueCount) {
		this.id = milestone.getId();
		this.name = milestone.getTitle();
		this.description = milestone.getDescription();
		this.dueDate = milestone.getDuedate();
		this.openIssueCount = openIssueCount;
		this.closedIssueCount = closedIssueCount;
	}
}
