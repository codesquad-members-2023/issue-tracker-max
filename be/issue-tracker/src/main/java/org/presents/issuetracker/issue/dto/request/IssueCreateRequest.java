package org.presents.issuetracker.issue.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueCreateRequest {
	private String title;
	private String contents;
	private Long authorId;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
}
