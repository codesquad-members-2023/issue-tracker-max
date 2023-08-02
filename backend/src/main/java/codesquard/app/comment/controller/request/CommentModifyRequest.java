package codesquard.app.comment.controller.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.comment.service.request.CommentModifyServiceRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentModifyRequest {

	@JsonProperty("content")
	@NotBlank(message = "내용은 필수입니다.")
	private String content;

	public CommentModifyServiceRequest toServiceRequest(Long id) {
		return new CommentModifyServiceRequest(id, content);
	}

}
