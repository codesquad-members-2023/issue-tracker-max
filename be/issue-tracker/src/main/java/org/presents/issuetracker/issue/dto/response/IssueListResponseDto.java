package org.presents.issuetracker.issue.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IssueListResponseDto {
	private Long openIssueCount;
	private Long closedIssueCount;
	private List<IssueResponseDto> issues;
}

