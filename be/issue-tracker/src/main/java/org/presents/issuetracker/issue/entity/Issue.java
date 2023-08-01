package org.presents.issuetracker.issue.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Issue {
	private Long issueId;
	private Long authorId;
	private Long milestoneId;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;
}
