package codesquard.app.comment.service.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentDeleteResponse {

	private Long deletedCommentId;

	public Long getDeletedCommentId() {
		return deletedCommentId;
	}

}
