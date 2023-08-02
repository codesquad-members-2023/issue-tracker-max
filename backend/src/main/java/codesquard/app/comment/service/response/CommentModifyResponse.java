package codesquard.app.comment.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentModifyResponse {

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("commentId")
	private Long commentId;

}
