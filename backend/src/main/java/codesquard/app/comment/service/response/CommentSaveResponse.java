package codesquard.app.comment.service.response;

public class CommentSaveResponse {

	private boolean success;
	private Long commentId;

	public CommentSaveResponse(boolean success, Long commentId) {
		this.success = success;
		this.commentId = commentId;
	}

}
