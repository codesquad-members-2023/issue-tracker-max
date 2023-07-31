package codesquard.app.comment.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentSaveRequest {

	@NotNull(message = "이슈 아이디는 필수입니다.")
	private final Long issueId;

	@NotNull(message = "사용자 아이디는 필수입니다.")
	private final Long userId;

	@NotEmpty(message = "내용은 필수입니다.")
	private final String content;

	public CommentSaveServiceRequest toServiceRequest() {
		return new CommentSaveServiceRequest(issueId, userId, content);
	}

}
