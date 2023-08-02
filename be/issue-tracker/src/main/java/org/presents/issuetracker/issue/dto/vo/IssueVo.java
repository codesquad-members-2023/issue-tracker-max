package org.presents.issuetracker.issue.dto.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.user.entity.User;
import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueVo {
	private Long issueId;
	private String title;
	private User author;
	private List<Label> labels;
	private Milestone milestone;
	private LocalDateTime createdAt;
	private String status;

	@Builder
	public IssueVo(Long issueId, String title, User author, List<Label> labels, Milestone milestone,
		LocalDateTime createdAt, String status) {
		Assert.notNull(author, "작성자가 존재하지 않습니다");
		Assert.notNull(labels, "레이블이 존재하지 않습니다");
		Assert.notNull(milestone, "마일스톤이 존재하지 않습니다");

		this.issueId = issueId;
		this.title = title;
		this.author = author;
		this.labels = labels;
		this.milestone = milestone;
		this.createdAt = createdAt;
		this.status = status;
	}
}
