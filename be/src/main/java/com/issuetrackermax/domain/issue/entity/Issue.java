package com.issuetrackermax.domain.issue.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Issue {
	private Long id;
	private String title;
	private Boolean isOpen;
	private Long writerId;
	private Long milestoneId;
	private LocalDateTime createdAt;

	@Builder
	public Issue(Long id, String title, Boolean isOpen, Long writerId, Long milestoneId, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.isOpen = isOpen;
		this.writerId = writerId;
		this.milestoneId = milestoneId;
		this.createdAt = createdAt;
	}
}
