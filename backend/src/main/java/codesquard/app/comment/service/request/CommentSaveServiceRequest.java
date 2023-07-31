package codesquard.app.comment.service.request;

import java.time.LocalDateTime;

import codesquard.app.comment.entity.Comment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentSaveServiceRequest {

	private final Long issueId;

	private final Long userId;

	private final String content;

	public Comment toEntity(LocalDateTime createdAt) {
		return new Comment(issueId, userId, content, createdAt);
	}

}
