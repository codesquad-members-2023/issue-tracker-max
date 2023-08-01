package codesquard.app.comment.service.request;

import java.time.LocalDateTime;

import codesquard.app.comment.entity.Comment;
import codesquard.app.errors.exception.CommentMaxLengthExceededException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentSaveServiceRequest {

	private final Long issueId;

	private final Long userId;

	private final String content;

	public Comment toEntity(LocalDateTime createdAt) {
		validateContentLength(this.content);
		return new Comment(issueId, userId, content, createdAt);
	}

	private void validateContentLength(String content) {
		if (content.length() > 10000) {
			throw new CommentMaxLengthExceededException();
		}
	}

}
