package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.IssueRead;
import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.member.ui.dto.MemberResponse;
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
	private List<LabelInformation> labels;
	private MilestoneSearchInformation milestone;
	private List<MemberInformation> assignees;

	public static IssueSearchInformation from(IssueRead issueRead) {
		return new IssueSearchInformation(
			issueRead.getId(),
			issueRead.getTitle(),
			issueRead.getIsOpen(),
			issueRead.getCreateAt(),
			issueRead.getAuthor().getNickname(),
			issueRead.getAuthor().getProfileImageUrl(),
			LabelInformation.from(issueRead.getLabels()),
			MilestoneSearchInformation.from(issueRead.getMilestone()),
			MemberInformation.from(issueRead.getAssignees())
		);
	}

	public static List<IssueSearchInformation> from(List<IssueRead> IssueReads) {
		return IssueReads.stream()
			.map(IssueSearchInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
