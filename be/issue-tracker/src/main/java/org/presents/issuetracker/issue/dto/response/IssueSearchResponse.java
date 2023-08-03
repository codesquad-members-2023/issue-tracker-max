package org.presents.issuetracker.issue.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueSearchResponse {
	private Long labelCount;
	private Long milestoneCount;
	private Long openIssueCount;
	private Long closedIssueCount;
	private List<IssueSearch> issues;

	public static IssueSearchResponse from(List<IssueSearch> issues, Long labelCount, Long milestoneCount,
		String status) {
		System.out.println(status);
		return IssueSearchResponse.builder()
			.labelCount(labelCount)
			.milestoneCount(milestoneCount)
			.openIssueCount(issues.stream()
				.filter(i -> "open".equals(i.getStatus()))
				.count())
			.closedIssueCount(issues.stream()
				.filter(i -> "closed".equals(i.getStatus()))
				.count())
			.issues(issues.stream()
				.filter(i -> i.getStatus().equals(status)).collect(
					Collectors.toUnmodifiableList()))
			.build();
	}
}

