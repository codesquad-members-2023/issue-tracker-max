package org.presents.issuetracker.issue.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreateRequest {
	private String title;
	private String contents;
	private Long authorId;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
}
