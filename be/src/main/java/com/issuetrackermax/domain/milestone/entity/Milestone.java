package com.issuetrackermax.domain.milestone.entity;

import java.time.LocalDateTime;

import com.issuetrackermax.controller.milestone.dto.request.MilestoneModifyRequest;
import com.issuetrackermax.controller.milestone.dto.request.MilestonePostRequest;

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

	public static Milestone from(MilestonePostRequest milestonePostRequest) {
		return Milestone
			.builder()
			.title(milestonePostRequest.getTitle())
			.description(milestonePostRequest.getDescription())
			.duedate(milestonePostRequest.getDueDate())
			.isOpen(true)
			.build();
	}

	public static Milestone from(MilestoneModifyRequest milestoneModifyRequest) {
		return Milestone
			.builder()
			.title(milestoneModifyRequest.getName())
			.description(milestoneModifyRequest.getDescription())
			.duedate(milestoneModifyRequest.getDueDate())
			.build();

	}
}
