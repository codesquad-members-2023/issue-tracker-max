package codesquard.app.issue.entity;

import java.time.LocalDateTime;

public class Issue {

	private Long id;
	private Long milestoneId;
	private Long userId;
	private String title;
	private String content;
	private IssueStatus status;
	private Boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public Issue(Long milestoneId, Long userId, String title, String content, IssueStatus status, LocalDateTime createdAt) {
		this.milestoneId = milestoneId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.status = status;
		this.createdAt = createdAt;
	}

	public Long getMilestoneId() {
		return milestoneId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

}
