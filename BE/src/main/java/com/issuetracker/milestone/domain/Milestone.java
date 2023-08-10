package com.issuetracker.milestone.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Milestone {

	private Long id;
	private String title;
	private String description;
	private LocalDate deadline;
	private boolean isOpen;
	private Integer progress;
	private Integer openIssueCount;
	private Integer closeIssueCount;

	@Builder
	public Milestone(Long id, String title, String description, LocalDate deadline, boolean isOpen, Integer progress,
		Integer openIssueCount, Integer closeIssueCount) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.isOpen = isOpen;
		this.progress = progress;
		this.openIssueCount = openIssueCount;
		this.closeIssueCount = closeIssueCount;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

}
