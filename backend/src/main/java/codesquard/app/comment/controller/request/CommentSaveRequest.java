package codesquard.app.comment.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentSaveRequest {

	@JsonProperty("issueId")
	@NotNull(message = "이슈 아이디는 필수입니다.")
	private Long issueId;

	@JsonProperty("content")
	@NotBlank(message = "내용은 필수입니다.")
	private String content;

	public CommentSaveServiceRequest toServiceRequest(Long userId) {
		return new CommentSaveServiceRequest(issueId, userId, content);
	}

}
