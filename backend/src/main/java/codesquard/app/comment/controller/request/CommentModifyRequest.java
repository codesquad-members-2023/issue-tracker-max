package codesquard.app.comment.controller.request;

import javax.validation.constraints.NotBlank;

import codesquard.app.comment.service.request.CommentModifyServiceRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommentModifyRequest {

	@NotBlank(message = "내용은 필수입니다.")
	private String content;

	public CommentModifyServiceRequest toServiceRequest(Long id) {
		return new CommentModifyServiceRequest(id, content);
	}

	public String getContent() {
		return content;
	}

}
