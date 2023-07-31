package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.label.application.dto.LabelSearchInformation;
import com.issuetracker.milestone.application.dto.MilestoneSearchInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueSearchInformation {

	private Long id;
	private String title;
	private Boolean isOpen;
	private LocalDateTime createAt;
	private String author;
	private String authorProfileUrl;
	private List<LabelSearchInformation> labels;
	private MilestoneSearchInformation milestone;

	public static IssueSearchInformation from(Issue issue) {
		return new IssueSearchInformation(
			issue.getId(),
			issue.getTitle(),
			issue.getIsOpen(),
			issue.getCreateAt(),
			issue.getAuthor().getNickname(),
			issue.getAuthor().getProFileImageUrl(),
			LabelSearchInformation.from(issue.getLabels()),
			MilestoneSearchInformation.from(issue.getMilestone())
		);
	}

	public static List<IssueSearchInformation> from(List<Issue> issues) {
		return issues.stream()
			.map(IssueSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
