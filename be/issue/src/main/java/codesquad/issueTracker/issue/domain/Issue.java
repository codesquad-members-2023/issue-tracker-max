package codesquad.issueTracker.issue.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Issue {

	private Long id;
	private Long milestoneId;
	private Long userId;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private Boolean isClosed;

	@Builder
	public Issue(Long id, Long milestoneId, Long userId, String title, String content, LocalDateTime createdAt,
		Boolean isClosed) {
		this.id = id;
		this.milestoneId = milestoneId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.isClosed = isClosed;
	}
}
