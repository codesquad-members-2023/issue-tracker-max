package org.presents.issuetracker.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.entity.vo.IssueSearchVo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueSearch {
	private Long id;
	private String title;
	private UserSearch author;
	private List<LabelSearch> labels;
	private MilestoneSearch milestone;
	private LocalDateTime createdAt;
	private String status;

	public static IssueSearch from(IssueSearchVo issue) {
		return IssueSearch.builder()
			.id(issue.getIssueId())
			.title(issue.getTitle())
			.author(UserSearch.fromEntity(issue.getAuthor()))
			.labels(issue.getLabels().stream().map(LabelSearch::fromEntity).collect(Collectors.toUnmodifiableList()))
			.milestone(MilestoneSearch.fromEntity(issue.getMilestone()))
			.createdAt(issue.getCreatedAt())
			.status(issue.getStatus()).build();
	}

	public static List<IssueSearch> from(List<IssueSearchVo> issues) {
		if (issues == null) {
			return null;
		}
		return issues.stream()
			.map(IssueSearch::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
