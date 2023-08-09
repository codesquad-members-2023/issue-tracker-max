package com.issuetracker.issue.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.issuetracker.issue.domain.comment.IssueCommentRead;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.domain.Milestone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueDetailRead {

	private Long id;
	private String title;
	private String content;
	private boolean isOpen;
	private LocalDateTime createAt;
	private Member author;
	private Milestone milestone;
	private List<Member> assignees;
	private List<IssueCommentRead> comments;
	private List<Label> labels;

	@Builder
	private IssueDetailRead(Long id, String title, String content, boolean isOpen, LocalDateTime createAt, Member author,
		Milestone milestone, List<Member> assignees, List<Label> labels, List<IssueCommentRead> comments) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
		this.createAt = createAt;
		this.author = author;
		this.milestone = milestone;
		this.assignees = assignees;
		this.labels = labels;
		this.comments = comments;
	}

	public boolean getIsOpen() {
		return isOpen;
	}
}
