package org.presents.issuetracker.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.entity.vo.IssueSearchInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueSearch {
	private Long id;
	private String title;
	private String authorLoginId;
	private List<AssigneeSearch> assignees;
	private List<LabelSearch> labels;
	private MilestoneSearch milestone;
	private LocalDateTime createdAt;
	private String status;

	public static IssueSearch from(IssueSearchInfo issue) {
		return IssueSearch.builder()
			.id(issue.getId())
			.title(issue.getTitle())
			.authorLoginId(issue.getAuthorLoginId())
			.assignees(AssigneeSearch.from(issue.getAssignees()))
			.labels(LabelSearch.from(issue.getLabels()))
			.milestone(MilestoneSearch.fromEntity(issue.getMilestone()))
			.createdAt(issue.getCreatedAt())
			.status(issue.getStatus()).build();
	}

	public static List<IssueSearch> from(List<IssueSearchInfo> issues) {
		if (issues == null) {
			return null;
		}
		return issues.stream()
			.map(IssueSearch::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
