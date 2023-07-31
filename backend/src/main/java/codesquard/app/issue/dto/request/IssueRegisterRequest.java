package codesquard.app.issue.dto.request;

import java.util.List;

import javax.validation.constraints.Size;

import codesquard.app.issue.entity.Issue;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssueRegisterRequest {

	private Long userId;
	@Size(min = 1, max = 50, message = "제목은 1글자 이상, 50글자 이하여야 합니다.")
	private String title;
	@Size(max = 10000, message = "내용은 10000글자 이하여야 합니다.")
	private String content;
	private Long milestone;
	private List<Long> labels;
	private List<Long> assignees;

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Long getMilestone() {
		return milestone;
	}

	public List<Long> getLabels() {
		return labels;
	}

	public List<Long> getAssignees() {
		return assignees;
	}

	public Issue toEntity(IssueRegisterRequest issueRegisterRequest, Long userId) {
		return Issue.builder()
			.userId(userId)
			.title(issueRegisterRequest.getTitle())
			.content(issueRegisterRequest.getContent())
			.milestoneId(issueRegisterRequest.getMilestone())
			.build();
	}
}
