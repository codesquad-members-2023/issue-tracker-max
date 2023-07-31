package codesquard.app.issue.dto.request;

import java.util.List;

import javax.validation.constraints.Size;

import codesquard.app.issue.entity.Issue;

public class IssueRegisterRequest {

	@Size(min = 1, max = 50, message = "제목은 1글자 이상, 50글자 이하여야 합니다.")
	private String title;
	@Size(max = 10000, message = "내용은 10000글자 이하여야 합니다.")
	private String content;
	private Long milestone;
	private List<Long> labels;
	private List<Long> assignees;

	public IssueRegisterRequest() {
	}

	public IssueRegisterRequest(String title, String content, Long milestone, List<Long> labels,
		List<Long> assignees) {
		this.title = title;
		this.content = content;
		this.milestone = milestone;
		this.labels = labels;
		this.assignees = assignees;
	}

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

	public Issue toEntity(Long userId) {
		return new Issue(milestone, userId, title, content);
	}
}
