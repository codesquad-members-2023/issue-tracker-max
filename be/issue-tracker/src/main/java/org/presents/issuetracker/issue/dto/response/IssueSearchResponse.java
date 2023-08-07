package org.presents.issuetracker.issue.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.entity.vo.IssueSearchCountVo;

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

	public static IssueSearchResponse from(IssueSearchCountVo counts, List<IssueSearch> issues, String status) {
		return IssueSearchResponse.builder()
			.labelCount(counts.getLabelCount())
			.milestoneCount(counts.getMilestoneCount())
			.openIssueCount(counts.getOpenIssueCount())
			.closedIssueCount(counts.getClosedIssueCount())
			.issues(issues.stream()
				.filter(i -> i.getStatus().equals(status)).collect(
					Collectors.toUnmodifiableList()))
			.build();
	}
}

