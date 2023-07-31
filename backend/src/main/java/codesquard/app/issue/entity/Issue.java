package codesquard.app.issue.entity;

import java.time.LocalDateTime;

public class Issue {

	private Long id;
	private final Long milestoneId;
	private final Long userId;
	private final String title;
	private final String content;
	private Boolean status;
	private Boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public Issue(Long milestoneId, Long userId, String title, String content) {
		this.milestoneId = milestoneId;
		this.userId = userId;
		this.title = title;
		this.content = content;
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
}
