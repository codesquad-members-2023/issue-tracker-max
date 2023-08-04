package com.issuetrackermax.domain.filter;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterResultVO {
	private Long id;
	private Boolean isOpen;
	private String title;
	private String editor;
	private LocalDateTime modifiedAt;
	private String labelIds;
	private String labelTitles;
	private String labelBackgroundColors;
	private String labelTextColors;
	private Long writerId;
	private String writer;
	private String assigneeIds;
	private String assigneeNames;
	private Long milestoneId;
	private String milestoneTitle;

	@Builder
	public FilterResultVO(Long id, Boolean isOpen, String title, String editor, LocalDateTime modifiedAt,
		String labelIds, String labelTitles, String labelBackgroundColors, String labelTextColors, Long writerId,
		String writer, String assigneeIds, String assigneeNames, Long milestoneId, String milestoneTitle) {
		this.id = id;
		this.isOpen = isOpen;
		this.title = title;
		this.editor = editor;
		this.modifiedAt = modifiedAt;
		this.labelIds = labelIds;
		this.labelTitles = labelTitles;
		this.labelBackgroundColors = labelBackgroundColors;
		this.labelTextColors = labelTextColors;
		this.writerId = writerId;
		this.writer = writer;
		this.assigneeIds = assigneeIds;
		this.assigneeNames = assigneeNames;
		this.milestoneId = milestoneId;
		this.milestoneTitle = milestoneTitle;
	}
}
