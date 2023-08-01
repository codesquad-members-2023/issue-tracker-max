package codesquard.app.comment.service.response;

public class CommentModifyResponse {

	private final boolean success;
	private final Long commentId;

	public CommentModifyResponse(boolean success, Long commentId) {
		this.success = success;
		this.commentId = commentId;
	}

}
