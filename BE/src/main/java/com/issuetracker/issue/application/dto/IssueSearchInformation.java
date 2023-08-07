package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.IssueRead;
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

	public static IssueSearchInformation from(IssueRead issueRead) {
		return new IssueSearchInformation(
			issueRead.getId(),
			issueRead.getTitle(),
			issueRead.getIsOpen(),
			issueRead.getCreateAt(),
			issueRead.getAuthor().getNickname(),
			issueRead.getAuthor().getProfileImageUrl(),
			LabelSearchInformation.from(issueRead.getLabels()),
			MilestoneSearchInformation.from(issueRead.getMilestone())
		);
	}

	public static List<IssueSearchInformation> from(List<IssueRead> IssueReads) {
		return IssueReads.stream()
			.map(IssueSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
