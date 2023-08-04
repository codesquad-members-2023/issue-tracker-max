package org.presents.issuetracker.issue.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Issue {
	private Long id;
	private Long authorId;
	private Long milestoneId;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;
}
