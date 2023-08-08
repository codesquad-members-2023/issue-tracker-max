package codesquard.app.comment.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentModifyResponse {

	@JsonProperty("modifiedCommentId")
	private Long modifiedCommentId;

}
