package codesquard.app.comment.service.request;

import java.time.LocalDateTime;

import codesquard.app.comment.entity.Comment;
import codesquard.app.errors.exception.CommentMaxLengthExceededException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentSaveServiceRequest {

	private Long issueId;
	private Long userId;
	private String content;

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
