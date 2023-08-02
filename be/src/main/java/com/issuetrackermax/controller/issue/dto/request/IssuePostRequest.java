package com.issuetrackermax.controller.issue.dto.request;

import java.util.List;

import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.issue.entity.Issue;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssuePostRequest {
	private String title;
	private String content;
	private String imageUrl;
	private Long writerId;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;

	@Builder
	public IssuePostRequest(String title, String content, String imageUrl, Long writerId, List<Long> assigneeIds,
		List<Long> labelIds, Long milestoneId) {
		this.title = title;
		this.content = content;
		this.imageUrl = imageUrl;
		this.writerId = writerId;
		this.assigneeIds = assigneeIds;
		this.labelIds = labelIds;
		this.milestoneId = milestoneId;
	}

	public Issue toIssue() {
		return Issue.builder()
			.title(title)
			.isOpen(true)
			.writerId(writerId)
			.milestoneId(milestoneId)
			.build();
	}

	public IssueApplyRequest toLabel() {
		return IssueApplyRequest.builder()
			.ids(labelIds)
			.build();
	}

	public IssueApplyRequest toAssignee() {
		return IssueApplyRequest.builder()
			.ids(assigneeIds)
			.build();
	}

	public Comment toComment() {
		return Comment.builder()
			.content(content)
			.imageUrl(imageUrl)
			.writerId(writerId)
			.build();
	}
}
