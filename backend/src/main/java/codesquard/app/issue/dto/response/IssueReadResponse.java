package codesquard.app.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.issue.entity.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class IssueReadResponse {

	@JsonProperty("id")
	private final Long id;
	@JsonProperty("title")
	private final String title;
	@JsonProperty("status")
	private final IssueStatus status;
	@JsonProperty("statusModifiedAt")
	private final LocalDateTime statusModifiedAt;
	@JsonProperty("createdAt")
	private final LocalDateTime createdAt;
	@JsonProperty("modifiedAt")
	private final LocalDateTime modifiedAt;
	@JsonProperty("commentCount")
	private int commentCount;
	@JsonProperty("content")
	private final String content;
	@JsonProperty("assignees")
	private List<IssueUserResponse> assignees;
	@JsonProperty("labels")
	private List<IssueLabelResponse> labels;
	@JsonProperty("milestone")
	private final IssueMilestoneResponse milestone;
	@JsonProperty("writer")
	private final IssueUserResponse writer;
	@JsonProperty("comments")
	private List<IssueCommentsResponse> comments;

	public IssueMilestoneResponse getMilestone() {
		return milestone;
	}

	public IssueReadResponse from(List<IssueUserResponse> assignees, List<IssueLabelResponse> labels,
		IssueMilestoneCountResponse issueMilestoneCountResponse, List<IssueCommentsResponse> issueCommentsResponse) {
		return new IssueReadResponse(this.id, this.title, this.status, this.statusModifiedAt, this.createdAt,
			this.modifiedAt, commentCount, this.content, assignees, labels,
			new IssueMilestoneResponse(this.milestone.getId(), this.milestone.getName(), issueMilestoneCountResponse),
			this.writer, issueCommentsResponse);
	}

	public String getTitle() {
		return title;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public String getContent() {
		return content;
	}

	public Long getMilestoneId() {
		return milestone.getId();
	}
}
