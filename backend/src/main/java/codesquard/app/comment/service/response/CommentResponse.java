package codesquard.app.comment.service.response;

import java.time.LocalDateTime;

import lombok.Builder;

public class CommentResponse {

	private Long id;
	private Long issueId;
	private Long userId;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	@Builder
	public CommentResponse(Long id, Long issueId, Long userId, String content, LocalDateTime createdAt,
		LocalDateTime modifiedAt) {
		this.id = id;
		this.issueId = issueId;
		this.userId = userId;
		this.content = content;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static CommentResponse toSaveResponse(Long savedCommentId) {
		return CommentResponse.builder()
			.id(savedCommentId)
			.build();
	}

}
