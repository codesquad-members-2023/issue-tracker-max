package codesquard.app.comment.service.request;

import java.time.LocalDateTime;

import codesquard.app.api.errors.exception.CommentMaxLengthExceededException;
import codesquard.app.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentModifyServiceRequest {

	private static final int MAX_LENGTH = 10000;

	private Long id;
	private String content;

	private void validateContentLength(String content) {
		if (content.length() > MAX_LENGTH) {
			throw new CommentMaxLengthExceededException();
		}
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Comment toEntity(LocalDateTime modifiedAt) {
		validateContentLength(this.content);
		return new Comment(id, content, modifiedAt);
	}

}
