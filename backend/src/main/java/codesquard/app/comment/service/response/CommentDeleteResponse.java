package codesquard.app.comment.service.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentDeleteResponse {

	private boolean success;

	public boolean isSuccess() {
		return success;
	}

}
