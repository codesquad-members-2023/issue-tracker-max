package com.issuetracker.issue.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.issuetracker.label.domain.Label;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.domain.Milestone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueRead {

	private Long id;
	private String title;
	private String content;
	private Boolean isOpen;
	private LocalDateTime createAt;
	private Member author;
	private Milestone milestone;
	private List<Label> labels;
	private List<Member> assignees;

	@Builder
	public IssueRead(Long id, String title, String content, Boolean isOpen, LocalDateTime createAt, Member author,
		Milestone milestone, List<Label> labels, List<Member> assignees) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
		this.createAt = createAt;
		this.author = author;
		this.milestone = milestone;
		this.labels = labels;
		this.assignees = assignees;
	}
}
