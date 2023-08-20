package codesquad.issueTracker.issue.dto;

import java.util.List;

import codesquad.issueTracker.global.common.Status;
import codesquad.issueTracker.issue.domain.IssueSearch;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueFilterRequestDto {

	private Boolean status;
	private List<Long> labels;
	private Long milestone;
	private Long writer;
	private List<Long> assignees;
	private Boolean isComment;

	@Builder
	public IssueFilterRequestDto(Boolean status, List<Long> labels, Long milestone, Long writer, List<Long> assignees,
		Boolean isComment) {
		this.status = status;
		this.labels = labels;
		this.milestone = milestone;
		this.writer = writer;
		this.assignees = assignees;
		this.isComment = isComment;
	}

	public static IssueFilterRequestDto of(String status, List<Long> labels, Long milestone, Long writer,
		List<Long> assignees, Boolean isComment) {
		return IssueFilterRequestDto.builder()
			.status(Status.from(status).getStatus())
			.labels(labels)
			.milestone(milestone)
			.writer(writer)
			.assignees(assignees)
			.isComment(isComment)
			.build();
	}

	public IssueSearch toIssueSearch(Long userId) {
		return IssueSearch.builder()
			.isClosed(this.status)
			.labelIds(this.labels)
			.milestoneId(this.milestone)
			.userId(this.writer)
			.assigneeIds(this.assignees)
			.commentAuthorId(getCommentByAuthorId(userId))
			.build();
	}

	private Long getCommentByAuthorId(Long userId) {
		if (isComment == null || isComment == Boolean.FALSE) {
			return null;
		}
		return userId;
	}
}
