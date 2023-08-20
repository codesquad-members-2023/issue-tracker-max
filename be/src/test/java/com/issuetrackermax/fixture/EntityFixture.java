package com.issuetrackermax.fixture;

import com.issuetrackermax.domain.assignee.entity.Assignee;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.issue.entity.Issue;
import com.issuetrackermax.domain.issue.entity.IssueWithLabel;
import com.issuetrackermax.domain.label.entity.Label;
import com.issuetrackermax.domain.member.entity.LoginType;
import com.issuetrackermax.domain.member.entity.Member;
import com.issuetrackermax.domain.milestone.entity.Milestone;

public class EntityFixture {
	public static Milestone makeMilestone(Boolean isOpen, String title, String description) {
		return Milestone.builder().isOpen(isOpen).title(title).description(description).build();
	}

	public static Label makeLabel(String title, String description, String textColor, String backgroundColor) {
		return Label.builder()
			.title(title)
			.description(description)
			.textColor(textColor)
			.backgroundColor(backgroundColor)
			.build();
	}

	public static Issue makeIssue(Boolean isOpen, String title, Long milestoneId, Long writerId) {
		return Issue.builder().isOpen(isOpen).title(title).milestoneId(milestoneId).writerId(writerId).build();
	}

	public static Assignee makeAssignee(Long issueId, Long memberId) {
		return Assignee.builder().issueId(issueId).memberId(memberId).build();
	}

	public static IssueWithLabel makeIssueWithLabel(Long labelId, Long issueId) {
		return IssueWithLabel.builder().issueId(issueId).labelId(labelId).build();
	}

	public static Member makeMember(String loginId, String password, String nickName,
		LoginType loginType) {
		return new Member(loginId, password, nickName, loginType);
	}

	public static Comment makeComment(String content, Long issueId, Long writerId) {
		return Comment.builder().content(content).issueId(issueId).writerId(writerId).build();
	}
}
