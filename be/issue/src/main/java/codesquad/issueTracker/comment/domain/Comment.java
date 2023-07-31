package codesquad.issueTracker.comment.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

	private Long id;
	private Long userId;
	private Long issueId;
	private String content;
	private LocalDateTime createdAt;

	@Builder

	public Comment(Long id, Long userId, Long issueId, String content, LocalDateTime createdAt) {
		this.id = id;
		this.userId = userId;
		this.issueId = issueId;
		this.content = content;
		this.createdAt = createdAt;
	}
}
