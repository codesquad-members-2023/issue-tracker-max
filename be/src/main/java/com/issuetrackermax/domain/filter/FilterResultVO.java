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
	private Long writerId;
	private String writer;
	private String writerImageUrl;
	private String assigneeIds;
	private String assigneeNames;
	private Long milestoneId;
	private String milestoneTitle;

	@Builder
	public FilterResultVO(Long id, Boolean isOpen, String title, String editor, LocalDateTime modifiedAt,
		String labelIds,
		Long writerId, String writer, String writerImageUrl, String assigneeIds, String assigneeNames, Long milestoneId,
		String milestoneTitle) {
		this.id = id;
		this.isOpen = isOpen;
		this.title = title;
		this.editor = editor;
		this.modifiedAt = modifiedAt;
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
