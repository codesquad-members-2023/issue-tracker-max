package codesquard.app.comment.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import codesquard.app.comment.service.request.CommentSaveServiceRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentSaveRequest {

	@NotNull(message = "이슈 아이디는 필수입니다.")
	private Long issueId;

	@NotNull(message = "사용자 아이디는 필수입니다.")
	private Long userId;

	@NotBlank(message = "내용은 필수입니다.")
	private String content;

	public CommentSaveServiceRequest toServiceRequest() {
		return new CommentSaveServiceRequest(issueId, userId, content);
	}

	public Long getIssueId() {
		return issueId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getContent() {
		return content;
	}

}
