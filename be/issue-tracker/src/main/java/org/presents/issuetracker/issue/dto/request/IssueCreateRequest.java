package org.presents.issuetracker.issue.dto.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreateRequest {
	@NotEmpty
	private String title;
	private String contents;
	private Long authorId;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
}
