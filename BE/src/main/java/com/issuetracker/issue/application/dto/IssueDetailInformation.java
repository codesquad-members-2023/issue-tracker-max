package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.issuetracker.issue.application.dto.assignee.AssigneeMemberInformation;
import com.issuetracker.issue.application.dto.comment.IssueCommentInformation;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.milestone.application.dto.IssueDetailMilestoneInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueDetailInformation {

	private Long id;
	private String title;
	private String content;
	private boolean isOpen;
	private LocalDateTime createAt;
	private IssueDetailMilestoneInformation milestone;
	private MemberInformation author;
	private List<AssigneeMemberInformation> assignees;
	private List<LabelInformation> labels;
	private List<IssueCommentInformation> comments;

	public static IssueDetailInformation from(IssueDetailRead issueDetailRead) {
		return new IssueDetailInformation(
			issueDetailRead.getId(),
			issueDetailRead.getTitle(),
			issueDetailRead.getContent(),
			issueDetailRead.getIsOpen(),
			issueDetailRead.getCreateAt(),
			IssueDetailMilestoneInformation.from(issueDetailRead.getMilestone()),
			MemberInformation.from(issueDetailRead.getAuthor()),
			AssigneeMemberInformation.from(issueDetailRead.getAssignees()),
			LabelInformation.from(issueDetailRead.getLabels()),
			IssueCommentInformation.from(issueDetailRead.getComments())
		);
	}

	public boolean getIsOpen() {
		return isOpen;
	}
}
