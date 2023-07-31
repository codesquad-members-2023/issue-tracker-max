package com.issuetracker.issue.ui.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.label.ui.dto.LabelSearchResponse;
import com.issuetracker.milestone.ui.dto.MilestoneSearchResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueSearchResponse {

	private Long id;
	private String title;
	private boolean isOpen;
	private LocalDateTime createAt;
	private String author;
	private String authorProfileUrl;
	private List<LabelSearchResponse> labels;
	private MilestoneSearchResponse milestone;

	public static IssueSearchResponse from(IssueSearchInformation issueSearchInformation) {
		return new IssueSearchResponse(
			issueSearchInformation.getId(),
			issueSearchInformation.getTitle(),
			issueSearchInformation.getIsOpen(),
			issueSearchInformation.getCreateAt(),
			issueSearchInformation.getAuthor(),
			issueSearchInformation.getAuthorProfileUrl(),
			LabelSearchResponse.from(issueSearchInformation.getLabels()),
			MilestoneSearchResponse.from(issueSearchInformation.getMilestone())
		);
	}

	public static List<IssueSearchResponse> from(List<IssueSearchInformation> issueSearchInformations) {
		return issueSearchInformations.stream()
			.map(IssueSearchResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
