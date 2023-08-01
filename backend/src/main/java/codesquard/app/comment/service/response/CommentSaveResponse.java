package codesquard.app.comment.service.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentSaveResponse {

	private boolean success;
	private Long commentId;

	public Long getCommentId() {
		return commentId;
	}

	public boolean isSuccess() {
		return success;
	}

}
