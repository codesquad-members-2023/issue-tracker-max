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
	private String labelTitles;
	private String labelTextColors;
	private String labelBackgroundColors;
	private Long writerId;
	private String writer;
	private String assigneeIds;
	private String assigneeNames;
	private Long milestoneId;
	private String milestoneTitle;

	@Builder
	public IssueResultVO(Long id, Boolean isOpen, String title, String labelIds, String labelTitles,
		String labelTextColors, String labelBackgroundColors, Long writerId, String writer, String assigneeIds,
		String assigneeNames, Long milestoneId, String milestoneTitle) {
		this.id = id;
		this.isOpen = isOpen;
		this.title = title;
		this.labelIds = labelIds;
		this.labelTitles = labelTitles;
		this.labelTextColors = labelTextColors;
		this.labelBackgroundColors = labelBackgroundColors;
		this.writerId = writerId;
		this.writer = writer;
		this.assigneeIds = assigneeIds;
		this.assigneeNames = assigneeNames;
		this.milestoneId = milestoneId;
		this.milestoneTitle = milestoneTitle;
	}
}
