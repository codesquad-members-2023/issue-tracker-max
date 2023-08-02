package org.presents.issuetracker.issue.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueLabel {
	private Long issueId;
	private Long labelId;
}
