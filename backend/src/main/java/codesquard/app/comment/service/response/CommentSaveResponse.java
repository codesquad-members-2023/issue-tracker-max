package codesquard.app.comment.service.response;

public class CommentSaveResponse {

	private final boolean success;
	private final Long commentId;

	public CommentSaveResponse(boolean success, Long commentId) {
		this.success = success;
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
	}

}
