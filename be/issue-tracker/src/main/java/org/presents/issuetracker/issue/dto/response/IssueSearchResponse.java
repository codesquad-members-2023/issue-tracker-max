package org.presents.issuetracker.issue.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IssueSearchResponse {
	private Long openIssueCount;
	private Long closedIssueCount;
	private List<IssueSearch> issues;

	public static IssueSearchResponse from(List<IssueSearch> issues) {
		return IssueSearchResponse.builder()
			.openIssueCount(issues.stream()
				.filter(i -> "open".equals(i.getStatus()))
				.count())
			.closedIssueCount(issues.stream()
				.filter(i -> "closed".equals(i.getStatus()))
				.count())
			.issues(issues)
			.build();
	}
}

