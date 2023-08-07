package org.presents.issuetracker.issue.entity.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueSearchInfo {
	private Long id;
	private String title;
	private User author;
	private List<Label> labels;
	private Milestone milestone;
	private LocalDateTime createdAt;
	private String status;

	@Builder
	public IssueSearchInfo(Long id, String title, User author, List<Label> labels, Milestone milestone,
		LocalDateTime createdAt, String status) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.labels = labels;
		this.milestone = milestone;
		this.createdAt = createdAt;
		this.status = status;
	}
}
