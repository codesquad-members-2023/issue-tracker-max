package codesquard.app.comment.service.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentSaveResponse {

	private Long savedCommentId;

	public Long getSavedCommentId() {
		return savedCommentId;
	}

}
