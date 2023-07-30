package com.issuetracker.app.issue.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.issuetracker.app.label.domain.Label;
import com.issuetracker.app.member.domain.Member;
import com.issuetracker.app.milestone.domain.Milestone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Issue {

	private Long id;
	private String title;
	private String content;
	private Boolean isOpen;
	private LocalDateTime createAt;
	private Member author;
	private Milestone milestone;
	private List<Label> labels;

	@Builder(builderClassName = "IssueBuilder")
	private Issue(Long id, String title, String content, Boolean isOpen, LocalDateTime createAt, Member author,
		Milestone milestone, List<Label> labels) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.isOpen = isOpen;
		this.createAt = createAt;
		this.author = author;
		this.milestone = milestone;
		this.labels = labels;
	}
}
