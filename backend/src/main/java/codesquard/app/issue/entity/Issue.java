package codesquard.app.issue.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class Issue {

	private Long id;
	private final Long milestoneId;
	private final Long userId;
	private final String title;
	private final String content;
	private IssueStatus status;
	private Boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

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
}

