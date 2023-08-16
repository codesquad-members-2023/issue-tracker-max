package com.issuetrackermax.domain.issue.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueResultVO {
	private Long id;
	private Boolean isOpen;
	private String title;
	private String labelIds;
	private Long writerId;
	private String writer;
	private String writerImageUrl;
	private String assigneeIds;
	private String assigneeNames;
	private Long milestoneId;
	private String milestoneTitle;

	@Builder
	public IssueResultVO(Long id, Boolean isOpen, String title, String labelIds, Long writerId, String writer,
		String writerImageUrl, String assigneeIds, String assigneeNames, Long milestoneId, String milestoneTitle) {
		this.id = id;
		this.isOpen = isOpen;
		this.title = title;
		this.labelIds = labelIds;
		this.writerId = writerId;
		this.writer = writer;
		this.writerImageUrl = writerImageUrl;
		this.assigneeIds = assigneeIds;
		this.assigneeNames = assigneeNames;
		this.milestoneId = milestoneId;
		this.milestoneTitle = milestoneTitle;
	}
}
