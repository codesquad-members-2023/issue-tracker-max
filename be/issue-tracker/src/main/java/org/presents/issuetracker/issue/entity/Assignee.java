package org.presents.issuetracker.issue.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Assignee {
	private Long issueId;
	private Long userId;
}
