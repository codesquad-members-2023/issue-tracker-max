package com.issuetracker.issue.ui.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.issuetracker.issue.application.dto.IssueDetailInformation;
import com.issuetracker.label.ui.dto.LabelResponse;
import com.issuetracker.member.ui.dto.MemberResponse;
import com.issuetracker.milestone.ui.dto.IssueDetailMilestoneResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueDetailResponse {

	private Long id;
	private String title;
	private String content;
	private boolean isOpen;
	private LocalDateTime createAt;
	private IssueDetailMilestoneResponse milestone;
	private MemberResponse author;
	private List<MemberResponse> assignees;
	private List<LabelResponse> labels;
	private List<CommentResponse> comments;

	public static IssueDetailResponse from(IssueDetailInformation issueDetailInformation) {
		return new IssueDetailResponse(
			issueDetailInformation.getId(),
			issueDetailInformation.getTitle(),
			issueDetailInformation.getContent(),
			issueDetailInformation.getIsOpen(),
			issueDetailInformation.getCreateAt(),
			IssueDetailMilestoneResponse.from(issueDetailInformation.getMilestone()),
			MemberResponse.from(issueDetailInformation.getAuthor()),
			MemberResponse.from(issueDetailInformation.getAssignees()),
			LabelResponse.from(issueDetailInformation.getLabels()),
			CommentResponse.from(issueDetailInformation.getComments())
		);
	}

	public boolean getIsOpen() {
		return isOpen;
	}
}
