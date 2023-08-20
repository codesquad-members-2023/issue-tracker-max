package com.issuetrackermax.domain.milestone.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

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

	public static Milestone fromMilestoneRepository(Long id, String title, String description, Timestamp duedate,
		Boolean isOpen) {
		return Milestone.builder()
			.id(id)
			.title(title)
			.description(description)
			.duedate(setDueDate(duedate))
			.isOpen(isOpen)
			.build();
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
			.title(milestoneModifyRequest.getTitle())
			.description(milestoneModifyRequest.getDescription())
			.duedate(milestoneModifyRequest.getDueDate())
			.build();

	}

	private static LocalDateTime setDueDate(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		} else {
			return LocalDateTime.ofInstant(
				Instant.ofEpochMilli(timestamp.getTime()), TimeZone.getDefault().toZoneId());
		}
	}
}
