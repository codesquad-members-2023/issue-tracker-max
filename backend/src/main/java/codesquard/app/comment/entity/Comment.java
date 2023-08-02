package codesquard.app.comment.entity;

import java.time.LocalDateTime;

public class Comment {

	private Long id;
	private Long issueId;
	private Long userId;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private boolean isDeleted;

	public Comment(Long issueId, Long userId, String content, LocalDateTime createdAt) {
		this.issueId = issueId;
		this.userId = userId;
		this.content = content;
		this.createdAt = createdAt;
	}

	public Comment(Long id, String content, LocalDateTime modifiedAt) {
		this.id = id;
		this.content = content;
		this.modifiedAt = modifiedAt;
	}

	public Long getId() {
		return id;
	}

	public Long getIssueId() {
		return issueId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

}
