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
	private String writer;
	private String assigneeIds;
	private String assigneeNames;
	private String milestoneTitle;

	@Builder
	public FilterResultVO(Long id, Boolean isOpen, String title, String editor, LocalDateTime modifiedAt,
		String labelIds,
		String labelTitles, String writer, String assigneeIds, String assigneeNames, String milestoneTitle) {
		this.id = id;
		this.isOpen = isOpen;
		this.title = title;
		this.editor = editor;
		this.modifiedAt = modifiedAt;
		this.labelIds = labelIds;
		this.labelTitles = labelTitles;
		this.writer = writer;
		this.assigneeIds = assigneeIds;
		this.assigneeNames = assigneeNames;
		this.milestoneTitle = milestoneTitle;
	}
}
