package org.presents.issuetracker.milestone.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Milestone {
	private Long milestoneId;
	private String name;
	private LocalDateTime deadline;
	private String description;
	private String status;
	private int openIssueCount;
	private int closedIssueCount;
	private int progress;
}
