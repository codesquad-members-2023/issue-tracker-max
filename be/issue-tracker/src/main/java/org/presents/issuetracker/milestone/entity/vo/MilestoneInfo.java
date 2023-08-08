package org.presents.issuetracker.milestone.entity.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MilestoneInfo {
	private Long id;
	private String name;
	private LocalDateTime deadline;
	private String description;
	private String status;
	private int openIssueCount;
	private int closedIssueCount;
	private int progress;
}
